<template>
  <div class="row" style="width:95%;margin:0 auto">
    <div class="col-lg-3"></div>
    <div class="passage col-lg-6">
      <passage-detail :passage="article"></passage-detail>
      <comment :cms="comments" :article-id="article.article_id" @commentSuccess="updateComments()"></comment>
    </div>
    <div class="col-lg d-none d-lg-block"></div>
  </div>
</template>
<script>
import PassageDetail from "../components/PassageDetail";
import Comment from "../components/Comment";
import api from "../service/api";

export default {
  name: "Passage",
  data() {
    return {
      article: Object,
      comments: new Array()
    };
  },
  created() {
    this.article = JSON.parse(window.localStorage.getItem("currentArticle"));
    api.increaseView(this.article.article_id);
    this.updateComments();
    window.scroll(0, 0);
  },
  methods: {
    updateComments() {
      this.comments = [];
      let mthis = this;
      api
        .getComments(mthis.article.article_id)
        .then(res => {
          let cms = res.data.result;
          for (let cmt of cms) {
            (function(cmt) {
              if (cmt.parent_comment_id) {
                api
                  .getCommentById(cmt.parent_comment_id)
                  .then(res => {
                    cmt.parent_comment = res.data.result;
                    mthis.comments.push(cmt);
                  })
                  .catch(error => console.log(error));
              } else mthis.comments.push(cmt);
            })(cmt);
          }
        })
        .catch(error => console.log(error));
      setTimeout(
        () => this.comments.sort((a, b) => b.comment_id - a.comment_id),
        2000
      );
    }
  },
  components: {
    PassageDetail,
    Comment
  }
};
</script>
