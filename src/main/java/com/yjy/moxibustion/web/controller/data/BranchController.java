package com.yjy.moxibustion.web.controller.data;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.dto.LayuiResp;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchContent;
import com.yjy.moxibustion.service.DiseaseByBranchCategoryService;
import com.yjy.moxibustion.service.DiseaseByBranchService;
import com.yjy.moxibustion.service.PlanFeedbackService;
import com.yjy.moxibustion.service.TreatmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/24 16:33
 */
@RestController
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    private DiseaseByBranchService diseaseByBranchService;

    @Autowired
    private DiseaseByBranchCategoryService diseaseByBranchCategoryService;

    @Autowired
    private PlanFeedbackService planFeedbackService;

    @Autowired
    private TreatmentPlanService treatmentPlanService;

    @GetMapping("/branchCategory/tree")
    public RestResp<List<Object>> tree(){
        List<Object> tree = diseaseByBranchCategoryService.getTree();
        return RestResp.success("success",tree);
    }


    /**
     * 编辑类别的名字
     * @param id
     * @param value
     * @return
     */
    @PostMapping("/update")
    public RestResp updateName(Integer id,String value){
        int i = diseaseByBranchCategoryService.updateName(id, value);
        if (i!=0){
            return RestResp.success();
        }else {
            //更新失败
            return RestResp.fail();
        }
    }

    /**
     * 根据id删除，如果该节点下还有节点，则不可以删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public RestResp deleteById(Integer id){
        int i = diseaseByBranchCategoryService.deleteById(id);
        if (i!=0){
            return RestResp.success();
        }else {
            return RestResp.fail();
        }
    }

    @GetMapping("/table")
    public LayuiResp<Map<String,Object>> branchTable(String page,String limit,
                                                     String name){
        if (name!=null){
            //id不为空，证明是搜索关键字
            Long count = diseaseByBranchService.branchSearchCount(name);
            List<Map<String, Object>> list = diseaseByBranchService.branchSearch(name,Integer.parseInt(page),Integer.parseInt(limit));
            return LayuiResp.createResult(count,list);
        }else {
            //id为空，证明是普通的加载表格
            List<BranchContent> all = diseaseByBranchService.getAll();
            List<Map<String, Object>> list = diseaseByBranchService.branchTable(null,Integer.parseInt(page),Integer.parseInt(limit));
            return LayuiResp.createResult((long) all.size(),list);
        }
    }

    @PostMapping("/add")
    public RestResp addBranchCategory(Integer id){
        int add = diseaseByBranchCategoryService.add(id);
        if (add!=0){
            //添加成功,把当前的节点的isParent设置为true
            int i = diseaseByBranchCategoryService.updateIsParent(id);
            if (i!=0){
                return RestResp.success("父节点更新成功");
            }else {
                return RestResp.fail("父节点更新失败");
            }
        }else {
            return RestResp.fail("添加失败");
        }
    }

    /**
     * 更新数据表格中的某个字段
     * @param id
     * @param value
     * @param field
     * @return
     */
    @PostMapping("/updateTable")
    public RestResp updateTable(Integer id,String value,String field){
        //System.out.println(id + "    " +  value  + "    " + field );
        int i = diseaseByBranchService.updateTable(id, value, field);
        if (i!=0){
            return RestResp.success();
        }else {
            return RestResp.fail();
        }
    }

    @PostMapping("/deleteTable")
    public RestResp deleteTable(Integer id){
        int i = diseaseByBranchService.deleteTable(id);
        if (i!=0){
            return RestResp.success("删除成功");
        }else {
            return RestResp.fail("删除失败");
        }
    }

    @GetMapping("/feedbackTable")
    public LayuiResp<Map<String,Object>> branchPlanFeedbackTable(String page,String limit,
                                                                 String name){
        if (name!=null){
            //id不为空，证明是搜索关键字
            List<Map<String, Object>> list = planFeedbackService.branchPlanFeedbackTableSearch(name);
            return LayuiResp.createResult((long) list.size(),list);
        }else {
            //id为空，证明是普通的加载表格
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
            List<Map<String, Object>> list = planFeedbackService.branchPlanFeedbackTable();
            return LayuiResp.createResult((long) list.size(),list);
        }
    }

    @PostMapping("/deleteFeedbackTable")
    public RestResp deleteFeedbackTable(Integer id){
        int i = planFeedbackService.deleteById(id);
        if (i!=0){
            return RestResp.success("删除成功");
        }else {
            return RestResp.fail("删除失败");
        }
    }

    @GetMapping("/treatmentPlanTable")
    public LayuiResp<Map<String,Object>> branchTreatmentPlanTable(String page,String limit,
                                                                  String name){
        if (name!=null){
            //id不为空，证明是搜索关键字
            List<Map<String, Object>> list = treatmentPlanService.branchTreatmentPlanTableSearch(name);
            return LayuiResp.createResult((long) list.size(),list);
        }else {
            //id为空，证明是普通的加载表格
            PageHelper.startPage(Integer.parseInt(page),Integer.parseInt(limit));
            List<Map<String, Object>> list = treatmentPlanService.branchTreatmentPlanTable();
            return LayuiResp.createResult((long) list.size(),list);
        }
    }

    @PostMapping("/deleteTreatmentPlanTable")
    public RestResp deleteTreatmentPlanTable(Integer id){
        int i = treatmentPlanService.deleteById(id);
        if (i!=0){
            return RestResp.success("删除成功");
        }else {
            return RestResp.fail("删除失败");
        }
    }


    /**
     * 用户端的分科搜索功能
     * 只返回name
     * @param name
     * @return
     */
    @GetMapping("/branchUserSearchByName")
    public RestResp branchUserSearchByName(String name){
        //System.out.println("-----------"+name);
        List<Map<String, Object>> list = diseaseByBranchCategoryService.branchUserSearchByName(name);
        //搜索成功
        if (list.size() != 0){
            return RestResp.success("搜索成功",list);
        }else {
            return RestResp.fail("搜索失败");
        }
    }

    @GetMapping("/branchDetailByName")
    public RestResp branchDetailByName(String name){
        List<Map<String, Object>> list = diseaseByBranchService.branchDetailByName(name);
        if (list.size() != 0){
            return RestResp.success("成功",list);
        }else {
            return RestResp.fail("失败");
        }
    }

}
