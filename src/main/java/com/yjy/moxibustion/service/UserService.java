package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.User;

/**
 * @author 杨景元
 * @date 2021/2/4 16:10
 */
public interface UserService extends BaseService<User> {


    //修改了user表，用于qq登录，故暂时注释
    /**
     * app用户登录
     * @param username
     * @param password
     * @return
    User login(String username,String password);*/

    /**
     * 通过name判断是否已经存入该用户了
     * @param name
     * @return
     */
    int userExistByName(String name);

    /**
     * 通过name，找到用户
     * @param name
     * @return
     */
    User selectByName(String name);
}
