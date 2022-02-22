package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "plan_feedback")
public class PlanFeedback {
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
     * 反馈治疗方案，所属类别id，即categoryID
     */
    @Column(name = "c_id")
    private Integer cId;

    /**
     * 反馈内容
     */
    private String content;

    private LocalDateTime created;

    private LocalDateTime updated;

    public PlanFeedback(Integer type, Integer uId, Integer cId, String content, LocalDateTime created, LocalDateTime updated) {
        this.type = type;
        this.uId = uId;
        this.cId = cId;
        this.content = content;
        this.created = created;
        this.updated = updated;
    }


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
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content;
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

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public PlanFeedback() {
    }

    @Override
    public String toString() {
        return "PlanFeedback{" +
                "id=" + id +
                ", type=" + type +
                ", uId=" + uId +
                ", cId=" + cId +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}