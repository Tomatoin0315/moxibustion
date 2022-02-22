package com.yjy.moxibustion.web.controller.app;

import com.yjy.moxibustion.abstracts.AbstractBaseController;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.BranchContent;
import com.yjy.moxibustion.service.DiseaseByBranchCategoryService;
import com.yjy.moxibustion.service.DiseaseByBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/21 10:28
 */
@RestController
@RequestMapping(value = "api/branchs")
public class DiseaseByBranchController {
    @Autowired
    private DiseaseByBranchCategoryService diseaseByBranchCategoryService;

    @Autowired
    private DiseaseByBranchService diseaseByBranchService;

    @RequestMapping("/categories")
    public RestResp<List<BranchCategory>> treeData(String name){
        Integer pid = diseaseByBranchCategoryService.getBranchCategoryByName(name);
        List<BranchCategory> list = diseaseByBranchCategoryService.selectByPid(pid);
        return RestResp.success("success",list);
    }

    @RequestMapping("/contents")
    public RestResp<BranchContent> getBranchContentByName(String name){
        Integer cid = diseaseByBranchCategoryService.getBranchCategoryByName(name);
        BranchContent content = diseaseByBranchService.getBranchContentByCid(cid);
        return RestResp.success("success",content);
    }
}
