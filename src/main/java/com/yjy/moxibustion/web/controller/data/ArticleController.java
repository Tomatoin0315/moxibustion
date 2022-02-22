package com.yjy.moxibustion.web.controller.data;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.dto.LayuiResp;
import com.yjy.moxibustion.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/6/1 23:00
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/table")
    public LayuiResp<Map<String,Object>> articleTable(String page, String limit,
                                                     String name){
        if (name!=null){
            //id不为空，证明是搜索关键字
            List<Map<String, Object>> list = articleService.articleTableSearch(name);
            return LayuiResp.createResult((long) list.size(),list);
        }else {
            //id为空，证明是普通的加载表格
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
            List<Map<String, Object>> list = articleService.articleTable();
            return LayuiResp.createResult((long) list.size(),list);
        }
    }
}
