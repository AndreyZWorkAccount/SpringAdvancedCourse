package com.epam.spring.core.controller;

import java.util.Date;
import java.util.List;

import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.Role;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

public interface IUserService {

    User add(String name, String email, Date birthday, String password, String roles, int account);

    User getById(int id);
    
    boolean add(User user, int account);

    List<Order> getBookedTickets(User user);

    List<Order> getBookedTickets(User user, Event event);

    List<Counter<User>> getStatistic();
    
    List<User> getAll();

    List<Role> getRoles();
    
    List<UserAccount> getAllAccount();
    
    void clear();
}
