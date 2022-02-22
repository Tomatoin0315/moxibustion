package com.yjy.moxibustion.service.impl;

import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.XueweiCategoryMapper;
import com.yjy.moxibustion.dao.XueweiContentMapper;
import com.yjy.moxibustion.pojo.XueweiCategory;
import com.yjy.moxibustion.service.XueweiCategoryService;
import com.yjy.moxibustion.utils.XueweiTreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/21 16:33
 */
@Service
public class XueweiCategoryServiceImpl extends AbstractBaseServiceImpl<XueweiCategory, XueweiCategoryMapper> implements XueweiCategoryService {

    @Autowired
    private XueweiCategoryMapper xueweiCategoryMapper;

    @Autowired
    private XueweiContentMapper xueweiContentMapper;

    @Override
    public Integer getXueweiCategoryByName(String name) {
        Example example = new Example(XueweiCategory.class);
        example.and().andEqualTo("name",name);
        List<XueweiCategory> list = xueweiCategoryMapper.selectByExample(example);
        XueweiCategory xueweiCategory = list.get(0);
        Integer id = xueweiCategory.getId();
        return id;
    }

    @Override
    public List<XueweiCategory> selectByPid(Integer pid) {
        Example example = new Example(XueweiCategory.class);
        example.and().andEqualTo("parentId",pid);
        List<XueweiCategory> list = xueweiCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<XueweiCategory> getAllP() {
        Example example = new Example(XueweiCategory.class);
        //父节点为0，即为根节点
        example.and().andEqualTo("parentId",0);
        List<XueweiCategory> list = xueweiCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Object> getTree() {
        //得到所有的根节点
        List<XueweiCategory> allP = getAllP();
        List<XueweiCategory> list = mapper.selectAll();
        List<Object> objects = XueweiTreeUtils.treeMenu(list);
        return objects;
    }

    @Override
    public int updateName(Integer id,String name) {
        XueweiCategory xueweiCategory = new XueweiCategory();
        xueweiCategory.setId(id);
        xueweiCategory.setName(name);
        xueweiCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(xueweiCategory);
        return i;
    }

    @Override
    public int deleteById(Integer id) {
        XueweiCategory one = getOne(id);
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
        XueweiCategory xueweiCategory = new XueweiCategory();
        xueweiCategory.setParentId(pid);
        xueweiCategory.setName("未命名");
        xueweiCategory.setStatus(1);
        xueweiCategory.setSortOrder(1);
        xueweiCategory.setIsParent(false);
        xueweiCategory.setCreated(LocalDateTime.now());
        xueweiCategory.setUpdated(LocalDateTime.now());
        int insert = mapper.insert(xueweiCategory);
        return insert;
    }

    @Override
    public int updateIsParent(Integer id) {
        XueweiCategory xueweiCategory = new XueweiCategory();
        xueweiCategory.setId(id);
        xueweiCategory.setIsParent(true);
        xueweiCategory.setUpdated(LocalDateTime.now());
        int i = mapper.updateByPrimaryKeySelective(xueweiCategory);
        return i;
    }
}
