package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 班级表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Grade implements Serializable {

    /**
     * 班级编号
     */
    private Long gid;
    private String gname;

}
