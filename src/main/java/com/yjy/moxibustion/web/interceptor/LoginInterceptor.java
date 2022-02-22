package com.yjy.moxibustion.web.interceptor;

import com.yjy.moxibustion.pojo.Admin;
import com.yjy.moxibustion.utils.ConstantUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 杨景元
 * @date 2021/1/28 10:58
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = (Admin) request.getSession().getAttribute(ConstantUtils.SESSION_ADMIN);

        //为了测试qq登录，暂时关闭拦截器
        /*//未登录
        if (admin == null){
            request.setAttribute("msg","没有权限请先登录");
            response.sendRedirect("/");
            return false;
        }else {
            //放行
            return true;
        }*/
        return true;
    }
}
