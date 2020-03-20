package com.hecorp.api.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import com.hersa.bp.importer.dao.bpreading.BpReadingDAO;
import com.hersa.bp.importer.dao.bpreading.BpReadingDAOFactory;



public class AbstractBaseManager {

	public void markCreated(BOM bom, String userName) {
		markCreated(bom, userName, new Date());
	}
	
	public void markCreated(BOM bom , String userName, Date date) {
		bom.setCreatedBy(userName);
		bom.setCreatedOn(date);
	    markUpdated(bom, userName, date);
	}
	
	public void markUpdated(BOM bom , String userName) {
		markUpdated(bom, userName, new Date());
	}
	
	public void markUpdated(BOM bom , String userName, Date date) {
		bom.setModifiedBy(userName);
		bom.setModifiedOn(date);
	}
	
	public void close(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConnectionProvider getDefautlConnectionProvider() {
		ConnectionProvider factory = new DefaultConnectionProvider(JNDI.EBILLING);
		return factory;
	}
	
	public BpReadingDAO getBloodPressureDAO() {
		BpReadingDAO dao = BpReadingDAOFactory.getDAO();
		dao.setConnectionProvider(this.getDefautlConnectionProvider());
		return dao;
	}

	public BpReadingDAO getBloodPressureDAO(Connection connection) {
		BpReadingDAO dao = BpReadingDAOFactory.getDAO(connection);
		return dao;
	}
	 

}
