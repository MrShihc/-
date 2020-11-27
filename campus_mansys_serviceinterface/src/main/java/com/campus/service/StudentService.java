package com.campus.service;

import com.campus.common.Result;
import com.campus.entity.Exam;
import com.campus.entity.MenuBean;
import com.campus.entity.Student;
import com.campus.entity.TestBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StudentService {
    //根据学生账户进行登录
    Student stuLogin(Student student);

    //获取学生菜单信息
    List<MenuBean> getStuMenuInfo(HttpServletRequest request);

    //根据学生id查询学生学生的考试信息
    List<TestBean> getStuTestInfo(HttpServletRequest request);

    //获取学生的历史考试信息
    List<TestBean> findStuHistoryTest(HttpServletRequest request);

    //根据试卷id获取对应的试题信息
    Result getTestInfoByTestid(Long testid,HttpServletRequest request);

    //保存学生的考试信息
    Result savestuToSubmit(Long sid, Long testid, List<Exam> exam,Long time);

    //获取学生历史试卷详情信息
    List<Exam> getStudentHistoryTest(String crediskey);
}
