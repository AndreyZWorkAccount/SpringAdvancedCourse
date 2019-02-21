package com.epam.spring.core.controller.services;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.spring.core.controller.IUserService;
import com.epam.spring.core.dao.DiscountCounterDAO;
import com.epam.spring.core.dao.OrderDAO;
import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;


public class UserService implements IUserService{

	@Autowired
	@Qualifier("UserDAODB")
	UserDAO userDAO;
	
	@Autowired
	@Qualifier("OrderDAODB")
	OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("DiscountCounterDAODB")
	DiscountCounterDAO counterDAO;
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public User add(String name, String email, Date birthday){
        User user = new User(name, email, birthday);
        user = (userDAO.add(user)) ? userDAO.findByName(user.getName()) : null;
        return user;
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean add(User user){
	    user = (userDAO.add(user)) ? userDAO.findByName(user.getName()):null;

	    return true;
	}
	
	public List<Order> getBookedTickets(User user){
		return orderDAO.getBookedTickets(user);
	}
	
	public List<Order> getBookedTickets(User user, Event event){
		return orderDAO.getBookedTickets(user, event);
	}
	
	public List<Counter<User>>  getStatistic(){
		return counterDAO.getAllDiscount();
	}
	
	public List<User>  getAll(){
		return userDAO.getAll();
	}
	
	
	
    @Override
    public User getById(int id) {
        return userDAO.findByID(id);
    }

    @Override
    public void clear() {
        userDAO.clear();
    }

    
}
