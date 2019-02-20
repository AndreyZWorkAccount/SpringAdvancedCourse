package com.epam.spring.core.dao.mock;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

@Component(value = "UserDAOMock")
public class UserDAOMock implements UserDAO {

	@Autowired
	MockDB mockDB;
	
	public boolean add(User user) {
		if (!mockDB.getUsers().contains(user)) {
			mockDB.addUser(user);
			return true;
		} else {
			return false;
		}
	}

	public boolean remove(User user) {
		for (User u: mockDB.getUsers()){
			if (user.equals(u)){
				mockDB.removeUser(u);
				return true;
			}
		}
		return false;
	}

	public boolean update(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	public User findByID(int id) {
		for (User u: mockDB.getUsers()){
			if (id == u.getId()){
				return u;
			}
		}
		return null;
	}

	public User findByEmail(String email) {
		for (User u: mockDB.getUsers()){
			if (email.equals(u.getEmail())){
				return u;
			}
		}
		return null;
	}

	public User findByName(String name) {
		for (User u: mockDB.getUsers()){
			if (name.equals(u.getName())){
				return u;
			}
		}
		return null;
	}

	public List<User> getAll() {
		return new LinkedList<User>(mockDB.getUsers());
	}

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<UserAccount> getAllAccount() {
        // TODO Auto-generated method stub
        return null;
    }

    
}
