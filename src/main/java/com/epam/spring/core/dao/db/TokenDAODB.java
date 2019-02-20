package com.epam.spring.core.dao.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.TokenDAO;

@Component(value = "TokenDAODB")
public class TokenDAODB implements TokenDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static final String DELETE = "DELETE FROM PERSISTENT_LOGINS WHERE USERNAME =?";

    @Override
    public boolean deleteByName(String username) {
        try {
            return (jdbcTemplate.update(DELETE, username) > 0);
        } catch (DataAccessException e) {
            return false;
        }
    }

}
