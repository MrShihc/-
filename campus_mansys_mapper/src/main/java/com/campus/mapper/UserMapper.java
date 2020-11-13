package com.campus.mapper;

import com.campus.entity.CityBean;
import com.campus.entity.Grade;
import com.campus.entity.Student;
import com.campus.entity.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    //查询所有的用户信息
    List<UserBean> getUserList();

    //查询所有的学生信息
    List<Student> getStuList();

    //查询所有的班级信息
    List<Grade> getGradelist();

    //根据id查询所对应的三级联动需要的信息
    List<CityBean> getProlistById(Long id);

    //保存学生信息
    void saveStuInfo(Student student);

    //保存学生和班级关联信息
    void saveStuGradeInfo(@Param("sid") Long sid, @Param("gid") Long gid);

    //根据sid修改学生信息
    void saveStuInfoBySid(Student student);

    //根据sid批量删除学生信息
    void deleteStuBatchBySid(Long sid);

    //根据sid批量删除学生和班级关联信息
    void deleteStuGradeBySid(Long sid);

    //根据学生id查询学生信息
    Student saveStuById(Long sid);

    //根据学生id修改学生和班级的中间表信息
    void saveStuGradeInfoById(@Param("gid") Long gid, @Param("sid") Long sid);
}
