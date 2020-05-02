package cn.sakurablossom.freeblog;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Site {

    @Id
    private Integer site_id = 1;
    private String site_name;
    private String site_title;
    private String image_url;
    @Lob
    @Column(columnDefinition = "text")
    private String site_introduction;
    @Lob
    @Column(columnDefinition = "text")
    private String about_page;

    /**
     * @return the site_id
     */
    public Integer getSite_id() {
        return site_id;
    }

    /**
     * @param site_id the site_id to set
     */
    public void setSite_id(Integer site_id) {
        this.site_id = site_id;
    }

    /**
     * @return the site_name
     */
    public String getSite_name() {
        return site_name;
    }

    /**
     * @param site_name the site_name to set
     */
    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    /**
     * @return the site_title
     */
    public String getSite_title() {
        return site_title;
    }

    /**
     * @param site_title the site_title to set
     */
    public void setSite_title(String site_title) {
        this.site_title = site_title;
    }

    /**
     * @return the image_url
     */
    public String getImage_url() {
        return image_url;
    }

    /**
     * @param image_url the image_url to set
     */
    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    /**
     * @return the site_introduction
     */
    public String getSite_introduction() {
        return site_introduction;
    }

    /**
     * @param site_introduction the site_introduction to set
     */
    public void setSite_introduction(String site_introduction) {
        this.site_introduction = site_introduction;
    }

    /**
     * @return the about_page
     */
    public String getAbout_page() {
        return about_page;
    }

    /**
     * @param about_page the about_page to set
     */
    public void setAbout_page(String about_page) {
        this.about_page = about_page;
    }
}