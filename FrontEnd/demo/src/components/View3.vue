<template>
  <b-container class="bv-example-row" fluid style='padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="8" style='background-color: azure; padding: 0px; margin: 0px; border: 0px'>
        <div id='map' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 880px;'></div>
      </b-col>
      <b-col xl="4" style='background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px'>
        <div class="mt-3" style="background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px">
        </div>
        <b-tabs pills card vertical style="background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px; height: 860px">
          <div v-for="country in countries" :key="country">
            <b-tab v-bind:title="country" @click="getData(country,'1w')"><b-card-text>
            </b-card-text></b-tab>
          </div>
        </b-tabs>
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
      geoJson: {
        'type': 'geojson',
        'data': {
          'type': 'FeatureCollection',
          'features': []
        }
      },
      countries: ['China', 'Thai', 'Korea', 'Japan', 'Mexican', 'India', 'Italy', 'America', 'Spain', 'Turkey', 'Greece', 'Pakistan', 'Ukraine', 'Australia']
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
    getData: function (type, period) {
      const that = this
      let url = ''
      axios.get(url, {
        params: {
          'type': type,
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
        that.geoJson = geoJson
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
