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
              <b-button variant="primary" v-for="country in countries" :key="country" @click="getData(country)">{{country}}</b-button>
            </b-button-group>
          </b-col>
          <b-col xl="9" style='background-color: whitesmoke; padding: 0px; margin: 0px; border: 0px'>
            <b-list-group style="background-color: whitesmoke; max-height: 830px; overflow: scroll; padding: 0px; margin: 0px; border: 0px">
              <div v-for="item in geoJson.data.features" :key="item.id">
                <b-list-group-item variant="light" button @click="$bvModal.show(item.id)" style="background-color: whitesmoke">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">{{item.properties.user.username}}</h5>
                    <small>{{item.created_at}}</small>
                  </div>
                  <p></p>
                  <strong>{{item.properties.content.text}}</strong>
                  <p></p>
                  <img v-if="item.properties.content.img_url" v-bind:src="item.properties.content.img_url" width="300px" height="150px">
                </b-list-group-item>
                <b-modal v-bind:id="item.id" hide-footer size="lg">
                  <div class="d-flex w-100 justify-content-between">
                    <h5 class="mb-1">Twitter Details</h5>
                    <small>{{item.created_at}}</small>
                  </div>
                  <p class="mb-1">
                    <strong>{{item.properties.content.text}}</strong>
                  </p>
                  <img v-if="item.properties.content.img_url" v-bind:src="item.properties.content.img_url" width="750px" height="400px">
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
  name: 'View1',
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
    this.getData('china')
  },
  mounted () {
    setTimeout(() => {
      mapboxgl.accessToken = 'pk.eyJ1Ijoid2VuanVudyIsImEiOiJjbDI5czM2MGIwbTVtM250MzVnZXJ4MTd1In0.8e5pzkNEMkUEe37MkLXT0g'
      const mapDiv = document.getElementById('map')
      this.map = new mapboxgl.Map({
        container: mapDiv,
        style: 'mapbox://styles/mapbox/light-v10', // style URL
        center: [144.9695, -37.8227], // starting position [lng, lat]
        zoom: 10 // starting zoom
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
      })
    }, 1000)
  },
  methods: {
    getData: function (country) {
      const that = this
      console.log(country)
      let url = 'http://172.26.133.175:8080//comp90024a2/tweet/search-tweet'
      axios.get(url, {
        params: {
          'country': country,
          'dbType': 'restful',
          'period': '1w'
        }
      }).then(function (resp) {
        console.log(JSON.parse(JSON.stringify(resp.data.data)))
        const geoJson = JSON.parse(JSON.stringify(resp.data.data))
        that.geoJson = geoJson
        if (this.map != null) {
          this.map.getSource('places').setData(that.geoJson.data)
        }
        return resp.data
      }).catch(resp => {
        console.log('请求失败：' + resp.status + ',' + resp.statusText)
        if (this.map != null) {
          this.map.getSource('places').setData(that.geoJson.data)
        }
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
