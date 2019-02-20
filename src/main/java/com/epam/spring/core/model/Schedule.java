package com.epam.spring.core.model;

public class Schedule {

    private int id;
    
    private Auditorium auditorium;
    
    private Event event;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Schedule [id=" + id + ", auditorium=" + auditorium + ", event=" + event + "]";
    }
    
    
    
}
