package com.epam.spring.core;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.Rating;
import com.epam.spring.core.model.Schedule;
import com.epam.spring.core.model.User;
import com.epam.spring.advanced.util.WebUtil;
import com.epam.spring.core.controller.IAuditoriumService;
import com.epam.spring.core.controller.IBookingService;
import com.epam.spring.core.controller.ICounterService;
import com.epam.spring.core.controller.IEventService;
import com.epam.spring.core.controller.IUserAccountService;
import com.epam.spring.core.controller.IUserService;

public class App {
    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        IAuditoriumService auditoriumService = (IAuditoriumService) ctx.getBean("AuditoriumService");
        IEventService eventService = (IEventService) ctx.getBean("EventService");
        IUserService userService = (IUserService) ctx.getBean("UserService");
        IBookingService bookingService = (IBookingService) ctx.getBean("BookingService");
        ICounterService counterService = (ICounterService) ctx.getBean("CounterService");
        IUserAccountService userAccountService = (IUserAccountService) ctx.getBean("UserAccountService");
        
        init( auditoriumService, eventService, userService,  bookingService, counterService, userAccountService);
       
        createSchedule(ctx, eventService, auditoriumService);
        printSchedule(auditoriumService);
        createOrders(userService, eventService, bookingService);
        printOrders(bookingService);

        eventService.getEventByName("Event2");
        eventService.getEventByName("Event4");
        printStatistic(userService, eventService);
    }

    
    private static void init(IAuditoriumService auditoriumService, IEventService eventService, IUserService userService,
            IBookingService bookingService, ICounterService counterService,IUserAccountService userAccountService) {
        auditoriumService.clear();
        eventService.clear();
        userService.clear();
        bookingService.clear();
        counterService.clear();
        userAccountService.clear();
        if (auditoriumService.getAll() == null || auditoriumService.getAll().size() == 0) {
            auditoriumService.add(auditoriumService.getAuditoriums().get(0));
            auditoriumService.add(auditoriumService.getAuditoriums().get(1));
            auditoriumService.add(auditoriumService.getAuditoriums().get(2));
        }
    }
    
    private static void createOrders(IUserService userService, IEventService eventService,
            IBookingService bookingService) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String msgUser = "Can't create user: ";
        String msgOrder = "Can't create order: ";
        DateTime birthday = new DateTime(2015, 1, 1, 0, 0, 0);
        User user = new User("user1", "User1@mail.com", birthday.toDate(),WebUtil.getMd5Password("user"),"BOOKING_MANAGER");
        if (userService.add(user, 1000)) {
            Event event = eventService.getAll().get(0);
            printText("Event: ", event.getName());
            printText("price default: ", event.getPrice());
            printText("Check seat N1 price: ", bookingService.getTicketPrice(event, 1, user));
            printText("Check seat N70 price: ", bookingService.getTicketPrice(event, 70, user));
            Order order = new Order(user, event, 1, event.getPrice());
            if (!bookingService.bookTicket(order)) {
                printError(msgOrder, order);
            }
        } else {
            printError(msgUser, user);
        }

        user = new User("user2", "User2@mail.com", new Date(),WebUtil.getMd5Password("user"),"REGISTERED_USER");
        if (userService.add(user,2000)) {
            Event event = eventService.getAll().get(0);
            printText("Event: ", event.getName());
            printText("price default: ", event.getPrice());
            printText("Check seat N2 price: ", bookingService.getTicketPrice(event, 2, user));
            printText("Check seat N70 price: ", bookingService.getTicketPrice(event, 70, user));
            Order order = new Order(user, event, 2, event.getPrice());
            if (!bookingService.bookTicket(order)) {
                printError(msgOrder, order);
            }
            order = new Order(user, event, 3, event.getPrice());
            if (!bookingService.bookTicket(order)) {
                printError(msgOrder, order);
            }
            order = new Order(user, event, 70, event.getPrice());
            if (!bookingService.bookTicket(order)) {
                printError(msgOrder, order);
            }
        } else {
            printError(msgUser, user);
        }
        
        user = new User("user", "User23@mail.com", new Date(),WebUtil.getMd5Password("user"),"REGISTERED_USER,BOOKING_MANAGER");
        userService.add(user,9999);
    }

    private static void printOrders(IBookingService bookingService) {
        printText("==========>", "ORDER LIST");
        for (Order o : bookingService.getAllOrders()) {
            printText("===", o);
        }
    }

    private static void createSchedule(ApplicationContext ctx, IEventService eventService,
            IAuditoriumService auditoriumService) {
        
      // auditoriumService.ge;
        List<Auditorium> listAud = auditoriumService.getAll();
        
        String msg = "Can't create event, because time is buzy. ";
        /* Create Schedule start: */
        DateTime startDate = new DateTime(2015, 1, 1, 10, 0, 0);
        DateTime endDate = new DateTime(2015, 1, 1, 11, 0, 0);
        Event event = (Event) ctx.getBean("Event");
        event.setName("Event1");
        event.setStartDate(startDate.toDate());
        event.setEndDate(endDate.toDate());
        event.setPrice(200);
        event.setRating(Rating.HIGH);
        event.setTicketPrice(0);
        Schedule schedule = new Schedule();
        schedule.setEvent(event);
        schedule.setAuditorium(listAud.get(0));
        if (!eventService.addEvent(schedule)) {
            printError(msg, event);
        }
        startDate = new DateTime(2015, 1, 1, 12, 0, 0);
        endDate = new DateTime(2015, 1, 1, 13, 0, 0);
        event = (Event) ctx.getBean("Event");
        event.setName("Event2");
        event.setStartDate(startDate.toDate());
        event.setEndDate(endDate.toDate());
        event.setPrice(200);
        event.setRating(Rating.HIGH);
        event.setTicketPrice(0);
        schedule = new Schedule();
        schedule.setEvent(event);
        schedule.setAuditorium(listAud.get(0));
        if (!eventService.addEvent(schedule)) {
            printError(msg, event);
        }

        startDate = new DateTime(2015, 1, 1, 11, 30, 0);
        endDate = new DateTime(2015, 1, 1, 13, 30, 0);
        event = (Event) ctx.getBean("Event");
        event.setName("Event3_with_error");
        event.setStartDate(startDate.toDate());
        event.setEndDate(endDate.toDate());
        event.setPrice(200);
        event.setRating(Rating.HIGH);
        event.setTicketPrice(0);
        schedule = new Schedule();
        schedule.setEvent(event);
        schedule.setAuditorium(listAud.get(0));
        if (!eventService.addEvent(schedule)) {
            printError(msg, event);
        }

        startDate = new DateTime(2015, 1, 1, 11, 30, 0);
        endDate = new DateTime(2015, 1, 1, 13, 30, 0);
        event = (Event) ctx.getBean("Event");
        event.setName("Event4");
        event.setStartDate(startDate.toDate());
        event.setEndDate(endDate.toDate());
        event.setPrice(200);
        event.setRating(Rating.HIGH);
        event.setTicketPrice(0);
        schedule = new Schedule();
        schedule.setEvent(event);
        schedule.setAuditorium(listAud.get(0));
        if (!eventService.addEvent(schedule)) {
            printError(msg, event);
        }
        /* Create Schedule end. */

    }

    private static void printSchedule(IAuditoriumService auditoriumService) {
        List<Schedule> list = auditoriumService.getSchedule();
        printText("==========>", "SCHEDULE");
        for (Schedule s : list) {
            printText("======", s);
        }
    }

    private static void printError(String msg, Object obj) {
        System.out.println("ERROR: " + msg + " " + obj.toString() + "\n");
    }

    private static void printText(String msg, Object obj) {
        System.out.println("--> " + msg + " " + obj.toString() + "\n");
    }

    private static void printStatistic(IUserService userService, IEventService eventService) {
        List<Counter<Event>> elist = eventService.getStatistic();
        System.out.println("=======Event staticstic=====");
        for (Counter<Event> counter : elist) {
            System.out.println(counter);
        }
        List<Counter<User>> ulist = userService.getStatistic();
        System.out.println("=======User staticstic=====");
        for (Counter<User> counter : ulist) {
            System.out.println(counter);
        }
    }
}
