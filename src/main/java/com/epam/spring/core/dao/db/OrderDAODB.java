package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.OrderDAO;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.Rating;
import com.epam.spring.core.model.User;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "OrderDAODB")
public class OrderDAODB implements OrderDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_ORDER= "INSERT INTO ORDERS (user_id, event_id, seats, price) VALUES (?,?,?,?)";
    
    private static final String REMOVE_ORDER = "DELETE FROM ORDERS WHERE user_id=? and event_id=? and seats=?";
    
    private static final String UPDATE_ORDER = "UPDATE  ORDERS "
            + "SET user_id=?, event_id=?, seats=?, price=?"
            + "WHERE id=?";
    
    private static final String BASE_OBJECT_SELECT = "Select "
            +"o.id, "
            +"o.event_id, "
            +"o.user_id, "
            +"o.seats, "
            +"o.price, "
            +"e.name as event_name, "
            +"e.startdate as event_startdate, "
            +"e.enddate as event_enddate, "
            +"e.price as event_price, "
            +"e.rating as event_rating, "
            +"e.ticketprice as event_ticketprice, "
            +"u.name as user_name, "
            +"u.email as user_email, "
            +"u.birthday as user_birthday "
            +"from orders o "
            +"inner join events e ON o.event_id=e.id "
            +"inner join users u ON o.user_id = u.id ";
    
    private static final String FIND_ID_ORDER = BASE_OBJECT_SELECT + " WHERE id=?";
    
    private static final String FIND_USERS_ORDER = BASE_OBJECT_SELECT + " WHERE user_id=?";
    
    private static final String FIND_USERS_ORDER_EVENT = BASE_OBJECT_SELECT + " WHERE user_id=? and event_id=?";
    
    private static final String ALL_ORDER = BASE_OBJECT_SELECT;
    
    
    private static final String DELETE_ALL = "DELETE FROM ORDERS";
    
    
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }
    
    @Override
    public boolean add(Order order) {
        try {
            return (jdbcTemplate.update(ADD_ORDER, order.getUser().getId(), order.getEvent().getId(), order.getSeat(),
                    order.getPrice()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(Order order) {
        try {
            return (jdbcTemplate.update(REMOVE_ORDER, order.getUser().getId(), order.getEvent().getId(),
                    order.getSeat()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(Order order) {
        try {
            return (jdbcTemplate.update(UPDATE_ORDER, order.getUser().getId(), order.getEvent().getId(),
                    order.getSeat(), order.getPrice(), order.getId()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Order findByID(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ID_ORDER, new Object[] { id }, new OrderRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Order> getAll() {
        try {
            return jdbcTemplate.query(ALL_ORDER, new OrderRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Order> getBookedTickets(User user) {
        try {
            return jdbcTemplate.query(FIND_USERS_ORDER, new Object[] { user.getId() }, new OrderRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Order> getBookedTickets(User user, Event event) {
        try {
            return jdbcTemplate.query(FIND_USERS_ORDER_EVENT, new Object[] { user.getId(), event.getId() },
                    new OrderRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    
    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getInt("event_id"));
            event.setName(rs.getString("event_name"));
            event.setStartDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_startdate")));
            event.setEndDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_enddate")));
            event.setPrice(rs.getInt("event_price"));
            event.setRating(Rating.valueOf(rs.getString("event_rating")));
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setEmail(rs.getString("user_email"));
            user.setBirthday(ClassCastUtil.getDateSQLToUtil(rs.getDate("user_birthday")));
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setSeat(rs.getInt("seats"));
            order.setPrice(rs.getInt("price"));
            order.setUser(user);
            order.setEvent(event);
            return order;
        }
    }
    
}
