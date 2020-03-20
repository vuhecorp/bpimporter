package com.hersa.app.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionProvider {
	
	public static Connection getConnection(String jndi) {
		Context initCtx;
		Connection conn = null;
		try {
			initCtx = new InitialContext();
			/* Context envCtx = (Context) initCtx.lookup(jndi); */
			DataSource ds = (DataSource) initCtx.lookup(jndi);
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
