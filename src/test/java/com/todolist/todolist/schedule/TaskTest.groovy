package com.todolist.todolist.schedule

import com.todolist.todolist.user.User
import spock.lang.Specification

class TaskTest extends Specification {

	def "default value"() {
		Task task = new Task(new User(), "")
		expect: "기본값 확인"
		!task.isCompleted()
		task.getChildTasks().isEmpty()
	}

	def "compare"() {
		Task task1 = new Task(new User(), "")
		task1.setTaskId(task1Id)
		Task task2 = new Task(new User(), "")
		task2.setTaskId(task2Id)

		expect: "id 값으로 동등 비교를 한다."
		(task1 == task2) == expected

		where:
		task1Id | task2Id | expected
		1L      | 1L      | true
		1L      | 2L      | false
	}
}
