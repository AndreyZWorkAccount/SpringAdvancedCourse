package com.epam.spring.advanced.mvc.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.spring.advanced.util.WebUtil;
import com.epam.spring.core.controller.IUserAccountService;
import com.epam.spring.core.controller.IUserService;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Order;
import com.epam.spring.core.model.User;
import com.epam.spring.core.model.UserAccount;

@Controller
@RequestMapping(value = "/user")
public class UsersController {

	@Autowired
	private IUserService service;

	private static final String VIEW = "menuUser";

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(Model model) {
		setMainAttributes(model, "welcome");
		return VIEW;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, @RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
			@RequestParam("password") String password,
			@RequestParam("account") int account,
			@RequestParam("role") String roles) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		User user = service.add(name, email, birthday, WebUtil.getMd5Password(password), roles, account);
		if (user != null) {
			setMainAttributes(model, "Created");
		} else {
			setMainAttributes(model, "Not Created");
		}
		return VIEW;
	}

	@RequestMapping(value = "/bookedTickets", method = RequestMethod.GET)
	public String getBookedTickets(Model model, @RequestParam int id) {
		User user = new User();
		user.setId(id);
		List<Order> list = service.getBookedTickets(user);
		model.addAttribute("orders", list);
		setMainAttributes(model, "OK");
		return VIEW;
	}

	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public String getStatistic(Model model) {
		List<Counter<User>> list = service.getStatistic();
		model.addAttribute("stats", list);
		setMainAttributes(model, "OK");
		return VIEW;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(Model model) {
		List<UserAccount> list = service.getAllAccount();
		model.addAttribute("userAccs", list);
		setMainAttributes(model, "OK");
		return VIEW;
	}

	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public String clear(Model model) {
		service.clear();
		setMainAttributes(model, "Cleared");
		return VIEW;
	}
	
	private Model setMainAttributes(Model model, String status) {
		model.addAttribute("roles", service.getRoles());
		return WebUtil.setMainAttributes(model, status);
	}
	
}
