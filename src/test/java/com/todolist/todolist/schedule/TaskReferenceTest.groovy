package com.todolist.todolist.schedule

import com.todolist.todolist.exception.ReferenceException
import com.todolist.todolist.user.User
import spock.lang.Shared
import spock.lang.Specification

class TaskReferenceTest extends Specification {

	@Shared User user = new User()

	def "task with ref"() {
		Task parentTask = createTask(1L)
		Task refTask = createTask(2L)

		when: "할일에 참조를 건다."
		parentTask.reference(refTask)

		then: "참조를 걸면 부모가 저장된다."
		refTask.getParentTasks().contains(parentTask)
		parentTask.getChildTasks().contains(refTask)
	}

	def "recursion step0 ref"() {
		Task parentTask = createTask(1L)

		when: "할일에 자기 자신을 참조 건다."
		parentTask.reference(parentTask)

		then: "익셉션이 발생한다."
		thrown ReferenceException
	}

	def "recursion step1 ref"() {
		Task parentTask = createTask(1L)
		Task refTask = createTask(2L)

		when: "재귀 참조를 건다."
		parentTask.reference(refTask)
		refTask.reference(parentTask)

		then: "재귀 참조는 예외가 발생한다."
		thrown ReferenceException
	}

	def "recursion step2 ref"() {
		Task frontTask = createTask(1L)
		Task middleTask = createTask(2L)
		Task endTask = createTask(3L)

		when: "재귀 참조를 건다."
		frontTask.reference(middleTask)
		middleTask.reference(endTask)
		endTask.reference(frontTask)

		then: "재귀 참조는 예외가 발생한다."
		thrown ReferenceException
	}

	def "unreferenced"() {
		Task parentTask = createTask(1L)
		Task refTask = createTask(2L)
		parentTask.reference(refTask)

		when: "참조를 끊는다."
		parentTask.unreferenced(refTask)

		then:
		!parentTask.getChildTasks().contains(parentTask)
		!refTask.getChildTasks().contains(parentTask)
	}

	private Task createTask(long taskId) {
		def task = new Task(user, "")
		task.setTaskId(taskId)
		return task
	}
}
