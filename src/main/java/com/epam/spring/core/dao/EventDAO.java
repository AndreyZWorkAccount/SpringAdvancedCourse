package com.epam.spring.core.dao;

import java.util.List;
import com.epam.spring.core.model.Event;

public interface EventDAO {

	boolean add(Event event);
	
	boolean remove(Event event);
	
	boolean update(Event event);
	
	Event findByID(int id);

	Event findByName(String name);
	
	List<Event> getAll();
	
	 void clear();
	
}
