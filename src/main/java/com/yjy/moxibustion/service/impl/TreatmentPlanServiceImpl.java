package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.BranchCategoryMapper;
import com.yjy.moxibustion.dao.PositionCategoryMapper;
import com.yjy.moxibustion.dao.TreatmentPlanMapper;
import com.yjy.moxibustion.dao.UserMapper;
import com.yjy.moxibustion.pojo.*;
import com.yjy.moxibustion.service.PlanFeedbackService;
import com.yjy.moxibustion.service.TreatmentPlanService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/6/1 19:33
 */
@Service
public class TreatmentPlanServiceImpl extends AbstractBaseServiceImpl<TreatmentPlan, TreatmentPlanMapper> implements TreatmentPlanService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BranchCategoryMapper branchCategoryMapper;

    @Autowired
    private PositionCategoryMapper positionCategoryMapper;

    //用于使用该类的搜索方法
    @Autowired
    private PlanFeedbackServiceImpl planFeedbackServiceImpl;

    /**
     * position为部位，type=1
     * branch为分科，type=2
     * @param type 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     * @return
     */
    @Override
    public List<TreatmentPlan> findAllByType(Integer type) {
        Example example = new Example(TreatmentPlan.class);
        example.and().andEqualTo("type",type);
        List<TreatmentPlan> treatmentPlans = mapper.selectByExample(example);
        return treatmentPlans;
    }

    @Override
    public int deleteById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> branchTreatmentPlanTable() {
        //2为按分科分类
        List<TreatmentPlan> allByType = findAllByType(2);
        List<Map<String, Object>> list = treatmentPlanTable(allByType);
        return list;
    }

    @Override
    public List<Map<String, Object>> branchTreatmentPlanTableSearch(String name) {
        List<Map<String, Object>> list = branchTreatmentPlanTable();
        List<Map<String, Object>> result = planFeedbackServiceImpl.planFeedbackSearch(list, name);
        return result;
    }

    @Override
    public List<Map<String, Object>> positionTreatmentPlanTable() {
        //1为按部位分类
        List<TreatmentPlan> allByType = findAllByType(1);
        List<Map<String, Object>> list = treatmentPlanTable(allByType);
        return list;
    }

    @Override
    public List<Map<String, Object>> positionTreatmentPlanTableSearch(String name) {
        List<Map<String, Object>> list = positionTreatmentPlanTable();
        List<Map<String, Object>> result = planFeedbackServiceImpl.planFeedbackSearch(list, name);
        return result;
    }

    /**
     * 将不同类型的数据存入数据表格所需的map中
     * @param allByType
     * @return
     */
    private List<Map<String,Object>> treatmentPlanTable(List<TreatmentPlan> allByType){
        List<Map<String,Object>> list = new ArrayList<>();
        for (TreatmentPlan treatmentPlan : allByType) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",treatmentPlan.getId());
            //得到发布该治疗方案的用户名字
            User user = userMapper.selectByPrimaryKey(treatmentPlan.getuId());
            map.put("people",user.getUsername());
            //得到其反馈治疗方案的名称
            map.put("name",treatmentPlan.getName());
            /*if (type.equals("branch")){
                Example example = new Example(BranchCategory.class);
                example.and().andEqualTo("id",treatmentPlan.getcId());
                List<BranchCategory> branchCategories = branchCategoryMapper.selectByExample(example);
                map.put("name",branchCategories.get(0).getName());
            }else if (type.equals("position")){
                Example example = new Example(PositionCategory.class);
                example.and().andEqualTo("id",treatmentPlan.getcId());
                List<PositionCategory> positionCategories = positionCategoryMapper.selectByExample(example);
                map.put("name",positionCategories.get(0).getName());
            }*/
            map.put("GaiShu",treatmentPlan.getGaishu());
            map.put("ZhengZhuang",treatmentPlan.getZhengzhuang());
            map.put("BingYin",treatmentPlan.getBingyin());
            map.put("QuXue",treatmentPlan.getQuxue());
            map.put("AnLi",treatmentPlan.getAnli());
            map.put("TiHui",treatmentPlan.getTihui());
            list.add(map);
        }
        return list;
    }
}
