package com.campus.mapper;

import com.campus.entity.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> getLogin(String saccount);
}
