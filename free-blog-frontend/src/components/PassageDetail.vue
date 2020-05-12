<template>
  <div>
    <div class="pg-title">{{passage.article_title}}</div>
    <div class="pg-author">{{passage.article_author+'————————'+passage.article_time}}</div>
    <div class="pg-content" v-html="content"></div>
  </div>
</template>
<script>
import showdown from "showdown";
import hljs from "highlight.js";
import "highlight.js/styles/github.css";
showdown.setOption("tables", true);
showdown.setOption("parseImgDimensions", true);
showdown.setOption("tasklists", true);
let converter = new showdown.Converter();
export default {
  name: "PassageDetail",
  props: ["passage"],
  data() {
    return {
      content: ""
    };
  },
  created() {
    this.content = converter.makeHtml(this.passage.article_content);
  },
  mounted() {
    document.querySelectorAll("pre code").forEach(block => {
      let lines = block.innerHTML.split("\n").length - 1;
      let numbering = document.createElement("ul");
      numbering.className += " pre-numbering";
      block.className += " has-numbering";
      for (let i = 1; i <= lines; i++) {
        let d = document.createElement("li");
        d.innerText = i;
        numbering.appendChild(d);
      }
      block.parentElement.appendChild(numbering);
      hljs.highlightBlock(block);
    });
  }
};
</script>
<style>
.pg-title {
  font-size: 30px;
  font-weight: bold;
  margin: 20px 0;
}
.pg-author {
  font-size: 15px;
  color: #666666;
  margin-bottom: 10px;
}
table {
  font-size: 13px;
  border-collapse: collapse;
  border-spacing: 0;
  empty-cells: show;
  border: 1px solid #eef2f8;
  width: 100%;
  margin: 13px 0;
}
table th {
  font-family: inherit;
  font-size: inherit;
  background: #f7f7f7 !important;
  white-space: nowrap;
  font-weight: 600;
  border: 1px solid #eef2f8 !important;
  padding: 8px 16px !important;
}
table td {
  border: 1px solid #eef2f8 !important;
  padding: 8px 16px !important;
}
pre {
  position: relative;
  margin-bottom: 24px;
  border-radius: 3px;
  border: 1px solid #c3ccd0;
  background: #fff;
  overflow: hidden;
}
code {
  display: block;
  padding: 12px 24px;
  overflow-y: auto;
  font-weight: 300;
  font-family: Menlo, monospace;
  font-size: 0.8em;
}
code.has-numbering {
  margin-left: 21px;
}
.pre-numbering {
  position: absolute;
  top: -3px;
  left: 0;
  width: 20px;
  padding: 11px 2px 12px 0;
  border-right: 1px solid #c3ccd0;
  border-radius: 3px 0 0 3px;
  background-color: #eee;
  text-align: right;
  font-family: Menlo, monospace;
  font-size: 14px;
  color: #aaa;
}
</style>