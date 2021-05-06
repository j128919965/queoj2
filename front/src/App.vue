<template>
  <div id="app" style="height: 100%">
    <HelloWorld/>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>

<style>
  body{
    width: 100%;
    margin: 0;
    padding: 0;
    color: rgba(0,0,0,0.65);
    font-size: 16px;
    line-height: 1.5;
    background-color: #fff;
  }
  .container{
    max-width: 1200px;
    width: 100%;
    margin: 0 auto;
  }
</style>
