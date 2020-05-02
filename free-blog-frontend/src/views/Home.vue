<template>
  <div class="row" style="width:95;margin:0 auto">
    <div class="col-lg-2"></div>
    <div class="col-lg-6">
      <div class="introducton">
        <h3>最新文章</h3>
      </div>
      <passage-list :list="list" :pages="pages" @pageTo="pageTo"></passage-list>
    </div>
    <div class="my-side-bar col-lg d-none d-lg-block">
      <side-bar></side-bar>
    </div>
  </div>
</template>
<script>
import PassageList from "../components/PassageList";
import SideBar from "../components/SideBar";
import api from "../service/api";
export default {
  name: "Home",
  data() {
    return {
      list: [],
      pages: 1
    };
  },
  created() {
    let self = this;
    api
      .getNewArticles(8, 1)
      .then(res => {
        this.pages = res.data.pages;
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
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    pageTo(page) {
      let self = this;
      self.list = [];
      api
        .getNewArticles(8, page)
        .then(res => {
          this.pages = res.data.pages;
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
        .catch(error => {
          console.log(error);
        });
    }
  },
  components: {
    PassageList,
    SideBar
  }
};
</script>
<style>
.my-side-bar {
  position: fixed;
  top: 60px;
  left: 67%;
}
</style>