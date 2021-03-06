package com.yjy.moxibustion.web.controller.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yjy.moxibustion.pojo.QQConstants;
import com.yjy.moxibustion.pojo.QQUserInfo;
import com.yjy.moxibustion.pojo.User;
import com.yjy.moxibustion.service.UserService;
import com.yjy.moxibustion.utils.HttpClientUtils;
import com.yjy.moxibustion.utils.QQUtils;
import com.yjy.moxibustion.utils.URLEncodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 杨景元
 * @date 2021/5/29 23:28
 */
@Controller
public class QQController {
    /**
     * QQ ：读取Appid相关配置信息静态类
     */
    @Autowired
    private QQConstants constants;

    @Autowired
    private UserService userService;

    @GetMapping("/qq/oauth")
    public String oauth(HttpSession session){
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        session.setAttribute("state",uuid);

        // 回调地址 ,回调地址要进行Encode转码
        String redirect_uri = constants.getQqRedirectUrl();
        //URLEncodeUtils.getURLEncoderString(redirect_uri)

        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code" +
                "&client_id=" + constants.getQqAppId() +
                "&redirect_uri=" + constants.getQqRedirectUrl() +
                "&state=" + uuid;

        System.out.println(url);

        return "redirect:" + url;
    }

    @GetMapping("/qq")
    public String qq(HttpServletRequest request,
                     HttpServletResponse response){

        HttpSession session = request.getSession();
        //String uuid = (String) session.getAttribute("state");
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        //System.out.println("uuid="+uuid);
        System.out.println("code="+code);
        System.out.println("state="+state);

        /*if (state != null){
            if (!uuid.equals(state)){
                throw new RuntimeException("QQ: state错误");
            }
        }*/

        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + constants.getQqAppId() +
                "&client_secret=" + constants.getQqAppSecret() +
                "&code=" + code +
                "&redirect_uri" + constants.getQqRedirectUrl();

        String token = null;
        try {
            token = QQUtils.getAccessToken(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = "https://graph.qq.com/oauth2.0/me?access_token=" + token;
        String openId = null;
        try {
            openId = QQUtils.getOpenId(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        url = "https://graph.qq.com/user/get_user_info" +
                "?access_token=" + token +
                "&oauth_consumer_key=" + constants.getQqAppId() +
                "&openid" + openId;

        //获取用户的个人信息
        QQUserInfo userInfo = null;
        try {
            userInfo = QQUtils.getUserInfo(url);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int num = userService.userExistByName(userInfo.getNickname());
        if (num == 0){
            //代表没有该用户，则保存用户进数据库
            User user = new User();
            user.setUsername(userInfo.getNickname());
            user.setGender(userInfo.getGender());
            user.setFigureUrl(userInfo.getFigureurl());
            userService.save(user);
        }

        User user = userService.selectByName(userInfo.getNickname());
        if (user != null){
            session.setAttribute("user",user);
            return "redirect:/";
        }

        return "redirect:/";
    }


    /**
     * qq登录测试页
     * @return
     */
    @GetMapping("/qqLoginTest")
    public String login(){
        return "qqtest";
    }

    /**
     * 获得跳转到qq登录页的url,前台直接a连接访问
     *
     * @author wangsong
     * @date 2019年6月18日 下午8:29:20
     * @param session
     * @return
     * @throws Exception
     */
    @GetMapping("/getQQCode")
    public String getCode(HttpSession session, Model model) throws Exception {
        // 拼接url
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/authorize?");
        url.append("response_type=code");
        url.append("&client_id=" + constants.getQqAppId());
        // 回调地址 ,回调地址要进行Encode转码
        String redirect_uri = constants.getQqRedirectUrl();
        // 转码
        url.append("&redirect_uri=" + URLEncodeUtils.getURLEncoderString(redirect_uri));
        url.append("&state=ok");
        // HttpClientUtils.get(url.toString(), "UTF-8");
        System.out.println(url.toString());
        model.addAttribute("url", url);
        return "/";
    }



    /**
     * 开始登录
     *
     * @param code
     * @param
    //     * @param 实际业务：token过期调用刷新    token重新获取token信息
    //     * @param 数据库字段: accessToken，expiresIn，refreshToken，openId
     * @return
     * @throws Exception
     */
    @GetMapping("/qq1")
    @ResponseBody
    public QQUserInfo QQLogin(String code, Model model) throws Exception {
        if (code != null) {
            System.out.println(code);
        }
        //获取tocket
        Map<String, Object> qqProperties = getToken(code);
        //获取openId(每个用户的openId都是唯一不变的)
        String openId = getOpenId(qqProperties);
        qqProperties.put("openId",openId);

        //tocket过期刷新token
        //Map<String, Object> refreshToken = refreshToken(qqProperties);

        //获取数据
        QQUserInfo	userInfo =  getUserInfo(qqProperties);
        return userInfo;
    }

    /**
     * 获得token信息（授权，每个用户的都不一致） --> 获得token信息该步骤返回的token期限为一个月
     *
     //     * @param (保存到Map<String,String> qqProperties)
     * @author wangsong
     * @return
     * @throws Exception
     * @date 2019年6月18日 下午8:56:45
     */
    public Map<String, Object> getToken(String code) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=authorization_code");

        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&code=" + code);
        // 回调地址
        String redirect_uri = constants.getQqRedirectUrl();
        // 转码
        url.append("&redirect_uri=" + URLEncodeUtils.getURLEncoderString(redirect_uri));
        // 获得token
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        System.out.println("url:" + url.toString());
        // 把token保存
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");
        //token信息
        Map<String,Object > qqProperties = new HashMap<String,Object >();
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", refreshToken);
        return qqProperties;
    }

    /**
     * 刷新token 信息（token过期，重新授权）
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/refreshToken")
    public Map<String,Object> refreshToken(Map<String,Object> qqProperties) throws Exception {
        // 获取refreshToken
        String refreshToken = (String) qqProperties.get("refreshToken");
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/token?");
        url.append("grant_type=refresh_token");
        url.append("&client_id=" + constants.getQqAppId());
        url.append("&client_secret=" + constants.getQqAppSecret());
        url.append("&refresh_token=" + refreshToken);
        System.out.println("url:" + url.toString());
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        // 把新获取的token存到map中
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String newRefreshToken = StringUtils.substringAfterLast(items[2], "=");
        //重置信息
        qqProperties.put("accessToken", accessToken);
        qqProperties.put("expiresIn", String.valueOf(expiresIn));
        qqProperties.put("refreshToken", newRefreshToken);
        return qqProperties;
    }

    /**
     * 获取用户openId（根据token）
     *
     //     * @param 把openId存到map中
     * @return
     * @throws Exception
     */
    public String getOpenId(Map<String,Object> qqProperties) throws Exception {
        // 获取保存的用户的token
        String accessToken = (String) qqProperties.get("accessToken");
        if (!StringUtils.isNotEmpty(accessToken)) {
            // return "未授权";
        }
        StringBuilder url = new StringBuilder("https://graph.qq.com/oauth2.0/me?");
        url.append("access_token=" + accessToken);
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        String openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        return openId;
    }

    /**
     * 根据token,openId获取用户信息
     */
    public QQUserInfo getUserInfo(Map<String,Object> qqProperties) throws Exception {
        // 取token
        String accessToken = (String) qqProperties.get("accessToken");
        String openId = (String) qqProperties.get("openId");
        if (!StringUtils.isNotEmpty(accessToken) || !StringUtils.isNotEmpty(openId)) {
            return null;
        }
        //拼接url
        StringBuilder url = new StringBuilder("https://graph.qq.com/user/get_user_info?");
        url.append("access_token=" + accessToken);
        url.append("&oauth_consumer_key=" + constants.getQqAppId());
        url.append("&openid=" + openId);
        // 获取qq相关数据
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        Object json = JSON.parseObject(result, QQUserInfo.class);
        QQUserInfo userInfo = (QQUserInfo) json;
        return userInfo;
    }
}

