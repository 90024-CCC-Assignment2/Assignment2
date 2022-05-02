<template>
  <div style="height: 850px">
    <div>
      <b-carousel
        id="carousel-fade"
        v-model="slide"
        :interval="4000"
        fade
        controls
        indicators
        background="#ababab"
        img-width="1920px"
        img-height="850px"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
      >
        <!-- Text slides with image -->
        <b-carousel-slide
          caption="First slide"
          text="Nulla vitae elit libero, a pharetra augue mollis interdum."
          img-src="https://picsum.photos/1024/480/?image=52"
        ></b-carousel-slide>
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=54">
          <h1>Hello world!</h1>
        </b-carousel-slide>
        <b-carousel-slide img-src="https://picsum.photos/1024/480/?image=58"></b-carousel-slide>
      </b-carousel>
    </div>
    <div style="background-color: whitesmoke;">
      <b-container class="bv-example-row" fluid style='background-color:azure; padding: 0px; width: 100%; margin: 0px; border: 0px'>
        <b-row style='padding: 0px; margin: 0px; border: 0px; text-align: center; vertical-align: middle'>
          <b-col xl="4" style='background-color: azure; padding: 0px; margin: 0px; border: 0px;'>
            <b-jumbotron style="font-size: 24px;" lead="COMP90024 Cluster and Cloud Computing Team 42">
            </b-jumbotron>
          </b-col>
          <b-col xl="4" style='background-color: azure; padding: 0px; margin: 0px; border: 0px;'>
            <b-jumbotron style="font-size: 24px;" lead="For more information visit our github repo">
            </b-jumbotron>
          </b-col>
          <b-col xl="4" style='background-color: azure; padding: 0px; margin: 0px; border: 0px;'>
            <b-link href="https://github.com/90024-CCC-Assignment2/Assignment2" style="font-size: 20px;">Github Link</b-link>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      name: 'index',
      slide: 0,
      sliding: null
    }
  },
  methods: {
    onSlideStart (slide) {
      this.sliding = true
    },
    onSlideEnd (slide) {
      this.sliding = false
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
