package com.todolist.todolist.schedule

import com.todolist.todolist.user.User
import spock.lang.Shared
import spock.lang.Specification

class SchedulerModifyTest extends Specification {

	@Shared String message = "오늘은 청소를 한다."
	@Shared String modifiedMessage = "오늘은 그냥 논다."
	@Shared User user = new User()
	TaskRepository taskRepository = new TaskRepository()
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

	def "complete"() {
		Task task = scheduler.addTask(user, message)

		when: "할일을 완료 시킨다."
		scheduler.completeTask(task.getTaskId())

		then: "할일이 완료된다."
		scheduler.getTask(task.getTaskId()).isCompleted()
	}
}
