package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {

    /**
     * 学生编号
     */
    private Long sid;

    /**
     * 学生名称
     */
    private String sname;

    /**
     * 学生账户
     */
    private String saccount;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户生日
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    /**
     * 用户所在地区
     */
    private String address;

    /**
     * 班级的实体bean   呈现出一对一的关系
     */
    private Grade grade = new Grade();
}
