
<template>
  <div>
    <div class="charts-container">
      <div class="chart-container">
        <h2 class="chart-title">不同Error数量比较</h2>
        <div class="chart" ref="errorNum"></div>
        <el-button type="primary" @click="loadErrData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载Error数据
        </el-button>
      </div>
      <div class="chart-container">
        <h2 class="chart-title">不同Error浏览量比较</h2>
        <div class="chart" ref="errViews"></div>
        <el-button type="primary" @click="loadErrViewData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载Error浏览量数据
        </el-button>
      </div>
      <div class="chart-container">
        <h2 class="chart-title">Exception数量统计</h2>
        <div class="chart" ref="exceptionNum"></div>
        <el-button type="primary" @click="loadExceptionNumData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载Exception数据
        </el-button>
      </div>
      <div class="chart-container">
        <h2 class="chart-title">Fatal Error数量统计</h2>
        <div class="chart" ref="fatalErr"></div>
        <el-button type="primary" @click="loadFatalErr" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载fatal error数据
        </el-button>
      </div>
      <div class="chart-container">
        <h2 class="chart-title">Exception浏览量统计</h2>
        <div class="chart" ref="exceptionView"></div>
        <el-button type="primary" @click="loadExceptionView" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载exception view数据
        </el-button>
      </div>
      <div class="chart-container">
        <h2 class="chart-title">Fatal Error浏览量统计</h2>
        <div class="chart" ref="fatalErrView"></div>
        <el-button type="primary" @click="loadFatalErrView" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
          加载fatal error view数据
        </el-button>
      </div>
    </div>
  </div>
</template>
  
<script>
  import * as echarts from 'echarts';
  export default {
    mounted(){
      this.loadErrData();
      this.loadErrViewData();
      this.loadExceptionNumData();
      this.loadFatalErr();
      this.loadExceptionView();
      this.loadFatalErrView();
    },
    data() {
      return {
        errorData: {
          data: [],
        },
        errViewData: {
          data: [],
        },
        exceptionNumData: {
          data: [],
        },
        fatalErrData: {
          data: [],
        },
        exceptionViewData: {
          data: [],
        },
        fatalErrViewData: {
          data: [],
        },
      }
    },
    methods: {
      async loadErrData(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getDifferentErrorNumber');
        const dataList = response.data.data;
        this.errorData.categories = [];
        this.errorData.data = [];
        for(let i = 0; i < dataList.length; i++){
          this.errorData.categories.push(dataList[i].content);
          this.errorData.data.push(dataList[i].num);
        }
        this.initBarChart(this.$refs.errorNum, this.errorData);
      },

      async loadErrViewData(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getDifferentErrorViewCount');
        const dataList = response.data.data;
        this.errViewData.categories = [];
        this.errViewData.data = [];
        for(let i = 0; i < dataList.length; i++){
          this.errViewData.categories.push(dataList[i].content);
          this.errViewData.data.push(dataList[i].num);
        }
        this.initBarChart(this.$refs.errViews, this.errViewData);
      },

      async loadExceptionNumData(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getExceptionNumber');
        const dataList = response.data.data;
        this.exceptionNumData.data = [];
        for (let i = 0; i < dataList.length; i++) {
          this.exceptionNumData.data.push({value: dataList[i].num, name: dataList[i].content});
        }
        this.initPieChart(this.$refs.exceptionNum, this.exceptionNumData);
      },

      async loadFatalErr(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getFatalErrorNumber');
        const dataList = response.data.data;
        this.fatalErrData.data = [];
        for (let i = 0; i < dataList.length; i++) {
          this.fatalErrData.data.push({value: dataList[i].num, name: dataList[i].content});
        }
        this.initPieChart(this.$refs.fatalErr, this.fatalErrData);
      },

      async loadExceptionView(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getExceptionViewCount');
        const dataList = response.data.data;
        this.exceptionViewData.data = [];
        for (let i = 0; i < dataList.length; i++) {
          this.exceptionViewData.data.push({value: dataList[i].num, name: dataList[i].content});
        }
        this.initPieChart(this.$refs.exceptionView, this.exceptionViewData);
      },

      async loadFatalErrView(){
        const response = await this.$axios.get('http://localhost:8081/Threads/getFatalErrorViewCount');
        const dataList = response.data.data;
        this.fatalErrViewData.data = [];
        for (let i = 0; i < dataList.length; i++) {
          this.fatalErrViewData.data.push({value: dataList[i].num, name: dataList[i].content});
        }
        this.initPieChart(this.$refs.fatalErrView, this.fatalErrViewData);
      },

      initPieChart(container, data) {
        const chart = echarts.init(container);
        const option = {
          title: {
            text: '',
            left: 'center',
            textStyle: {
              color: '#333',
            },
          },
          label: {
              show: true,
              padding: [0, -60],
              overflow: 'none',
              fontSize: 15,
              fontWeight: 'bold',
              formatter: '{b}: {d}%\n\n{c}', // Use {b} to display the data name
          },
          labelLine: {
              show: true,
              length: 15,
              length2: 60
          },
          series: [
            {
              name: 'Value',
              type: 'pie',
              radius: '50%',
              data: data.data,
            },
          ],
        };
        chart.setOption(option);
      },
      initBarChart(container, data) {
        const chart = echarts.init(container);
        const option = {
          title: {
            text: '',
            textStyle: {
              color: '#333',
            },
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true,
          },
          xAxis: {
            type: 'category',
            data: data.categories,
            axisLine: {
              lineStyle: {
                color: '#999',
              },
            },
            axisLabel: {
              interval: 0, // Display all labels
              rotate: 45, // Rotate the labels for better visibility
            },
          },
          yAxis: {
            type: 'value',
            axisLine: {
              lineStyle: {
                color: '#999',
              },
            },
          },
          series: [
            {
              name: 'Value',
              type: 'bar',
              data: data.data,
              label: {
                show: true,
                position: 'top', // Display label on top of each bar
              },
            },
          ],
          tooltip: {
            show: true,
            trigger: 'axis',
            axisPointer: {
              type: 'shadow',
            },
          },
        };
        chart.setOption(option);
      },
    }
  }
</script>
  
<style scoped>
.charts-container {
  display: flex;
  flex-direction: column; /* 确保子元素纵向排列 */
  align-items: center; /* 居中对齐 */
  margin: 20px;
}

.chart-container {
  text-align: center;
  margin-bottom: 20px; /* 为每个 chart-container 添加底边距 */
}

.chart-title {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}

.chart {
  width: 1000px; /* Increase the width of each chart */
  height: 700px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  background: #fff;
}
</style>