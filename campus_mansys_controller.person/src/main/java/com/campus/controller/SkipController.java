package com.campus.controller;

import com.campus.common.TestVo;
import com.campus.entity.Grade;
import com.campus.entity.Teacher;
import com.campus.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * 跳转至发布试题界面，
     */
    @RequestMapping("/goMakeTest")
    public String goMakeTest(){
       //返回到发布试题界面
       return "make_test";
    }

    /**
     * 跳转至阅卷管理界面,并根据老师id查询出该老师可以阅卷的信息
     */
    @RequestMapping("/goReadManager")
    public String goReadManager(Model model, HttpServletRequest request){

        Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
//        Long tid = 1L ;
        List<TestVo> readInfo = teacherService.getReadInfo(teacher.getTid());
        model.addAttribute("readInfo",readInfo);
        return "read_manager";
    }
}
