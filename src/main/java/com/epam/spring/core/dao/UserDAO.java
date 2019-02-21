package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.User;

public interface UserDAO {

	boolean add(User user);
	
	boolean remove(User user);
	
	boolean update(User user);
	
	User findByID(int id);
	
	User findByEmail(String email);
	
	User findByName(String name);
	
	List<User> getAll();

	void clear();
	
}
