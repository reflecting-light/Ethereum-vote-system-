package com.example.interceptor;


import com.example.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object o)throws Exception{
        HttpSession session = httpServletRequest.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String[] requireAuthPages = new String[]{"index",};

        String uri = httpServletRequest.getRequestURI();
        uri = StringUtils.remove(uri,contextPath+'/');
        String page = uri;

        if(begingWith(page,requireAuthPages)){
            User user = (User) session.getAttribute("user");
            if(user==null){
                httpServletResponse.sendRedirect("login");
                return false;
            }
        }
        return true;
    }
    private boolean begingWith(String page,String[] requireAuthPages){
        boolean result = false;
        for(String requireAuthPage : requireAuthPages){
            if(StringUtils.startsWith(page,requireAuthPage)){
                result=true;
                break;
            }
        }
        return result;
    }

}
