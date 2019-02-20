package com.epam.spring.core.controller.discount;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.epam.spring.core.controller.DiscountStrategy;
import com.epam.spring.core.dao.OrderDAO;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;

public class EveryTicketStrategy implements DiscountStrategy {

	@Value("${everyTicket.percent}")
	private int percent;
	@Value("${everyTicket.ticketCount}")
	private int ticketCount;

	@Autowired
	@Qualifier("OrderDAOMock")
	OrderDAO orderDAO;

	@Override
	public String getName() {
		return "EveryTicketStrategy";
	}

	@Override
	public void setDiscount(User user, List<Order> list) {
		int size = orderDAO.getBookedTickets(user).size();
		for (Order o : list) {
			size += 1;
			if (size % ticketCount == 0)
				o.setPrice(o.getPrice() - o.getPrice() * percent / 100);
		}
	}
	
	public void setDiscount(User user, Order order) {
		int size = orderDAO.getBookedTickets(user).size();
		size += 1;
			if (size % ticketCount == 0){
				order.setPrice(order.getPrice() - order.getPrice() * percent / 100);
		}
	}

	public int getDiscount(User user, List<Order> list) {
		int price = 0;
		for (Order o : list) {
			price += o.getPrice();
		}

		int size = orderDAO.getBookedTickets(user).size();
		int sizeAll = size + list.size();
		int count = sizeAll / ticketCount - size / ticketCount;
		if (count > 0) {
			return price * percent * count / 100;
		}
		return 0;
	}

	public int getDiscount(User user, Order order) {
		int price = order.getPrice();
		int size = orderDAO.getBookedTickets(user).size();
		int sizeAll = size + 1;
		int count = sizeAll / ticketCount - size / ticketCount;
		if (count > 0) {
			return price * percent * count / 100;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
