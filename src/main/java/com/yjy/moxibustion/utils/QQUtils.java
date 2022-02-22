package com.yjy.moxibustion.utils;

import com.alibaba.fastjson.JSONObject;
import com.yjy.moxibustion.pojo.QQUserInfo;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author 杨景元
 * @date 2021/6/7 2:25
 */
public class QQUtils {
    public static String getAccessToken(String url) throws IOException {
        String token = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null){
            String result = EntityUtils.toString(entity, "UTF-8");
            String[] arr = result.split("&");
            for (String s : arr) {
                if (s.indexOf("=") >= 0){
                    token = s.substring(s.indexOf("=") + 1);
                    break;
                }
            }

            //System.out.println(result);
        }
        return token;
    }

    public static String getOpenId(String url) throws IOException {
        String openId = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null){
            String result = EntityUtils.toString(entity, "UTF-8");
            JSONObject object = JsonUtils.castToJson(result);
            openId = object.getString("openid");
        }
        return openId;
    }

    public static QQUserInfo getUserInfo(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity,"UTF-8");
        QQUserInfo qqUserInfo = JSONObject.parseObject(result, QQUserInfo.class);
        return qqUserInfo;
    }
}
