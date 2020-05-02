<template>
  <div>
    <div id="new-article">
      <el-button @click="show=false">编辑</el-button>
      <el-button @click="show=true">预览</el-button>
    </div>
    <edit-article ref="edit" v-show="!show"></edit-article>
    <preview-article :md-content="content" v-if="show"></preview-article>
  </div>
</template>
<script>
import EditArticle from "../components/EditArticle";
import PreviewArticle from "../components/PreviewArticle";
export default {
  name: "NewArticle",
  data() {
    return {
      show: false
    };
  },
  computed: {
    content: function() {
      return this.$refs.edit.article.content;
    }
  },
  components: {
    EditArticle,
    PreviewArticle
  }
};
</script>
<style>
#new-article {
  margin: 5px 5px;
}
</style>