package com.campus.mapper;

import com.campus.entity.MenuBean;

import java.util.List;

public interface SysMapper {

    //根据角色id查询菜单
    public List<MenuBean> getMenuList(Long rid);
}
