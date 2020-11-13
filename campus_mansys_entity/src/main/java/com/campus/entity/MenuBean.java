package com.campus.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuBean implements Serializable {

    /**
     * 菜单编号
     */
    private Long mid;

    /**
     * 菜单名称
     */
    private String mname;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 跳转位置
     */
    private String target;

    /**
     * 样式
     */
    private String icon;
}
