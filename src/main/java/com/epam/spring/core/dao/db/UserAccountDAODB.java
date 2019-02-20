package com.epam.spring.core.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.UserAccountDAO;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;
import com.epam.spring.core.util.ClassCastUtil;

@Component(value = "UserAccountDAODB")
public class UserAccountDAODB implements UserAccountDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String ADD_USERACCOUNT = "INSERT INTO USERACCOUNTS (user_id, account) VALUES (?,?)";

    private static final String REMOVE_USERACCOUNT = "DELETE FROM USERACCOUNTS WHERE id=?";

    private static final String UPDATE_USERACCOUNT = "UPDATE  USERACCOUNTS "
            + "SET account=? " 
            + "WHERE id=? ";

    private static final String BASE_SELECT = "SELECT "
            + "a.ID as ID,  " 
            + "a.ACCOUNT as ACCOUNT, "
            + "a.USER_ID as USER_ID, " 
            + "u.BIRTHDAY  as USER_BIRTHDAY, " 
            + "u.NAME  as USER_NAME, "
            + "u.EMAIL as USER_EMAIL, " 
            + "u.password  as USER_PASSWORD, "
            + "u.roles as USER_ROLES " 
            + "FROM USERACCOUNTS a " 
            + "inner join users u ON a.user_id = u.id ";

    private static final String FIND_NAME_USER = BASE_SELECT + " WHERE u.NAME = ? ";

    private static final String FIND_ID_USERACCOUNT = BASE_SELECT + " WHERE id = ?";

    private static final String ALL_USERACCOUNTS = BASE_SELECT;

    private static final String DELETE_ALL = "DELETE FROM USERACCOUNTS";

    @Override
    public boolean add(UserAccount userAccount) {
//        try {
            return (jdbcTemplate.update(ADD_USERACCOUNT, userAccount.getUser().getId(), userAccount.getAccount()) > 0);
//        } catch (DataAccessException e) {
//            return false;
//        }
    }

    @Override
    public boolean remove(UserAccount userAccount) {
//        try {
            return (jdbcTemplate.update(REMOVE_USERACCOUNT, userAccount.getId()) > 0);
//       } catch (DataAccessException e) {
//           return false;
//        }
    }

    @Override
    public boolean update(UserAccount userAccount) {
//        try {
            return (jdbcTemplate.update(UPDATE_USERACCOUNT, userAccount.getAccount(), userAccount.getId()) > 0);
//        } catch (DataAccessException e) {
//            return false;
//        }
    }

    @Override
    public UserAccount findByID(int id) {
//        try {
            return jdbcTemplate.queryForObject(FIND_ID_USERACCOUNT, new Object[] { id }, new UserAccountRowMapper());
//        } catch (DataAccessException e) {
//            return null;
//        }
    }

    @Override
    public UserAccount findByUser(User user) {
//        try {
            return jdbcTemplate.queryForObject(FIND_NAME_USER, new Object[] { user.getName() },
                    new UserAccountRowMapper());
//        } catch (DataAccessException e) {
//            return null;
//        }
    }

    @Override
    public List<UserAccount> getAll() {
//        try {
            return jdbcTemplate.query(ALL_USERACCOUNTS, new UserAccountRowMapper());
//        } catch (DataAccessException e) {
//            return null;
//        }
    }

    @Override
    public void clear() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public static class UserAccountRowMapper implements RowMapper<UserAccount> {

        @Override
        public UserAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setEmail(rs.getString("user_email"));
            user.setPassword(rs.getString("user_password"));
            user.setRoles(rs.getString("user_roles"));
            user.setBirthday(ClassCastUtil.getDateSQLToUtil(rs.getDate("user_birthday")));
            UserAccount userAccount = new UserAccount();
            userAccount.setId(rs.getInt("id"));
            userAccount.setUser(user);
            userAccount.setAccount(rs.getInt("account"));
            return userAccount;
        }
    }

}
