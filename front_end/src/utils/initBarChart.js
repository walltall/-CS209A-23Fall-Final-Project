const initBarChart = (chart, title, name, data) => {
    const option = {
        title: {
            text: title,
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
            name: name,
            type: 'bar',
            data: data,
        },
        ],
    }
    chart.setOption(option);
}
export default initBarChart;