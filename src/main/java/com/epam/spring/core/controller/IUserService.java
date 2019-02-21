package com.epam.spring.core.controller;

import java.util.Date;
import java.util.List;

import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

public interface IUserService {

    User add(String name, String email, Date birthday);

    User getById(int id);
    
    boolean add(User user);

    List<Order> getBookedTickets(User user);

    List<Order> getBookedTickets(User user, Event event);

    List<Counter<User>> getStatistic();
    
    List<User> getAll();

    void clear();
}
