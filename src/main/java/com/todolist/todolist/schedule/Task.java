package com.todolist.todolist.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todolist.todolist.exception.NotCompletedException;
import com.todolist.todolist.exception.ReferenceException;
import com.todolist.todolist.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString(exclude = {"childTasks"})
@EqualsAndHashCode(of = "taskId")
public class Task {
	private Long taskId;
	private User user;
	private String message;
	private boolean isCompleted;
	private List<Task> parentTasks = new ArrayList<>();
	@JsonIgnore
	private List<Task> childTasks = new ArrayList<>();
	private Date createdAt = new Date();
	private Date updatedAt = new Date();

	public Task(User user, String message) {
		this.user = user;
		this.message = message;
	}

	public void reference(Task childTask) {
		childTask.addParentTask(this);
		childTasks.add(childTask);
		if (isRecursive(parentTasks)) {
			throw new ReferenceException("순환 참조되고 있습니다.");
		}
	}

	private boolean isRecursive(List<Task> parentTasks) {
		return parentTasks.stream().anyMatch(task -> task.equals(this) || isRecursive(task.getParentTasks()));
	}

	private void addParentTask(Task parentTask) {
		parentTasks.add(parentTask);
	}

	public void unreferenced(Task childTask) {
		childTasks.remove(childTask);
		childTask.getParentTasks().remove(this);
	}

	public void complete() {
		if (!childTasks.stream().allMatch(Task::isCompleted)) {
			throw new NotCompletedException("완료되지 않은 참조 일감이 존재합니다.");
		}

		isCompleted = true;
	}

	public String getParentTaskNumbers() {
		if (parentTasks.isEmpty()) {
			return "";
		}

		return "@" + String.join(" @", parentTasks.stream().map(task -> task.getTaskId().toString()).collect(Collectors.toList()));
	}
}
