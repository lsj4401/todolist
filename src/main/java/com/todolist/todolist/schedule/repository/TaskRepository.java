package com.todolist.todolist.schedule.repository;

import com.todolist.todolist.schedule.Task;
import com.todolist.todolist.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskRepository {
	Task save(Task task);

	Task findByTaskId(Long taskId);

	Page<Task> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
