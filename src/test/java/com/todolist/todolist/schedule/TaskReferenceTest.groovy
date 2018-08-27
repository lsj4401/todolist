package com.todolist.todolist.schedule

import com.todolist.todolist.exception.ReferenceException

class TaskReferenceTest extends TestBase {

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

		then: "참조가 부모자식 모두 제거된다."
		!parentTask.getChildTasks().contains(parentTask)
		!refTask.getChildTasks().contains(parentTask)
	}


	def "parent referenced"() {
		Task parentTask1 = createTask(1L)
		Task parentTask2 = createTask(2L)
		Task refTask = createTask(3L)
		parentTask1.reference(refTask)
		parentTask2.reference(refTask)

		when: "참조 목록을 @id 형태로 보여준다."
		String numbers = refTask.getParentTaskNumbers()

		then: "참조된 부모의 목록이 표시된다."
		numbers == "@1 @2"
	}

	def "parent referenced empty"() {
		Task refTask = createTask(3L)

		when: "참조가 없는 상태에서 참조 목록을 호출한다."
		String numbers = refTask.getParentTaskNumbers()

		then: "아무것도 표시되지 않는다."
		numbers == ""
	}
}
