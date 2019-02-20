package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.DiscountCounterDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.User;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "DiscountCounterDAODB")
public class DiscountCounterDAODB implements DiscountCounterDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_USER_COUNTERDISCOUNT= "INSERT INTO DISCOUNTCOUNTER (name, user_id, count) VALUES (?,?,?)";
    
    private static final String REMOVE_USER_COUNTERDISCOUNT = "DELETE FROM DISCOUNTCOUNTER WHERE name=?";
    
    private static final String UPDATE_USER_COUNTERDISCOUNT = "UPDATE  DISCOUNTCOUNTER "
            + "SET count=?"
            + "WHERE name=? and user_id=? ";
    
    private static final String BASE_OBJECT_SELECT = "Select "
    +" c.id, "
    +" c.name, "
    +" c.COUNT, "
    +" c.user_id, "
    +" u.name as user_name, "
    +" u.email as user_email, "
    +" u.birthday as user_birthday "
    +" from DISCOUNTCOUNTER c "
    +" inner join users u ON c.user_id = u.id ";
    
    private static final String FIND_USER_COUNTERDISCOUNT = BASE_OBJECT_SELECT+ " WHERE user_id=?";
    
   // private static final String FIND_NAME_COUNTERDISCOUNT = "SELECT * FROM DISCOUNTCOUNTER WHERE name=?";
    
    private static final String ALL_COUNTERDISCOUNT = BASE_OBJECT_SELECT;
    
    private static final String DELETE_ALL = "DELETE FROM DISCOUNTCOUNTER";
    
    
    @Override
    public void clear() {
      jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void addCounter(Counter<User> counter) {
       jdbcTemplate.update(ADD_USER_COUNTERDISCOUNT, counter.getName(),  counter.getValue().getId(), counter.getCount());
    }

    @Override
    public void removeCounter(Counter<User> counter) {
        jdbcTemplate.update(REMOVE_USER_COUNTERDISCOUNT, counter.getName());
    }
    
    @Override
    public void updateCounter(Counter<User> counter) {
        jdbcTemplate.update(UPDATE_USER_COUNTERDISCOUNT, counter.getCount(), counter.getName(),  counter.getValue().getId());
    }

    @Override
    public List<Counter<User>> getAllDiscount() {
        try {
            return jdbcTemplate.query(ALL_COUNTERDISCOUNT, new CounterRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Counter<User>> find(User user) {
        try {
            return jdbcTemplate.query(FIND_USER_COUNTERDISCOUNT, new Object[] { user.getId() }, new CounterRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
    
    private class CounterRowMapper implements RowMapper<Counter<User>> {

        @Override
        public Counter<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setEmail(rs.getString("user_email"));
            user.setBirthday(ClassCastUtil.getDateSQLToUtil(rs.getDate("user_birthday")));
            Counter<User> counter = new Counter<>();
            counter.setName(rs.getString("name"));
            counter.setCount(rs.getInt("count"));
            counter.setValue(user);
            return counter;
        }
    }
 

}
