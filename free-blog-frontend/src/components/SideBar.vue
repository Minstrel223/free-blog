<template>
  <div class="side-bar">
    <img class="side-bar-img" :src="imgUrl" alt="头像" />
    <div class="side-bar-content" v-html="introduction"></div>
  </div>
</template>
<script>
export default {
  name: "SideBar",
  data() {
    return {
      imgUrl: "https://s1.ax1x.com/2020/06/01/t8Jf5q.jpg",
      introduction: "桃之夭夭，灼灼其华<br>之子于归，宜其室家"
    };
  },
  mounted() {
    setTimeout(() => {
      let info = JSON.parse(window.localStorage.getItem("siteInfo"));
      this.imgUrl = info.image_url;
      this.introduction = info.site_introduction;
    }, 100);
  }
};
</script>
<style>
.side-bar {
  width: 250px;
  border: 1px solid #17a2b8;
  border-radius: 3px;
  margin-top: 40px;
}
.side-bar-img {
  margin: 0 0;
  width: 100%;
}
.side-bar-content {
  width: 160px;
  margin: 10px 10px;
}
</style>