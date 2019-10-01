package com.hit.exception;

public class UnknownIdException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownIdException(String msg,Throwable err) {
		super(msg,err);
	}
	public UnknownIdException(String msg) {
		super(msg);
	}
	public UnknownIdException() {
		System.out.println("Error- trying access a game that does not exist");
	}

}
