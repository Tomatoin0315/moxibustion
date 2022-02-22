package com.yjy.moxibustion.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 已废弃！
 * @author 杨景元
 * @date 2021/1/16 21:18
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //使用Mabatis第一步：获取SqlSessionFactory对象
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //有了SqlsessionFactory，我们就可以从中获得SqlSession的实例了。
    public static SqlSession getSqlSession(){

        return sqlSessionFactory.openSession(true); //如果不设置参数或者参数为false就是手动提交事务，参数设置为true就是自动提交事务
    }
}
