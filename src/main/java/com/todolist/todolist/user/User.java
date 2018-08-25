package com.todolist.todolist.user;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Random;

@ToString
@EqualsAndHashCode(of = "userId")
public class User {
	private Long userId;

	public User() {
		userId = (long)(new Random().nextInt(100));
	}
}
