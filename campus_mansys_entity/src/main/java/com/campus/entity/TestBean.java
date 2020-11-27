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

    /**
     * 得分
     */
    private Double score;

    /**
     * 客观分数
     */
    private Double cscore;

    /**
     * 主观分数
     */
    private Double qscore;

    /**
     * 学生考试时长
     */
    private Long stesttime;

    /**
     * 学生考试结束时间
     */
    private Date testendtime;

    /**
     * 主观rediskey
     */
    private String qrediskey;

    /**
     * 客观rediskey
     */
    private String crediskey;

    /**
     * 是否在考试时间
     */
    private String testStatus;

    /**
     * 阅卷时需要的
     */
    private String sname;

}
