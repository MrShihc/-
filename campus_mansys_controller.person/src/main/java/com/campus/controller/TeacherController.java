package com.campus.controller;

import com.campus.entity.Exam;
import com.campus.entity.TestBean;
import com.campus.service.TeacherService;
import com.campus.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师控制层
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController{

    @Resource
    private TeacherService teacherService;

    //获取登录用户的用户id
    Long tid = 1L;

    /**
     * 根据登录的用户id查询该用户可以看到的考试信息
     * @param model
     * @return
     */
    @RequestMapping("/getDuliTestList")
    public String getDuliTestList(Model model){

        List<TestBean> testList = teacherService.getDuliTestList(tid);
        model.addAttribute("testList",testList);

        //跳转至前端的独立考试界面
        return "duli_test";
    }

    /**
     * 验证考试名称的唯一性
     */
    @RequestMapping("/uniTestname")
    @ResponseBody
    public Result uniTestname(String testname){
        return teacherService.uniTestname(testname);
    }

    /**
     *发布试题
     */
    @RequestMapping("/makeTest")
    @ResponseBody
    public Result makeTest(TestBean testBean,Long[] gids, HttpServletRequest request){

        //获取试卷信息
        List<Exam> exam = (List<Exam>) request.getSession().getAttribute("exam");
        Result result =  teacherService.saveMakeTest(exam,testBean,gids);

//        return "redirect:getDuliTestList.do";
        return result;
    }



}
