package com.yjy.moxibustion.service.impl;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.abstracts.AbstractBaseServiceImpl;
import com.yjy.moxibustion.dao.PositionCategoryMapper;
import com.yjy.moxibustion.dao.PositionContentMapper;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.BranchCategory;
import com.yjy.moxibustion.pojo.BranchContent;
import com.yjy.moxibustion.pojo.PositionCategory;
import com.yjy.moxibustion.pojo.PositionContent;
import com.yjy.moxibustion.service.DiseaseByPositionService;
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
public class DiseaseByPositionServiceImpl extends AbstractBaseServiceImpl<PositionContent,PositionContentMapper> implements DiseaseByPositionService {
    @Autowired
    private PositionContentMapper positionContentMapper;

    @Autowired
    private PositionCategoryMapper positionCategoryMapper;


    @Override
    public PositionContent getPositionContentByCid(Integer cid) {
        Example example = new Example(PositionContent.class);
        example.and().andEqualTo("categoryId",cid);
        List<PositionContent> list = positionContentMapper.selectByExample(example);
        PositionContent content = list.get(0);
        return content;
    }

    @Override
    public List<Map<String, Object>> positionTable(String name,Integer page,Integer limit) {
        if (name == null){
            PageHelper.startPage(page,limit);
        }
        List<PositionContent> positionContents = positionContentMapper.selectAll();
        List<Map<String,Object>> list = new ArrayList<>();
        for (PositionContent content : positionContents) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",content.getId());
            PositionCategory pCategory = positionCategoryMapper.selectByPrimaryKey(content.getCategoryId());
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

        PositionContent content = new PositionContent();
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
        int i = positionContentMapper.updateByPrimaryKeySelective(content);
        return i;
    }


    /**
     * 对表格进行搜素处理
     * @param name
     * @return
     */
    @Override
    public List<Map<String, Object>> positionSearch(String name,Integer page,Integer limit) {
        List<Map<String, Object>> list = positionTable(name,page,limit);
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
    public Long positionSearchCount(String name){
        Example example = new Example(PositionCategory.class);
        example.createCriteria().andLike("name","%"+name+"%");
        List<PositionCategory> list = positionCategoryMapper.selectByExample(example);
        return Long.valueOf(list.size());
    }

    @Override
    public int deleteTable(Integer id) {
        int i = positionContentMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public List<Map<String, Object>> positionAppSearch(String name) {
        Example example = new Example(PositionCategory.class);
        if (!name.isEmpty()){
            example.createCriteria().andLike("name","%"+name+"%");
        }
        List<PositionCategory> positionCategories = positionCategoryMapper.selectByExample(example);

        //构造一个新类型的list
        List<Map<String,Object>> list = new ArrayList<>();
        if (positionCategories != null){
            for (PositionCategory category : positionCategories) {
                Example example1 = new Example(PositionContent.class);
                example1.and().andEqualTo("categoryId",category.getId());
                List<PositionContent> positionContents = positionContentMapper.selectByExample(example1);
                //得到具体内容
                if (positionContents.size() != 0){
                    PositionContent content = positionContents.get(0);

                    //构造map
                    Map<String,Object> map = new HashMap<>();
                    map.put("type","position");
                    map.put("name",category.getName());
                    map.put("content",content);
                    list.add(map);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> positionDetailByName(String name) {
        Example example1 = new Example(PositionCategory.class);
        example1.and().andEqualTo("name",name);
        List<PositionCategory> positionCategories = positionCategoryMapper.selectByExample(example1);
        PositionCategory positionCategory = positionCategories.get(0);
        Integer fid = positionCategory.getId();


        Example example = new Example(PositionContent.class);
        example.and().andEqualTo("categoryId",fid);
        List<PositionContent> positionContents = positionContentMapper.selectByExample(example);
        PositionContent content = positionContents.get(0);

        List<Map<String,Object>> result = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",content.getId());
        PositionCategory pCategory = positionCategoryMapper.selectByPrimaryKey(content.getCategoryId());
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
