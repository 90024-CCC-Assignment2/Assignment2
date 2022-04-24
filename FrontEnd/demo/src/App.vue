<template>
  <div id="app">
    <div id="nav">
        <b-nav fill card-header pills>
          <b-nav-item to="../view1" exact exact-active-class="active">View1</b-nav-item>
          <b-nav-item to="../view2" exact exact-active-class="active">View2</b-nav-item>
          <b-nav-item to="../view3" exact exact-active-class="active">View3</b-nav-item>
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
  background: whitesmoke;
}
</style>
