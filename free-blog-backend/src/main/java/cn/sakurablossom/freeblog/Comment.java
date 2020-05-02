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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer comment_id;
    @Lob
    @Column(columnDefinition = "text", nullable = false)
    private String comment_content;
    private String commentor_name;
    private String commentor_email;
    private Integer article_id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private Date comment_time;
    @Column(nullable = true)
    private Integer parent_comment_id;

    /**
     * @return the comment_id
     */
    public Integer getComment_id() {
        return comment_id;
    }

    /**
     * @param comment_id the comment_id to set
     */
    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    /**
     * @return the comment_content
     */
    public String getComment_content() {
        return comment_content;
    }

    /**
     * @param comment_content the comment_content to set
     */
    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    /**
     * @return the commentor_email
     */
    public String getCommentor_email() {
        return commentor_email;
    }

    /**
     * @param commentor_email the commentor_email to set
     */
    public void setCommentor_email(String commentor_email) {
        this.commentor_email = commentor_email;
    }

    /**
     * @return the commentor_name
     */
    public String getCommentor_name() {
        return commentor_name;
    }

    /**
     * @param commentor_name the commentor_name to set
     */
    public void setCommentor_name(String commentor_name) {
        this.commentor_name = commentor_name;
    }

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
     * @return the comment_time
     */
    public Date getComment_time() {
        return comment_time;
    }

    /**
     * @param comment_time the comment_time to set
     */
    public void setComment_time(Date comment_time) {
        this.comment_time = comment_time;
    }

    /**
     * @return the parent_comment_id
     */
    public Integer getParent_comment_id() {
        return parent_comment_id;
    }

    /**
     * @param parent_comment_id the parent_comment_id to set
     */
    public void setParent_comment_id(Integer parent_comment_id) {
        this.parent_comment_id = parent_comment_id;
    }
}