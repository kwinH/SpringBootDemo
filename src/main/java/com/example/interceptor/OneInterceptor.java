package com.example.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class OneInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {


        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            System.out.println("被one拦截了");
            response.sendRedirect("/");
            return false;
        }

        System.out.println("被one拦截了，放行.....");
        return true;

    }
}
