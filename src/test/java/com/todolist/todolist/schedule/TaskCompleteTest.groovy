package com.todolist.todolist.schedule

import com.todolist.todolist.exception.NotCompletedException

class TaskCompleteTest extends TestBase {
	def "complete task"() {
		Task task = createTask(1L)

		when: "참조가 없는 할일을 완료 한다"
		task.complete()

		then: "완료된다"
		task.isCompleted()
	}

	def "complete task with completed task"() {
		Task task = createTask(1L)
		Task refTask = createTask(2L)
		refTask.setCompleted(true)

		when: "참조가 완료된 할일을 완료 한다"
		task.complete()

		then: "완료된다"
		task.isCompleted()
	}

	def "complete task with mixed task"() {
		Task task = createTask(1L)
		Task refTask1 = createTask(2L)
		refTask1.setCompleted(true)
		Task refTask2 = createTask(3L)
		refTask2.setCompleted(false)
		task.reference(refTask1)
		task.reference(refTask2)

		when: "참조의 완료 상태가 섞여 있는 할일을 완료 한다"
		task.complete()

		then: "참조 할일이 완료되지 않으면 해당 할일은 완료할 수 없다."
		thrown NotCompletedException
	}

	def "complete with no completed task"() {
		Task parentTask = createTask(1L)
		Task refTask = createTask(2L)
		refTask.setCompleted(false)

		parentTask.reference(refTask)

		when: "참조 할일이 완료되지 않은 할일을 완료시킨다."
		parentTask.complete()

		then: "참조 할일이 완료되지 않으면 해당 할일은 완료할 수 없다."
		thrown NotCompletedException
	}
}
