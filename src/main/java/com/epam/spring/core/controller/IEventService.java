package com.epam.spring.core.controller;

import java.util.List;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Schedule;

public interface IEventService {

    boolean addEvent(Schedule schedule);
    
    boolean isTimeFree(Event event, Auditorium auditorium);
    
    List<Event> getAll();
    
    Event getEventByName(String name);
    
    Event getById(int id);
    
    List<Counter<Event>>  getStatistic();
    
    void clear();
}
