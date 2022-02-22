package com.yjy.moxibustion.web.controller.app;

import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.User;
import com.yjy.moxibustion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * app端的用户controller
 * @author 杨景元
 * @date 2021/2/4 17:44
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    /*@PostMapping("/login")
    public RestResp login(String username, String password){
        User user = userService.login(username, password);
        if (user != null){
            //登录成功
            return RestResp.success("success",user);
        }else {
            //登录失败
            return RestResp.fail();
        }
    }*/

    /**
     * 用户注册
     * @param username
     * @param password
     * @param phone
     * @param email
     * @return
     */
    /*@PostMapping("/register")
    public RestResp register(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        RestResp result = userService.save(user);
        if (result.getStatus() == 200){
            //保存成功
            return result;
        }else {
            return RestResp.fail("用户注册失败");
        }
    }*/
}
