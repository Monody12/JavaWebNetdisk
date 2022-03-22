package com.netdisk.resolver;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error.jsp");
        modelAndView.addObject("errorInfo",ex.toString());
        if (ex instanceof java.lang.NullPointerException){
            modelAndView.addObject("errorType","null");
        }else{
            modelAndView.addObject("errorType","other");
        }
        ex.printStackTrace();
        return modelAndView;
    }
}

