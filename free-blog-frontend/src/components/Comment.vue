<template>
  <div>
    <div class="commentor">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
        <b-form-textarea
          id="textarea"
          v-model="form.content"
          placeholder="写下你的评论吧"
          rows="3"
          max-rows="6"
          required
        ></b-form-textarea>

        <b-form-group id="input-group-1" label="Email address:" label-for="input-1">
          <b-form-input
            id="input-1"
            v-model="form.email"
            type="email"
            required
            placeholder="Enter email"
          ></b-form-input>
        </b-form-group>

        <b-form-group id="input-group-2" label="Your Name:" label-for="input-2">
          <b-form-input id="input-2" v-model="form.name" required placeholder="Enter name"></b-form-input>
        </b-form-group>

        <b-button type="submit" variant="primary" style="margin-right:30px">提交</b-button>
        <b-button type="reset" variant="danger">重置</b-button>
      </b-form>
    </div>

    <div class="comments">
      <b-card v-for="item of cms" :key="item.comment_id">
        <b-media>
          <template v-slot:aside>
            <b-icon icon="person" style="width: 50px; height: 50px; background:#ccc"></b-icon>
          </template>

          <h5 class="mt-0">{{item.commentor_name}}</h5>
          <p>{{item.comment_content}}</p>
          <template v-if="item.parent_comment">
            <b-media>
              <template v-slot:aside>
                <b-icon icon="person" style="width: 40px; height: 40px; background:#ccc"></b-icon>
              </template>

              <h5 class="mt-0">{{item.parent_comment.commentor_name}}</h5>
              <p class="mb-0">{{item.parent_comment.comment_content}}</p>
            </b-media>
          </template>
        </b-media>
        <br />
        <span style="float:left;font-size:10x;color:#ccc;margin-top:10px;">{{item.comment_time}}</span>
        <b-button class="reply-btn" variant="light" @click="reply(item)">
          <b-icon icon="chat"></b-icon>
        </b-button>
      </b-card>
    </div>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  name: "Comment",
  props: ["cms", "articleId"],
  data() {
    return {
      form: {
        content: "",
        email: "",
        name: ""
      },
      target_comment_id: "", //目标评论ID
      show: true
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      api
        .submitComment(
          this.form.name,
          this.form.email,
          this.form.content,
          this.articleId,
          this.target_comment_id
        )
        .then(res => {
          if (res.data.status == "success") {
            this.$emit("commentSuccess");
            this.$bvToast.toast("评论成功", {
              variant: "success",
              solid: true
            });
          } else {
            this.$bvToast.toast("评论失败", {
              variant: "danger",
              solid: true
            });
            console.log(res.data);
          }
        })
        .catch(err => {
          this.$bvToast("评论失败", {
            variant: "danger",
            solid: true
          });
          console.log(err);
        });
    },
    onReset(evt) {
      evt.preventDefault();
      // 清空表单
      console.log("reset");
      this.form.email = "";
      this.form.name = "";
      this.form.content = "";
      this.target_comment_id = "";
      //清除浏览器本地表单验证状态
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    reply(target) {
      this.form.content = "";
      document.querySelector(".commentor").scrollIntoView();
      window.scrollBy(0, -200);
      document
        .querySelector("textarea")
        .setAttribute("placeholder", "回复给" + target.commentor_name);
      document.querySelector("textarea").focus();
      this.target_comment_id = target.comment_id;
    }
  }
};
</script>
<style>
.commentor {
  margin: 50px;
}
.reply-btn {
  float: right;
}
</style>