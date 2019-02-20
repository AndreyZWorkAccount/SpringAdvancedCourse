package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.User;

public interface DiscountCounterDAO {

    List<Counter<User>> find(User user);
    
    void addCounter(Counter<User> counter);

    void removeCounter(Counter<User> counter);
    
    void updateCounter(Counter<User> counter);
    
    List<Counter<User>> getAllDiscount();
    
    void clear();
}
