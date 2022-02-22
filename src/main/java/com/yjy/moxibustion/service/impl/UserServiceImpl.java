package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.UserMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.User;
import com.yjy.moxibustion.service.UserService;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 杨景元
 * @date 2021/2/4 16:10
 */
@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User, UserMapper> implements UserService {
    @Override
    public RestResp save(User user){
        user.setUpdated(LocalDateTime.now());
        if (user.getId() == null){
            //新增用户
            user.setCreated(LocalDateTime.now());
            mapper.insert(user);
        }else {
            //编辑用户
            mapper.updateByPrimaryKeySelective(user);
        }
        return RestResp.success("信息保存成功!");
    }

    @Override
    public int userExistByName(String name) {
        Example example = new Example(User.class);
        example.and().andEqualTo("username",name);
        List<User> users = mapper.selectByExample(example);
        return users.size();
    }

    @Override
    public User selectByName(String name) {
        Example example = new Example(User.class);
        example.and().andEqualTo("username",name);
        List<User> users = mapper.selectByExample(example);
        User user = users.get(0);
        return user;
    }
}
