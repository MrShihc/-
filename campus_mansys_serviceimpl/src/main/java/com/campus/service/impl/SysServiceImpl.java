package com.campus.service.impl;

import com.campus.entity.MenuBean;
import com.campus.mapper.SysMapper;
import com.campus.service.SysService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysServiceImpl implements SysService {

    @Resource
    private SysMapper sysMapper;

    public List<MenuBean> getMenuList() {
        return sysMapper.getMenuList();
    }
}
