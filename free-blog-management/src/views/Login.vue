<template>
  <div style="margin:200px auto; width:500px;">
    <h5>用户名：</h5>
    <el-input v-model="user.name" placeholder="请输入用户名"></el-input>
    <h5>密码：</h5>
    <el-input placeholder="请输入密码" v-model="user.password" show-password></el-input>
    <el-button type="primary" style="width:500px; margin-top:20px" @click="login">登录</el-button>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  name: "Login",
  data() {
    return {
      user: {
        name: "",
        password: ""
      }
    };
  },
  methods: {
    login() {
      if (this.user.name && this.user.password) {
        console.log(this.user.name);
        api
          .login(this.user.name, this.user.password)
          .then(res => {
            if (res.data.status == "success") {
              this.$message.info("登录成功");
              window.sessionStorage.setItem("token", res.data.result);
              window.sessionStorage.setItem("username", this.user.name);
              api.axios.interceptors.request.use(config => {
                config.headers.Token = window.sessionStorage.getItem("token");
                return config;
              });
              this.$router.push("/");
            } else this.$message.error("登录失败" + res.data.status);
          })
          .catch(err => this.$message.error("登录失败" + err));
      } else this.$message.warning("请输入用户名和密码");
    }
  }
};
</script>