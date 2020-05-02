<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info" fixed="top">
      <router-link to="/">
        <b-navbar-brand>
          <b-icon icon="book"></b-icon>
          {{siteInfo.site_name}}
        </b-navbar-brand>
      </router-link>
      <span class="motto d-none d-lg-block">{{siteInfo.site_title}}</span>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <!-- 向右对齐 -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item href="#">
            <router-link to="/" tag="span">首页</router-link>
          </b-nav-item>
          <b-nav-item v-for="item of catalog" :key="item.id">
            <router-link
              tag="span"
              :to="`/catalog?name=${item.catagory_name}&id=${item.catagory_id}`"
            >{{item.catagory_name}}</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/about" tag="span">关于</router-link>
          </b-nav-item>
          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search" v-model="sq"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" @click="search">
              <b-icon icon="search"></b-icon>
            </b-button>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  name: "MyHeader",
  data() {
    return {
      siteInfo: {
        site_name: "MyBlog",
        site_title: "show me your code"
      },
      catalog: [],
      sq: ""
    };
  },
  created() {
    api
      .getCatalog()
      .then(res => (this.catalog = res.data.result))
      .catch(error => console.log(error));
    api
      .getSiteInfo()
      .then(res => {
        this.siteInfo = res.data.result;
        document.title = this.siteInfo.site_name;
        window.localStorage.setItem("siteInfo", JSON.stringify(this.siteInfo));
      })
      .catch(err => console.log(err));
  },
  methods: {
    search() {
      if (this.sq) {
        this.$router.push(`/search?q=${this.sq}`);
      }
    }
  }
};
</script>
<style>
.motto {
  font-size: 13px;
  color: #ffffff;
}
</style>