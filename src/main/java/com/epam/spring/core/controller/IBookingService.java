package com.epam.spring.core.controller;

import java.util.List;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

public interface IBookingService {

    int getTicketPrice(Event event, int seat, User user);

    boolean bookTicket(Order order);
    
    List<Order> getAllOrders();
    
    int getVipPersent();

    void setVipPersent(int vipPersent);

    IDiscountService getDiscountService();

    void setDiscountService(IDiscountService discountService);
    
    void clear();
}
