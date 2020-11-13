package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 考卷表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestBean implements Serializable {

    /**
     * 考卷编号
     */
    private Long testid;

    /**
     * 考卷名称
     */
    private String testname;

    /**
     * 总分数
     */
    private Double totalscore;

    /**
     * 通过分数
     */
    private Double passscore;

    /**
     * 考试时长
     */
    private Integer testtime;

    /**
     * 开始时间
     */
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 上传时间
     */
    private Date updatetime = new Date();

    /**
     * 发布人
     */
    private String testauthor;

    /**
     * 发布人编号
     */
    private Long authorid;

}
