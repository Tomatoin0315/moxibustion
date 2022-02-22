package com.yjy.moxibustion.service;

import com.yjy.moxibustion.persistence.BaseService;
import com.yjy.moxibustion.pojo.XueweiContent;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/16 19:37
 */
public interface XueweiService extends BaseService<XueweiContent> {
    /**
     * 根据Cid查找该病症的治疗内容
     * @param cid
     * @return
     */
    XueweiContent getXueweiContentByCid(Integer cid);

    /**
     * 管理员拿数据表格
     * @param name 如果name为空，即渲染数据表格，对整个数据进行分页
     *             如果name不为空，即搜索功能，不对整个数据进行分页
     * @param page
     * @param limit
     * @return
     */
    List<Map<String,Object>> xueweiTable(String name,Integer page,Integer limit);

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
    List<Map<String,Object>> xueweiSearch(String name,Integer page,Integer limit);

    /**
     * 搜索总数
     * @param name
     * @return
     */
    Long xueweiSearchCount(String name);

    /**
     * 根据id删除指定内容
     * @param id
     * @return
     */
    int deleteTable(Integer id);

    /**
     * App端的搜索
     * @param name
     * @return
     */
    List<Map<String,Object>> xueweiAppSearch(String name);

    /**
     * 用户端的穴位搜索
     * 只返回穴位名称
     * @param name
     * @return
     */
    List<Map<String,Object>> xueweiUserSearchByName(String name);

    /**
     * 用户端的穴位详情
     * @param name
     * @return
     */
    List<Map<String,Object>> xueweiDetailByName(String name);
}
