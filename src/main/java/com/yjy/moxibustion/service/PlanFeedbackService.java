package com.yjy.moxibustion.service;

import com.yjy.moxibustion.dao.PlanFeedbackMapper;
import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.PlanFeedback;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/5/31 19:37
 */
public interface PlanFeedbackService extends BaseService<PlanFeedback> {

    /**
     * 找出所有属于按分科分类的反馈内容和所有属于按部位分类的反馈内容
     * 用于在科目治疗方案反馈中展示和在部位治疗方案反馈中展示
     * @param type 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     * @return
     */
    List<PlanFeedback> findAllByType(Integer type);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 属于按分科分类的反馈内容的数据表格
     * @return
     */
    List<Map<String,Object>> branchPlanFeedbackTable();

    /**
     * 属于按分科分类的反馈内容的数据表格的搜索功能
     * @param name 查询关键字
     * @return
     */
    List<Map<String,Object>> branchPlanFeedbackTableSearch(String name);

    /**
     * 属于按部位分类的反馈内容的数据表格
     * @return
     */
    List<Map<String,Object>> positionPlanFeedbackTable();

    /**
     * 属于按部位分类的反馈内容的数据表格的搜索功能
     * @param name 查询关键字
     * @return
     */
    List<Map<String,Object>> positionPlanFeedbackTableSearch(String name);
}
