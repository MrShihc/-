package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 老师表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Teacher implements Serializable {

    /**
     * 老师编号
     */
    private Long tid;

    /**
     * 老师名称
     */
    private String tname;

    /**
     * 老师账户
     */
    private String taccount;

    /**
     * 老师电话
     */
    private String telphone;

    /**
     * 老师邮箱
     */
    private String email;

    /**
     * 老师密码
     */
    private String pwd;
}
