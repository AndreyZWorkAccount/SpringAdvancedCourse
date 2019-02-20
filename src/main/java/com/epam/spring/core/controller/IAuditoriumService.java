package com.epam.spring.core.controller;

import java.util.List;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Schedule;


public interface IAuditoriumService  {

    boolean add(Auditorium auditorium);
    
    List<Auditorium> getAuditoriums();

    void setAuditoriums(List<Auditorium> auditoriums);
    
    List<Schedule> getSchedule();
    
    List<Auditorium> getAll();
    
    void clear();
}
