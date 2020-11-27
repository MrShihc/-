package com.campus.mapper;

import com.campus.common.TestVo;
import com.campus.entity.Grade;
import com.campus.entity.Teacher;
import com.campus.entity.TestBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 老师数据访问接口
 */
public interface TeacherMapper {
    //根据老师id获取独立考试表的信息
    List<TestBean> getDuliTestList(Long tid);

    //根据老师id获取当前登录人可以发布试题的班级
    List<Grade> getCurLoginPeoGrade(Long tid);

    //验证试卷名称的唯一性
    TestBean uniTestname(String testname);

    //保存考试表信息
    void saveTestInfo(TestBean testBean);

    //查询出所有的老师信息
    List<Teacher> getTeacher();

    //根据老师id查询出该老师要阅卷的信息
    public List<TestVo> getReadInfo(@Param("tid") Long tid);

    //获取单个班级的考试总人数
    public Map getCounts(@Param("gid") Long gid, @Param("testid") Long testid);

    //去阅卷，查询出学生信息
    public List<TestBean> readTestStudent(@Param("gid") Long gid,@Param("testid") Long testid);

    //根据crediskey查询出学生和考试关联的信息
    TestBean getStudentTestByCrediskey(String crediskey);

    //根据crediskey更新学生的主观分数以及总分
    void updateStuTestQscoreAndScore(@Param("crediskey") String crediskey,@Param("qscore") Double qscore);

    //根据老师账户以及角色id去查询数据库信息
    List<Teacher> getLogin(@Param("taccount") String taccount);
}
