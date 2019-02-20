package com.epam.spring.advanced.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class DefaultHandlerException implements HandlerExceptionResolver {

    private static final String VIEW = "exception";
    
    private static final String STATUS_LINE = "Status message:";
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
       ModelAndView mav = new ModelAndView();
       mav.addObject("status", STATUS_LINE + "Ooops, we have some problems...");
       mav.addObject("exception", ex);
       mav.setViewName(VIEW);
       return mav;
    }

}
