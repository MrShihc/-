package com.campus.service;

import com.campus.common.Result;
import com.campus.common.TestVo;
import com.campus.entity.Exam;
import com.campus.entity.Grade;
import com.campus.entity.Teacher;
import com.campus.entity.TestBean;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师业务逻辑层接口
 */
public interface TeacherService {
    //根据老师id查询出该老师发布的独立考试信息
    List<TestBean> getDuliTestList(Long tid);

    //根据当前登录人id，把这个老师发布的班级查询出来，直接给页面
    List<Grade> getCurLoginPeoGrade(Long tid);

    //判断试卷名称的唯一性
    Result uniTestname(String testname);

    //发布试题
    Result saveMakeTest(Long tid, List<Exam> exam, TestBean testBean, List<Grade> grade,HttpServletRequest request);

    //查询出所有的老师信息
    List<Teacher> getTeacher();

    //根据老师id查询出该老师要阅卷的信息
    public List<TestVo> getReadInfo(@Param("tid") Long tid);

    //去阅卷，查询出学生信息
    List<TestBean> getReadTestStudent(Long gid, Long testid);

    //获取学生的主观题信息
    List<Exam> getPanfen(String crediskey);

    //保存学生的客观题作答信息
    void saveTestStudentQuestion(String crediskey, Long[] examids, Double[] fenzhis);

    //根据老师账户进行登录
    Teacher getLogin(Teacher teacher, HttpServletRequest request);
}
