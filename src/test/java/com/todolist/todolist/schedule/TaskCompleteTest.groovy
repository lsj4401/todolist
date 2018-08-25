package com.todolist.todolist.schedule

import com.todolist.todolist.exception.NotCompletedException
import com.todolist.todolist.user.User
import spock.lang.Specification

class TaskCompleteTest extends Specification {
	def "complete task"() {
		Task task = new Task(new User(), "")

		when: "참조가 없는 할일을 완료 한다"
		task.complete()

		then: "완료된다"
		task.isCompleted()
	}

	def "complete task with completed task"() {
		Task task = new Task(new User(), "")
		Task refTask = new Task(new User(), "")
		refTask.setCompleted(true)

		when: "참조가 완료된 할일을 완료 한다"
		task.complete()

		then: "완료된다"
		task.isCompleted()
	}

	def "complete task with mixed task"() {
		Task task = new Task(new User(), "")
		task.setTaskId(1L)
		Task refTask1 = new Task(new User(), "")
		refTask1.setTaskId(2L)
		refTask1.setCompleted(true)
		Task refTask2 = new Task(new User(), "")
		refTask2.setTaskId(3L)
		refTask2.setCompleted(false)
		task.reference(refTask1)
		task.reference(refTask2)

		when: "참조의 완료 상태가 섞여 있는 할일을 완료 한다"
		task.complete()

		then: "참조 할일이 완료되지 않으면 해당 할일은 완료할 수 없다."
		thrown NotCompletedException
	}

	def "complete with with no completed task"() {
		Task parentTask = new Task(new User(), "")
		parentTask.setTaskId(1L)
		Task refTask = new Task(new User(), "")
		refTask.setTaskId(2L)
		refTask.setCompleted(false)

		parentTask.reference(refTask)

		when: "참조 할일이 완료되지 않은 할일을 완료시킨다."
		parentTask.complete()

		then: "참조 할일이 완료되지 않으면 해당 할일은 완료할 수 없다."
		thrown NotCompletedException
	}
}
