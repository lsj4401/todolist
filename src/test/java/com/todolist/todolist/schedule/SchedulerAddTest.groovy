package com.todolist.todolist.schedule

import com.todolist.todolist.exception.EmptyMessageException
import com.todolist.todolist.schedule.repository.InMemoryTaskRepositoryImpl
import com.todolist.todolist.user.User
import spock.lang.Shared

class SchedulerAddTest extends TestBase {

	@Shared String message = "오늘은 청소를 한다."
	InMemoryTaskRepositoryImpl taskRepository = new InMemoryTaskRepositoryImpl()
	Scheduler scheduler = new Scheduler(
			taskRepository: taskRepository
	)

	def "add task's null message"() {
		when: "할일 메시지에 null 을 넣는다."
		scheduler.addTask(new User(), null)

		then: "null 은 저장하지 않는다 에러 발생"
		thrown EmptyMessageException
	}

	def "사용자는 텍스트로 된 할일을 추가할 수 있다"() {
		when: "할일을 추가한다."
		Task task = scheduler.addTask(new User(), message)

		then: "데이터가 저장된다. 저장된 할일은 Id 를 부여받는다."
		scheduler.getTask(task.getTaskId()).getMessage() == message
	}
}
