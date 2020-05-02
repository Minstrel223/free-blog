<template>
  <div class="row" style="width:95;margin:0 auto">
    <div class="col-lg-2"></div>
    <div class="col-lg-6">
      <div class="introducton">
        <h3>搜索: {{sq}}</h3>
      </div>
      <passage-list :list="list"></passage-list>
      <span>{{list.length==0?"抱歉，暂时没有相关文章":""}}</span>
    </div>
    <div class="my-side-bar col-lg d-none d-lg-block">
      <side-bar></side-bar>
    </div>
  </div>
</template>
<script>
import api from "../service/api";
import PassageList from "../components/PassageList";
import SideBar from "../components/SideBar";

export default {
  name: "Search",
  data() {
    return {
      sq: "",
      list: []
    };
  },
  mounted() {
    this.getContent();
  },
  watch: {
    $route() {
      this.getContent();
    }
  },
  methods: {
    getContent() {
      let self = this;
      self.list = [];
      this.sq = this.$route.query.q;
      api
        .searchArticles(this.sq)
        .then(res => {
          let list = res.data.result;
          for (let i of list) {
            (function(i) {
              api.getTags(i.article_id).then(res => {
                i.tags = res.data.result;
                self.list.push(i);
              });
            })(i);
          }
        })
        .catch(err => console.log(err));
    }
  },
  components: {
    PassageList,
    SideBar
  }
};
</script>