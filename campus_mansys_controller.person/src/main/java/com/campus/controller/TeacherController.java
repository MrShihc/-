package com.campus.controller;

import com.campus.common.GradeTeacher;
import com.campus.common.TestVo;
import com.campus.entity.Exam;
import com.campus.entity.Grade;
import com.campus.entity.Teacher;
import com.campus.entity.TestBean;
import com.campus.service.TeacherService;
import com.campus.common.Result;
import org.apache.http.client.methods.HttpPatch;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 老师控制层
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController{

    @Resource
    private TeacherService teacherService;

    //获取登录用户的用户id
   // Long tid = 1L;

    /**
     * 给学生判分
     */
    @RequestMapping("/toPanfen")
    public String toPanfen(Model model, String crediskey){
       List<Exam> examList =  teacherService.getPanfen(crediskey);
       model.addAttribute("examList",examList);
       model.addAttribute("crediskey",crediskey);
       return "panfen";
    }

    /**
     * 去阅卷界面,并查询出学生信息
     */
    @RequestMapping("/toReadTestStudent")
    public String toReadTestStudent(Model model,Long gid,Long testid,HttpServletRequest request){
       List<TestBean> testList = teacherService.getReadTestStudent(gid,testid);

       //把考试id以及班级id存入session中用于后续使用
        request.getSession().setAttribute("gid",gid);
        request.getSession().setAttribute("testid",testid);

       model.addAttribute("testList",testList);
       return "read_test";
    }

    /**
     * 获取到选中的班级信息以及老师信息
     */
    @RequestMapping("/saveGradeTeacher")
    @ResponseBody
    public Result saveGradeTeacher(@RequestBody List<Grade> grade, HttpServletRequest request){
        if(grade!=null && grade.size()>=1){
            request.getSession().setAttribute("grade",grade);
            return new Result(true,"保存成功");
        }else{
            return new Result(false,"保存失败，请及时联系管理员！");
        }
    }

    /**
     * 获取班级信息以及老师信息
     */
    @RequestMapping("/getGradeAndTeacher")
    @ResponseBody
    public Map getGradeAndTeacher(HttpServletRequest request){
        Teacher teacher1 = (Teacher)request.getSession().getAttribute("teacher");
        //班级信息
        List<Grade> curLoginPeoGrade = teacherService.getCurLoginPeoGrade(teacher1.getTid());

        //老师信息
        List<Teacher> teacher = teacherService.getTeacher();

        HashMap map = new HashMap();
        map.put("grade",curLoginPeoGrade);
        map.put("teacher",teacher);
        return map;
    }

    /**
     * 根据登录的用户id查询该用户可以看到的考试信息
     * @param model
     * @return
     */
    @RequestMapping("/getDuliTestList")
    public String getDuliTestList(Model model,HttpServletRequest request){
        Teacher teacher1 = (Teacher)request.getSession().getAttribute("teacher");

        List<TestBean> testList = teacherService.getDuliTestList(teacher1.getTid());
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
    public Result makeTest(TestBean testBean,HttpServletRequest request){

        //获取试卷信息
        List<Exam> exam = (List<Exam>) request.getSession().getAttribute("exam");

        //获取班级以及老师信息
        List<Grade> grade = (List<Grade>)request.getSession().getAttribute("grade");
        Teacher teacher1 = (Teacher)request.getSession().getAttribute("teacher");

        Result result =  teacherService.saveMakeTest(teacher1.getTid(),exam,testBean,grade,request);

//        return "redirect:getDuliTestList.do";
        return result;
    }

    /**
     * 去保存学生的客观题信息
     */
    @RequestMapping("/saveTestStudentQuestion")
    public String saveTestStudentQuestion(String crediskey,Long[] examids,Double[] fenzhis,HttpServletRequest request){
        teacherService.saveTestStudentQuestion(crediskey,examids,fenzhis);
        Long gid = (Long)request.getSession().getAttribute("gid");
        Long testid = (Long)request.getSession().getAttribute("testid");
        return "forward:toReadTestStudent.do?gid="+gid+"&testid="+testid;
    }

    /**
     * 老师登录
     */
    @RequestMapping("/toTeacherLogin")
    @ResponseBody
    public Result teacherLogin(Teacher teacher,HttpServletRequest request){
        if(teacher!=null){
            Teacher tea = teacherService.getLogin(teacher,request);
            if(tea!=null){
                return new Result(true,"登录成功！");
            }
        }
        return new Result(false,"登录失败！");
    }

    @RequestMapping("/toLogin")
    public String goLogin(){
        return "login";
    }

    /**
     * 跳转到main界面
     */
    @RequestMapping("/toMain")
    public String goMain(){
        return "main";
    }

}
