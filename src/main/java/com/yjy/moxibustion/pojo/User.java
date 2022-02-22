package com.yjy.moxibustion.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;

@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    /**
     * 性别，若获取不到，默认为“男”
     */
    private String gender;

    /**
     * 大小为30×30像素的QQ空间头像URL
     */
    @Column(name = "figure_url")
    private String figureUrl;

    @Column(name = "is_doctor")
    private Integer isDoctor;

    /**
     * 所属医师认证id
     */
    private Integer did;

    /**
     * 医师证明
     */
    @Column(name = "doctor_prove")
    private String doctorProve;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取性别，若获取不到，默认为“男”
     *
     * @return gender - 性别，若获取不到，默认为“男”
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置性别，若获取不到，默认为“男”
     *
     * @param gender 性别，若获取不到，默认为“男”
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取大小为30×30像素的QQ空间头像URL
     *
     * @return figure_url - 大小为30×30像素的QQ空间头像URL
     */
    public String getFigureUrl() {
        return figureUrl;
    }

    /**
     * 设置大小为30×30像素的QQ空间头像URL
     *
     * @param figureUrl 大小为30×30像素的QQ空间头像URL
     */
    public void setFigureUrl(String figureUrl) {
        this.figureUrl = figureUrl;
    }

    /**
     * @return is_doctor
     */
    public Integer getIsDoctor() {
        return isDoctor;
    }

    /**
     * @param isDoctor
     */
    public void setIsDoctor(Integer isDoctor) {
        this.isDoctor = isDoctor;
    }

    /**
     * 获取所属医师认证id
     *
     * @return did - 所属医师认证id
     */
    public Integer getDid() {
        return did;
    }

    /**
     * 设置所属医师认证id
     *
     * @param did 所属医师认证id
     */
    public void setDid(Integer did) {
        this.did = did;
    }

    /**
     * 获取医师证明
     *
     * @return doctor_prove - 医师证明
     */
    public String getDoctorProve() {
        return doctorProve;
    }

    /**
     * 设置医师证明
     *
     * @param doctorProve 医师证明
     */
    public void setDoctorProve(String doctorProve) {
        this.doctorProve = doctorProve;
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