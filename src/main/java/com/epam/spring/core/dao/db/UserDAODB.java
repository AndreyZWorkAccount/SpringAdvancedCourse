package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.User;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "UserDAODB")
public class UserDAODB implements UserDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    private static final String ADD_USER = "INSERT INTO USERS (name, email, birthday, password) VALUES (?,?,?,?)";
    
    private static final String REMOVE_USER = "DELETE FROM USERS WHERE name=? and email=?";
    
    private static final String UPDATE_USER = "UPDATE  Users "
            + "SET name=?, email=?, birthday=?, password=?"
            + "WHERE id=?";
    
    private static final String FIND_ID_USER = "SELECT * FROM USERS WHERE id=?";
    
    private static final String FIND_EMAIL_USER = "SELECT * FROM USERS WHERE email=?";
    
    private static final String FIND_NAME_USER = "SELECT * FROM USERS WHERE name=?";
    
    private static final String ALL_USER = "SELECT * FROM USERS";
    
    
    private static final String DELETE_ALL = "DELETE FROM USERS";
   
    private static final String SELECT_ALL_ACCOUNTS = "SELECT "
           + "a.ID as ID,  " 
           + "a.ACCOUNT as ACCOUNT, "
           + "u.ID as USER_ID, " 
           + "u.BIRTHDAY  as USER_BIRTHDAY, " 
           + "u.NAME  as USER_NAME, "
           + "u.EMAIL as USER_EMAIL, " 
           + "FROM USERS u "
           + "left join userACCOUNTs a ON a.user_id = u.id ";
    
	@Override
	public void clear() {
		jdbcTemplate.update(DELETE_ALL);
	}

    @Override
    public boolean add(User user) {
        try {
            return (jdbcTemplate.update(ADD_USER, user.getName(), user.getEmail(),
                    ClassCastUtil.getDateUtilToSQL(user.getBirthday())) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean remove(User user) {
        try {
            return (jdbcTemplate.update(REMOVE_USER, user.getName(), user.getEmail()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            return (jdbcTemplate.update(UPDATE_USER, user.getName(), user.getEmail(),
                    ClassCastUtil.getDateUtilToSQL(user.getBirthday()),
                    user.getId()) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public User findByID(int id) {
        try {
            return jdbcTemplate.queryForObject(FIND_ID_USER, new Object[] { id }, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(FIND_EMAIL_USER, new Object[] { email }, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public User findByName(String name) {
        try {
            return jdbcTemplate.queryForObject(FIND_NAME_USER, new Object[] { name }, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return jdbcTemplate.query(ALL_USER, new UserRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }
    
	private class UserRowMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setBirthday(ClassCastUtil.getDateSQLToUtil(rs.getDate("birthday")));
			return user;
        }
    }
	
    
}
