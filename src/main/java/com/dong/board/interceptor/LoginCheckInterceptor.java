package com.dong.board.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Log4j2
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    @Override//controller 호출전에 동작한다.
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Interceptor preHandle");
        HttpSession session=request.getSession(false);
        if(session!=null){
            String dest=request.getParameter("dest");
            if(dest!=null){
                request.getSession().setAttribute("dest",dest);
            }
        }

        return super.preHandle(request, response, handler);
    }
}
