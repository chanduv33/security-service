package com.storesmanagementsystem.springsecuritymicro.exceptions;

@SuppressWarnings("serial")
public class InvalidDetailsException extends RuntimeException {
	String message;
	public InvalidDetailsException() {
		this.message = "Invalid Credentials";
	}
	@Override
	public String getMessage() {
		return this.message;
	}
}
