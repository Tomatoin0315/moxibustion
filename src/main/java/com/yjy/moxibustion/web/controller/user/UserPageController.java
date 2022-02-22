package com.yjy.moxibustion.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebParam;

/**
 * @author 杨景元
 * @date 2021/6/5 17:28
 */
@Controller
public class UserPageController {
    private String prefix = "user/";

    @GetMapping("/")
    public String index(){
        return prefix + "index";
    }

    //轮播图
    @GetMapping("/carousel")
    public String carousel(){
        return prefix + "carousel";
    }

    @GetMapping("/xueweiIndex")
    public String xueweiIndex(){

        return prefix + "xueweiIndex";
    }

    @GetMapping("/xueweiDetail")
    public String xueweiDetail(String name, Model model){
        //System.out.println(name);
        model.addAttribute("name",name);
        return prefix + "xueweiDetail";
    }

    @GetMapping("/planIndex")
    public String planIndex(){
        return prefix + "planIndex";
    }

    @GetMapping("/planDetail")
    public String planDetail(String name,String type,
                             Model model){
        model.addAttribute("name",name);
        model.addAttribute("type",type);
        return prefix + "planDetail";
    }

    @GetMapping("/searchResult")
    public String searchResult(String name,String type,
                               Model model){
        model.addAttribute("name",name);
        model.addAttribute("type",type);
        //System.out.println("name:"+name);
        //System.out.println("type:"+type);
        return prefix + "searchResult";
    }

    @GetMapping("/article")
    public String article(){
        return prefix + "article";
    }

    @GetMapping("/article1")
    public String article1(){
        return prefix + "article1";
    }

    @GetMapping("/article2")
    public String article2(){
        return prefix + "article2";
    }

    @GetMapping("/information")
    public String information(){
        return prefix + "information";
    }
}
