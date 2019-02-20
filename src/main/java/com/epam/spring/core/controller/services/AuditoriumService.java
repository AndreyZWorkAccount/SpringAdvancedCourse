package com.epam.spring.core.controller.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.spring.core.controller.IAuditoriumService;
import com.epam.spring.core.dao.AuditoriumDAO;
import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.dao.ScheduleDAO;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Schedule;

public class AuditoriumService implements IAuditoriumService {

    @Autowired
    @Qualifier("EventDAODB")
    EventDAO eventDAO;

    @Autowired
    @Qualifier("AuditoriumDAODB")
    AuditoriumDAO auditoriumDAO;
    
    @Autowired
    @Qualifier("ScheduleDAODB")
    ScheduleDAO scheduleDAO;

    private List<Auditorium> auditoriums;

    public AuditoriumService() {
       
    }

    public boolean add(Auditorium auditorium){
       return auditoriumDAO.add(auditorium);
    }
	
	public List<Auditorium> getAuditoriums() {
		return auditoriums;
	}

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public List<Schedule> getSchedule() {
       return scheduleDAO.getAll();
    }

    @Override
    public List<Auditorium> getAll() {
        return auditoriumDAO.getAll();
    }

    @Override
    public void clear() {
        auditoriumDAO.clear();
        scheduleDAO.clear();
    }

    
}
