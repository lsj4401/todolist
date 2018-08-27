package com.todolist.todolist.schedule;

import com.google.common.collect.Lists;
import com.todolist.todolist.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository {
	private List<Task> tasks = new ArrayList<>();

	public Task save(Task task) {
		task.setTaskId(1L + (long) (Math.random() * (10L - 1L)));
		tasks.add(task);
		return task;
	}

	public Task findByTaskId(Long taskId) {
		for (Task task : tasks) {
			if (task.getTaskId().equals(taskId)) {
				return task;
			}
		}

		return null;
	}

	public Page<Task> findByUserPaging(User user, Pageable pageable) {
		List<Task> usersTasks = tasks.stream().filter(task -> task.getUser().equals(user)).collect(Collectors.toList());
		List<List<Task>> partition = Lists.partition(usersTasks, pageable.getPageSize());
		if (partition.size() > pageable.getPageNumber()) {
			return new PageImpl<>(partition.get(pageable.getPageNumber()), pageable, tasks.size());
		}
		return new PageImpl<>(Collections.emptyList(), pageable, tasks.size());
	}

	public Task update(Task task) {
		// updateTask

		return task;
	}
}
