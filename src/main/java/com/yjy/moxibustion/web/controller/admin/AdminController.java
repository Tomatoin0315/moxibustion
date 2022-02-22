package com.yjy.moxibustion.web.controller.admin;

import com.yjy.moxibustion.pojo.Admin;
import com.yjy.moxibustion.service.AdminService;
import com.yjy.moxibustion.utils.ConstantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.websocket.Session;

/**
 * @author 杨景元
 * @date 2021/1/28 11:03
 */
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/login")
    public String login(@RequestParam("account") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model){
        //System.out.println("user:"+username + "    pwd:" + password);
        Admin admin = adminService.login(username, password);
        //登录成功
        if (admin != null){
            //将登录信息放入会话
            session.setAttribute(ConstantUtils.SESSION_ADMIN,admin);
            return "redirect:/admin/index";
        }else {
            //登录失败
            model.addAttribute("msg","用户名或密码错误，请重新输入");
            return "redirect:/admin/";
        }
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return "redirect:/";
    }

    /**
     * 用户名的后端验证
     * 使用Validator后端验证，写上@Valid，
     * 在后紧跟一个BindingResult，就可以获取到校验的结果
     * @param admin
     * @return
     */
    @PostMapping(value = "check")
    public boolean validatorCheck(@Valid @RequestParam("admin") String admin,
                                  BindingResult result){
        if (result != null && result.getErrorCount() > 0){
            return false;
        }else {
            return true;
        }
    }
}
