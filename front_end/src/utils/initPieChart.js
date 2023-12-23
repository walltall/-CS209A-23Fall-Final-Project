const initPieChart = (chart, title, subtext, name, data) => {
    const option = {
        title: {
            text: title,
            subtext: subtext,
            left: 'center',
            top: '43%',
            subtextStyle: {
              fontSize: 18
            }
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            icon: 'circle',
            top: '0',
            left: 'right'
        },
        series: [
            {
                name: name,
                type: 'pie',
                radius: ['40%', '55%'],
                label: {
                    show: true,
                    padding: [0, -60],
                    overflow: 'none',
                    fontSize: '15',
                    fontWeight: 'bold',
                    formatter: '{d}%\n\n{c}'
                },
                labelLine: {
                    show: true,
                    length: 15,
                    length2: 60
                },
                itemStyle: {
                    normal: {
                        color: function (params) {
                            var colorList = ['#4FC3F7', '#00C853', '#F57F17']
                            return colorList[params.dataIndex]
                        }
                    }
                },
                data: data
            }
        ]
    }
    chart.setOption(option);
};
export default initPieChart;