package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 选项表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamOption implements Serializable {

    /**
     * 选项编号
     */
    private Long optionid;

    /**
     * 选项名称
     */
    private String oname;

    /**
     * 是否正确   0--不正确，1--正确
     */
    private Integer istrue =0;

    /**
     * 试题编号
     */
    private Long examid;
}
