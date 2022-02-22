package com.yjy.moxibustion.web.controller.data;

import com.github.pagehelper.PageHelper;
import com.yjy.moxibustion.dto.LayuiResp;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.XueweiContent;
import com.yjy.moxibustion.service.XueweiCategoryService;
import com.yjy.moxibustion.service.XueweiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 杨景元
 * @date 2021/1/25 15:59
 */
@RestController
@RequestMapping("/xuewei")
public class XueweiWebController {

    @Autowired
    private XueweiCategoryService xueweiCategoryService;

    @Autowired
    private XueweiService xueweiService;

    @GetMapping("/xueweiCategory/tree")
    public RestResp<List<Object>> tree(){
        List<Object> tree = xueweiCategoryService.getTree();
        return RestResp.success("success",tree);
    }


    /**
     * 编辑类别的名字
     * @param id
     * @param value
     * @return
     */
    @PostMapping("/update")
    public RestResp updateName(Integer id,String value){
        int i = xueweiCategoryService.updateName(id, value);
        if (i!=0){
            return RestResp.success();
        }else {
            //更新失败
            return RestResp.fail();
        }
    }

    /**
     * 根据id删除，如果该节点下还有节点，则不可以删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public RestResp deleteById(Integer id){
        int i = xueweiCategoryService.deleteById(id);
        if (i!=0){
            return RestResp.success();
        }else {
            return RestResp.fail();
        }
    }

    @GetMapping("/table")
    public LayuiResp<Map<String,Object>> xueweiTable(String page, String limit,
                                                     String name){
        /*System.out.println("-------------------------");
        System.out.println("page:"+page+"      "+"limit:"+limit);*/
        if (name!=null){
            //代表是搜素
            Long count = xueweiService.xueweiSearchCount(name);//搜索后的总条数
            List<Map<String, Object>> list = xueweiService.xueweiSearch(name,Integer.parseInt(page),Integer.parseInt(limit));
            return LayuiResp.createResult(count,list);
        }else {
            List<XueweiContent> all = xueweiService.getAll();
            List<Map<String, Object>> list = xueweiService.xueweiTable(null,Integer.parseInt(page),Integer.parseInt(limit));
            return LayuiResp.createResult((long) all.size(),list);
        }
    }

    @PostMapping("/add")
    public RestResp addXueweiCategory(Integer id){
        int add = xueweiCategoryService.add(id);
        if (add!=0){
            //添加成功,把当前的节点的isParent设置为true
            int i = xueweiCategoryService.updateIsParent(id);
            if (i!=0){
                return RestResp.success("父节点更新成功");
            }else {
                return RestResp.fail("父节点更新失败");
            }
        }else {
            return RestResp.fail("添加失败");
        }
    }

    /**
     * 更新数据表格中的某个字段
     * @param id
     * @param value
     * @param field
     * @return
     */
    @PostMapping("/updateTable")
    public RestResp updateTable(Integer id,String value,String field){
        //System.out.println(id + "    " +  value  + "    " + field );
        int i = xueweiService.updateTable(id, value, field);
        if (i!=0){
            return RestResp.success();
        }else {
            return RestResp.fail();
        }
    }

    @PostMapping("/deleteTable")
    public RestResp deleteTable(Integer id){
        int i = xueweiService.deleteTable(id);
        if (i!=0){
            return RestResp.success("删除成功");
        }else {
            return RestResp.fail("删除失败");
        }
    }

    /**
     * 用户端的穴位搜索功能
     * 只返回name
     * @param name
     * @return
     */
    @GetMapping("/xueweiUserSearchByName")
    public RestResp xueweiUserSearchByName(String name){
        //System.out.println(name);
        List<Map<String, Object>> list = xueweiService.xueweiUserSearchByName(name);
        //搜索成功
        if (list.size() != 0){
            return RestResp.success("搜索成功",list);
        }else {
            return RestResp.fail("搜索失败");
        }
    }

    @GetMapping("/xueweiDetailByName")
    public RestResp xueweiDetailByName(String name){
        List<Map<String, Object>> list = xueweiService.xueweiDetailByName(name);
        if (list.size() != 0){
            return RestResp.success("成功",list);
        }else {
            return RestResp.fail("失败");
        }

    }
}
