package com.todolist.todolist.schedule

import com.todolist.todolist.exception.EmptyMessageException
import spock.lang.Shared
import spock.lang.Specification

class SchedulerTest extends Specification {

	@Shared String message = "오늘은 청소를 한다."

	def "add schedule message"() {
		Scheduler scheduler = new Scheduler()

		when: "할일 메시지에 null 을 넣는다."
		scheduler.addTask(null)

		then: "null 은 저장하지 않는다 에러 발생"
		thrown EmptyMessageException
	}

	def "add schedule"() {
		Scheduler scheduler = new Scheduler()

		when: "할일을 추가한다."
		Task task = scheduler.addTask(message)

		then: "저장된 할일을 볼 수 있다."
		task.getMessage() == message
	}

	def "listAll"() {
		Scheduler scheduler = new Scheduler()
		scheduler.addTask(message)

		when: "저장된 task 를 조회한다."
		List<Task> tasks = scheduler.listAll()

		then: "저장된 task 의 메시지를 확인 가능하다."
		tasks.get(0).getMessage() == message
	}
}
