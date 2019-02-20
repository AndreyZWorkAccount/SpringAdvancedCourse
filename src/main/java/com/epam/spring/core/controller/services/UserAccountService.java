package com.epam.spring.core.controller.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.epam.spring.core.controller.IUserAccountService;
import com.epam.spring.core.dao.UserAccountDAO;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

public class UserAccountService implements IUserAccountService {

    @Autowired
    @Qualifier("UserAccountDAODB")
    UserAccountDAO userAccountDAO;

    @Override
    public boolean add(UserAccount userAccount) {
        return userAccountDAO.add(userAccount);
    }

    @Override
    public boolean remove(UserAccount userAccount) {
        return userAccountDAO.remove(userAccount);
    }

    @Override
    public boolean update(UserAccount userAccount) {
        return userAccountDAO.update(userAccount);
    }

    @Override
    public UserAccount findByID(int id) {
        return userAccountDAO.findByID(id);
    }

    @Override
    public UserAccount findByUser(User user) {
        return userAccountDAO.findByUser(user);
    }

    @Override
    public List<UserAccount> getAll() {
        return userAccountDAO.getAll();
    }

    @Override
    public void clear() {
        userAccountDAO.clear();
    }

}
