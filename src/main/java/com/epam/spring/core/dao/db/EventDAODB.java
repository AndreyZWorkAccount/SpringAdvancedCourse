package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.EventDAO;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Rating;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "EventDAODB")
public class EventDAODB implements EventDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_EVENT= "INSERT INTO EVENTS (name, startdate, enddate, price, rating, ticketPrice) VALUES (?,?,?,?,?,?)";
    
    private static final String REMOVE_EVENT = "DELETE FROM EVENTS WHERE name=? and startdate=? and enddate=?";
    
    private static final String UPDATE_EVENT = "UPDATE  EVENTS "
            + "SET name=?, startdate=?, enddate=?, price=?, rating=?, ticketPrice=? "
            + "WHERE id=?";
    
    private static final String FIND_ID_EVENT = "SELECT * FROM EVENTS WHERE id=?";
    
    private static final String FIND_NAME_EVENT = "SELECT * FROM EVENTS WHERE name=?";
    
    private static final String ALL_EVENT = "SELECT * FROM EVENTS";
    
    private static final String DELETE_ALL = "DELETE FROM EVENTS";
    
    
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }

    
    @Override
    public boolean add(Event event) {
        try {
            return (jdbcTemplate.update(ADD_EVENT, event.getName(),
                    ClassCastUtil.getDateUtilToSQL(event.getStartDate()),
                    ClassCastUtil.getDateUtilToSQL(event.getEndDate()), event.getPrice(), event.getRating().toString(),
                    event.getTicketPrice()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(Event event) {
        try {
            return (jdbcTemplate.update(REMOVE_EVENT, event.getName(),
                    ClassCastUtil.getDateUtilToSQL(event.getStartDate()),
                    ClassCastUtil.getDateUtilToSQL(event.getEndDate())) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(Event event) {
        try {
            return (jdbcTemplate.update(UPDATE_EVENT, event.getName(),
                    ClassCastUtil.getDateUtilToSQL(event.getStartDate()),
                    ClassCastUtil.getDateUtilToSQL(event.getEndDate()), event.getPrice(), event.getRating().toString(),
                    event.getTicketPrice(), event.getId()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Event findByID(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ID_EVENT, new Object[] { id }, new EventRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Event findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_NAME_EVENT, new Object[] { name }, new EventRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Event> getAll() {
        try {
            return jdbcTemplate.query(ALL_EVENT, new EventRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    private class EventRowMapper implements RowMapper<Event> {

        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getInt("id"));
            event.setName(rs.getString("name"));
            event.setStartDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("startdate")));
            event.setEndDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("enddate")));
            event.setPrice(rs.getInt("price"));
            event.setTicketPrice(rs.getInt("ticketprice"));
            event.setRating(Rating.valueOf(rs.getString("rating")));
            return event;
        }
    }

}
