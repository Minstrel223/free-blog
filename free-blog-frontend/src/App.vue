<template>
  <div id="app">
    <div class="header">
      <my-header></my-header>
    </div>
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import MyHeader from "./components/MyHeader"
export default {
  name:"app",
  components:{
    MyHeader
  }
}
</script>
<style>
.content{
  margin-top: 60px;
}
</style>