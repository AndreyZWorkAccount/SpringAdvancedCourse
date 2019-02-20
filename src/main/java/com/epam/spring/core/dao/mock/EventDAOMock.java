package com.epam.spring.core.dao.mock;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;

@Component(value = "EventDAOMock")
public class EventDAOMock implements EventDAO {

	@Autowired
	MockDB mockDB;
	
	public boolean add(Event event, Auditorium auditorium) {
		if (mockDB.getShcedule().get(event)==null) {
			mockDB.addEvent(event, auditorium );
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(Event event) {
		if (mockDB.getShcedule().get(event)==null) {
			mockDB.removeEvent(event);
			return true;
		} else {
			return false;
		}
	}

	public boolean update(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	public Event findByID(int id) {
		Iterator<Entry<Event, Auditorium>> it = mockDB.getShcedule().entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Event, Auditorium> pair = it.next();
	        Event e = pair.getKey();
			if (id == e.getId()){
				return e;
			}
		}
		return null;
	}

	public Event findByName(String name) {
		Iterator<Entry<Event, Auditorium>> it = mockDB.getShcedule().entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Event, Auditorium> pair = it.next();
	        Event e = pair.getKey();
			if (name.equals(e.getName())){
				return e;
			}
		}
		return null;
	}

	public List<Event> getAll() {
		return new LinkedList<Event>(mockDB.getShcedule().keySet());
	}

    @Override
    public boolean add(Event event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }


	
	
}
