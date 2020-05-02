<template>
  <div id="app">
    <el-row id="main-row">
      <el-col :span="2" id="left-col">
        <el-menu
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          class="menu"
          router
        >
          <el-menu-item index="/">
            <span>编写文章</span>
          </el-menu-item>
          <el-menu-item index="/ArticleManagement">
            <span>管理文章</span>
          </el-menu-item>
          <el-menu-item index="/CategoryManagement">
            <span>分类管理</span>
          </el-menu-item>
          <el-menu-item index="/CommentsManagement">
            <span>评论管理</span>
          </el-menu-item>
          <el-menu-item index="/SiteInfo">
            <span>站点信息</span>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="22">
        <router-view></router-view>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "app",
  created() {
    let token = window.sessionStorage.getItem("token");
    if (!token) this.$router.push("/Login");
  }
};
</script>
<style>
html,
body {
  margin: 0;
  height: 100%;
}
#app {
  height: 100%;
}
#main-row,
#left-col {
  height: 100%;
}
.menu {
  text-align: center;
  height: 100%;
}
</style>
