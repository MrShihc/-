package com.campus.controller;

import com.campus.entity.Exam;
import com.campus.entity.MenuBean;
import com.campus.entity.Student;
import com.campus.entity.TestBean;
import com.campus.service.StudentService;
import com.campus.common.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 忘记密码
     */
    @RequestMapping("/uppwd")
    public String uppwd(Student student, HttpSession session){
        try{
            //获取存入session的验证码
            String myCode = session.getAttribute("ucode").toString();
            System.out.println(myCode);
            //判断验证码是否一致
            if(myCode!=null){
                if(myCode.equals  (student.getScode())){
                  //  emailService.updatePwd(email);
                    return "ok";
                }else{
                    return "error";
                }
            }
            return "shixiao";

        }catch (Exception e){
            e.printStackTrace();
            return "no";
        }
    }

    /**
     * 学生登录
     */
    @RequestMapping("/stuLogin")
    public Result stuLogin(@RequestBody Student student, HttpServletRequest request){
       Student stu = studentService.stuLogin(student);
        /**
         * 判断查询的信息是否存在
         */
        if(stu!=null){
            /**
             * 登录成功
             * 把获取到的登录信息存入session中，用于之后使用
             */
            request.getSession().setAttribute("student",stu);
            return new Result(true,"登录成功！");
        }else{
            /**
             * 登录失败，给出相应提示
             */
            return new Result(false,"登录失败，账号或密码错误，请重试！");
        }
    }

    /**
     * 获取session中的学生姓名信息
     */
    @RequestMapping("/getStuName")
    public Result getStuName(HttpServletRequest request){
        Student student = (Student)request.getSession().getAttribute("student");
        if(student!=null){
            return new Result(true,student.getSname());
        }else{
            return new Result(false,"该账户用户名不存在！");
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logOut")
    public Result logOut(HttpServletRequest request){
        request.getSession().removeAttribute("student");
        return new Result(true,"退出成功！");
    }

    /**
     * 获取学生菜单信息
     */
    @RequestMapping("/getStuMenuInfo")
    public List<MenuBean> getStuMenuInfo(HttpServletRequest request){
        List<MenuBean> stuMenuInfo = studentService.getStuMenuInfo(request);
        return stuMenuInfo;
    }

    /**
     * 获取学生考试信息
     */
    @RequestMapping("/getStuTestInfo")
    public List<TestBean> getStuTestInfo(HttpServletRequest request){
        List<TestBean> stuTestInfo = studentService.getStuTestInfo(request);
        return stuTestInfo;
    }

    /**
     * 获取学生的历史考试信息
     */
    @RequestMapping("/findStuHistoryTest")
    public List<TestBean> findStuHistoryTest(HttpServletRequest request){
        return studentService.findStuHistoryTest(request);
    }

    /**
     * 根据试卷id获取试题信息
     */
    @RequestMapping("/getTestInfoByTestid")
    public Result getTestInfoByTestid(Long testid,HttpServletRequest request){
        return studentService.getTestInfoByTestid(testid,request);
    }

    /**
     * 学生去交卷
     */
    @RequestMapping("/savestuToSubmit")
    public Result savestuToSubmit(@RequestBody List<Exam> exam,Long testid,HttpServletRequest request){
        Student student = (Student)request.getSession().getAttribute("student");
        if(student!=null){
            Long time = (Long)request.getSession().getAttribute("time");
            Result result = studentService.savestuToSubmit(student.getSid(), testid, exam,time);
            return result;
        }
        return null;
    }

    /**
     * 获取学生历史试卷详情信息
     */
    @RequestMapping("/getStudentHistoryTest")
    public List<Exam> getStudentHistoryTest(String crediskey){
        List<Exam> studentHistoryTest = studentService.getStudentHistoryTest(crediskey);
//        for (Exam exam : studentHistoryTest) {
//            System.out.println(exam);
//        }
        return studentHistoryTest;
    }
}
