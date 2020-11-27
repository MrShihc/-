package com.campus.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FilterController implements Filter {

    Set<String> setUrl = new HashSet<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String notFilterUrl = filterConfig.getInitParameter("notFilterUrl");
        for(String url:notFilterUrl.split(",")){
            setUrl.add(url.trim());
        }
        System.out.println(notFilterUrl);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req =  (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        String uri = req.getRequestURI();
        uri = uri.substring(uri.lastIndexOf("/"));
        if(setUrl.contains(uri)){
            chain.doFilter(request,response);
        }else{
            Object student = req.getSession().getAttribute("student");
            if(student!=null){
                chain.doFilter(request, response);
            }else{
                resp.sendRedirect("/stu_login.html");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
