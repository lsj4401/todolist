package com.todolist.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScheduleViewController {
	@GetMapping(value = "/view")
	public String view() {
		return "index";
	}
}
