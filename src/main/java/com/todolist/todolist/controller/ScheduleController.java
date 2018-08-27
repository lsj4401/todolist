package com.todolist.todolist.controller;

import com.todolist.todolist.exception.BizException;
import com.todolist.todolist.schedule.Scheduler;
import com.todolist.todolist.schedule.Task;
import com.todolist.todolist.user.User;
import com.todolist.todolist.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

	private User user;

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {
		user = userRepository.save(new User());
	}

	@PostMapping
	public Task addTask(@RequestBody String message) {
		return scheduler.addTask(user, message);
	}

	@GetMapping
	public Page<Task> listAll(@PageableDefault Pageable pageable) {
		return scheduler.paging(user, pageable);
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

	@ExceptionHandler({ BizException.class })
	public String bizException(Exception ex) throws Exception {
		log.info(ex.getMessage());
		throw ex;
	}
}
