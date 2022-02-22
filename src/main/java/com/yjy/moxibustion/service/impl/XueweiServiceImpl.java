package com.yjy.moxibustion.service.impl;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.XueweiCategoryMapper;
import com.yjy.moxibustion.dao.XueweiContentMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.XueweiCategory;
import com.yjy.moxibustion.pojo.XueweiContent;
import com.yjy.moxibustion.service.XueweiService;
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
 * @date 2021/1/16 19:37
 */
@Service
public class XueweiServiceImpl extends AbstractBaseServiceImpl<XueweiContent,XueweiContentMapper> implements XueweiService {
    @Autowired
    private XueweiContentMapper xueweiContentMapper;

    @Autowired
    private XueweiCategoryMapper xueweiCategoryMapper;


    @Override
    public XueweiContent getXueweiContentByCid(Integer cid) {
        Example example = new Example(XueweiContent.class);
        example.and().andEqualTo("categoryId",cid);
        List<XueweiContent> xueweiContents = xueweiContentMapper.selectByExample(example);
        XueweiContent xueweiContent = xueweiContents.get(0);
        return xueweiContent;
    }

    @Override
    public List<Map<String, Object>> xueweiTable(String name,Integer page,Integer limit) {
        if (name == null){
            PageHelper.startPage(page,limit);
        }
        List<XueweiContent> xueweiContents = xueweiContentMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (XueweiContent content : xueweiContents) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",content.getId());
            XueweiCategory pCategory = xueweiCategoryMapper.selectByPrimaryKey(content.getCategoryId());
            map.put("name",pCategory.getName());
            XueweiCategory jingmai = xueweiCategoryMapper.selectByPrimaryKey(pCategory.getParentId());
            map.put("JingMai",jingmai.getName());
            map.put("code",content.getGuojidaima());
            map.put("TeDingXue",content.getTedingxue());
            map.put("DingWei",content.getDingwei());
            map.put("QuXueFa",content.getQuxuefa());
            map.put("ZhuZhiBingZheng",content.getZhuzhi());
            map.put("CanShu",content.getCanshu());
            map.put("JingYan",content.getYingyong());
            list.add(map);
        }
        return list;
    }

    @Override
    public int updateTable(Integer id, String value, String field) {

        XueweiContent content = new XueweiContent();
        content.setId(id);
        switch (field){
            case "code":
                content.setGuojidaima(value);
                break;
            case "TeDingXue":
                content.setTedingxue(value);
                break;
            case "DingWei":
                content.setDingwei(value);
                break;
            case "QuXueFa":
                content.setQuxuefa(value);
                break;
            case "ZhuZhiBingZheng":
                content.setZhuzhi(value);
                break;
            case "CanShu":
                content.setCanshu(value);
                break;
            case "JingYan":
                content.setYingyong(value);
                break;
        }
        content.setUpdated(LocalDateTime.now());
        int i = xueweiContentMapper.updateByPrimaryKeySelective(content);
        return i;
    }

    /**
     * 对表格进行搜素处理
     * @param name
     * @return
     */
    @Override
    public List<Map<String, Object>> xueweiSearch(String name,Integer page,Integer limit) {
        List<Map<String, Object>> list = xueweiTable(name,page,limit);
        List<Integer> resultNum = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String name1 = (String) map.get("name");
            String jingMai = (String) map.get("JingMai");
            String code = (String) map.get("code");
            if (name1.contains(name) || jingMai.contains(name) || code.contains(name)){
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

    @Override
    public Long xueweiSearchCount(String name){
        int count = 0;
        List<XueweiContent> xueweiContents = xueweiContentMapper.selectAll();
        for (XueweiContent xueweiContent : xueweiContents) {
            XueweiCategory pCategory = xueweiCategoryMapper.selectByPrimaryKey(xueweiContent.getCategoryId());
            String name1 = pCategory.getName();
            XueweiCategory jingmai = xueweiCategoryMapper.selectByPrimaryKey(pCategory.getParentId());
            String jingMai = jingmai.getName();
            String code = xueweiContent.getGuojidaima();
            if (name1.contains(name) || jingMai.contains(name) || code.contains(name)){
                count++;
            }
        }
        return Long.valueOf(count);
    }

    @Override
    public int deleteTable(Integer id) {
        int i = xueweiContentMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public List<Map<String, Object>> xueweiAppSearch(String name) {
        Example example = new Example(XueweiCategory.class);
        if (!name.isEmpty()){
            example.createCriteria().andLike("name",name);
        }
        List<XueweiCategory> xueweiCategories = xueweiCategoryMapper.selectByExample(example);
        //构造新的list
        List<Map<String,Object>> list = new ArrayList<>();
        if (xueweiCategories != null){
            for (XueweiCategory category : xueweiCategories) {
                Example example1 = new Example(XueweiContent.class);
                example1.and().andEqualTo("categoryId",category.getId());
                List<XueweiContent> xueweiContents = xueweiContentMapper.selectByExample(example1);
                //具体内容
                if (xueweiContents.size() != 0){
                    XueweiContent content = xueweiContents.get(0);

                    //构造map
                    Map<String,Object> map = new HashMap<>();
                    map.put("type","xuewei");
                    map.put("name",category.getName());
                    map.put("content",content);
                    list.add(map);
                }

            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> xueweiUserSearchByName(String name) {
        Example example = new Example(XueweiCategory.class);
        example.and().andEqualTo("isParent",false);
        List<XueweiCategory> xueweiCategories = xueweiCategoryMapper.selectByExample(example);
        List<Map<String,Object>> result = new ArrayList<>();
        for (XueweiCategory xueweiCategory : xueweiCategories) {
            String name1 = xueweiCategory.getName();
            if (name1 == null){
                break;
            }
            //如果该名称包含关键字，则将其加入list
            if (name1.contains(name)){
                Map<String,Object> map = new HashMap<>();
                map.put("name",name1);
                result.add(map);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> xueweiDetailByName(String name) {
        List<XueweiContent> xueweiContents = xueweiContentMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (XueweiContent content : xueweiContents) {
            XueweiCategory pCategory = xueweiCategoryMapper.selectByPrimaryKey(content.getCategoryId());
            String name1 = pCategory.getName();
            if (name1.equals(name)){
                Map<String,Object> map = new HashMap<>();
                map.put("id",content.getId());
                map.put("name",name);
                XueweiCategory jingmai = xueweiCategoryMapper.selectByPrimaryKey(pCategory.getParentId());
                map.put("JingMai",jingmai.getName());
                map.put("code",content.getGuojidaima());
                map.put("TeDingXue",content.getTedingxue());
                map.put("DingWei",content.getDingwei());
                map.put("QuXueFa",content.getQuxuefa());
                map.put("ZhuZhiBingZheng",content.getZhuzhi());
                map.put("CanShu",content.getCanshu());
                map.put("JingYan",content.getYingyong());
                list.add(map);
                break;
            }
        }
        return list;
    }


}
