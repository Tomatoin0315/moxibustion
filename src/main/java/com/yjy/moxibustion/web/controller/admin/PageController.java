package com.yjy.moxibustion.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 杨景元
 * @date 2021/1/16 19:22
 */
@Controller
@RequestMapping("/admin")
public class PageController {

    @GetMapping("/")
    public String login(){
        return "Login";
    }

    @GetMapping("/index")
    public String index(){
        return "Index";
    }

    @GetMapping("/xueweiCategory")
    public String xueweiCategory(){
        return "Acupoint";
    }

    @GetMapping("/xuewei")
    public String xuewei(){
        return "AcupointTable";
    }

    @GetMapping("/branchCategory")
    public String branchCategory(){
        return "Subject";
    }

    @GetMapping("/branch")
    public String branch(){
        return "SubjectTable";
    }

    @GetMapping("/branchFeedback")
    public String branchFeedback(){
        return "SubjectFeedback";
    }

    @GetMapping("/branchTreatmentPlan")
    public String branchTreatmentPlan(){
        return "SubjectTreatmentPlan";
    }

    @GetMapping("/positionCategory")
    public String positionCategory(){
        return "Location";
    }

    @GetMapping("/position")
    public String position(){
        return "LocationTable";
    }

    @GetMapping("/positionFeedback")
    public String positionFeedback(){
        return "LocationFeedback";
    }

    @GetMapping("/positionTreatmentPlan")
    public String positionTreatmentPlan(){
        return "LocationTreatmentPlan";
    }

    @GetMapping("/article")
    public String article(){
        return "Article";
    }

    @GetMapping("/page1")
    public String page1(){
        return "Page1";
    }
}
