<template>
  <div>
    <el-table :data="comments" style="width: 100%">
      <el-table-column label="日期" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.comment_time }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="comment_content" label="内容" width="240"></el-table-column>
      <el-table-column prop="commentor_name" label="用户" width="180"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination layout="prev, pager, next" :page-count="pages" @current-change="pageTo"></el-pagination>
    </div>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  data() {
    return {
      comments: [],
      pages: 1
    };
  },
  created() {
    this.update();
  },
  methods: {
    handleDelete(row) {
      api
        .deleteComment(row.comment_id)
        .then(res => {
          if (res.data.status == "success") {
            this.$message.info("删除成功");
            this.update();
          } else this.$message("删除失败" + res.data.status);
        })
        .catch(err => this.$message.error("删除失败" + err));
    },
    pageTo(page) {
      api
        .getNewComments(8, page)
        .then(res => {
          if (res.data.status == "success") {
            this.comments = res.data.result;
          }
        })
        .catch(err => console.log(err));
    },
    update() {
      api
        .getNewComments(8, 1)
        .then(res => {
          if (res.data.status == "success") {
            this.comments = res.data.result;
            this.pages = res.data.pages;
          }
        })
        .catch(err => console.log(err));
    }
  }
};
</script>