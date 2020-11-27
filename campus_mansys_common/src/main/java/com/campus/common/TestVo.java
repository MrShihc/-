package com.campus.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestVo implements Serializable,Comparable<TestVo> {
    private Long testid;
    private String testname;
    private Integer totalcount;
    private Integer nocount;
    private Long gid;
    private String gname;

    public int compareTo(TestVo o) { //自定义排序逻辑
        return o.totalcount-this.totalcount;//以传入的Cell的横坐标由小到大进行排序
    }
}
