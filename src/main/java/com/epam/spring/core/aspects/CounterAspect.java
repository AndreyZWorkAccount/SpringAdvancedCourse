package com.epam.spring.core.aspects;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.CounterDAO;
import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;

@Aspect
@Component
public class CounterAspect {

	@Autowired
	@Qualifier("CounterDAODB")
	CounterDAO counterDAO;

    @Autowired
    @Qualifier("EventDAODB")
    EventDAO eventDAO;

	@Pointcut("execution(* *.getTicketPrice(..))")
	private void countEventGetPrice() {
	}

	@Pointcut("execution(* *.getEventByName(..))")
	private void countEventByName() {
	}

	@Pointcut("execution(* *.bookTicket(..))")
	private void countEventBook() {
	}

	@AfterReturning(pointcut = "countEventByName()", returning = "returnValue")
	public void countByName(final JoinPoint jp, Event returnValue) {
		String name = jp.getSignature().getName();
		if (returnValue != null) {
			incCounter(returnValue, name);
		}
	}

	@After("countEventGetPrice()")
	public void countGetPrice(final JoinPoint jp) {
		String name = jp.getSignature().getName();
		for (final Object argument : jp.getArgs()) {
			if (argument.getClass().equals(Event.class)) {
				incCounter((Event) argument, name);
			}
		}
	}

	@AfterReturning(pointcut = "countEventBook()", returning = "returnValue")
	public void countBook(final JoinPoint jp, boolean returnValue) {
		String name = jp.getSignature().getName();
		if (returnValue) {
			for (final Object argument : jp.getArgs()) {
				if (argument.getClass().equals(Order.class)) {
					incCounter(((Order) argument).getEvent(), name);
				}
			}
		}
	}

	private void incCounter(Event event, String name) {
	    Event eventFind = eventDAO.findByName(event.getName());
	    List<Counter<Event>> counters = counterDAO.find(eventFind);
        if (counters != null && counters.size()!=0) {
            for (Counter<Event> counter: counters){
                if (name.equals(counter.getName())) {
                    counter.setCount(counter.getCount()+1);
                    counterDAO.updateCounter(counter);
                } 
            }
        } else {
            Counter<Event> counter = new Counter<>();
            counter.setValue(eventFind);
            counter.setName(name);
            counter.setCount(1);
            counterDAO.addCounter(counter);
        }
	}

}
