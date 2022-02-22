package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "position_content")
public class PositionContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 部位内容类目ID
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 概述
     */
    private String gaishu;

    /**
     * 症状
     */
    private String zhengzhuang;

    /**
     * 病因分析与鉴别
     */
    private String bingyin;

    /**
     * 艾灸取穴
     */
    private String aijiuquxue;

    /**
     * 案例
     */
    private String anli;

    /**
     * 艾灸体会
     */
    private String tihui;

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
     * 获取部位内容类目ID
     *
     * @return category_id - 部位内容类目ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置部位内容类目ID
     *
     * @param categoryId 部位内容类目ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取概述
     *
     * @return gaishu - 概述
     */
    public String getGaishu() {
        return gaishu;
    }

    /**
     * 设置概述
     *
     * @param gaishu 概述
     */
    public void setGaishu(String gaishu) {
        this.gaishu = gaishu;
    }

    /**
     * 获取症状
     *
     * @return zhengzhuang - 症状
     */
    public String getZhengzhuang() {
        return zhengzhuang;
    }

    /**
     * 设置症状
     *
     * @param zhengzhuang 症状
     */
    public void setZhengzhuang(String zhengzhuang) {
        this.zhengzhuang = zhengzhuang;
    }

    /**
     * 获取病因分析与鉴别
     *
     * @return bingyin - 病因分析与鉴别
     */
    public String getBingyin() {
        return bingyin;
    }

    /**
     * 设置病因分析与鉴别
     *
     * @param bingyin 病因分析与鉴别
     */
    public void setBingyin(String bingyin) {
        this.bingyin = bingyin;
    }

    /**
     * 获取艾灸取穴
     *
     * @return aijiuquxue - 艾灸取穴
     */
    public String getAijiuquxue() {
        return aijiuquxue;
    }

    /**
     * 设置艾灸取穴
     *
     * @param aijiuquxue 艾灸取穴
     */
    public void setAijiuquxue(String aijiuquxue) {
        this.aijiuquxue = aijiuquxue;
    }

    /**
     * 获取案例
     *
     * @return anli - 案例
     */
    public String getAnli() {
        return anli;
    }

    /**
     * 设置案例
     *
     * @param anli 案例
     */
    public void setAnli(String anli) {
        this.anli = anli;
    }

    /**
     * 获取艾灸体会
     *
     * @return tihui - 艾灸体会
     */
    public String getTihui() {
        return tihui;
    }

    /**
     * 设置艾灸体会
     *
     * @param tihui 艾灸体会
     */
    public void setTihui(String tihui) {
        this.tihui = tihui;
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