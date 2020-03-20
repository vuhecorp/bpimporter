package com.hecorp.api.dao;

import java.sql.SQLException;

public class DAOException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 680850421589749085L;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, SQLException e) {
		// TODO Auto-generated constructor stub
	}

	public DAOException(SQLException e) {
		// TODO Auto-generated constructor stub
	}

	public DAOException(String message, Exception e) {
		// TODO Auto-generated constructor stub
	}

	public DAOException(Exception e) {
		// TODO Auto-generated constructor stub
	}
}
