package com.campus.mapper;

import com.campus.entity.Exam;
import com.campus.entity.MenuBean;
import com.campus.entity.Student;
import com.campus.entity.TestBean;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StudentMapper {

    //根据学生账户进行登录验证
    List<Student> getLogin(@Param("saccount") String saccount);

    //根据角色id查询该学生可以看到的菜单
    List<MenuBean> getStuMenuInfo(@Param("rid") Long rid);

    //根据学生id查询出学生的考试信息
    List<TestBean> getStuTestInfo(@Param("sid") Long sid);

    //获取学生的历史考试信息
    List<TestBean> findStuHistoryTest(@Param("sid") Long sid);

    //根据试卷id获取试题信息
    List<Exam> getTestInfoByTestid(@Param("testid") Long testid);

    //根据试卷id获取试卷信息
    TestBean getTestInfo(@Param("testid") Long testid);

    //保存学生作答信息
    void stuToSubmits(@Param("sid") Long sid,@Param("testid") Long testid,
                     @Param("cscore") Double cscore,@Param("stesttime")Long stesttime,
                     @Param("testendtime") Date testendtime,@Param("crediskey") String crediskey);

    //更改主观分数
    void updateStudentQscore(@Param("sid") Long sid,@Param("testid") Long testid);
}
