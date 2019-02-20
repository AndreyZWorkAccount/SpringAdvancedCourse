package com.epam.spring.core.dao;

import java.util.List;

import com.epam.spring.core.model.Auditorium;

public interface AuditoriumDAO {

        boolean add(Auditorium auditorium);
        
        boolean remove(Auditorium auditorium);
        
        boolean update(Auditorium auditorium);
        
        Auditorium findByID(int id);

        Auditorium findByName(String name);
        
        List<Auditorium> getAll();
        
        void clear();
}
