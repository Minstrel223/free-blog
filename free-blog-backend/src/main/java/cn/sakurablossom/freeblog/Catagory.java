package cn.sakurablossom.freeblog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Catagory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer catagory_id;

    private String catagory_name;

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
     * @return the catagory_name
     */
    public String getCatagory_name() {
        return catagory_name;
    }

    /**
     * @param catagory_name the catagory_name to set
     */
    public void setCatagory_name(String catagory_name) {
        this.catagory_name = catagory_name;
    }
}