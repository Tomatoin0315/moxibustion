package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.BranchCategoryMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.service.DiseaseByBranchCategoryService;
import com.yjy.moxibustion.utils.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 杨景元
 * @date 2021/1/20 19:19
 */
@Service
public class DiseaseByBranchCategoryServiceImpl extends AbstractBaseServiceImpl<BranchCategory,BranchCategoryMapper> implements DiseaseByBranchCategoryService {

    @Autowired
    private BranchCategoryMapper branchCategoryMapper;

    @Override
    public RestResp save(BranchCategory entity) {
        return null;
    }

    @Override
    public BranchCategory getOne(Integer id) {
        return branchCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BranchCategory> getAll() {
        return branchCategoryMapper.selectAll();
    }

    @Override
    public Integer getBranchCategoryByName(String name) {
        Example example = new Example(BranchCategory.class);
        example.and().andEqualTo("name",name);
        List<BranchCategory> list = branchCategoryMapper.selectByExample(example);
        BranchCategory category = list.get(0);
        return category.getId();
    }

    @Override
    public List<BranchCategory> selectByPid(Integer pid) {
        Example example = new Example(BranchCategory.class);
        example.and().andEqualTo("parentId",pid);
        List<BranchCategory> list = branchCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<BranchCategory> getAllP() {
        Example example = new Example(BranchCategory.class);
        //父节点为0，即为根节点
        example.and().andEqualTo("parentId",0);
        List<BranchCategory> list = branchCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Object> getTree() {
        //得到所有的根节点
        List<BranchCategory> allP = getAllP();
        /*for (BranchCategory Pcategory : allP) {
            Map<String,Object> map = new HashMap<>();
            map.put("title",Pcategory.getName());
            map.put("id",Pcategory.getId());
            //不是叶子节点
            if (Pcategory.getIsParent() == true){
                //map.put("children",);
                List<Map<String,Object>> list = new ArrayList<>();
                //得到该节点下的子节点(第一层)
                List<BranchCategory> list1 = selectByPid(Pcategory.getId());
                for (BranchCategory Zcategory : list1) {
                    Map<String,Object> map1 = new HashMap<>();
                    map.put("title",Zcategory.getName());
                    map.put("id",Zcategory.getId());
                    List<Map<String,Object>> list2 = new ArrayList<>();
                    //不是叶子节点
                    if (Zcategory.getIsParent() == true){
                        Map<String,Object> map2 = new HashMap<>();
                        //第二层(只有两层)
                        List<BranchCategory> list3 = selectByPid(Zcategory.getId());

                    }
                }
            }
        }*/
        List<BranchCategory> list = mapper.selectAll();
        List<Object> objects = TreeUtils.treeMenu(list);
        return objects;
    }

    @Override
    public int updateName(Integer id,String name) {
        BranchCategory branchCategory = new BranchCategory();
        branchCategory.setId(id);
        branchCategory.setName(name);
        branchCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(branchCategory);
        return i;
    }

    @Override
    public int deleteById(Integer id) {
        BranchCategory one = getOne(id);
        //如果该节点下还有节点，则无法删除
        if (one.getIsParent()){
            return 0;
        }else {
            //如果该节点是叶子节点，则可以删除
            return mapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public int add(Integer pid) {
        BranchCategory branchCategory = new BranchCategory();
        branchCategory.setParentId(pid);
        branchCategory.setName("未命名");
        branchCategory.setStatus(1);
        branchCategory.setSortOrder(1);
        branchCategory.setIsParent(false);
        branchCategory.setCreated(LocalDateTime.now());
        branchCategory.setUpdated(LocalDateTime.now());
        int insert = mapper.insert(branchCategory);
        return insert;
    }

    @Override
    public int updateIsParent(Integer id) {
        BranchCategory branchCategory = new BranchCategory();
        branchCategory.setId(id);
        branchCategory.setIsParent(true);
        branchCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(branchCategory);
        return i;
    }

    @Override
    public List<Map<String, Object>> branchUserSearchByName(String name) {
        List<BranchCategory> branchCategories = branchCategoryMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (BranchCategory branchCategory : branchCategories) {
            //找到当前的病症
            if (branchCategory.getName().equals(name)){
                Integer fid = branchCategory.getId();
                //查找父节点为fid的病症
                for (BranchCategory category : branchCategories) {
                    if (category.getParentId().equals(fid)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",category.getName());
                        map.put("type","branch");
                        map.put("isParent",category.getIsParent());
                        list.add(map);
                    }
                }
                //代表是子节点
                if (list.size() == 0){
                    Map<String,Object> map = new HashMap<>();
                    map.put("name",branchCategory.getName());
                    map.put("type","branch");
                    map.put("isParent",branchCategory.getIsParent());
                    list.add(map);
                }
                break;
            }
        }
        return list;
    }


    /**
     * 排序
     * @param sourceList 源数据
     * @param targetList 目标数据
     * @param parentId
     */
    private void sortList(List<BranchCategory> sourceList,List<BranchCategory> targetList,int parentId){
        for (BranchCategory source : sourceList) {
            if (source.getParentId().equals(parentId)){
                targetList.add(source);

                //判断有没有子节点，有则继续追加
                if (source.getIsParent()){
                    for (BranchCategory current : sourceList) {
                        if (current.getParentId().equals(source.getId())){
                            sortList(sourceList,targetList,source.getId());
                            break;
                        }
                    }
                }
            }
        }
    }
}
