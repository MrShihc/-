package com.campus.service;

import com.campus.entity.CityBean;
import com.campus.entity.Grade;
import com.campus.entity.Student;
import com.campus.entity.UserBean;
import com.campus.utils.Result;

import java.util.List;

public interface UserService {
    List<UserBean> getUserList();

    List<Student> getStuList();

    List<Grade> getGradelist();

    List<CityBean> getProlistById(Long id);

    Result saveStuInfo(Student student);

    Result deleteStuBatchBySid(Long[] sids);

    Student saveStuById(Long sid);
}
