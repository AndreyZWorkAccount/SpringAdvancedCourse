package com.epam.spring.core.aspects;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.spring.core.dao.DiscountCounterDAO;
import com.epam.spring.core.dao.UserDAO;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.User;

@Aspect
@Component
public class DiscountAspect {

    @Autowired
    @Qualifier("DiscountCounterDAODB")
    DiscountCounterDAO counterDAO;

    @Autowired
    @Qualifier("UserDAODB")
    UserDAO userDAO;

    @Pointcut("execution(* com.epam.spring.core.controller.discount.*.setDiscount(..))")
    private void countAllDiscount() {
    }

    @After("countAllDiscount()")
    public void countGetPrice(final JoinPoint jp) {
        String name = jp.getTarget().toString();
        for (final Object argument : jp.getArgs()) {
            if (argument.getClass().equals(User.class)) {
                incCounter((User) argument, name);
            }
        }
    }

    private void incCounter(User user, String name) {
        User userFind = userDAO.findByName(user.getName());
        List<Counter<User>> counters = counterDAO.find(userFind);
        if (counters != null && counters.size() != 0) {
            for (Counter<User> counter : counters) {
                if (name.equals(counter.getName())) {
                    counter.setCount(counter.getCount() + 1);
                    counterDAO.updateCounter(counter);
                }
            }
        } else {
            Counter<User> counter = new Counter<>();
            counter.setValue(user);
            counter.setName(name);
            counter.setCount(1);
            counterDAO.addCounter(counter);
        }
    }

}
