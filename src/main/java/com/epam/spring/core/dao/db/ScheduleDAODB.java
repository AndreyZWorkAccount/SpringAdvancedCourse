package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.ScheduleDAO;
import com.epam.spring.core.model.Auditorium;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Rating;
import com.epam.spring.core.model.Schedule;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "ScheduleDAODB")
public class ScheduleDAODB implements ScheduleDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_SCHEDULE= "INSERT INTO SCHEDULE (event_id, auditorium_id) VALUES (?,?)";
    
    private static final String REMOVE_SCHEDULE = "DELETE FROM SCHEDULE WHERE event_id=? and auditorium_id=?";
    
    private static final String BASE_OBJECT_SELECT = "Select "
            +"s.id, "
            +"s.event_id, "
            +"s.auditorium_id, "
            +"e.name as event_name, "
            +"e.startdate as event_startdate, "
            +"e.enddate as event_enddate, "
            +"e.price as event_price, "
            +"e.rating as event_rating, "
            +"e.ticketprice as event_ticketprice, "
            +"a.name as auditorium_name, "
            +"a.seatscount as auditorium_seatscount, "
            +"a.vipseats as auditorium_vipseats "
            +"from schedule s "
            +"inner join events e ON s.event_id=e.id "
            +"inner join auditoriums a ON s.auditorium_id = a.id ";
    
    private static final String FIND_ID_SCHEDULE = BASE_OBJECT_SELECT + " WHERE id=?";
    
    private static final String FIND_EVENT_ID_SCHEDULE = BASE_OBJECT_SELECT + " WHERE event_id=?";
    
    private static final String ALL_SCHEDULE = BASE_OBJECT_SELECT;
     
    private static final String DELETE_ALL = "DELETE FROM SCHEDULE";
     
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }
    
    @Override
    public boolean add(Schedule schedule) {
        try {
            return (jdbcTemplate.update(ADD_SCHEDULE, schedule.getEvent().getId(),
                    schedule.getAuditorium().getId()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(Schedule schedule) {
        try {
            return (jdbcTemplate.update(REMOVE_SCHEDULE, schedule.getEvent().getId(),
                    schedule.getAuditorium().getId()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Schedule findByID(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ID_SCHEDULE, new Object[] { id }, new ScheduleRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Schedule findByEventID(Event event) {
        try {
            return jdbcTemplate.queryForObject(FIND_EVENT_ID_SCHEDULE, new Object[] { event.getId() },
                    new ScheduleRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Schedule> getAll() {
        try {
            return jdbcTemplate.query(ALL_SCHEDULE, new ScheduleRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    
    private class ScheduleRowMapper implements RowMapper<Schedule> {

        @Override
        public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getInt("event_id"));
            event.setName(rs.getString("event_name"));
            event.setStartDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_startdate")));
            event.setEndDate(ClassCastUtil.getDateSQLToUtil(rs.getDate("event_enddate")));
            event.setPrice(rs.getInt("event_price"));
            event.setRating(Rating.valueOf(rs.getString("event_rating")));
            event.setTicketPrice(rs.getInt("event_ticketprice"));
            Auditorium auditorium = new Auditorium();
            auditorium.setId(rs.getInt("auditorium_id"));
            auditorium.setName(rs.getString("auditorium_name"));
            auditorium.setSeatsCount(rs.getInt("auditorium_seatscount"));
            List<Integer> intList = new LinkedList<>();
            for(String s : Arrays.asList(rs.getString("auditorium_vipseats").replace("[", "").replace("]", "").replace(" ", "").split(","))){
                intList.add(Integer.valueOf(s));
            }
            auditorium.setVipSeats(intList);
            Schedule schedule = new Schedule();
            schedule.setId(rs.getInt("id"));
            schedule.setAuditorium(auditorium);
            schedule.setEvent(event);
            return schedule;
        }
    }
}
