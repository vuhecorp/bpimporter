package com.hecorp.api.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {

	private static ServiceLocator instance = null;
	
	protected ServiceLocator() {
		
	}

	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}
	public static DataSource getDataSource(String jndi) {
		Context initCtx;
		Connection conn = null;
		DataSource ds = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			ds = (DataSource) envCtx.lookup(jndi);
			//conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return ds;
	}
	private static String getService(String service) {
		Context initCtx;
		Connection conn = null;
		try {
			initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup(service);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
}
