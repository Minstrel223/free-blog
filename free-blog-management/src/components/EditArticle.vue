<template>
  <div id="editor">
    <el-row>
      <el-col :span="18">
        <h3>标题</h3>
        <el-input v-model="article.title" placeholder="请输入标题" class="title"></el-input>
        <h3>内容</h3>
        <el-input
          v-model="article.content"
          type="textarea"
          rows="20"
          placeholder="请输入内容(Markdown)"
          class="text"
        ></el-input>
      </el-col>
      <el-col :span="6">
        <h3>分类</h3>
        <el-select v-model="article.catagoryId" placeholder="请选择">
          <el-option
            v-for="item in catalog"
            :key="item.catagory_id"
            :label="item.catagory_name"
            :value="item.catagory_id"
          ></el-option>
        </el-select>
        <h3>标签</h3>
        <el-tag
          :key="tag"
          v-for="tag in article.tags"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
        >{{tag}}</el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        ></el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
        <br />
        <h3>特色图片链接</h3>
        <el-input v-model="article.cover" placeholder="请输入图片链接" style="width:230px"></el-input>
        <br />
        <img :src="article.cover" style="width:230px" />
        <br />
        <el-button type="primary" id="sub-article" @click="submitArticle">{{article.id?"更新":"发布"}}</el-button>
        <el-button type="danger" id="sub-article" @click="resetArticle">重置</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  name: "EditArticle",
  data() {
    return {
      article: {
        title: "",
        content: "",
        catagoryId: "",
        tags: [],
        id: "",
        cover: ""
      },
      catalog: [],
      inputVisible: false,
      inputValue: ""
    };
  },
  created() {
    api
      .getCatalog()
      .then(res => (this.catalog = res.data.result))
      .catch(err => console.log(err));
    let ca = window.localStorage.getItem("cArticle");
    if (ca) this.article = JSON.parse(ca);
  },
  methods: {
    handleClose(tag) {
      //删除标签
      this.article.tags.splice(this.article.tags.indexOf(tag), 1);
    },

    showInput() {
      this.inputVisible = true;
      this.$nextTick(() => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    handleInputConfirm() {
      //增加标签
      let inputValue = this.inputValue;
      if (inputValue) {
        this.article.tags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = "";
    },
    submitArticle() {
      if (this.article.title && this.article.content) {
        if (this.article.id == "") {
          //增加文章
          let tagString = "";
          for (let tag of this.article.tags) {
            tagString += "," + tag;
          }
          tagString = tagString.substr(1);
          api
            .addArticle(
              this.article.title,
              this.article.content,
              this.article.catagoryId,
              tagString,
              window.sessionStorage.getItem("username"),
              this.article.cover
            )
            .then(res => {
              if (res.data.status == "success") {
                this.$message({
                  message: "操作成功",
                  type: "success"
                });
                this.resetArticle();
              } else {
                this.$message.error("操作失败 " + res.data.status);
              }
            })
            .catch(err => {
              this.$message.error("操作失败 " + err);
            });
        } else {
          //更新文章
          let tagString = "";
          for (let tag of this.article.tags) {
            tagString += "," + tag;
          }
          tagString = tagString.substr(1);
          api
            .updateArticle(
              this.article.id,
              this.article.title,
              this.article.content,
              this.article.catagoryId,
              tagString,
              window.sessionStorage.getItem("username"),
              this.article.cover
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
      } else this.$message.warning("请输入标题和内容");
    },
    resetArticle() {
      this.article = {
        title: "",
        content: "",
        catagoryId: "",
        tags: [],
        cover: "",
        id: ""
      };
      window.localStorage.setItem("cArticle", "");
    }
  }
};
</script>
<style>
#editor {
  margin: 20px 20px;
}
.title,
.text {
  width: 80%;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
#sub-article {
  margin-top: 30px;
}
</style>