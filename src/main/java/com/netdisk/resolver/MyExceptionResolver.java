package com.netdisk.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error.jsp");
        modelAndView.addObject("errorInfo", ex.toString());
        if (ex instanceof java.lang.NullPointerException) {
            modelAndView.addObject("errorType", "null");
        } else {
            modelAndView.addObject("errorType", "other");
        }
        log.error("出现异常：{}", ex.toString());
//        ex.printStackTrace();
        return modelAndView;
    }
}

