<template>
  <b-container class="bv-example-row" fluid style='padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="6" style='background-color: antiquewhite; padding: 0px; margin: 0px; border: 0px'>
        <div id='map1' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 400px;'></div>
        <div id='map2' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 480px;'></div>
      </b-col>
      <b-col xl="6" style='background-color: antiquewhite; padding: 0px; margin: 0px; border: 0px'>
        <div id='info' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 200px;'></div>
        <div id='map3' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 680px;'></div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import * as echarts from 'echarts'
import axios from 'axios'

export default {
  name: 'View3',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      countries: ['China', 'Thai', 'Korea', 'Japan', 'Mexican', 'India', 'Italy', 'America', 'Spain', 'Turkey', 'Greece', 'Pakistan', 'Ukraine', 'Australia'],
      category: ['Eastern Asia', 'Southern Asia', 'Australia', 'America and Mexican', 'Europe'],
      restaurants: [
        [120, 132, 101, 134],
        [220, 182, 191, 234],
        [150, 232, 201, 154],
        [320, 332, 301, 334],
        [820, 932, 901, 934]
      ]
    }
  },
  created () {
    this.getData()
  },
  mounted () {
    const chartDom1 = document.getElementById('map1')
    const myChart1 = echarts.init(chartDom1)
    let option1 = {
      title: [
        {
          text: 'Country of Birth of Person in Melbourne',
          subtext: 'Data Source: LGA profiles data 2015 for VIC(AURIN) and 2016 Census Community Profiles(ABS)',
          left: 'center'
        }
      ],
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: 'Chart',
          type: 'pie',
          radius: [30, 120],
          center: ['25%', '50%'],
          itemStyle: {
            borderRadius: 8
          },
          data: [
            { value: 2684072, name: 'Australia' },
            { value: 176838, name: 'China' },
            { value: 161078, name: 'India' },
            { value: 133300, name: 'England' },
            { value: 79054, name: 'Vietnam' },
            { value: 78906, name: 'New Zealand' },
            { value: 63332, name: 'Sri Lanka' },
            { value: 47642, name: 'Malaysia' },
            { value: 45618, name: 'Greece' },
            { value: 45157, name: 'Philippines' },
            { value: 24168, name: 'South Africa' }
          ]
        },
        {
          name: 'Chart',
          type: 'pie',
          radius: [30, 120],
          center: ['75%', '50%'],
          itemStyle: {
            borderRadius: 4
          },
          data: [
            { value: 176838, name: 'China' },
            { value: 161078, name: 'India' },
            { value: 63332, name: 'Italy' },
            { value: 45618, name: 'Greece' },
            { value: 20033, name: 'Pakistan' },
            { value: 16765, name: 'America' },
            { value: 14861, name: 'Turkey' },
            { value: 14074, name: 'Korea' },
            { value: 13909, name: 'Thai' },
            { value: 7872, name: 'Japan' }
          ]
        }
      ]
    }
    option1 && myChart1.setOption(option1)
    const chartDom2 = document.getElementById('map2')
    const myChart2 = echarts.init(chartDom2)
    let option2 = {
      title: [
        {
          text: 'Summary of Twitters\' attitude',
          subtext: 'Data Source: Twitter API',
          left: 'center'
        }
      ],
      color: ['#5470c6', '#91cc75', '#ee6666'],
      legend: {
        right: '50px',
        top: '20px'
      },
      tooltip: {},
      dataset: {
        source: [
          ['#Twitter', 'All', 'Positive', 'Negative'],
          ['China', 43.3, 85.8, 93.7],
          ['Thai', 83.1, 73.4, 55.1],
          ['Korea', 86.4, 65.2, 82.5],
          ['Japan', 72.4, 53.9, 39.1],
          ['Mexican', 43.3, 85.8, 93.7],
          ['India', 83.1, 73.4, 55.1],
          ['Italy', 86.4, 65.2, 82.5],
          ['America', 72.4, 53.9, 39.1],
          ['Spain', 43.3, 85.8, 93.7],
          ['Turkey', 83.1, 73.4, 55.1],
          ['Greece', 86.4, 65.2, 82.5],
          ['Pakistan', 72.4, 53.9, 39.1],
          ['Ukraine', 86.4, 65.2, 82.5],
          ['Australia', 72.4, 53.9, 39.1]
        ]
      },
      xAxis: { type: 'category' },
      yAxis: {},
      // Declare several bar series, each will be mapped
      // to a column of dataset.source by default.
      series: [{type: 'bar'}, {type: 'bar', stack: 'stack'}, {type: 'bar', stack: 'stack'}]
    }
    option2 && myChart2.setOption(option2)
    const chartDom3 = document.getElementById('map3')
    const myChart3 = echarts.init(chartDom3)
    let option3 = {
      title: {
        text: 'Restaurant Increasing Chart',
        top: '5%'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: this.category,
        top: '10%'
      },
      toolbox: {
        feature: {
          saveAsImage: {}
        }
      },
      grid: {
        top: '20%',
        left: '5%',
        right: '5%',
        bottom: '7%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: ['2002', '2007', '2012', '2017']
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'Eastern Asia',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: this.restaurants[0]
        },
        {
          name: 'Southern Asia',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: this.restaurants[1]
        },
        {
          name: 'Australia',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: this.restaurants[2]
        },
        {
          name: 'America and Mexican',
          type: 'line',
          stack: 'Total',
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: this.restaurants[3]
        },
        {
          name: 'Europe',
          type: 'line',
          stack: 'Total',
          label: {
            show: true,
            position: 'top'
          },
          areaStyle: {},
          emphasis: {
            focus: 'series'
          },
          data: this.restaurants[4]
        }
      ]
    }
    option3 && myChart3.setOption(option3)
  },
  methods: {
    getData: function () {
      console.log('Call getData()')
      this.getCount1('Restful')
      this.getCount2()
    },
    getCount1: function (type) {
      console.log('Call getData()')
      const that = this
      let url = 'https://localhost:8080/count-tag'
      axios.get(url, {
      }).then(function (resp) {
        that.imgs = resp.data
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
      })
    },
    getCount2: function () {
      console.log('Call getData()')
      const that = this
      let url = 'https://localhost:8080/search-pic'
      axios.get(url, {
      }).then(function (resp) {
        that.imgs = resp.data
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
