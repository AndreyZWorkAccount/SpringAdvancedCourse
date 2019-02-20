package com.epam.spring.advanced.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.spring.advanced.util.WebUtil;
import com.epam.spring.core.controller.IBookingService;
import com.epam.spring.core.controller.IDiscountService;
import com.epam.spring.core.controller.IEventService;
import com.epam.spring.core.controller.IUserService;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

@Controller
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private IBookingService service;

    @Autowired
    private IUserService serviceU;

    @Autowired
    private IEventService serviceE;

    @Autowired
    @Qualifier("DiscountService")
    private IDiscountService discount;

    private static final String VIEW = "menuOrder";

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model) {
    	WebUtil.setMainAttributes(model,"welcome");
        return VIEW;
    }

    @RequestMapping(value = "/bookTicket", method = RequestMethod.POST)
    public String add(Model model, @RequestParam("userId") int userId, @RequestParam("eventId") int eventId,
            @RequestParam("seat") int seat) {
        User user = serviceU.getById(userId);
        Event event = serviceE.getById(eventId);
        Order order = new Order(user, event, seat, 0);
        service.setDiscountService(discount);
        if (service.bookTicket(order)) {
        	WebUtil.setMainAttributes(model,"Created");
        } else {
        	WebUtil.setMainAttributes(model,"Not Created");
        }
        return VIEW;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAll(Model model) {
        List<Order> list = service.getAllOrders();
        model.addAttribute("orders", list);
        WebUtil.setMainAttributes(model,"OK");
        return VIEW;
    }

    @RequestMapping(value = "/clear", method = RequestMethod.GET)
    public String clear(Model model) {
        service.clear();
        WebUtil.setMainAttributes(model,"Cleared");
        return VIEW;
    }

}
