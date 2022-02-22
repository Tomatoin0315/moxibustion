package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.BranchCategoryMapper;
import com.yjy.moxibustion.dao.PlanFeedbackMapper;
import com.yjy.moxibustion.dao.PositionCategoryMapper;
import com.yjy.moxibustion.dao.UserMapper;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.PlanFeedback;
import com.yjy.moxibustion.pojo.PositionCategory;
import com.yjy.moxibustion.pojo.User;
import com.yjy.moxibustion.service.PlanFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/5/31 19:37
 */
@Service
public class PlanFeedbackServiceImpl extends AbstractBaseServiceImpl<PlanFeedback, PlanFeedbackMapper> implements PlanFeedbackService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BranchCategoryMapper branchCategoryMapper;

    @Autowired
    private PositionCategoryMapper positionCategoryMapper;


    /**
     * position为部位，type=1
     * branch为分科，type=2
     * @param type 该治疗方案属于哪个类别，1：按部位分类，2：按分科分类
     * @return
     */
    @Override
    public List<PlanFeedback> findAllByType(Integer type) {
        Example example = new Example(PlanFeedback.class);
        example.and().andEqualTo("type",type);
        List<PlanFeedback> planFeedbacks = mapper.selectByExample(example);
        return planFeedbacks;
    }

    @Override
    public int deleteById(Integer id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Map<String, Object>> branchPlanFeedbackTable() {
        //2为按分科分类
        List<PlanFeedback> allByType = findAllByType(2);
        /*for (PlanFeedback feedback : allByType) {
            System.out.println("-------------按分科分类的治疗方案反馈-----------");
            System.out.println(feedback);
        }*/
        List<Map<String,Object>> list = new ArrayList<>();
        for (PlanFeedback planFeedback : allByType) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",planFeedback.getId());
            //得到反馈该治疗方案的用户名字
            User user = userMapper.selectByPrimaryKey(planFeedback.getuId());
            map.put("people",user.getUsername());
            //得到其反馈治疗方案的名称
            Example example = new Example(BranchCategory.class);
            example.and().andEqualTo("id",planFeedback.getcId());
            List<BranchCategory> branchCategories = branchCategoryMapper.selectByExample(example);
            map.put("name",branchCategories.get(0).getName());
            map.put("content",planFeedback.getContent());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> branchPlanFeedbackTableSearch(String name) {
        List<Map<String, Object>> list = branchPlanFeedbackTable();
        List<Map<String, Object>> result = planFeedbackSearch(list, name);
        return result;
    }

    @Override
    public List<Map<String, Object>> positionPlanFeedbackTable() {
        //1为按部位分类
        List<PlanFeedback> allByType = findAllByType(1);
        List<Map<String,Object>> list = new ArrayList<>();
        for (PlanFeedback planFeedback : allByType) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",planFeedback.getId());
            //得到反馈该治疗方案的用户名字
            User user = userMapper.selectByPrimaryKey(planFeedback.getuId());
            map.put("people",user.getUsername());
            //得到其反馈治疗方案的名称
            Example example = new Example(PositionCategory.class);
            example.and().andEqualTo("id",planFeedback.getcId());
            List<PositionCategory> positionCategories = positionCategoryMapper.selectByExample(example);
            map.put("name",positionCategories.get(0).getName());
            map.put("content",planFeedback.getContent());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> positionPlanFeedbackTableSearch(String name) {
        List<Map<String, Object>> list = positionPlanFeedbackTable();
        List<Map<String, Object>> result = planFeedbackSearch(list, name);
        return result;
    }

    /**
     * 反馈治疗方案的搜索
     * @param list
     * @return
     */
    public List<Map<String,Object>> planFeedbackSearch(List<Map<String,Object>> list,String name){
        //用来记录搜索结果所在的位置
        List<Integer> resultNum = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String people = (String) map.get("people");
            String name1 = (String) map.get("name");
            //实现多属性查询，查询所属人的名字和治疗方案的名称
            //若有，则将其添加到resultNum
            if (people.contains(name)){
                resultNum.add(i);
            }else if (name1.contains(name)){
                resultNum.add(i);
            }
        }
        List<Map<String,Object>> result = new ArrayList<>();
        for (Integer integer : resultNum) {
            Map<String, Object> map = list.get(integer);
            result.add(map);
        }
        return result;
    }


}
