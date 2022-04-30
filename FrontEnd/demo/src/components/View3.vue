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
                    <b-form-radio value="RESTful">RESTful</b-form-radio>
                    <p></p>
                    <strong>Query data period:</strong>
                  </b-col>
                  <b-col xl="6" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
                    <b-form-radio value="Historical">Historical</b-form-radio>
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
        <b-row style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
          <b-col xl="2" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
          </b-col>
          <b-col xl="8" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
            <b-carousel
              id="carousel-food"
              style="text-shadow: 0px 0px 2px #000; top: 20px"
              interval:5000
              fade
              indicators
            >
              <div v-for="item in imgs" :key="item.id">
                <b-carousel-slide
                  v-bind:img-src="item.src"
                ></b-carousel-slide>
              </div>
            </b-carousel>
          </b-col>
          <b-col xl="2" style='background-color: darkgrey; padding: 0px; margin: 0px; border: 0px'>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import mapboxgl from 'mapbox-gl'
import axios from 'axios'

export default {
  name: 'View3',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      dataType: 'RESTful',
      period: '1d',
      country: 'China',
      periodOption: [
        { value: '1d', text: '1 Day' },
        { value: '3d', text: '3 Day' },
        { value: '1w', text: '1 Week' },
        { value: '1m', text: '1 Month' },
        { value: '1y', text: '1 Year' }
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
      imgs: [
        {id: 1, src: 'https://img-cdn-china-admissions.imgix.net/wp-content/uploads/2020/02/Chinese-food.jpg?auto=format%2Cenhance%2Ccompress'},
        {id: 2, src: 'https://www.kohinoor-joy.com/wp-content/uploads/2020/01/indo-chinese-food.jpg'},
        {id: 3, src: 'https://venues.ipaypro.co/media/cuisineImages/Chinesecuisinenearme.jpg'}
      ]
    }
  },
  created () {
    this.getReq('', {})
  },
  mounted () {
    setTimeout(() => {
      mapboxgl.accessToken = 'pk.eyJ1Ijoid2VuanVudyIsImEiOiJjbDI5czM2MGIwbTVtM250MzVnZXJ4MTd1In0.8e5pzkNEMkUEe37MkLXT0g'
      const map = new mapboxgl.Map({
        container: 'map', // container ID
        style: 'mapbox://styles/mapbox/dark-v10', // style URL
        center: [144.9695, -37.8227], // starting position [lng, lat]
        zoom: 12 // starting zoom
      })
      const geoJson = {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': [
            {
              'id': '123',
              'type': 'Feature',
              'created_at': '2022-04-29 19:07',
              'properties': {
                'content': {
                  'text': 'sample textsample textsample textsample textsample textsample textsample textsample textsample textsample text',
                  'img_url': 'https://media.timeout.com/images/105655794/1372/772/image.jpg'
                },
                'user': {
                  'username': 'mallory',
                  'profile_img_url': 'https://www.shareicon.net/data/512x512/2016/07/26/802043_man_512x512.png'
                },
                'attitude_label': 0
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [144.9695, -37.8227]
              }
            },
            {
              'id': '1234',
              'type': 'Feature',
              'created_at': '2022-04-29 19:07',
              'properties': {
                'content': {
                  'text': 'sample textsample textsample textsample textsample textsample textsample textsample textsample textsample text',
                  'img_url': 'https://media.timeout.com/images/105655794/1372/772/image.jpg'
                },
                'user': {
                  'username': 'mallory',
                  'profile_img_url': 'https://www.shareicon.net/data/512x512/2016/07/26/802043_man_512x512.png'
                },
                'attitude_label': 0
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [144.995, -37.8427]
              }
            },
            {
              'id': '12345',
              'type': 'Feature',
              'created_at': '2022-04-29 19:07',
              'properties': {
                'content': {
                  'text': 'sample textsample textsample textsample textsample textsample textsample textsample textsample textsample text',
                  'img_url': 'https://media.timeout.com/images/105655794/1372/772/image.jpg'
                },
                'user': {
                  'username': 'mallory',
                  'profile_img_url': 'https://www.shareicon.net/data/512x512/2016/07/26/802043_man_512x512.png'
                },
                'attitude_label': 1
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [144.9795, -37.8027]
              }
            },
            {
              'id': '123456',
              'type': 'Feature',
              'created_at': '2022-04-29 19:07',
              'properties': {
                'content': {
                  'text': 'sample textsample textsample textsample textsample textsample textsample textsample textsample textsample text',
                  'img_url': 'https://media.timeout.com/images/105655794/1372/772/image.jpg'
                },
                'user': {
                  'username': 'mallory',
                  'profile_img_url': 'https://www.shareicon.net/data/512x512/2016/07/26/802043_man_512x512.png'
                },
                'attitude_label': 1
              },
              'geometry': {
                'type': 'Point',
                'coordinates': [144.9395, -37.8128]
              }
            }
          ]
        }
      }
      map.on('load', () => {
        // Add a geojson point source.
        // Heatmap layers also work with a vector tile source.
        map.addSource('points', geoJson)

        map.addLayer(
          {
            'id': 'point',
            'type': 'circle',
            'source': 'points',
            'minzoom': 7,
            'paint': {
              'circle-radius': 10,
              'circle-color': [
                'interpolate',
                ['linear'],
                ['get', 'attitude_label'],
                0,
                'rgb(103,169,207)',
                1,
                'rgb(178,24,43)'
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
      const that = this
      let url = ''
      axios.get(url, {
        params: {
          'country': country,
          'dataType': type,
          'period': period
        }
      }).then(function (resp) {
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
        console.log('request type:' + type)
        const geoJson = {
          'type': 'geojson',
          'data': {
            'type': 'FeatureCollection',
            'features': []
          }
        }
        that.typeData = [{'Number': 1, 'Positive😄': 1, 'Negative😠': 1, 'Rate': 1}]
        that.geoJson = geoJson
        that.imgs = []
        this.map.getSource('places').setData(that.geoJson.data)
      })
    },
    getReq: function (url, args) {
      const that = this
      axios.get(url, {
        params: {
        }
      }).then(function (resp) {
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
        const geoJson = {
          'type': 'geojson',
          'data': {
            'type': 'FeatureCollection',
            'features': [
              {
                'id': '123',
                'type': 'Feature',
                'created_at': '2022-04-29 19:07',
                'properties': {
                  'content': {
                    'text': 'sample textsample textsample textsample textsample textsample textsample textsample textsample textsample text',
                    'img_url': 'https://media.timeout.com/images/105655794/1372/772/image.jpg',
                    'attitude_label': null
                  },
                  'user': {
                    'username': 'mallory',
                    'profile_img_url': 'https://www.shareicon.net/data/512x512/2016/07/26/802043_man_512x512.png'
                  }
                },
                'geometry': {
                  'type': 'Point',
                  'coordinates': [144.93038, -37.801567]
                }
              }
            ]
          }
        }
        that.geoJson = geoJson
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
