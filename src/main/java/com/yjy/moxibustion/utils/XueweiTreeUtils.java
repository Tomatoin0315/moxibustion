package com.yjy.moxibustion.utils;

import com.yjy.moxibustion.pojo.PositionCategory;
import com.yjy.moxibustion.pojo.XueweiCategory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 * @author 杨景元
 * @date 2021/1/24 21:12
 */
public class XueweiTreeUtils {

    public static Map<String,Object> mapArray = new LinkedHashMap<String, Object>();

    public static List<XueweiCategory> menuCommon; //TreeNode是我刚创建的实体类,你需要放你的实体类
    public static List<Object> list = new ArrayList<>();

    public static List<Object> treeMenu(List<XueweiCategory> menu){
        menuCommon = menu;
        for (XueweiCategory treeNode : menu) {
            Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
            //我的数据库 `TYPE` int(4) DEFAULT NULL COMMENT '部门类型: 1根部门(企业),2二级单位,3三级部门,依次类推
            //这个根据需求,有可能父部门id是一样的,那就在判断语句中把父部门的id等于多少作为if中的条件
            if (treeNode.getParentId()==0) {
                setTreeMap(mapArr,treeNode);
                list.add(mapArr);
            }
        }
        return list;
    }

    //这里的fid参数是父id
    public static List<?> menuChild(Integer fid){
        List<Object> lists = new ArrayList<Object>();
        for(XueweiCategory a:menuCommon){
            Map<String,Object> childArray = new LinkedHashMap<String, Object>();
            if (a.getParentId()==fid) {
                setTreeMap(childArray,a);
                lists.add(childArray);
            }

        }
        return lists;
    }

    //写上你的类结构
    private static void setTreeMap(Map<String,Object> mapArr,XueweiCategory treeNode){
        mapArr.put("title", treeNode.getName());
        mapArr.put("id", treeNode.getId());
        if (treeNode.getIsParent()){
            mapArr.put("children", menuChild(treeNode.getId()));
        }
    }

}
