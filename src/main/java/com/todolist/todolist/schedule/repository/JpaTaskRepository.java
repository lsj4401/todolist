package com.todolist.todolist.schedule.repository;

import com.todolist.todolist.schedule.Task;
import com.todolist.todolist.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTaskRepository extends JpaRepository<Task, Long>, TaskRepository {
	Task findByTaskId(Long taskId);

	Page<Task> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
