<template>
  <b-container class="bv-example-row" fluid style='background-color: whitesmoke; padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="8" style='background-color: azure; padding: 0px; margin: 0px; border: 0px'>
        <div id='map' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 880px;'></div>
      </b-col>
      <b-col xl="4" style='background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px'>
        <p></p>
        <b-row style='padding: 0px; margin: 0px; border: 0px'>
          <b-col xl="3" style='background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px'>
            <b-button-group vertical>
              <b-button variant="primary" v-for="country in countries" :key="country">{{country}}</b-button>
            </b-button-group>
          </b-col>
          <b-col xl="9" style='background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px'>
            <b-list-group style="background-color: whitesmoke; max-height: 830px; overflow: scroll; padding: 0px; margin: 0px; border: 0px">
              <div v-for="item in geoJson.data.features" :key="item.id">
                <b-list-group-item variant="light" button @click="$bvModal.show(item.id)" style="background-color: whitesmoke">
                  <div class="d-flex w-100 justify-content-between">
                    <img v-bind:src="item.properties.user.profile_img_url" width="30px" height="30px">
                    <h5 class="mb-1">{{item.properties.user.username}}</h5>
                    <small>{{item.created_at}}</small>
                  </div>
                  <p></p>
                  <strong>{{item.properties.content.text}}</strong>
                  <p></p>
                  <img v-bind:src="item.properties.content.img_url" width="300px" height="150px">
                </b-list-group-item>
                <b-modal v-bind:id="item.id" hide-footer size="lg">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Twitter Details</h5>
                    <small>{{item.created_at}}</small>
                  </div>
                  <p class="mb-1">
                    <strong>{{item.properties.content.text}}</strong>
                  </p>
                  <img v-bind:src="item.properties.content.img_url" width="750px" height="400px">
                </b-modal>
              </div>
            </b-list-group>
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
  name: 'View2',
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
      map: null,
      countries: ['China', 'Thai', 'Korea', 'Japan', 'Mexican', 'India', 'Italy', 'America', 'Spain', 'Turkey', 'Greece', 'Pakistan', 'Ukraine', 'Australia']
    }
  },
  created () {
    this.getReq('', {})
  },
  mounted () {
    setTimeout(() => {
      mapboxgl.accessToken = 'pk.eyJ1Ijoid2VuanVudyIsImEiOiJjbDI5czM2MGIwbTVtM250MzVnZXJ4MTd1In0.8e5pzkNEMkUEe37MkLXT0g'
      const mapDiv = document.getElementById('map')
      this.map = new mapboxgl.Map({
        container: mapDiv,
        style: 'mapbox://styles/mapbox/light-v10', // style URL
        center: [144.9695, -37.8227], // starting position [lng, lat]
        zoom: 12 // starting zoom
      })
      this.map.on('load', () => {
        this.map.addSource('places', this.geoJson)
        this.map.addLayer({
          'id': 'places',
          'type': 'circle',
          'source': 'places',
          'paint': {
            'circle-color': '#4264fb',
            'circle-radius': 6,
            'circle-stroke-width': 2,
            'circle-stroke-color': '#ffffff'
          }
        })
        // Create a popup, but don't add it to the map yet.
        const popup = new mapboxgl.Popup({
          closeButton: false,
          closeOnClick: false
        })
        this.map.on('mouseenter', 'places', (e) => {
          // Change the cursor style as a UI indicator.
          this.map.getCanvas().style.cursor = 'pointer'
          // Copy coordinates array.
          const coordinates = e.features[0].geometry.coordinates.slice()
          const content = e.features[0].properties.content
          // Ensure that if the map is zoomed out such that multiple
          // copies of the feature are visible, the popup appears
          // over the copy being pointed to.
          while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
            coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360
          }
          // Populate the popup and set its coordinates
          // based on the feature found.
          const imgUrl = content.split('attitude_label')[0].split(':')[3].split('"')[0]
          popup.setLngLat(coordinates).setHTML('<img src="https:' + imgUrl + '" width="200px" height="100px">').addTo(this.map)
        })
        this.map.on('mouseleave', 'places', () => {
          this.map.getCanvas().style.cursor = ''
          popup.remove()
        })
      })
      document.getElementById('update').addEventListener('click', () => {
        this.geoJson = {
          'type': 'geojson',
          'data': {
            'type': 'FeatureCollection',
            'features': []
          }
        }
        let newGeo = {
          'type': 'FeatureCollection',
          'features': []
        }
        this.map.getSource('places').setData(newGeo)
      })
    }, 1000)
  },
  methods: {
    getData: function (country) {
      const that = this
      let url = ''
      axios.get(url, {
        params: {
          'country': country,
          'dataType': null,
          'period': null
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
