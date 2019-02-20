package com.epam.spring.core.dao.mock;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.OrderDAO;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

@Component(value = "OrderDAOMock")
public class OrderDAOMock implements OrderDAO {
	
	@Autowired
	MockDB mockDB;

	public boolean add(Order order) {
		if (!mockDB.getOrders().contains(order)) {
			mockDB.addOrder(order);
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(Order order) {
		for (Order o: mockDB.getOrders()){
			if (order.equals(o)){
				mockDB.removeOrder(o);
				return true;
			}
		}
		return false;
	}

	public boolean update(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	public Order findByID(int id) {
		for (Order o: mockDB.getOrders()){
			if (id==o.getId()){
				return o;
			}
		}
		return null;
	}

	public List<Order> getAll() {
		return new LinkedList<Order>(mockDB.getOrders());
	}

	public List<Order> getBookedTickets(User user) {
		List<Order> result = new LinkedList<>();
		for (Order o: mockDB.getOrders()){
			if (user.equals(o.getUser())){
				result.add(o);
			}
		}
		return result;
	}

	public List<Order> getBookedTickets(User user, Event event) {
		List<Order> result = new LinkedList<>();
		for (Order o: mockDB.getOrders()){
			if (user.equals(o.getUser()) && event.equals(o.getEvent())){
				result.add(o);
			}
		}
		return result;
	}

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

}
