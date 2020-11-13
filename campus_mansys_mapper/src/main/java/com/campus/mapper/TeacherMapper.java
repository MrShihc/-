package com.campus.mapper;

import com.campus.entity.Grade;
import com.campus.entity.TestBean;

import java.util.List;

public interface TeacherMapper {
    //根据老师id获取独立考试表的信息
    List<TestBean> getDuliTestList(Long tid);

    //根据老师id获取当前登录人可以发布试题的班级
    List<Grade> getCurLoginPeoGrade(Long tid);

    //验证试卷名称的唯一性
    TestBean uniTestname(String testname);

    //保存考试表信息
    void saveTestInfo(TestBean testBean);
}
