package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.Article;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/6/1 23:03
 */
public interface ArticleService extends BaseService<Article> {
    /**
     * 文章管理数据表格
     * @return
     */
    List<Map<String,Object>> articleTable();

    /**
     * 文章管理数据表格搜索
     * @return
     */
    List<Map<String,Object>> articleTableSearch(String name);
}
