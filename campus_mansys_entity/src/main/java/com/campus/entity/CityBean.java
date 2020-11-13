package com.campus.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 城市的实体bean
 */
@Data
public class CityBean implements Serializable {
    private Long id;
    private Long pid;
    private String cname;

}
