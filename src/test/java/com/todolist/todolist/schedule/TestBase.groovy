package com.todolist.todolist.schedule

import com.todolist.todolist.user.User
import spock.lang.Shared
import spock.lang.Specification

class TestBase extends Specification {
	@Shared User user = new User()

	protected Task createTask(long taskId) {
		def task = new Task(user, "")
		task.setTaskId(taskId)
		return task
	}
}
