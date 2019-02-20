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
import com.epam.spring.core.dao.UserAccountDAO;
import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.Role;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;


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
	
    @Autowired
    @Qualifier("UserAccountDAODB")
    UserAccountDAO userAccountDAO;
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public User add(String name, String email, Date birthday, String password, String roles, int account){
        User user = new User(name, email, birthday, password, roles);
        user = (userDAO.add(user)) ? userDAO.findByName(user.getName()) : null;
        UserAccount userAccount = new UserAccount(user, account);
        userAccountDAO.add(userAccount);
        return user;
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean add(User user, int account){
	    user = (userDAO.add(user)) ? userDAO.findByName(user.getName()):null;
	    UserAccount userAccount = new UserAccount(user, account);
	    userAccountDAO.add(userAccount);	    
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
    public List<UserAccount> getAllAccount() {
        return userDAO.getAllAccount();
    }

    @Override
    public User getById(int id) {
        return userDAO.findByID(id);
    }

    @Override
    public void clear() {
        userDAO.clear();
    }

	@Override
	public List<Role> getRoles() {
		List<Role> roles = new LinkedList<>();
		roles = Arrays.asList(Role.values());
		return roles;
	}
    
    
}
