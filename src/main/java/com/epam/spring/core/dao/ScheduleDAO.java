package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Schedule;

public interface ScheduleDAO {

    boolean add(Schedule schedule);
    
    boolean remove(Schedule schedule);
    
    Schedule findByID(int id);
    
    Schedule findByEventID(Event event);

    List<Schedule> getAll();
    
    void clear();
}
