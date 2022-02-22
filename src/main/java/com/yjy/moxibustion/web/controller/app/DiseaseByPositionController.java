package com.yjy.moxibustion.web.controller.app;

import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.BranchContent;
import com.yjy.moxibustion.pojo.PositionCategory;
import com.yjy.moxibustion.pojo.PositionContent;
import com.yjy.moxibustion.service.DiseaseByPositionCategoryService;
import com.yjy.moxibustion.service.DiseaseByPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/21 15:58
 */
@RestController
@RequestMapping(value = "api/positions")
public class DiseaseByPositionController {

    @Autowired
    private DiseaseByPositionService diseaseByPositionService;

    @Autowired
    private DiseaseByPositionCategoryService diseaseByPositionCategoryService;

    @RequestMapping("/categories")
    public RestResp<List<PositionCategory>> treeData(String name){
        Integer pid = diseaseByPositionCategoryService.getPositionCategoryByName(name);
        List<PositionCategory> list = diseaseByPositionCategoryService.selectByPid(pid);
        return RestResp.success("success",list);
    }

    @RequestMapping("/contents")
    public RestResp<PositionContent> getBranchContentByName(String name){
        Integer cid = diseaseByPositionCategoryService.getPositionCategoryByName(name);
        PositionContent content = diseaseByPositionService.getPositionContentByCid(cid);
        return RestResp.success("success",content);
    }
}
