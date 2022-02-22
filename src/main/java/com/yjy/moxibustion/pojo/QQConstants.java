package com.yjy.moxibustion.pojo;

/**
 * @author 杨景元
 * @date 2021/5/29 23:21
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * qq登录常量配置类
 */
@Configuration
public class QQConstants {
    @Value("${qqAppId}")
    private String qqAppId;

    @Value("${qqAppSecret}")
    private String qqAppSecret;

    @Value("${qqRedirectUrl}")
    private String qqRedirectUrl;

    public String getQqAppId() {
        return qqAppId;
    }

    public void setQqAppId(String qqAppId) {
        this.qqAppId = qqAppId;
    }

    public String getQqAppSecret() {
        return qqAppSecret;
    }

    public void setQqAppSecret(String qqAppSecret) {
        this.qqAppSecret = qqAppSecret;
    }

    public String getQqRedirectUrl() {
        return qqRedirectUrl;
    }

    public void setQqRedirectUrl(String qqRedirectUrl) {
        this.qqRedirectUrl = qqRedirectUrl;
    }
}
