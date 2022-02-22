package com.yjy.moxibustion.service.impl;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.dao.BranchCategoryMapper;
import com.yjy.moxibustion.dao.BranchContentMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.BranchContent;
import com.yjy.moxibustion.service.DiseaseByBranchService;
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
 * @date 2021/1/16 19:49
 */
@Service
public class DiseaseByBranchServiceImpl implements DiseaseByBranchService {
    @Autowired
    private BranchContentMapper branchContentMapper;

    @Autowired
    private BranchCategoryMapper branchCategoryMapper;


    @Override
    public RestResp save(BranchContent entity) {
        return null;
    }

    @Override
    public BranchContent getOne(Integer id) {
        return branchContentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BranchContent> getAll() {
        return branchContentMapper.selectAll();
    }

    @Override
    public BranchContent getBranchContentByCid(Integer cid) {
        Example example = new Example(BranchContent.class);
        example.and().andEqualTo("categoryId",cid);
        List<BranchContent> list = branchContentMapper.selectByExample(example);
        BranchContent content = list.get(0);
        return content;
    }

    @Override
    public List<Map<String, Object>> branchTable(String name,Integer page,Integer limit) {
        if (name == null){
            PageHelper.startPage(page,limit);
        }
        List<BranchContent> branchContents = branchContentMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (BranchContent content : branchContents) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",content.getId());
            BranchCategory pCategory = branchCategoryMapper.selectByPrimaryKey(content.getCategoryId());
            map.put("name",pCategory.getName());
            map.put("GaiShu",content.getGaishu());
            map.put("ZhengZhuang",content.getZhengzhuang());
            map.put("BingYin",content.getBingyin());
            map.put("QuXue",content.getAijiuquxue());
            map.put("AnLi",content.getAnli());
            map.put("TiHui",content.getTihui());
            list.add(map);
        }

        return list;
    }

    @Override
    public int updateTable(Integer id, String value, String field) {

        BranchContent content = new BranchContent();
        content.setId(id);
        switch (field){
            case "GaiShu":
                content.setGaishu(value);
                break;
            case "ZhengZhuang":
                content.setZhengzhuang(value);
                break;
            case "BingYin":
                content.setBingyin(value);
                break;
            case "QuXue":
                content.setAijiuquxue(value);
                break;
            case "AnLi":
                content.setAnli(value);
                break;
            case "TiHui":
                content.setTihui(value);
                break;
        }
        content.setUpdated(LocalDateTime.now());
        //System.out.println(content);
        int i = branchContentMapper.updateByPrimaryKeySelective(content);
        //System.out.println(i);
        return i;
    }

    /**
     * 对表格进行搜素处理
     * @param name
     * @return
     */
    @Override
    public List<Map<String, Object>> branchSearch(String name,Integer page,Integer limit) {
        List<Map<String, Object>> list = branchTable(name,page,limit);
        List<Integer> resultNum = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String name1 = (String) map.get("name");
            if (name1.contains(name)){
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
    public Long branchSearchCount(String name){
        Example example = new Example(BranchCategory.class);
        example.createCriteria().andLike("name", "%"+name+"%");
        List<BranchCategory> branchCategories = branchCategoryMapper.selectByExample(example);
        return Long.valueOf(branchCategories.size());
    }

    @Override
    public int deleteTable(Integer id) {
        int i = branchContentMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public List<Map<String, Object>> branchAppSearch(String name) {
        Example example = new Example(BranchCategory.class);
        if (!name.isEmpty()){
            example.createCriteria().andLike("name","%"+name+"%");
        }
        List<BranchCategory> branchCategories = branchCategoryMapper.selectByExample(example);

        //构造一个新类型的list
        List<Map<String,Object>> list = new ArrayList<>();
        if (branchCategories != null){
            for (BranchCategory category : branchCategories) {
                Example example1 = new Example(BranchContent.class);
                example1.and().andEqualTo("categoryId",category.getId());
                List<BranchContent> branchContents = branchContentMapper.selectByExample(example1);
                //得到具体内容
                if (branchContents.size() != 0){
                    BranchContent content = branchContents.get(0);

                    //构造map
                    Map<String,Object> map = new HashMap<>();
                    map.put("type","branch");
                    map.put("name",category.getName());
                    map.put("content",content);
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> branchDetailByName(String name) {

        System.out.println(name);
        Example example = new Example(BranchCategory.class);
        example.and().andEqualTo("name",name);
        List<BranchCategory> branchCategories = branchCategoryMapper.selectByExample(example);
        BranchCategory branchCategory = branchCategories.get(0);
        Integer fid = branchCategory.getId();

        Example example1 = new Example(BranchContent.class);
        example1.and().andEqualTo("categoryId",fid);
        List<BranchContent> branchContents = branchContentMapper.selectByExample(example1);
        BranchContent content = branchContents.get(0);

        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",content.getId());
        BranchCategory pCategory = branchCategoryMapper.selectByPrimaryKey(content.getCategoryId());
        map.put("name",pCategory.getName());
        map.put("GaiShu",content.getGaishu());
        map.put("ZhengZhuang",content.getZhengzhuang());
        map.put("BingYin",content.getBingyin());
        map.put("QuXue",content.getAijiuquxue());
        map.put("AnLi",content.getAnli());
        map.put("TiHui",content.getTihui());
        result.add(map);
        return result;
    }


}
