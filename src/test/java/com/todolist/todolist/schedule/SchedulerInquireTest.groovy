package com.todolist.todolist.schedule

import com.todolist.todolist.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Shared
import spock.lang.Specification

class SchedulerInquireTest extends Specification {
	@Shared	User user = new User()
	@Shared	String message = "오늘은 청소를 한다."

	TaskRepository taskRepository = new TaskRepository()
	Scheduler scheduler = new Scheduler(
			taskRepository: taskRepository
	)

	def "paging task"() {
		List<Task> tasks = new ArrayList<>()
		tasks.add(scheduler.addTask(user, message))
		tasks.add(scheduler.addTask(user, message))
		tasks.add(scheduler.addTask(user, message))
		List<Task> expectedContains = new ArrayList<>()
		int startIndex = size * pageNumber
		for (int i = 0; i < expectedSize; i++) {
			expectedContains.add(tasks.get(i + startIndex))
		}

		when: "size 개씩 조회하는 pageNumber 번째 page 를 조회한다"
		Page<Task> page = scheduler.paging(user, new PageRequest(pageNumber, size))

		then: "해당 size 크기로 pageNumber 의 할일이 조회된다."
		page.getContent().size() == expectedSize
		page.getContent().containsAll(expectedContains)

		where:
		size | pageNumber | expectedSize
		2    | 0          | 2
		2    | 1          | 1
		2    | 2          | 0
		4    | 0          | 3
	}

	def "getOneTask"() {
		User user = new User()
		Task savedTask = scheduler.addTask(user, message)

		when: "저장된 할일을 조회한다."
		Task inquireTask = scheduler.getTask(savedTask.getTaskId())

		then: "저장된 할일이 조회된다."
		inquireTask.getMessage() == message
	}
}
