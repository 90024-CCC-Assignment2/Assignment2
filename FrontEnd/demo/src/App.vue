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
  <div id="app">
    <div id="nav">
        <b-nav fill card-header pills>
          <b-nav-item to="../view1" exact exact-active-class="active">Streaming Data</b-nav-item>
          <b-nav-item to="../view2" exact exact-active-class="active">RESTful&Historical Data</b-nav-item>
          <b-nav-item to="../view3" exact exact-active-class="active">Data Comparison(AURIN)</b-nav-item>
        </b-nav>
      </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      name: 'app'
    }
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}

#nav {
  background: #83beff;
}
</style>
