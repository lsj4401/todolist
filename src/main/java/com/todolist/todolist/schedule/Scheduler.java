package com.todolist.todolist.schedule;

import com.todolist.todolist.exception.EmptyMessageException;
import com.todolist.todolist.exception.ReferenceException;
import com.todolist.todolist.schedule.repository.TaskRepository;
import com.todolist.todolist.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.todolist.todolist.utils.Constansts.EMPTY_MESSAGE;
import static com.todolist.todolist.utils.Constansts.NO_SEARCH_TASK;

@Service
public class Scheduler {
	@Autowired
	private TaskRepository taskRepository;

	@Transactional
	public Task addTask(User user, String message) {
		if (message == null) {
			throw new EmptyMessageException(EMPTY_MESSAGE);
		}
		return taskRepository.save(new Task(user, message));
	}

	@Transactional(readOnly = true)
	public Task getTask(Long taskId) {
		return taskRepository.findByTaskId(taskId);
	}

	@Transactional(readOnly = true)
	public Page<Task> paging(User user, Pageable pageable) {
		return taskRepository.findByUserOrderByCreatedAtDesc(user, pageable);
	}

	@Transactional
	public Task modifyMessage(Long taskId, String message) {
		Task task = taskRepository.findByTaskId(taskId);
		task.setMessage(message);
		return task;
	}

	@Transactional
	public void referenceTask(Long parentTaskId, Long childTaskId) {
		Task parentTask = taskRepository.findByTaskId(parentTaskId);
		Task childTask = taskRepository.findByTaskId(childTaskId);
		nullCheck(parentTask, childTask);

		parentTask.reference(childTask);
	}

	@Transactional
	public void unreferencedTask(Long parentTaskId, Long childTaskId) {
		Task parentTask = taskRepository.findByTaskId(parentTaskId);
		Task childTask = taskRepository.findByTaskId(childTaskId);
		nullCheck(parentTask, childTask);

		parentTask.unreferenced(childTask);
	}

	@Transactional
	public Task completeTask(Long taskId) {
		Task task = taskRepository.findByTaskId(taskId);
		nullCheck(task, task);
		task.complete();
		return task;
	}

	private void nullCheck(Task parentTask, Task childTask) {
		if (parentTask == null || childTask == null) {
			throw new ReferenceException(NO_SEARCH_TASK);
		}
	}

}
