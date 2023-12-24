<template>
  <div class="charts-container">
    <div>
      <p class="data-line">最热门话题：{{mostName}}</p>
      <p class="data-line">浏览量: {{ mostNum }}</p>
    </div>
    <div class="chart-container">
      <h2 class="chart-title">按标签数量排序</h2>
      <div class="chart" ref="tags"></div>
      <el-button type="primary" @click="loadTagData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
        加载标签数据
      </el-button>
    </div>
    <div class="chart-container">
      <h2 class="chart-title">按总浏览量排序</h2>
      <div class="chart" ref="views"></div>
      <el-button type="primary" @click="loadViewData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
        加载浏览数据
      </el-button>
    </div>
    <div class="chart-container">
      <h2 class="chart-title">按每日浏览量排序</h2>
      <div class="chart" ref="dviews"></div>
      <el-button type="primary" @click="loadViewData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
        加载每日浏览数据
      </el-button>
    </div>
    <div class="chart-container">
      <h2 class="chart-title">按回答率排序</h2>
      <div class="chart" ref="anRate"></div>
      <el-button type="primary" @click="loadAnRateData" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
        加载回答率数据
      </el-button>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return {
      mostName: '',
      mostNum: 0,
      // Sample data for demonstration
      tagData: {
        categories: [],
        data: [],
      },
      viewData: {
        categories: [],
        data: [],
      },
      dviewData: {
        categories: [],
        data: [],
      },
      anRateData: {
        categories: [],
        data: [],
      },
    };
  },
  mounted() {
    this.loadMostData();
    this.loadTagData();
    this.initBarChart(this.$refs.tags, this.tagData);
    this.loadViewData();
    this.initBarChart(this.$refs.views, this.viewData);
    this.loadDviewsData();
    this.initBarChart(this.$refs.dviews, this.dviewData);
    this.loadAnRateData();
    this.initBarChart(this.$refs.anRate, this.anRateData);
  },
  methods: {
    async loadTagData() {
      const response = await this.$axios.get('http://localhost:8081/Threads/checkInterestingTags');
      const dataList = response.data.data;
      this.tagData.categories = [];
      this.tagData.data = [];
      for (let i = 0; i < dataList.length; i++) {
        this.tagData.categories.push(dataList[i].content);
        this.tagData.data.push(dataList[i].num);
      }
      this.initBarChart(this.$refs.tags, this.tagData);
    },

    async loadViewData() {
      const response = await this.$axios.get('http://localhost:8081/Threads/checkInterestingDataViewCount');
      const dataList = response.data.data;
      this.viewData.categories = [];
      this.viewData.data = [];
      for (let i = 0; i < dataList.length; i++) {
        this.viewData.categories.push(dataList[i].content);
        this.viewData.data.push(dataList[i].num);
      }
      this.initBarChart(this.$refs.views, this.viewData);
    },
    async loadDviewsData(){
      const response = await this.$axios.get('http://localhost:8081/Threads/checkInterestingDataAverageViewCount');
      const dataList = response.data.data;
      this.dviewData.categories = [];
      this.dviewData.data = [];
      for (let i = 0; i < dataList.length; i++) {
        this.dviewData.categories.push(dataList[i].content);
        this.dviewData.data.push(dataList[i].num);
      }
      this.initBarChart(this.$refs.dviews, this.dviewData);
    },
    async loadAnRateData(){
      const response = await this.$axios.get('http://localhost:8081/Threads/checkInterestingDataAnswerRate');
      console.log(response);
      const dataList = response.data.data;
      this.anRateData.categories = [];
      this.anRateData.data = [];
      for (let i = 0; i < dataList.length; i++) {
        this.anRateData.categories.push(dataList[i].content);
        this.anRateData.data.push(dataList[i].num);
      }
      this.initBarChart(this.$refs.anRate, this.anRateData);
    },
    async loadMostData(){
      const response = await this.$axios.get('http://localhost:8081/Threads/checkMostOneInterestingDataViewCount');
      const dataList = response.data.data;
      this.mostName = dataList[0].content;
      this.mostNum = dataList[0].num;
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
  },
};
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
