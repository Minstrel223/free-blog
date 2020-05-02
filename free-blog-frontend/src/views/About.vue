<template>
  <div class="row">
    <div class="col-lg-2"></div>
    <div class="col-lg-8" v-html="aboutPage"></div>
  </div>
</template>
<script>
export default {
  name: "about",
  data() {
    return {
      aboutPage: ""
    };
  },
  created() {
    this.aboutPage = JSON.parse(
      window.localStorage.getItem("siteInfo")
    ).about_page;
  }
};
</script>