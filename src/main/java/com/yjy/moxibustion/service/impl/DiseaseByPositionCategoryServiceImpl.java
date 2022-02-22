package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.PositionCategoryMapper;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.PositionCategory;
import com.yjy.moxibustion.service.DiseaseByPositionCategoryService;
import com.yjy.moxibustion.utils.PositionTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/21 16:00
 */
@Service
public class DiseaseByPositionCategoryServiceImpl extends AbstractBaseServiceImpl<PositionCategory, PositionCategoryMapper> implements DiseaseByPositionCategoryService {
    @Autowired
    private PositionCategoryMapper positionCategoryMapper;

    @Override
    public Integer getPositionCategoryByName(String name) {
        Example example = new Example(PositionCategory.class);
        example.and().andEqualTo("name",name);
        List<PositionCategory> list = positionCategoryMapper.selectByExample(example);
        if (list != null){
            PositionCategory category = list.get(0);
            return category.getId();
        }else {
            return 0;
        }

    }

    @Override
    public List<PositionCategory> selectByPid(Integer pid) {
        Example example = new Example(PositionCategory.class);
        example.and().andEqualTo("parentId",pid);
        List<PositionCategory> list = positionCategoryMapper.selectByExample(example);
        return list;
    }


    @Override
    public List<PositionCategory> getAllP() {
        Example example = new Example(BranchCategory.class);
        //父节点为0，即为根节点
        example.and().andEqualTo("parentId",0);
        List<PositionCategory> list = positionCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Object> getTree() {
        //得到所有的根节点
        List<PositionCategory> allP = getAllP();
        List<PositionCategory> list = mapper.selectAll();
        List<Object> objects = PositionTreeUtils.treeMenu(list);
        return objects;
    }

    @Override
    public int updateName(Integer id,String name) {
        PositionCategory positionCategory = new PositionCategory();
        positionCategory.setId(id);
        positionCategory.setName(name);
        positionCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(positionCategory);
        return i;
    }

    @Override
    public int deleteById(Integer id) {
        PositionCategory one = getOne(id);
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
        PositionCategory positionCategory = new PositionCategory();
        positionCategory.setParentId(pid);
        positionCategory.setName("未命名");
        positionCategory.setStatus(1);
        positionCategory.setSortOrder(1);
        positionCategory.setIsParent(false);
        positionCategory.setCreated(LocalDateTime.now());
        positionCategory.setUpdated(LocalDateTime.now());
        int insert = mapper.insert(positionCategory);
        return insert;
    }

    @Override
    public int updateIsParent(Integer id) {
        PositionCategory positionCategory = new PositionCategory();
        positionCategory.setId(id);
        positionCategory.setIsParent(true);
        positionCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(positionCategory);
        return i;
    }

    @Override
    public List<Map<String, Object>> positionUserSearchByName(String name) {
        List<PositionCategory> positionCategories = positionCategoryMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (PositionCategory positionCategory : positionCategories) {
            if (positionCategory.getName().equals(name)){
                Integer fid = positionCategory.getId();
                //查找父节点为fid的子节点
                for (PositionCategory category : positionCategories) {
                    //System.out.println("ParentId:"+category.getParentId());
                    if (category.getParentId().equals(fid)){
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",category.getName());
                        map.put("type","position");
                        list.add(map);
                    }
                }
                //找到一个名字匹配的就可以退出循环了
                break;
            }
        }
        return list;
    }
}
