package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.PositionContent;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/16 19:48
 */
public interface DiseaseByPositionService extends BaseService<PositionContent> {
    /**
     * 根据Cid查找该病症的治疗内容
     * @param cid
     * @return
     */
    PositionContent getPositionContentByCid(Integer cid);

    /**
     * 管理员拿数据表格
     * @return
     */
    List<Map<String,Object>> positionTable(String name,Integer page,Integer limit);

    /**
     * 更改某个字段
     * @param id
     * @param value
     * @param field
     * @return
     */
    int updateTable(Integer id,String value,String field);

    /**
     * 搜索
     * @param name
     * @return
     */
    List<Map<String,Object>> positionSearch(String name,Integer page,Integer limit);

    /**
     * 搜索后的总条数
     * @param name
     * @return
     */
    Long positionSearchCount(String name);

    /**
     * 根据id删除内容
     * @param id
     * @return
     */
    int deleteTable(Integer id);

    /**
     * App端的搜索
     * @param name
     * @return
     */
    List<Map<String,Object>> positionAppSearch(String name);

    /**
     * 用户端的按照名字查看详细治疗方案
     * @param name
     * @return
     */
    List<Map<String,Object>> positionDetailByName(String name);
}
