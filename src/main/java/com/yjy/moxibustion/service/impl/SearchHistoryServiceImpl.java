package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.SearchHistoryMapper;
import com.yjy.moxibustion.dao.UserMapper;
import com.yjy.moxibustion.pojo.SearchHistory;
import com.yjy.moxibustion.pojo.User;
import com.yjy.moxibustion.service.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨景元
 * @date 2021/2/5 10:27
 */
@Service
public class SearchHistoryServiceImpl extends AbstractBaseServiceImpl<SearchHistory, SearchHistoryMapper> implements SearchHistoryService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int saveSearchHistory(String title, String username,String type) {
        Example example = new Example(User.class);
        example.and().andEqualTo("username",username);
        List<User> users = userMapper.selectByExample(example);
        //得到当前登录的用户
        User user = users.get(0);

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setuId(user.getId());
        searchHistory.setTitle(title);
        searchHistory.setType(type);
        searchHistory.setCreated(LocalDateTime.now());
        searchHistory.setUpdated(LocalDateTime.now());
        int insert = mapper.insert(searchHistory);

        return insert;
    }

    @Override
    public List<String> selectByUsernameDESC(String username) {
        Example example = new Example(SearchHistory.class);
        example.setOrderByClause("created DESC");
        List<SearchHistory> searchHistories = mapper.selectByExample(example);

        List<String> result = new ArrayList<>();
        for (SearchHistory history : searchHistories) {
            result.add(history.getTitle());
        }
        //返回只带标题的list
        return result;
    }
}
