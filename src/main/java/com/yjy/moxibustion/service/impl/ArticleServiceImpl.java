package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.ArticleMapper;
import com.yjy.moxibustion.pojo.Article;
import com.yjy.moxibustion.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/6/1 23:04
 */
@Service
public class ArticleServiceImpl extends AbstractBaseServiceImpl<Article, ArticleMapper> implements ArticleService {


    @Override
    public List<Map<String, Object>> articleTable() {
        List<Article> articles = mapper.selectAll();
        List<Map<String,Object>> result = new ArrayList<>();
        for (Article article : articles) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",article.getId());
            map.put("title",article.getTitle());
            map.put("created",article.getCreated());
            map.put("updated",article.getUpdated());
            result.add(map);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> articleTableSearch(String name) {
        List<Map<String, Object>> list = articleTable();
        List<Integer> resultNum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String title = (String) map.get("title");
            if (title.contains(name)){
                resultNum.add(i);
            }
        }

        List<Map<String,Object>> result = new ArrayList<>();
        for (Integer integer : resultNum) {
            Map<String, Object> map = list.get(integer);
            result.add(map);
        }
        return result;
    }
}
