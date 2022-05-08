<template>
  <b-container class="bv-example-row" fluid style='padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="6" style='background-color: antiquewhite; padding: 0px; margin: 0px; border: 0px'>
        <div id='map1' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 400px;'></div>
        <div id='map2' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 480px;'></div>
      </b-col>
      <b-col xl="6" style='background-color: antiquewhite; padding: 0px; margin: 0px; border: 0px'>
        <div id='map3' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 400px;'></div>
        <div id='map4' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 480px;'></div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: 'View3',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      countries: ['China', 'Thai', 'Korea', 'Japan', 'Mexican', 'India', 'Italy', 'America', 'Spain', 'Turkey', 'Greece', 'Pakistan', 'Ukraine', 'Australia']
    }
  },
  created () {
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
  },
  methods: {
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
