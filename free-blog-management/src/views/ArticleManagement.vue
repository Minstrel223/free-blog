<template>
  <div>
    <el-table :data="articles" style="width: 100%">
      <el-table-column label="日期" width="200">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.article_time }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="article_title" label="标题" width="180"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
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
  name: "ArticleManagement",
  data() {
    return {
      articles: [],
      pages: 1
    };
  },
  created() {
    this.update();
  },
  methods: {
    handleEdit(row) {
      let ca = {
        title: row.article_title,
        content: row.article_content,
        catagoryId: row.catagory_id,
        cover: row.article_cover,
        id: row.article_id
      };
      api.getTags(row.article_id).then(res => {
        if (res.data.status == "success") {
          ca.tags = res.data.result;
          window.localStorage.setItem("cArticle", JSON.stringify(ca));
          this.$router.push("/");
        }
      });
    },
    handleDelete(row) {
      this.$confirm("此操作将永久删除该文章, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          api
            .deleteArticle(row.article_id)
            .then(res => {
              if (res.data.status == "success") {
                this.$message({
                  type: "success",
                  message: "删除成功!"
                });
                this.update();
              } else this.$message.error("删除失败" + res.data.status);
            })
            .catch(err => this.$message.error("删除失败" + err));
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    pageTo(page) {
      api
        .getNewArticles(8, page)
        .then(res => {
          if (res.data.status == "success") {
            this.articles = res.data.result;
          }
        })
        .catch(err => console.log(err));
    },
    update() {
      api
        .getNewArticles(8, 1)
        .then(res => {
          if (res.data.status == "success") {
            this.articles = res.data.result;
            this.pages = Number(res.data.pages);
          }
        })
        .catch(err => console.log(err));
    }
  }
};
</script>