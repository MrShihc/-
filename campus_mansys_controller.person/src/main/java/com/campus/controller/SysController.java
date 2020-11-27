package com.campus.controller;

import com.alibaba.fastjson.JSONArray;
import com.campus.entity.MenuBean;
import com.campus.entity.Teacher;
import com.campus.service.SysService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (主模块)系统模块
 */
@Controller
@RequestMapping("/sys")
public class SysController {

    @Resource
    private SysService sysService;

    /**
     * 获取所有的菜单信息，并以ztree格式展示
     * @param model
     * @return
     */
    @RequestMapping("/getMenuJson")
    public String getMenuJson(Model model, HttpServletRequest request){
        Teacher teacher = (Teacher)request.getSession().getAttribute("teacher");
        if(teacher!=null){
            List<MenuBean> list = sysService.getMenuList(teacher.getRid());
            String json = JSONArray.toJSONString(list);
            model.addAttribute("json",json);
        }
        return "left";
    }

}
