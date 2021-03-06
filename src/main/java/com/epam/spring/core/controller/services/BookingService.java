package com.epam.spring.core.controller.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.epam.spring.core.controller.IBookingService;
import com.epam.spring.core.controller.IDiscountService;
import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.dao.OrderDAO;
import com.epam.spring.core.dao.ScheduleDAO;
import com.epam.spring.core.dao.UserAccountDAO;
import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

public class BookingService implements IBookingService {

    private int vipPersent;

    private IDiscountService discountService;

    @Autowired
    @Qualifier("OrderDAODB")
    private OrderDAO orderDAO;

    @Autowired
    @Qualifier("EventDAODB")
    private EventDAO eventDAO;

    @Autowired
    @Qualifier("ScheduleDAODB")
    ScheduleDAO scheduleDAO;

    @Autowired
    @Qualifier("UserDAODB")
    UserDAO userDAO;

    @Autowired
    @Qualifier("UserAccountDAODB")
    UserAccountDAO userAccountDAO;

    public int getTicketPrice(Event event, int seat, User user) {
        Auditorium auditorium = scheduleDAO.findByEventID(event).getAuditorium();
        int price = event.getPrice();
        if (auditorium.getVipSeats().contains(new Integer(seat))) {
            price = price + price / 100 * vipPersent;
        }
        List<Order> list = new LinkedList<>();
        list.add(new Order(user, event, seat, price));
        return price - discountService.getDiscount(user, list);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean bookTicket(Order order) {
        Auditorium auditorium = scheduleDAO.findByEventID(order.getEvent()).getAuditorium();
        order.getUser().setId(userDAO.findByName(order.getUser().getName()).getId());
        // order.getEvent().setPrice(eventDAO.findByID(order.getEvent().getId()).getPrice());
        int price = order.getEvent().getPrice();
        if (auditorium.getVipSeats().contains(new Integer(order.getSeat()))) {
            price = price + price / 100 * vipPersent;
        }
        order.setPrice(price);
        List<Order> list = new LinkedList<>();
        list.add(order);
        discountService.setDiscount(order.getUser(), order);
        if (!orderDAO.getAll().contains(order)) {
                orderDAO.add(order);
                System.out.println(orderDAO.getAll());
                decreaseAccount(order.getUser(), order.getPrice());
                increasePrice(order.getEvent(), order.getPrice());
                
                return true;

        }
        return false;
    }

    private boolean decreaseAccount(User user, int price) {
        UserAccount userAccount = userAccountDAO.findByUser(user);
        if (userAccount != null) {
            userAccount.setAccount(userAccount.getAccount() - price);
            userAccountDAO.update(userAccount);
            return true;
        }
        return false;
    }
    
    private boolean increasePrice(Event event, int price) {
        event.setTicketPrice(event.getTicketPrice() + price);
        return eventDAO.update(event);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAll();
    }

    public int getVipPersent() {
        return vipPersent;
    }

    public void setVipPersent(int vipPersent) {
        this.vipPersent = vipPersent;
    }

    public IDiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(IDiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public void clear() {
        orderDAO.clear();
    }

}
