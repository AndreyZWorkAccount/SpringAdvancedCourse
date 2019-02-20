package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

public interface OrderDAO {

	boolean add(Order order);
	
	boolean remove(Order order);
	
	boolean update(Order order);
	
	Order findByID(int id);
	
	List<Order> getAll();
	
	List<Order> getBookedTickets(User user);
	
	List<Order> getBookedTickets(User user, Event event);
	
	 void clear();
}
