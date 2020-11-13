package com.campus.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果工具类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    //返回的编码，success--成功，fail--失败
    private String success = "success";

    private Boolean flag;

    //返回的信息
    private String message;

    //返回的数据
    private Object data1;
    private Object data2;
    private Object data3;

    public Result(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}
