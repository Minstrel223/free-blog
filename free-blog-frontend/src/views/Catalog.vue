<template>
  <div class="row" style="width:95;margin:0 auto">
    <div class="col-lg-2"></div>
    <div class="col-lg-6">
      <div class="introducton">
        <h3>{{catalogName}}</h3>
      </div>
      <passage-list :list="list"></passage-list>
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
  name: "Catalog",
  data() {
    return {
      catalogId: "",
      catalogName: "",
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
      this.catalogId = this.$route.query.id;
      this.catalogName = this.$route.query.name;
      api
        .getArticlesByCatagoryId(this.catalogId)
        .then(res => {
          let list = res.data.result;
          list.forEach(element => {
            element.tags = [];
          });
          self.list = list;
          for (let i of self.list) {
            (function(i) {
              api.getTags(i.article_id).then(res => {
                i.tags = res.data.result;
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