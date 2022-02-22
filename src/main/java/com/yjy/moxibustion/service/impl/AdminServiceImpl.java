package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.AdminMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.Admin;
import com.yjy.moxibustion.service.AdminService;
import com.yjy.moxibustion.utils.BeanValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/24 9:53
 */
@Service
public class AdminServiceImpl extends AbstractBaseServiceImpl<Admin, AdminMapper> implements AdminService {

    @Override
    public RestResp save(Admin admin){
        String validator = BeanValidator.validator(admin);
        //验证不通过
        if (validator != null){
            return RestResp.fail("fail",validator);
        }else {
            //通过验证
            admin.setUpdated(LocalDateTime.now());
            //新增用户
            if (admin.getId() == null){
                //密码需要加密
                admin.setPassword(DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()));
                admin.setCreated(LocalDateTime.now());
                mapper.insert(admin);
            }else {
                //编辑用户
                mapper.updateByPrimaryKeySelective(admin);
            }
            return RestResp.success("信息保存成功!");
        }
    }


    @Override
    public Admin login(String username, String password) {
        Example example = new Example(Admin.class);
        example.and().andEqualTo("username",username);
        List<Admin> admins = mapper.selectByExample(example);
        //List集合若为空的话，不可以使用get，会下标越界
        if (admins!=null){
            if (admins.get(0) != null){
                //明文密码加密
                String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

                //判断加密后的密码和数据库中存放的密码是否匹配，匹配则表示允许登录
                if (md5Password.equals(admins.get(0).getPassword())){
                    return admins.get(0);
                }
            }
        }

        return null;
    }
}
