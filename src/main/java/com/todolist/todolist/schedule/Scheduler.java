package com.todolist.todolist.schedule;

import com.todolist.todolist.exception.EmptyMessageException;
import com.todolist.todolist.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class Scheduler {
	private TaskRepository taskRepository = new TaskRepository();

	public Task addTask(User user, String message) {
		if (message == null) {
			throw new EmptyMessageException();
		}
		return taskRepository.save(new Task(user, message));
	}

	public Task getTask(Long taskId) {
		return taskRepository.findByTaskId(taskId);
	}

	public Page<Task> paging(User user, Pageable pageable) {
		return taskRepository.findByUserPaging(user, pageable);
	}

	public Task modifyMessage(Long taskId, String message) {
		Task task = taskRepository.findByTaskId(taskId);
		task.setMessage(message);
		return taskRepository.update(task);
	}

	public void referenceTask(Long parentTaskId, Long childTaskId) {
		Task parentTask = taskRepository.findByTaskId(parentTaskId);
		Task childTask = taskRepository.findByTaskId(childTaskId);

		parentTask.reference(childTask);
	}

	public void unreferencedTask(Long parentTaskId, Long childTaskId) {
		Task parentTask = taskRepository.findByTaskId(parentTaskId);
		Task childTask = taskRepository.findByTaskId(childTaskId);

		parentTask.unreferenced(childTask);
	}

	public Task completeTask(Long taskId) {
		Task task = taskRepository.findByTaskId(taskId);
		task.complete();

		return task;
	}
}
