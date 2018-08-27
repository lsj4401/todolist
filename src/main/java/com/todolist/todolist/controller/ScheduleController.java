package com.todolist.todolist.controller;

import com.todolist.todolist.schedule.Scheduler;
import com.todolist.todolist.schedule.Task;
import com.todolist.todolist.user.User;
import com.todolist.todolist.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

	private static User USER;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		USER = userRepository.save(new User());
	}

	@PostMapping
	public Task addTask(@RequestBody String message) {
		return scheduler.addTask(USER, message);
	}

	@GetMapping
	public Page<Task> listAll(@PageableDefault Pageable pageable) {
		return scheduler.paging(USER, pageable);
	}

	@PutMapping(value = "/message/{taskId}")
	public Task modifyMessage(@PathVariable Long taskId, @RequestBody String message) {
		return scheduler.modifyMessage(taskId, message);
	}

	@PutMapping(value = "/reference")
	public void referenceTask(@RequestParam Long parentTaskId, @RequestParam Long childTaskId) {
		scheduler.referenceTask(parentTaskId, childTaskId);
	}

	@PutMapping(value = "/complete/{taskId}")
	public Task completeTask(@PathVariable Long taskId) {
		return scheduler.completeTask(taskId);
	}

}
