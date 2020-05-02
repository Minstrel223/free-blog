package cn.sakurablossom.freeblog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer article_id;

    @Column(nullable = false)
    private String article_title;

    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String article_content;

    private String article_author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private Date article_time;

    private String article_cover;

    @Column(nullable = true)
    private Integer catagory_id;

    private int article_views;

    private int comments_number;

    /**
     * @return the article_id
     */
    public Integer getArticle_id() {
        return article_id;
    }

    /**
     * @param article_id the article_id to set
     */
    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    /**
     * @return the article_title
     */
    public String getArticle_title() {
        return article_title;
    }

    /**
     * @param article_title the article_title to set
     */
    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    /**
     * @return the article_content
     */
    public String getArticle_content() {
        return article_content;
    }

    /**
     * @param article_content the article_content to set
     */
    public void setArticle_content(String article_content) {
        this.article_content = article_content;
    }

    /**
     * @return the article_author
     */
    public String getArticle_author() {
        return article_author;
    }

    /**
     * @param article_author the article_author to set
     */
    public void setArticle_author(String article_author) {
        this.article_author = article_author;
    }

    /**
     * @return the article_time
     */
    public Date getArticle_time() {
        return article_time;
    }

    /**
     * @param article_time the article_time to set
     */
    public void setArticle_time(Date article_time) {
        this.article_time = article_time;
    }

    /**
     * @return the article_cover
     */
    public String getArticle_cover() {
        return article_cover;
    }

    /**
     * @param article_cover the article_cover to set
     */
    public void setArticle_cover(String article_cover) {
        this.article_cover = article_cover;
    }

    /**
     * @return the catagory_id
     */
    public Integer getCatagory_id() {
        return catagory_id;
    }

    /**
     * @param catagory_id the catagory_id to set
     */
    public void setCatagory_id(Integer catagory_id) {
        this.catagory_id = catagory_id;
    }

    /**
     * @return the article_views
     */
    public int getArticle_views() {
        return article_views;
    }

    /**
     * @param article_views the article_views to set
     */
    public void setArticle_views(int article_views) {
        this.article_views = article_views;
    }

    /**
     * @return the comments_number
     */
    public int getComments_number() {
        return comments_number;
    }

    /**
     * @param comments_number the comments_number to set
     */
    public void setComments_number(int comments_number) {
        this.comments_number = comments_number;
    }
}