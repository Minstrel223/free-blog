<template>
  <div id="preview" v-html="content"></div>
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
  name: "PreviewArticle",
  props: ["mdContent"],
  data() {
    return {
      content: {}
    };
  },
  created() {
    this.content = converter.makeHtml(this.mdContent);
    setTimeout(function() {
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
    }, 100);
  }
};
</script>
<style>
#preview {
  margin: 20px 20px;
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
  top: -18px;
  left: 0;
  width: 20px;
  padding: 12px 2px 12px 0;
  border-right: 1px solid #c3ccd0;
  border-radius: 3px 0 0 3px;
  background-color: #eee;
  text-align: right;
  font-family: Menlo, monospace;
  font-size: 0.8em;
  color: #aaa;
}
</style>