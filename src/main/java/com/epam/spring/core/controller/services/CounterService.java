package com.epam.spring.core.controller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.spring.core.controller.ICounterService;
import com.epam.spring.core.dao.CounterDAO;
import com.epam.spring.core.dao.DiscountCounterDAO;

public class CounterService implements ICounterService {

    @Autowired
    @Qualifier("CounterDAODB")
    CounterDAO counterDAO;
    
    @Autowired
    @Qualifier("DiscountCounterDAODB")
    DiscountCounterDAO counterDAOD;
    
    @Override
    public void clear() {
        counterDAO.clear();
        counterDAOD.clear();
    }

}
