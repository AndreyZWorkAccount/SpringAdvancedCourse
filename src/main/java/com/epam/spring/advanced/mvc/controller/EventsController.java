package com.epam.spring.advanced.mvc.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epam.spring.advanced.util.WebUtil;
import com.epam.spring.core.controller.IEventService;
import com.epam.spring.core.model.Counter;
import com.epam.spring.core.model.Event;
import com.epam.spring.core.model.Schedule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/event")
public class EventsController {

	@Autowired
	private IEventService service;
	
	private static final String VIEW = "menuEvent";
	
	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public String menu(Model model) {
		WebUtil.setMainAttributes(model,"welcome");
		return VIEW;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(Model model, @RequestParam("file") MultipartFile file) throws IOException {
		String input = new String(file.getBytes());
		Gson gson = new Gson();
		Type itemsListType = new TypeToken<LinkedList<Schedule>>() {}.getType();
		List<Schedule> list = gson.fromJson(input, itemsListType);
		int counter =0;
		for (Schedule schedule: list){
			if (service.addEvent(schedule)){
				counter++;
			}
		}
		WebUtil.setMainAttributes(model,"uploaded "+counter +" events");
		return VIEW;
	}
     
    
	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public String getStatistic(Model model) {
		List<Counter<Event>> list = service.getStatistic();
		model.addAttribute("stats", list);
		WebUtil.setMainAttributes(model,"OK");
		return VIEW;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String getAll(Model model) {
		List<Event> list = service.getAll();
		model.addAttribute("events", list);
		WebUtil.setMainAttributes(model,"OK");
		return VIEW;
	}
	
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public String clear(Model model) {
		service.clear();
		WebUtil.setMainAttributes(model,"Cleared");
		return VIEW;
	}
	
}
