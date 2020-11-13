package com.campus.controller;

import com.campus.entity.Grade;
import com.campus.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 专门用于跳转界面的控制层
 */
@Controller
@RequestMapping("/skip")
public class SkipController {

    @Resource
    private TeacherService teacherService;

    /**
     * 跳转至发布试题界面，并根据当前登录人id，把这个登录人发布的班级查询出来，并展示给页面
     */
    @RequestMapping("/goMakeTest")
    public String goMakeTest(Model model){
       Long tid = 1L;
       List<Grade> gradeList =  teacherService.getCurLoginPeoGrade(tid);
       model.addAttribute("gradeList",gradeList);
       return "make_test";
    }
}
