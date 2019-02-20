package com.epam.spring.core.controller.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.spring.core.controller.IEventService;
import com.epam.spring.core.dao.CounterDAO;
import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.dao.ScheduleDAO;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Schedule;

public class EventService implements IEventService {

	@Autowired
	@Qualifier("EventDAODB")
	EventDAO eventDAO;
	
	@Autowired
	@Qualifier("CounterDAODB")
	CounterDAO counterDAO;

    @Autowired
    @Qualifier("ScheduleDAODB")
    ScheduleDAO scheduleDAO;
	
	public boolean addEvent(Schedule schedule) {
		if (isTimeFree(schedule.getEvent(), schedule.getAuditorium())) {
			eventDAO.add(schedule.getEvent());
			schedule.getEvent().setId(eventDAO.findByName(schedule.getEvent().getName()).getId());
			scheduleDAO.add(schedule);
			return true;
		}
		return false;
	}

	public boolean isTimeFree(Event event, Auditorium auditorium) {
	    List<Schedule> list = scheduleDAO.getAll();
        if (list != null) {
            for (Schedule s : list) {
                if (auditorium.equals(s.getAuditorium())) {
                    Date startDate = s.getEvent().getStartDate();
                    Date endDate = s.getEvent().getEndDate();
                    if (!(startDate.after(event.getEndDate()) || endDate.before(event.getStartDate()))) {
                        return false;
                    }
                }
            }
        }
		return true;
	}
	
	public List<Event> getAll(){
		return eventDAO.getAll();
	}
	
	public Event getEventByName(String name){
		return eventDAO.findByName(name);
	}
	
	public List<Counter<Event>>  getStatistic(){
		return counterDAO.getAll();
	}
	
    @Override
    public Event getById(int id) {
        return eventDAO.findByID(id);
    }

    @Override
    public void clear() {
        eventDAO.clear();
    }
}
