package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "xuewei_content")
public class XueweiContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分科内容类目ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 国际代码
     */
    private String guojidaima;

    /**
     * 特定穴
     */
    private String tedingxue;

    /**
     * 定位
     */
    private String dingwei;

    /**
     * 取穴法
     */
    private String quxuefa;

    /**
     * 主治
     */
    private String zhuzhi;

    /**
     * 艾灸参数
     */
    private String canshu;

    /**
     * 经验应用
     */
    private String yingyong;

    private String picture;

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
     * 获取分科内容类目ID
     *
     * @return category_id - 分科内容类目ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分科内容类目ID
     *
     * @param categoryId 分科内容类目ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取国际代码
     *
     * @return guojidaima - 国际代码
     */
    public String getGuojidaima() {
        return guojidaima;
    }

    /**
     * 设置国际代码
     *
     * @param guojidaima 国际代码
     */
    public void setGuojidaima(String guojidaima) {
        this.guojidaima = guojidaima;
    }

    /**
     * 获取特定穴
     *
     * @return tedingxue - 特定穴
     */
    public String getTedingxue() {
        return tedingxue;
    }

    /**
     * 设置特定穴
     *
     * @param tedingxue 特定穴
     */
    public void setTedingxue(String tedingxue) {
        this.tedingxue = tedingxue;
    }

    /**
     * 获取定位
     *
     * @return dingwei - 定位
     */
    public String getDingwei() {
        return dingwei;
    }

    /**
     * 设置定位
     *
     * @param dingwei 定位
     */
    public void setDingwei(String dingwei) {
        this.dingwei = dingwei;
    }

    /**
     * 获取取穴法
     *
     * @return quxuefa - 取穴法
     */
    public String getQuxuefa() {
        return quxuefa;
    }

    /**
     * 设置取穴法
     *
     * @param quxuefa 取穴法
     */
    public void setQuxuefa(String quxuefa) {
        this.quxuefa = quxuefa;
    }

    /**
     * 获取主治
     *
     * @return zhuzhi - 主治
     */
    public String getZhuzhi() {
        return zhuzhi;
    }

    /**
     * 设置主治
     *
     * @param zhuzhi 主治
     */
    public void setZhuzhi(String zhuzhi) {
        this.zhuzhi = zhuzhi;
    }

    /**
     * 获取艾灸参数
     *
     * @return canshu - 艾灸参数
     */
    public String getCanshu() {
        return canshu;
    }

    /**
     * 设置艾灸参数
     *
     * @param canshu 艾灸参数
     */
    public void setCanshu(String canshu) {
        this.canshu = canshu;
    }

    /**
     * 获取经验应用
     *
     * @return yingyong - 经验应用
     */
    public String getYingyong() {
        return yingyong;
    }

    /**
     * 设置经验应用
     *
     * @param yingyong 经验应用
     */
    public void setYingyong(String yingyong) {
        this.yingyong = yingyong;
    }

    /**
     * @return picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @param picture
     */
    public void setPicture(String picture) {
        this.picture = picture;
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