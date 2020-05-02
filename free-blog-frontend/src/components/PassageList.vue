<template>
  <div class="passage-list">
    <div class="pl-item row" v-for="item of list" :key="item.id">
      <div
        class="img-container col-lg-5 col-12"
        :style="`background-image: url(${item.article_cover||defaultImg})`"
        @click="goPassage(item)"
      ></div>
      <div class="col-lg-7 col-12">
        <div class="pl-item-title" @click="goPassage(item)">{{item.article_title}}</div>
        <div class="pl-item-content">{{item.article_content.substr(0,80)}}......</div>
        <span class="pli-left-aside">
          <b-badge v-for="i of item.tags" :key="i" variant="info" style="margin-right:10px">{{i}}</b-badge>
        </span>
        <span class="pli-right-aside">
          <span>
            <b-icon icon="eye"></b-icon>
            {{item.article_views}}
          </span>
          <span>
            <b-icon icon="chat"></b-icon>
            {{item.comments_number}}
          </span>
        </span>
      </div>
    </div>
    <br />
    <b-pagination
      v-if="pages"
      v-model="currentPage"
      :total-rows="pages"
      per-page="1"
      first-text="First"
      prev-text="Prev"
      next-text="Next"
      last-text="Last"
      @change="pageTo"
    ></b-pagination>
  </div>
</template>
<script>
export default {
  name: "PassageList",
  props: {
    list: Array,
    pages: Number
  },
  data() {
    return {
      defaultImg: "https://i.loli.net/2020/03/01/58CoY2fb79SrPNj.jpg",
      currentPage: 1
    };
  },
  methods: {
    goPassage(item) {
      this.$router.push(`/passage?name=${item.article_title}`);
      window.localStorage.setItem("currentArticle", JSON.stringify(item));
    },
    pageTo(page) {
      this.$emit("pageTo", page);
    }
  }
};
</script>
<style>
.pl-item {
  padding: 10px 5px;
  border: 1px solid #17a2b8;
}
.img-container {
  height: 200px;
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  cursor: pointer;
}
.pl-item-title {
  font-size: 25px;
  font-weight: bold;
  cursor: pointer;
}
.pl-item-content {
  color: #333333;
  font-size: 15px;
  margin-top: 10px;
}
.pli-left-aside {
  margin-top: 35px;
  float: left;
}
.pli-right-aside {
  margin-top: 35px;
  float: right;
}
</style>