package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.CounterDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Rating;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "CounterDAODB")
public class CounterDAODB implements CounterDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_EVENT_COUNTER= "INSERT INTO EVENTCOUNTER (name, event_id, count) VALUES (?,?,?)";
    
    private static final String REMOVE_EVENT_COUNTER = "DELETE FROM EVENTCOUNTER WHERE name=?";
    
    private static final String UPDATE_EVENT_COUNTER = "UPDATE  EVENTCOUNTER "
            + "SET count=?"
            + "WHERE name=? and event_id=?";
    
    
   // private static final String FIND_NAME_COUNTER = "SELECT * FROM EVENTCOUNTER WHERE name=?";
    
    private static final String BASE_OBJECT_SELECT = "Select "
    +" c.id, "
    +" c.name, "
    +" c.COUNT, "
    +" c.event_id, "
    +" e.name as event_name, "
    +" e.startdate as event_startdate, "
    +" e.enddate as event_enddate, "
    +" e.price as event_price, "
    +" e.rating as event_rating, "
    +" from EVENTCOUNTER c "
    +" inner join events e ON c.event_id=e.id ";
    
    private static final String FIND_EVENT_COUNTER = BASE_OBJECT_SELECT +"  WHERE event_id=?";
        
    private static final String ALL_COUNTER = BASE_OBJECT_SELECT;
    
    private static final String DELETE_ALL = "DELETE FROM EVENTCOUNTER";
    
    
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }
    
    @Override
    public void addCounter(Counter<Event> counter) {
        jdbcTemplate.update(ADD_EVENT_COUNTER, counter.getName(),  counter.getValue().getId(), counter.getCount());
    }
    
    @Override
    public void updateCounter(Counter<Event> counter) {
        jdbcTemplate.update(UPDATE_EVENT_COUNTER, counter.getCount(), counter.getName(),  counter.getValue().getId());
        
    }

    @Override
    public void removeCounter(Counter<Event> counter) {
        jdbcTemplate.update(REMOVE_EVENT_COUNTER, counter.getName());

    }

    @Override
    public List<Counter<Event>> find(Event event) {
        try {
            return jdbcTemplate.query(FIND_EVENT_COUNTER, new Object[] { event.getId() }, new CounterRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Counter<Event>> getAll() {
        try {
            return jdbcTemplate.query(ALL_COUNTER, new CounterRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }


    private class CounterRowMapper implements RowMapper<Counter<Event>> {

        @Override
        public Counter<Event> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getInt("event_id"));
            event.setName(rs.getString("event_name"));
            event.setStartDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_startdate")));
            event.setEndDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_enddate")));
            event.setPrice(rs.getInt("event_price"));
            event.setRating(Rating.valueOf(rs.getString("event_rating")));
            Counter<Event> counter = new Counter<>();
            counter.setName(rs.getString("name"));
            counter.setCount(rs.getInt("COUNT"));
            counter.setValue(event);
            return counter;
        }
    }




}
