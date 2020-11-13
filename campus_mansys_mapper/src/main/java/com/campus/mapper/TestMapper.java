package com.campus.mapper;

import org.apache.ibatis.annotations.Param;

public interface TestMapper {
    //保存考试和班级关联的信息
    void saveTestGradeInfo(@Param("gids") Long[] gids, @Param("testid") Long testid);
}
