package com.yjy.moxibustion.web.controller.app;

import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.SearchHistory;
import com.yjy.moxibustion.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/2/5 10:08
 */
@RestController
@RequestMapping("api")
public class SearchController {
    @Autowired
    private XueweiService xueweiService;

    @Autowired
    private XueweiCategoryService xueweiCategoryService;

    @Autowired
    private DiseaseByPositionService diseaseByPositionService;

    @Autowired
    private DiseaseByPositionCategoryService diseaseByPositionCategoryService;

    @Autowired
    private DiseaseByBranchService diseaseByBranchService;

    @Autowired
    private SearchHistoryService searchHistoryService;

    /**
     * app端的搜索
     * @param title
     * @return
     */
    @GetMapping("/search")
    public RestResp searchApp(String title,String username){
        List<Map<String, Object>> list = diseaseByBranchService.branchAppSearch(title);
        List<Map<String, Object>> list1 = diseaseByPositionService.positionAppSearch(title);
        List<Map<String, Object>> list2 = xueweiService.xueweiAppSearch(title);

        //将三个list拼接成一个
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            result.add(map);
        }
        for (Map<String, Object> map : list1) {
            result.add(map);
        }
        for (Map<String, Object> map : list2) {
            result.add(map);
        }
        if (result != null){
            if (result.size() == 1){
                //代表只返回了一种类型，type为map中的type
                Map<String, Object> map = result.get(0);
                String type = (String) map.get("type");
                //保存搜索记录
                searchHistoryService.saveSearchHistory(title,username,type);
            }else {
                //代表返回了多种类型，type为mix(混合)
                String type = "mix";
                //保存搜索记录
                searchHistoryService.saveSearchHistory(title,username,type);
            }
            return RestResp.success("success",result);
        }else {
            return RestResp.fail();
        }
    }

    /**
     * 得到该用户的搜索记录
     * 并按降序排列
     * @param username
     * @return
     */
    @GetMapping("/searchHistory")
    public RestResp getSearchHistory(String username){
        List<String> list = searchHistoryService.selectByUsernameDESC(username);
        return RestResp.success("success",list);
    }
}
