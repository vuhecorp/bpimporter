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
package com.hecorp.api.dao;
  import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;  
 
  public abstract class AbstractDAImpl implements AbstractDA {  
      protected Connection con = null;          
      protected boolean connectionSetByCaller = false; 
      protected ConnectionProvider factory = null; 
      public abstract void open();      
      public abstract void close();      
      protected abstract Logger getLogger();      
      public void setConnection(Connection c) {      
          connectionSetByCaller = true; 
          con = c;      
      }      
      
       public void setConnectionProvider(ConnectionProvider factory) { 
          this.factory = factory; 
      } 
       /**** used to allow caller to control the # of records returned *****/     
      private int maxRows = 0;     
      private boolean persistMaxRows = false;     
      public void setMaxRows(int r){     
          maxRows = r;     
          persistMaxRows = false;     
      }     
      public void setMaxRows(int r, boolean persistMaxRowCount){     
          maxRows = r;     
          persistMaxRows = persistMaxRowCount;     
      }     
      protected int getMaxRows( ){     
          int r = maxRows;     
          if (!persistMaxRows)     
              maxRows = 0;     
          return r;     
      }     
      /**** ************************************************ *****/     

     /** used to allow caller to set the dynamic connection as "ReadOnly" **/ 
        protected boolean readOnly = false; 
        protected boolean persistReadOnly = false; 
        public boolean isReadOnly() {
          return readOnly; 
        }    
        public void setReadOnly(boolean isReadOnly, boolean persistReadOnlyState) {
          setReadOnly(isReadOnly);
          persistReadOnly = persistReadOnlyState;
        }
        public void setReadOnly(boolean isReadOnly) {
          throw new IllegalStateException("Read Only setting not supported in base implementation.");
          //readOnly = isReadOnly;        
        }
        

      protected void logSQLException(SQLException e) {      
          logSQLException(null, e);      
      }      
      protected void logSQLException(String sql, SQLException e) {      
          StringBuffer buf = new StringBuffer();      
          if (sql != null) {      
              buf.append("SQL:");      
              buf.append(sql);      
              buf.append(System.getProperty("line.separator"));      
          }      
          buf.append("SQLException Info:");      
          buf.append("SQL ErrorCode=");      
          buf.append(e.getErrorCode());      
          buf.append(",SQL Message=");      
          buf.append(e.getMessage());      
          buf.append(",SQL State=");      
          buf.append(e.getSQLState());      
          if (getLogger().isEnabledFor(Level.ERROR)) {      
              getLogger().error(buf.toString(), e);      
          } else {      
              System.err.println(buf.toString());      
              e.printStackTrace(System.err);      
          }      
      }      
      protected boolean isSQLConstraintViolated(SQLException e) {      
          return false;
      }      
      protected String getExceptionMessage(String template, Object[] contents) {      
          MessageFormat formatter = new MessageFormat(template);      
          return formatter.format(contents);      
      }      
      protected boolean toBooleanValue(String s) {      
          return (      
              ("true".equalsIgnoreCase(s))      
                  || ("on".equalsIgnoreCase(s))      
                  || ("y".equalsIgnoreCase(s))      
                  || ("yes".equalsIgnoreCase(s))      
                  || ("1".equals(s)));      
      }      
  


  	/**       
  	 * This function allows any DAO Impl to easily process BLOB data by simply      
  	 * passing in a DAOStreamReader, the resultset, and the column position of      
  	 * the blob/clob data. If unexpected error occurs during processing, such      
  	 * as an IOException, then DAOException is thrown.      
  	 * @param DAOStreamReader reader - concrete class that processes the inputStream      
  	 *  or java.io.Reader depending on datatype (binary or character).       
  	 * @param ResultSet rs - an opened resultSet       
  	 * @param int blobColPosition - column position in the result set      
  	 * @throws SQLException      
  	 * @throws Exception      
  	 */      
  	protected void processBlobData(      
  			DAOStreamReader reader,       
  			ResultSet rs,      
  			int blobColPosition)       
  	throws SQLException, Exception {      
  		      
  		
  	}      
  	      
  	protected int paginationStartIndex = -1;      
  	protected int paginationPageSize = -1;      
  	public void setPagination(int startIndex, int pageSize) {      
  		paginationStartIndex = startIndex;      
  		paginationPageSize = pageSize;      
  	}      
  } 
