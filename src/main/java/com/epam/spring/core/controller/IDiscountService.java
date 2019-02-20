package com.epam.spring.core.controller;

import java.util.List;

import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

public interface IDiscountService {

    List<DiscountStrategy> getDiscountList();

    void setDiscountList(List<DiscountStrategy> discountList);

    int getDiscount(User user, List<Order> list);

    void setDiscount(User user, List<Order> list);
    
    void setDiscount(User user, Order order);

}
