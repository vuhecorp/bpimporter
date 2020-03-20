/*  
 * ===========================================================================  
 *                     Proprietary Rights Notice  
 * ===========================================================================  
 * All rights reserved.  This document contains valuable and proprietary  
 * properties of EZ Access.  This document embodies substantial  
 * creative works and confidential information, ideas and expressions, no  
 * part of which may be reproduced or transmitted IN ANY FORM OR BY ANY MEANS,  
 * electronic, mechanical or otherwise, including but not limited to photo-  
 * copying and recording or in connection with any information storage or  
 * retrieval system without the express written permission of Easy Access Inc.  
 * ===========================================================================  
 */  
/*  
 *  
 *  
 */ 
package com.hecorp.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.hersa.app.db.ConnectionProvider;
 
public class ConnectionUtil {
	
	@SuppressWarnings("unused")
    private static final String LOOKUP_CONNECTION_TEST_SQL = "java:comp/env/connectionTestSQL";
    @SuppressWarnings("unused")
	private static final String LOOKUP_CONNECTION_RETRY_COUNT = "java:comp/env/connectionRetryCount";
    @SuppressWarnings("unused")
    private static final String LOOKUP_CONNECTION_RETRY_DELAY = "java:comp/env/connectionRetryDelay";
    
    private final static Logger logger = Logger.getLogger(ConnectionUtil.class);

    private static int MAX_RETRY_COUNT = -1;
    private static int MAX_RETRY_DELAY = -1;
    private static String CONNECTION_TEST_SQL= null;
    
    
    public ConnectionUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static Connection openConnection(boolean external) {
        String ds = getDefaultJndiDataSourceName(external);
        return openConnection(ds);
    }
    
    public static Connection openConnection(String jndiDataSourceName) {
        Connection con = null;
        int retries = 0;
        int MAX_RETRY = getConnectionRetryCount();
        int delay = getConnectionRetryDelay();
        
        Exception ex = null;
        while (con == null && retries <= MAX_RETRY ) {
            if (retries > 0) {
                logger.debug("Connection Retry Attempt: #" + retries);
                if (delay > 0) { 
                    logger.debug("Delaying for " + delay + " seconds...");
                    try {
                        Thread.sleep(delay*1000);
                    } catch (InterruptedException e1) {
                    }
                }
            }
            ex = null;
            try {
                con = openConnection_Internal(jndiDataSourceName);
                con = testConnection(con);
            } catch (Exception e) {
                con = null;
                ex = e; 
            }
            retries++;
        }
        if (ex != null) {
            try {
				throw new DAOException("Could not acquire a connection after " + (retries-1) + " attempts.", ex);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return con;   
    }
    
    public static void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    /*** Internal functions below *****/
    
    @SuppressWarnings("unused")
	private static Connection openConnection_Internal(boolean external) {
        String ds = getDefaultJndiDataSourceName(external);
        return openConnection_Internal(ds);        
    }
    
    
    private static Connection openConnection_Internal(String jndiDataSource) {
    	Connection connection = null;
    	try {
        	connection = ConnectionProvider.getConnection(jndiDataSource);
        } catch (Exception e) {
            if (logger.isEnabledFor(Level.ERROR)) {
                logger.error(e);
            }
        }
    	return connection;
    }

    /**
     * @param external
     * @return
     */
    private static String getDefaultJndiDataSourceName(boolean external) {
        String ds = JNDI.NON_XA_DATASOURCE;
        if (external) {
            ds = JNDI.XA_DATASOURCE;
        }
        return ds;
    }
 

    
    /** Functions to retrieve System settings from ServiceLocator **/
    /**
     * 
     */
    private static int getConnectionRetryCount() {
        if (MAX_RETRY_COUNT == -1) {
            try {
                Integer retryCount = 3;
                MAX_RETRY_COUNT = retryCount.intValue();
                if (MAX_RETRY_COUNT < 0) {
                    logger.error("Invalid value for connectionRetryCount (" + MAX_RETRY_COUNT + "). Defaulting to 0.");                    
                    MAX_RETRY_COUNT = 0;
                }
            } catch (Exception e) {
                logger.error("Could not retrieve connectionRetryCount. Using default value of 3.");
                MAX_RETRY_COUNT = 3;
            }
        }
        return MAX_RETRY_COUNT;
    }
    private static int getConnectionRetryDelay() {
        if (MAX_RETRY_DELAY == -1) {
            try {
                Integer retryCount = 3;
                MAX_RETRY_DELAY = retryCount.intValue();
            } catch (Exception e) {
                logger.error("Could not retrieve connectionRetryDelay. Using default value of 3.");
                MAX_RETRY_DELAY = 3;
            }
        }
        return MAX_RETRY_DELAY;
    }
    private static String getConnectionTestSQL() {
        if (CONNECTION_TEST_SQL == null) {
            try {
                String sql = "";
                CONNECTION_TEST_SQL = sql;
            } catch (Exception e) {
                logger.error("Could not retrieve sqlConnectionTestQuery. Using default.");
                CONNECTION_TEST_SQL = "SELECT 'PING' AS connectionPingSQL";
            }
        }
        return CONNECTION_TEST_SQL;
    }
    
    public static void main(String[] args) {
    }
    
//    public  static Connection openConnection(boolean external) {
//        try {
//            if (external) {
//                return ServiceLocator
//                    .getInstance()
//                    .getDataSource(JNDI.XA_DATASOURCE)
//                    .getConnection();
//            } else {
//                return ServiceLocator
//                    .getInstance()
//                    .getDataSource(JNDI.NON_XA_DATASOURCE)
//                    .getConnection();
//            }
//        } catch (Exception e) {
//            if (logger.isEnabledFor(Level.ERROR)) {
//                logger.error(e);
//            }
//            throw new DAOException(e);
//        }
//    }
    


    @SuppressWarnings("unused")
	private static Connection testConnection(Connection con) throws Exception{  
      
    //        if ((Math.random() * 100) >= 30 ) {  
    //            throw new Exception("Forced Random Connection Failure.");  
    //        }  
             
    String sql = getConnectionTestSQL();  
       if (StringUtils.isNotBlank(sql)) {  
           PreparedStatement stmt = con.prepareStatement(sql);  
           ResultSet rs = stmt.executeQuery();  
       }  
         
       return con;  
    }  
}
