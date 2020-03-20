package com.hecorp.api.dao;

public class ApplicationException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7375891733647660966L;

	public ApplicationException() {
		super();
	}
	
	public ApplicationException(String message) {
		super(message);
	}
}
