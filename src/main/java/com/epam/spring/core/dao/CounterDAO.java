package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;

public interface CounterDAO {

    List<Counter<Event>> find(Event event);
	
	void addCounter(Counter<Event> counter);

	void removeCounter(Counter<Event> counter);
	
	void updateCounter(Counter<Event> counter);
	
	List<Counter<Event>> getAll();
	
	void clear();
}
