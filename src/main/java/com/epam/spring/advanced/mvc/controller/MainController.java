package com.epam.spring.advanced.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.epam.spring.advanced.util.WebUtil;

@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage(Model model) {
		WebUtil.setUserInfo(model);
		return "index";
	}

}
