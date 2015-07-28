package com.netease.exception;

public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ControllerException() {

	}

	public ControllerException(String msg) {
		super(msg);
	}
	
	public ControllerException(Throwable t) {
		super(t);
	}
	
	public ControllerException(String msg, Throwable t) {
		super(msg, t);
	}
}
