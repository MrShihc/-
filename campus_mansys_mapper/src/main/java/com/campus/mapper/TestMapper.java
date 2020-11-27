package com.campus.mapper;

import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    //保存考试和班级关联的信息
    void saveTestGradeInfo(@Param("gid") Long gid,@Param("testid") Long testid,@Param("tid") Long tid);
}
