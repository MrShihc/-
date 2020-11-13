package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 试题表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Exam implements Serializable {

    /**
     * 试题编号
     */
    private Long examid;

    /**
     * 试题名称
     */
    private String ename;

    /**
     * 试题类型
     */
    private String etype;

    /**
     * 试题分值
     */
    private Double efenzhi;

    /**
     * 考卷id
     */
    private Long testid;

    private List<ExamOption> options = new ArrayList<ExamOption>();
}
