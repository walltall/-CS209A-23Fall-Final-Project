<template>
    <div>
        <div>
        <el-input
            ref="input"
            v-model="input"
            placeholder="输入Phrase的内容"
            id="input"
        >
        <el-button slot="append" icon="el-icon-search" id="search" @click="searchHandler"></el-button>
        </el-input>
        </div>
        <div>
            <p class="data-line">话题数量：{{ quantity }}</p>
            <p class="data-line">话题浏览量：{{ views }}</p>
        </div>
        <div class="charts-container">
            <div class="chart-container">
                <h2 class="chart-title">相关话题（按数量排序）</h2>
                <div class="chart" ref="reNum"></div>
                <el-button type="primary" @click="loadReNum" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
                    加载数据
                </el-button>
            </div>
            <div class="chart-container">
                <h2 class="chart-title">相关话题（按浏览量排序）</h2>
                <div class="chart" ref="reView"></div>
                <el-button type="primary" @click="loadReView" style="margin-top: 10px; horiz-align: center; margin-bottom: 10px">
                    加载数据
                </el-button>
            </div>
        </div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  data() {
    return{
        input: '',
        quantity: 0,
        views: 0,
        reNumData: {
            categories: [],
            data: [],
        },
        reViewData: {
            categories: [],
            data: [],
        },
    };
  },
  mounted() {
  },
  methods: {
    async loadReNum(){
        const response = await this.$axios.get(`http://localhost:8081/Threads/getRelationTopic/${this.input}`);
        const dataList = response.data.data;
        this.reNumData.categories = [];
        this.reNumData.data = [];
        dataList.forEach((item) => {
            this.reNumData.categories.push(item.content);
            this.reNumData.data.push(item.num);
        });
        this.initBarChart(this.$refs.reNum, this.reNumData);
    },
    async loadReView(){
        const response = await this.$axios.get(`http://localhost:8081/Threads/getRelationViewCountTopic/${this.input}`);
        const dataList = response.data.data;
        this.reViewData.categories = [];
        this.reViewData.data = [];
        dataList.forEach((item) => {
            this.reViewData.categories.push(item.content);
            this.reViewData.data.push(item.num);
        });
        this.initBarChart(this.$refs.reView, this.reViewData);
    },
    async loadNum(){
        const response = await this.$axios.get(`http://localhost:8081/Threads/getUserParseNumber/${this.input}`);
        const dataList = response.data.data;
        this.quantity = dataList[0].num;
    },
    async loadView(){
        const response = await this.$axios.get(`http://localhost:8081/Threads/getUserParseViewCount/${this.input}`);
        const dataList = response.data.data;
        this.views = dataList[0].num;
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
    async searchHandler() {
        this.input = this.$refs.input.$el.querySelector('input').value;
        await this.loadNum();
        await this.loadView();
        await this.loadReNum();
        await this.loadReView();
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