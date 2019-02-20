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

import com.epam.spring.core.dao.AuditoriumDAO;
import com.epam.spring.core.model.Auditorium;


@Component(value = "AuditoriumDAODB")
public class AuditoriumDAODB implements AuditoriumDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_AUDITORIUM= "INSERT INTO AUDITORIUMS (name, seatscount, vipseats) VALUES (?,?,?)";
    
    private static final String REMOVE_AUDITORIUM = "DELETE FROM AUDITORIUMS WHERE name=?";
    
    private static final String UPDATE_AUDITORIUM = "UPDATE  AUDITORIUMS "
            + "SET name=?, seatscount=?, vipseats=?"
            + "WHERE name=?";
    
    private static final String FIND_ID_AUDITORIUM = "SELECT * FROM AUDITORIUMS WHERE id=?";
    
    private static final String FIND_NAME_AUDITORIUM = "SELECT * FROM AUDITORIUMS WHERE name=?";
    
    private static final String ALL_AUDITORIUM = "SELECT * FROM AUDITORIUMS";
    
    private static final String DELETE_ALL = "DELETE FROM AUDITORIUMS";
    
      
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public boolean add(Auditorium auditorium) {
        try {
            return (jdbcTemplate.update(ADD_AUDITORIUM, auditorium.getName(), auditorium.getSeatsCount(),
                    auditorium.getVipSeats().toString()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(Auditorium auditorium) {
        try {
            return (jdbcTemplate.update(REMOVE_AUDITORIUM, auditorium.getName()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(Auditorium auditorium) {
        try {
            return (jdbcTemplate.update(UPDATE_AUDITORIUM, auditorium.getName(), auditorium.getSeatsCount(),
                    auditorium.getVipSeats().toString(), auditorium.getName()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public Auditorium findByID(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ID_AUDITORIUM, new Object[] { id }, new AuditoriumRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Auditorium findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_NAME_AUDITORIUM, new Object[] { name }, new AuditoriumRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Auditorium> getAll() {
        try {
            return jdbcTemplate.query(ALL_AUDITORIUM, new AuditoriumRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    private class AuditoriumRowMapper implements RowMapper<Auditorium> {

        @Override
        public Auditorium mapRow(ResultSet rs, int rowNum) throws SQLException {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(rs.getInt("id"));
            auditorium.setName(rs.getString("name"));
            auditorium.setSeatsCount(rs.getInt("seatscount"));
            List<Integer> intList = new LinkedList<>();
            for(String s : Arrays.asList(rs.getString("vipseats").replace("[", "").replace("]", "").replace(" ", "").split(","))){
                intList.add(Integer.valueOf(s));
            }
            auditorium.setVipSeats(intList);
            return auditorium;
        }
    }

}
