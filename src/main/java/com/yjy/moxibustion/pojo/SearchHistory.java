package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "search_history")
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 这条搜索记录是哪个用户搜索的
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 搜索内容
     */
    private String title;

    /**
     * 返回的内容是哪类(xuewei=穴位,branch=按分科,position=按部位,mix=多种类型)
     */
    private String type;

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
     * 获取这条搜索记录是哪个用户搜索的
     *
     * @return u_id - 这条搜索记录是哪个用户搜索的
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置这条搜索记录是哪个用户搜索的
     *
     * @param uId 这条搜索记录是哪个用户搜索的
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * 获取搜索内容
     *
     * @return title - 搜索内容
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置搜索内容
     *
     * @param title 搜索内容
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取返回的内容是哪类(xuewei=穴位,branch=按分科,position=按部位,mix=多种类型)
     *
     * @return type - 返回的内容是哪类(xuewei=穴位,branch=按分科,position=按部位,mix=多种类型)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置返回的内容是哪类(xuewei=穴位,branch=按分科,position=按部位,mix=多种类型)
     *
     * @param type 返回的内容是哪类(xuewei=穴位,branch=按分科,position=按部位,mix=多种类型)
     */
    public void setType(String type) {
        this.type = type;
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