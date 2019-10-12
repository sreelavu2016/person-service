package com.person.exception;

public class PersonNotFoundException extends RuntimeException {

	private String message;
	public PersonNotFoundException(String message) {
		super(message);
	}
}
