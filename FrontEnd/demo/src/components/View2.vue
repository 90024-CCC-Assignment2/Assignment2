#
# COMP90024 Cluster and Cloud Computing Assignment2 - Team 42
#
# Authors:
#
#  - Haoyu Gao (Student ID: 1141213)
#  - Runqi Zhao (Student ID: 1159719)
#  - Shukai Liu (Student ID: 1233459)
#  - Siyu Liu (Student ID：1094521)
#  - Wenjun Wang (Student ID: 1249890)
#
# Location: Melbourne
#
<template>
  <b-container class="bv-example-row" fluid style='padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="8" style='background-color: azure; padding: 0px; margin: 0px; border: 0px'>
        <div id='map' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 880px;'></div>
      </b-col>
      <b-col xl="4" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
        <p></p>
        <p></p>
        <b-row style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
          <b-col xl="3" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                id="btn-radios"
                v-model="country"
                :options="countries"
                :aria-describedby="ariaDescribedby"
                name="radio-btn-stacked"
                buttons
                stacked
              ></b-form-radio-group>
            </b-form-group>
          </b-col>
          <b-col xl="9" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
            <h2>Twitter Attitude</h2>
            <p></p>
            <img src="https://measuringu.com/wp-content/uploads/2019/04/emojis.jpg" style="width: 200px">
            <p></p>
            <b-form-group v-slot="{ ariaDescribedby }">
              <b-form-radio-group
                v-bind:id="country"
                v-model="dataType"
                :aria-describedby="ariaDescribedby"
              >
                <b-row style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
                  <b-col xl="6" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
                    <b-form-radio value="restful">RESTful</b-form-radio>
                    <p></p>
                    <strong>Query data period:</strong>
                    <strong>R:2020.01.01~Now</strong>
                    <strong>H:2014.07.29~2017.06.29</strong>
                  </b-col>
                  <b-col xl="6" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
                    <b-form-radio value="historical">Historical</b-form-radio>
                    <p></p>
                    <b-form-select v-model="period" :options="periodOption"></b-form-select>
                  </b-col>
                  <p></p>
                  <b-button variant="primary" @click="getData(country,dataType,period)">Show me the data!</b-button>
                </b-row>
                <p></p>
                <p></p>
                <b-table striped hover stacked :items="typeData"></b-table>
              </b-form-radio-group>
            </b-form-group>
          </b-col>
        </b-row>
<!--        <b-row style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>-->
<!--          <b-col xl="2" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>-->
<!--          </b-col>-->
<!--          <b-col xl="8" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>-->
<!--            <b-carousel-->
<!--              id="carousel-food"-->
<!--              style="text-shadow: 0px 0px 2px #000; top: 20px"-->
<!--              interval:5000-->
<!--              fade-->
<!--              indicators-->
<!--            >-->
<!--              <div v-for="item in imgs" :key="item.id">-->
<!--                <b-carousel-slide-->
<!--                  v-bind:img-src="item.src"-->
<!--                ></b-carousel-slide>-->
<!--              </div>-->
<!--            </b-carousel>-->
<!--          </b-col>-->
<!--          <b-col xl="2" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>-->
<!--          </b-col>-->
<!--        </b-row>-->
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import mapboxgl from 'mapbox-gl'
import axios from 'axios'

export default {
  name: 'View2',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      dataType: 'restful',
      period: '1w',
      country: 'China',
      periodOption: [
        { value: '1w', text: '1 Week' },
        { value: '1m', text: '1 Month' },
        { value: '3m', text: '3 Month' },
        { value: '6m', text: '6 Month' },
        { value: '1y', text: '1 Year' },
        { value: '2y', text: '2 Year' },
        { value: '3y', text: '3 Year' }
      ],
      typeData: [
        {'Number': null, 'Positive😄': null, 'Negative😠': null, 'Rate': null}
      ],
      geoJson: {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': []
        }
      },
      countries: ['China', 'Thai', 'Korea', 'Japan', 'Mexican', 'India', 'Italy', 'America', 'Spain', 'Turkey', 'Greece', 'Pakistan', 'Ukraine', 'Australia'],
      imgs: [],
      map: null
    }
  },
  created () {
  },
  mounted () {
    setTimeout(() => {
      mapboxgl.accessToken = 'pk.eyJ1Ijoid2VuanVudyIsImEiOiJjbDI5czM2MGIwbTVtM250MzVnZXJ4MTd1In0.8e5pzkNEMkUEe37MkLXT0g'
      this.map = new mapboxgl.Map({
        container: 'map', // container ID
        style: 'mapbox://styles/mapbox/dark-v10', // style URL
        center: [144.9695, -37.8227], // starting position [lng, lat]
        zoom: 10 // starting zoom
      })
      this.map.on('load', () => {
        // Add a geojson point source.
        // Heatmap layers also work with a vector tile source.
        this.map.addSource('points', this.geoJson)

        this.map.addLayer(
          {
            'id': 'point',
            'type': 'circle',
            'source': 'points',
            'minzoom': 7,
            'paint': {
              'circle-radius': 5,
              'circle-color': [
                'interpolate',
                ['linear'],
                ['get', 'attitude_label'],
                0,
                'rgb(178,24,43)',
                1,
                'rgb(103,169,207)'
              ],
              'circle-stroke-color': 'white',
              'circle-stroke-width': 1,
              'circle-opacity': [
                'interpolate',
                ['linear'],
                ['zoom'],
                7,
                0,
                8,
                1
              ]
            }
          },
          'waterway-label'
        )
      })
    }, 1000)
  },
  methods: {
    getData: function (country, type, period) {
      console.log('Call getData(' + country + ',' + type + ',' + period + ')')
      this.getImages(country, type, period)
      this.getTwitters(country, type, period)
    },
    getImages: function (country, type, period) {
      console.log('Call getImages(' + country + ',' + type + ',' + period + ')')
      const that = this
      let url = 'http://172.26.133.175:8080/comp90024a2/tweet/search-pic'
      axios.get(url, {
        params: {
          'country': country,
          'dbType': type,
          'period': period
        }
      }).then(function (resp) {
        console.log(JSON.parse(JSON.stringify(resp.data.data)))
        const imgs = JSON.parse(JSON.stringify(resp.data.data))
        that.imgs = imgs
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
      })
    },
    getTwitters: function (country, type, period) {
      console.log('Call getData(' + country + ',' + type + ',' + period + ')')
      const that = this
      let url = 'http://172.26.133.175:8080/comp90024a2/tweet/search-tweet'
      axios.get(url, {
        params: {
          'country': country,
          'dbType': type,
          'period': period
        }
      }).then(function (resp) {
        const geoJson = JSON.parse(JSON.stringify(resp.data.data))
        let num = geoJson.data.features.length
        let pos = 0
        let neg = 0
        for (let i = 0; i < num; i++) {
          let item = geoJson.data.features[i]
          if (item.properties.attitude_label === 1) {
            pos += 1
          } else {
            neg += 1
          }
        }
        that.typeData = [{'Number': num, 'Positive😄': pos, 'Negative😠': neg, 'Rate': (pos / (pos + neg) * 100).toFixed(2) + '%'}]
        that.geoJson = geoJson
        that.map.getSource('points').setData(that.geoJson.data)
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
        const geoJson = null
        that.geoJson = geoJson
        that.map.getSource('points').setData(that.geoJson.data)
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
