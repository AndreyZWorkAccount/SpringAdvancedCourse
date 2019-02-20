package com.epam.spring.core.dao.mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

@Repository
public class MockDB {

	private int userCounter;
	
	private int eventCounter;
	
	private int orderCounter;	
	
	private Map<Event, Auditorium> shcedule;
	
	private Map<Event, Map<String, Integer>> counter;
	
	private Map<User, Map<String, Integer>> counterDiscount;
	
	private Set<Order> booking;
	
	private Set<User> users;
	
	private Set<Auditorium> auditoriums;
	
	@PostConstruct
    public void init(){
		this.booking = new HashSet<Order>();
		this.shcedule = new HashMap<Event, Auditorium>(); 
		this.users = new HashSet<User>();
		this.auditoriums = new HashSet<Auditorium>();
		this.counter = new HashMap<Event, Map<String, Integer>>(); 
		this.counterDiscount = new HashMap<User, Map<String, Integer>>(); 
	}
	
	public MockDB() {
	} 
	
	public Set<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		user.setId(userCounter);
		userCounter+=1;
		this.users.add(user);
	}
	
	public void removeUser(User user){
		this.users.remove(user);
	}

	public Set<Auditorium> getAuditoriums() {
		return auditoriums;
	}

	public void addAuditorium(Auditorium auditorium) {
		this.auditoriums.add(auditorium);
	}

	public void removeAuditorium(Auditorium auditorium){
		this.auditoriums.remove(auditorium);
	}
	
	public Map<Event, Auditorium> getShcedule() {
		return shcedule;
	}

	public void addEvent(Event event, Auditorium auditorium) {
		event.setId(eventCounter);
		eventCounter +=1;  
		this.shcedule.put(event, auditorium);
	}

	public void removeEvent(Event event) {
		this.shcedule.remove(event);
	}
	
	public Set<Order> getOrders() {
		return booking;
	}

	public void addOrder(Order order) {
		order.setId(orderCounter);
		orderCounter+=1;
		this.booking.add(order);
	}
	
	public void removeOrder(Order order){
		this.booking.remove(order);
	}

	public void addCounter(Event event, Map<String, Integer> counters) {
		this.counter.put(event, counters);
	}

	public void removeCounter(Event event) {
		this.counter.remove(event);
	}
	
	public Map<String, Integer> getCounter(Event event){
		return	this.counter.get(event);
	}
	
	public Map<Event, Map<String, Integer>> getAllCounters(){
		return this.counter;
	}
	
	public void addCounterDiscount(User user, Map<String, Integer> counters) {
		this.counterDiscount.put(user, counters);
	}

	public void removeCounter(User user) {
		this.counterDiscount.remove(user);
	}
	
	public Map<String, Integer> getCounter(User user){
		return	this.counterDiscount.get(user);
	}
	
	public Map<User, Map<String, Integer>> getAllCountersDiscount(){
		return this.counterDiscount;
	}
}
