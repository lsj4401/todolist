package com.todolist.todolist.exception;

public class NotCompletedException extends RuntimeException {
	public NotCompletedException(String message) {
		super(message);
	}
}
