package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    /**
     * 所属用户ID
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 所属文章ID
     */
    @Column(name = "a_id")
    private Integer aId;

    private LocalDateTime created;

    private LocalDateTime updated;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取所属用户ID
     *
     * @return u_id - 所属用户ID
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置所属用户ID
     *
     * @param uId 所属用户ID
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 获取所属文章ID
     *
     * @return a_id - 所属文章ID
     */
    public Integer getaId() {
        return aId;
    }

    /**
     * 设置所属文章ID
     *
     * @param aId 所属文章ID
     */
    public void setaId(Integer aId) {
        this.aId = aId;
    }

    /**
     * @return created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * @param created
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * @return updated
     */
    public LocalDateTime getUpdated() {
        return updated;
    }

    /**
     * @param updated
     */
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}