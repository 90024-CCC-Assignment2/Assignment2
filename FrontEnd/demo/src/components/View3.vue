<template>
  <b-container class="bv-example-row" fluid style='padding: 0px; width: 100%; margin: 0px; border: 0px'>
    <b-row style='padding: 0px; margin: 0px; border: 0px'>
      <b-col xl="10" style='background-color: azure; padding: 0px; margin: 0px; border: 0px'>
        <div id='map' style='padding: 0px; margin: 0px; border: 0px; width: 100%; height: 900px;'></div>
      </b-col>
      <b-col xl="2" style='background-color: azure; padding: 0px; margin: 0px; border: 0px'>
        1 of 3
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import mapboxgl from 'mapbox-gl'

export default {
  name: 'View3',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  mounted () {
    mapboxgl.accessToken = 'pk.eyJ1Ijoid2VuanVudyIsImEiOiJjbDI5czM2MGIwbTVtM250MzVnZXJ4MTd1In0.8e5pzkNEMkUEe37MkLXT0g'
    const map = new mapboxgl.Map({
      container: 'map', // container ID
      style: 'mapbox://styles/mapbox/dark-v10', // style URL
      center: [144.9695, -37.8227], // starting position [lng, lat]
      zoom: 12 // starting zoom
    })
    const geoJson = {
      'type': 'FeatureCollection',
      'crs': {
        'type': 'name',
        'properties': {
          'name': 'urn:ogc:def:crs:OGC:1.3:CRS84'
        }},
      'features': [
        {
          'type': 'Feature',
          'properties': {
            'mag': 2.3
          },
          'geometry': {
            'type': 'Point',
            'coordinates': [144.9695, -37.8227]
          }
        },
        {
          'type': 'Feature',
          'properties': {
            'mag': 1.5
          },
          'geometry': {
            'type': 'Point',
            'coordinates': [144.995, -37.4227]
          }
        },
        {
          'type': 'Feature',
          'properties': {
            'mag': 4
          },
          'geometry': {
            'type': 'Point',
            'coordinates': [144.9795, -37.9227]
          }
        },
        {
          'type': 'Feature',
          'properties': {
            'mag': 7
          },
          'geometry': {
            'type': 'Point',
            'coordinates': [144.9395, -37.8128]
          }
        }
      ]
    }
    map.on('load', () => {
      // Add a geojson point source.
      // Heatmap layers also work with a vector tile source.
      map.addSource('heatmap', {
        'type': 'geojson',
        'data': geoJson
      })
      map.addLayer(
        {
          'id': 'heat',
          'type': 'heatmap',
          'source': 'heatmap',
          'maxzoom': 9,
          'paint': {
          // Increase the heatmap weight based on frequency and property magnitude
            'heatmap-weight': [
              'interpolate',
              ['linear'],
              ['get', 'mag'],
              0,
              0,
              6,
              1
            ],
            // Increase the heatmap color weight weight by zoom level
            // heatmap-intensity is a multiplier on top of heatmap-weight
            'heatmap-intensity': [
              'interpolate',
              ['linear'],
              ['zoom'],
              0,
              1,
              9,
              3
            ],
            // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
            // Begin color ramp at 0-stop with a 0-transparancy color
            // to create a blur-like effect.
            'heatmap-color': [
              'interpolate',
              ['linear'],
              ['heatmap-density'],
              0,
              'rgba(33,102,172,0)',
              0.2,
              'rgb(103,169,207)',
              0.4,
              'rgb(209,229,240)',
              0.6,
              'rgb(253,219,199)',
              0.8,
              'rgb(239,138,98)',
              1,
              'rgb(178,24,43)'
            ],
            // Adjust the heatmap radius by zoom level
            'heatmap-radius': [
              'interpolate',
              ['linear'],
              ['zoom'],
              0,
              2,
              9,
              20
            ],
            // Transition from heatmap to circle layer by zoom level
            'heatmap-opacity': [
              'interpolate',
              ['linear'],
              ['zoom'],
              7,
              1,
              9,
              0
            ]
          }
        },
        'waterway-label'
      )

      map.addLayer(
        {
          'id': 'point',
          'type': 'circle',
          'source': 'heatmap',
          'minzoom': 7,
          'paint': {
            // Size circle radius by earthquake magnitude and zoom level
            'circle-radius': [
              'interpolate',
              ['linear'],
              ['zoom'],
              7,
              ['interpolate', ['linear'], ['get', 'mag'], 1, 1, 6, 4],
              16,
              ['interpolate', ['linear'], ['get', 'mag'], 1, 5, 6, 50]
            ],
            // Color circle by earthquake magnitude
            'circle-color': [
              'interpolate',
              ['linear'],
              ['get', 'mag'],
              1,
              'rgba(33,102,172,0)',
              2,
              'rgb(103,169,207)',
              3,
              'rgb(209,229,240)',
              4,
              'rgb(253,219,199)',
              5,
              'rgb(239,138,98)',
              6,
              'rgb(178,24,43)'
            ],
            'circle-stroke-color': 'white',
            'circle-stroke-width': 1,
            // Transition from heatmap to circle layer by zoom level
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
