package com.campus.controller;

import com.campus.entity.Student;
import com.campus.service.StudentService;
import com.campus.utils.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    private StudentService studentService;

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
}
