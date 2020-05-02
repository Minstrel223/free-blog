<template>
  <div>
    <el-table :data="catalog" style="width: 100%">
      <el-table-column prop="catagory_name" label="分类名称" width="180"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">重命名</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="edit">
      <el-input v-model="input" class="ed-input"></el-input>
      <el-button @click="handleSave()">{{action}}</el-button>
    </div>
  </div>
</template>
<script>
import api from "../service/api";
export default {
  data() {
    return {
      catalog: [],
      input: "",
      tempIndex: -1, //当前编辑分类的index, -1 代表新分类
      action: "新建"
    };
  },
  created() {
    this.update();
  },
  methods: {
    handleEdit(index, row) {
      this.action = "重命名";
      this.input = row.catagory_name;
      this.tempIndex = index;
    },
    handleDelete(row) {
      api
        .deleteCatagory(row.catagory_id)
        .then(res => {
          if (res.data.status == "success") {
            this.$message({
              message: "操作成功",
              type: "success"
            });
            this.update();
          } else {
            this.$message.error("操作失败 " + res.data.status);
            this.update();
          }
        })
        .catch(err => {
          this.$message.error("操作失败 " + err);
          this.update();
        });
    },
    handleSave() {
      if (this.input) {
        if (this.tempIndex == -1) {
          //新增分类
          api
            .addCatagory(this.input)
            .then(res => {
              if (res.data.status == "success") {
                this.$message({
                  message: "操作成功",
                  type: "success"
                });
                this.update();
              } else {
                this.$message.error("操作失败 " + res.data.status);
                this.update();
              }
            })
            .catch(err => {
              this.$message.error("操作失败 " + err);
              this.update();
            });
        } else {
          //更改已有分类
          api
            .updateCatagory(
              this.catalog[this.tempIndex].catagory_id,
              this.input
            )
            .then(res => {
              if (res.data.status == "success") {
                this.$message({
                  message: "操作成功",
                  type: "success"
                });
                this.update();
              } else {
                this.$message.error("操作失败 " + res.data.status);
                this.update();
              }
            })
            .catch(err => {
              this.$message.error("操作失败 " + err);
              this.update();
            });
        }
      }
    },
    update() {
      api.getCatalog().then(res => (this.catalog = res.data.result));
      this.input = "";
      this.tempIndex = -1;
      this.action = "新建";
    }
  }
};
</script>
<style>
.edit {
  margin-top: 10px;
}
.ed-input {
  width: 200px;
  margin-right: 10px;
}
</style>