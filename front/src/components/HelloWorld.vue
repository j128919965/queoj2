<template>
  <div style="height: 100%">
    <TitleBar/>
    <router-view></router-view>
  </div>

</template>

<script>
import TitleBar from "@/components/TitleBar";
export default {
  name: 'HelloWorld',
  components:{
    TitleBar
  },
  created :function () {
    this.$store.commit("open")
  }
}
</script>
<style scoped>

</style>