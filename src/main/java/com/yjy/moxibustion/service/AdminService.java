package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.Admin;

/**
 * @author 杨景元
 * @date 2021/1/24 9:52
 */
public interface AdminService extends BaseService<Admin> {
    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    Admin login(String username,String password);
}
