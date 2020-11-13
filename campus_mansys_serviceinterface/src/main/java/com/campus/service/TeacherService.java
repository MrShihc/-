package com.campus.service;

import com.campus.entity.Exam;
import com.campus.entity.Grade;
import com.campus.entity.TestBean;
import com.campus.utils.Result;

import java.util.List;

public interface TeacherService {
    List<TestBean> getDuliTestList(Long tid);

    List<Grade> getCurLoginPeoGrade(Long tid);

    Result uniTestname(String testname);

    Result saveMakeTest(List<Exam> exam, TestBean testBean, Long[] gids);
}
