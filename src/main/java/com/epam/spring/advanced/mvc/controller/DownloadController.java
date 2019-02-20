package com.epam.spring.advanced.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.advanced.util.WebUtil;
import com.epam.spring.core.controller.IBookingService;
import com.epam.spring.core.model.Order;

@Controller
@RequestMapping(value = "/download", headers = HttpHeaders.ACCEPT+"="+ "application/pdf")
public class DownloadController {

    @Autowired
    private IBookingService service;
    
    private static final String VIEW = "menuDownload";
    
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model) {
    	WebUtil.setMainAttributes(model,"welcome");
        return VIEW;
    }
    
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public ModelAndView getAll() {
              
        List<Order> list = service.getAllOrders();
        ModelAndView mav = new ModelAndView("pdfPageOrder","orders",list);  
        return mav;
    }
    
}
