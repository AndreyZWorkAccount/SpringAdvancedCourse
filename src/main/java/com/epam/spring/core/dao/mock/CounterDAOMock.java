package com.epam.spring.core.dao.mock;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.CounterDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.User;

@Component(value = "CounterDAOMock")
public class CounterDAOMock implements CounterDAO{

	@Autowired
	MockDB mockDB;
	
	public List<Counter<Event>> find(Event event){
	//	return mockDB.getCounter(event);
	    return null;
    }
	
	public void addCounter(Event event, Map<String, Integer> counters) {
		mockDB.addCounter(event, counters);
	}

	public void removeCounter(Event event) {
		mockDB.removeCounter(event);
	}
	
	public List<Counter<Event>> getAll(){
		//return mockDB.getAllCounters();
	    return null;
	}
	
	public Map<String, Integer> find(User user){
		return mockDB.getCounter(user);
	}
	
	public void addCounter(User user, Map<String, Integer> counters) {
		mockDB.addCounterDiscount(user, counters);
	}

	public void removeCounter(User user) {
		mockDB.removeCounter(user);
	}
	
	public Map<User, Map<String, Integer>> getAllDiscount(){
		return mockDB.getAllCountersDiscount();
	}

    @Override
    public void addCounter(Counter<Event> counter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeCounter(Counter<Event> counter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateCounter(Counter<Event> counter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }
}
