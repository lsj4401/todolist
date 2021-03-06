package com.todolist.todolist.schedule

import com.todolist.todolist.exception.ReferenceException
import com.todolist.todolist.schedule.repository.InMemoryTaskRepositoryImpl
import com.todolist.todolist.user.User
import spock.lang.Shared
import spock.lang.Specification

class SchedulerModifyTest extends Specification {

	@Shared String message = "오늘은 청소를 한다."
	@Shared String modifiedMessage = "오늘은 그냥 논다."
	@Shared User user = new User()
	InMemoryTaskRepositoryImpl taskRepository = new InMemoryTaskRepositoryImpl()
	Scheduler scheduler = new Scheduler(
			taskRepository: taskRepository
	)

	def "modify message"() {
		Task task = scheduler.addTask(new User(), message)

		when: "메시지를 수정한다."
		scheduler.modifyMessage(task.getTaskId(), modifiedMessage)

		then: "메시지가 수정된다."
		scheduler.getTask(task.getTaskId()).getMessage() == modifiedMessage
	}

	def "reference"() {
		Task parentTask = scheduler.addTask(user, message)
		Task childTask = scheduler.addTask(user, message)

		when: "저장된 할일에 참조를 건다."
		scheduler.referenceTask(parentTask.getTaskId(), childTask.getTaskId())

		then: "참조가 걸린다"
		scheduler.getTask(parentTask.getTaskId()).getChildTasks().contains(childTask)
		scheduler.getTask(childTask.getTaskId()).getParentTasks().contains(parentTask)
	}

	def "empty reference"() {
		Task parentTask = scheduler.addTask(user, message)

		when: "child 조회값이 없는 할일을 참조건다."
		scheduler.referenceTask(parentTask.getTaskId(), 999L)

		then: "참조를 걸 수 없다."
		thrown ReferenceException
	}

	def "unreferenced"() {
		Task parentTask = scheduler.addTask(user, message)
		Task childTask = scheduler.addTask(user, message)
		scheduler.referenceTask(parentTask.getTaskId(), childTask.getTaskId())

		when: "저장된 할일에 참조를 끊는다."
		scheduler.unreferencedTask(parentTask.getTaskId(), childTask.getTaskId())

		then: "참조가 제거된다"
		!scheduler.getTask(parentTask.getTaskId()).getChildTasks().contains(childTask)
		!scheduler.getTask(childTask.getTaskId()).getParentTasks().contains(parentTask)
	}

	def "empty unreferenced"() {
		Task parentTask = scheduler.addTask(user, message)

		when: "child 조회값이 없는 할일에 참조를 끊는다."
		scheduler.unreferencedTask(parentTask.getTaskId(), 999L)

		then: "참조를 걸 수 없다."
		thrown ReferenceException
	}


	def "complete"() {
		Task task = scheduler.addTask(user, message)

		when: "할 일을 완료 시킨다."
		scheduler.completeTask(task.getTaskId())

		then: "할 일이 완료된다."
		scheduler.getTask(task.getTaskId()).isCompleted()
	}

	def "empty complete"() {
		when: "할 일을 완료 시킨다."
		scheduler.completeTask(999L)

		then: "완료할 수 없다."
		thrown ReferenceException
	}


}
