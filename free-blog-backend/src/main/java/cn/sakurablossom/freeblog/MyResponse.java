package cn.sakurablossom.freeblog;

public class MyResponse {
    public String status;
    public Object result;
    public int pages;

    public MyResponse(String status, Object result, int pages) {
        this.status = status;
        this.result = result;
        this.pages = pages;
    }
}