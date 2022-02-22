package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "treatment_plan")
public class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     */
    private Integer type;

    /**
     * 所属用户ID
     */
    @Column(name = "u_id")
    private Integer uId;

    /**
     * 治疗方案名称
     */
    private String name;

    private String gaishu;

    private String zhengzhuang;

    private String bingyin;

    private String quxue;

    private String anli;

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
     * 获取该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     *
     * @return type - 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     *
     * @param type 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return gaishu
     */
    public String getGaishu() {
        return gaishu;
    }

    /**
     * @param gaishu
     */
    public void setGaishu(String gaishu) {
        this.gaishu = gaishu;
    }

    /**
     * @return zhengzhuang
     */
    public String getZhengzhuang() {
        return zhengzhuang;
    }

    /**
     * @param zhengzhuang
     */
    public void setZhengzhuang(String zhengzhuang) {
        this.zhengzhuang = zhengzhuang;
    }

    /**
     * @return bingyin
     */
    public String getBingyin() {
        return bingyin;
    }

    /**
     * @param bingyin
     */
    public void setBingyin(String bingyin) {
        this.bingyin = bingyin;
    }

    /**
     * @return quxue
     */
    public String getQuxue() {
        return quxue;
    }

    /**
     * @param quxue
     */
    public void setQuxue(String quxue) {
        this.quxue = quxue;
    }

    /**
     * @return anli
     */
    public String getAnli() {
        return anli;
    }

    /**
     * @param anli
     */
    public void setAnli(String anli) {
        this.anli = anli;
    }

    /**
     * @return tihui
     */
    public String getTihui() {
        return tihui;
    }

    /**
     * @param tihui
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}