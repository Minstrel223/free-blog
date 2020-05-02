package cn.sakurablossom.freeblog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String tag_name;

    private Integer article_id;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the tag_name
     */
    public String getTag_name() {
        return tag_name;
    }

    /**
     * @param tag_name the tag_name to set
     */
    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
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
}