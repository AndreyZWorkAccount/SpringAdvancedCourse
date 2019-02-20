package com.epam.spring.core.controller;

import java.util.List;

import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

public interface IUserAccountService {

    boolean add(UserAccount userAccount);

    boolean remove(UserAccount userAccount);

    boolean update(UserAccount userAccount);

    UserAccount findByID(int id);

    UserAccount findByUser(User user);

    List<UserAccount> getAll();

    void clear();

}
