package com.campus.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class GradeTeacher implements Serializable {
    private Long gid;
    private Long tid;
}
