package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.PlanFeedback;
import com.yjy.moxibustion.pojo.TreatmentPlan;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/6/1 19:29
 */
public interface TreatmentPlanService extends BaseService<TreatmentPlan> {

    /**
     * 找出所有属于按分科分类的反馈内容和所有属于按部位分类的医师治疗方案
     * 用于在治疗方案审核中展示
     * @param type 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     * @return
     */
    List<TreatmentPlan> findAllByType(Integer type);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 属于按分科分类的治疗方案审核的数据表格
     * @return
     */
    List<Map<String,Object>> branchTreatmentPlanTable();

    /**
     * 属于按分科分类的治疗方案审核的数据表格的搜索功能
     * @param name 查询关键字
     * @return
     */
    List<Map<String,Object>> branchTreatmentPlanTableSearch(String name);

    /**
     * 属于按部位分类的治疗方案审核的数据表格
     * @return
     */
    List<Map<String,Object>> positionTreatmentPlanTable();

    /**
     * 属于按部位分类的治疗方案审核的数据表格的搜索功能
     * @param name 查询关键字
     * @return
     */
    List<Map<String,Object>> positionTreatmentPlanTableSearch(String name);
}
