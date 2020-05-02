<template>
  <div style="margin:0 20px">
    <el-row>
      <el-col :span="10">
        <h3>站点名称</h3>
        <el-input v-model="name"></el-input>
        <h3>站点标题</h3>
        <el-input v-model="title"></el-input>
        <h3>特色图片URL</h3>
        <el-input v-model="image"></el-input>
        <h3>站点介绍</h3>
        <el-input type="textarea" v-model="introduction" rows="10"></el-input>
        <el-button type="primary" @click="submitInfo" style="margin-top:30px">发布</el-button>
      </el-col>
      <el-col :span="12" style="margin-left:20px;">
        <h3>关于页面</h3>
        <el-input type="textarea" v-model="about" rows="28"></el-input>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  name: "SiteInfo",
  data() {
    return {
      name: "",
      title: "",
      image: "",
      introduction: "",
      about: ""
    };
  },
  created() {
    api
      .getSiteInfo()
      .then(res => {
        if (res.data.status == "success") {
          let s = res.data.result;
          this.name = s.site_name;
          this.title = s.site_title;
          this.image = s.image_url;
          this.introduction = s.site_introduction;
          this.about = s.about_page;
        }
      })
      .catch(err => console.log(err));
  },
  methods: {
    submitInfo() {
      api
        .updateSiteInfo(
          this.name,
          this.title,
          this.image,
          this.introduction,
          this.about
        )
        .then(res => {
          if (res.data.status == "success") {
            this.$message({
              message: "操作成功",
              type: "success"
            });
          } else {
            this.$message.error("操作失败 " + res.data.status);
          }
        })
        .catch(err => {
          this.$message.error("操作失败 " + err);
        });
    }
  }
};
</script>