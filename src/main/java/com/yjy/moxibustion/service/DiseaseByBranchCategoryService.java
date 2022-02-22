package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.BranchCategory;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/20 19:18
 */
public interface DiseaseByBranchCategoryService extends BaseService<BranchCategory> {
    /**
     * 通过类别的名字，找到该类别的id，并返回
     * @param name
     * @return
     */
    Integer getBranchCategoryByName(String name);

    /**
     * 根据父级节点Id查询所有子节点
     * @param pid
     * @return
     */
    List<BranchCategory> selectByPid(Integer pid);

    /**
     * 得到所有的父节点
     * @return
     */
    List<BranchCategory> getAllP();

    /**
     * 转换成树的格式
     * @return
     */
    List<Object> getTree();

    /**
     * 编辑该类别名字
     * @param id
     * @return
     */
    int updateName(Integer id,String name);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 在该节点下添加新的节点
     * @param pid
     * @return
     */
    int add(Integer pid);

    /**
     * 将该节点的isParent设置为true
     * @param id
     * @return
     */
    int updateIsParent(Integer id);

    /**
     * 用户端的通过名称查询
     * 只返回name
     * @param name
     * @return
     */
    List<Map<String,Object>> branchUserSearchByName(String name);

}
