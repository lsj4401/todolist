package com.todolist.todolist.schedule;

import java.util.List;

public class Scheduler {
	public Task addTask(String message) {
		return new Task(message);
	}

	public List<Task> listAll() {
		return null;
	}
}
