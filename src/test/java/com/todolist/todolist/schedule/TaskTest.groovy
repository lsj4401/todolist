package com.todolist.todolist.schedule

import org.joda.time.LocalDateTime

class TaskTest extends TestBase {

	def "default value"() {
		Task task = createTask(1L)
		expect: "기본값 확인"
		!task.isCompleted()
		task.getChildTasks().isEmpty()
	}

	def "compare"() {
		Task task1 = createTask(task1Id)
		Task task2 = createTask(task2Id)

		expect: "id 값으로 동등 비교를 한다."
		(task1 == task2) == expected

		where:
		task1Id | task2Id | expected
		1L      | 1L      | true
		1L      | 2L      | false
	}

	def "update"() {
		Date before = LocalDateTime.now().minusDays(1).toDate()

		Task task = createTask(1L)
		task.setUpdatedAt(before)

		when: "업데이트를 한다."
		task.onUpdate()

		then: "업데이트 날짜가 변경된다."
		task.getUpdatedAt().getTime() > before.getTime()
	}
}
