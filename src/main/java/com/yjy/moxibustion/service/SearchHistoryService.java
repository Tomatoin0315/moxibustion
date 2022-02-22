package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.SearchHistory;

import java.util.List;

/**
 * @author 杨景元
 * @date 2021/2/5 10:26
 */
public interface SearchHistoryService extends BaseService<SearchHistory> {
    /**
     * 保存用户的搜索记录
     * @param title
     * @param username
     * @return
     */
    int saveSearchHistory(String title,String username,String type);

    /**
     * 根据时间降序排列，返回该用户的搜索记录
     * @param username
     * @return
     */
    List<String> selectByUsernameDESC(String username);
}
