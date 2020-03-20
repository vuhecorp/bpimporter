/*  
 * ===========================================================================  
 *                     Proprietary Rights Notice  
 * ===========================================================================  
 * All rights reserved.  This document contains valuable and proprietary  
 * properties of Hersa Corp.  This document embodies substantial  
 * creative works and confidential information, ideas and expressions, no  
 * part of which may be reproduced or transmitted IN ANY FORM OR BY ANY MEANS,  
 * electronic, mechanical or otherwise, including but not limited to photo-  
 * copying and recording or in connection with any information storage or  
 * retrieval system without the express written permission of Easy Access Inc.  
 * ===========================================================================  
 */  
package com.hersa.bp.importer.dao.bpreading;
import com.hecorp.api.dao.AbstractDAImpl;
import com.hecorp.api.dao.DAOStreamReader;
import com.hecorp.api.dao.ConnectionUtil;      
import com.hecorp.api.dao.DefaultConnectionProvider;      
import java.sql.Connection; 
import java.sql.PreparedStatement; 
import java.sql.Statement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.List; 
import java.math.BigDecimal;      
import java.io.IOException; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.BufferedInputStream; 
import org.apache.log4j.Logger; 
import org.apache.log4j.Level;
import com.hecorp.api.dao.DAOException;
import com.hecorp.api.dao.ServiceLocator;
class BpReadingDAOImpl extends AbstractDAImpl implements BpReadingDAO {

  private static final Logger logger = 
      Logger.getLogger(BpReadingDAOImpl.class);

  protected String tableName = BpReadingPrefix.PREFIX + "BP_READING"; 
  public String getTable(){
      return "\"" + this.tableName + "\""; 
}
  public void setTable(String table){
      this.tableName = table; 
}
  protected String getSelectSQL(){
      String sql = " SELECT DATE, DESCRIPTION, DIASTOLIC, ID, PULSE, SYSTOLIC, TAGS, WEIGHT FROM " + getTable() + " " ;
      return sql;
}
  protected String getSelectCountSQL(){
      String sql = " SELECT COUNT(*) FROM " + getTable();
      return sql;
}
  protected String getInsertSQL(){
      String sql = " INSERT INTO " + getTable() + " ( DATE, DESCRIPTION, DIASTOLIC, PULSE, SYSTOLIC, TAGS, WEIGHT ) VALUES  ( ?, ?, ?, ?, ?, ?, ? )";
      return sql;
}
  protected String getUpdateSQL(){
      String sql = " UPDATE " + getTable() + " SET  DATE = ? , DESCRIPTION = ? , DIASTOLIC = ? , PULSE = ? , SYSTOLIC = ? , TAGS = ? , WEIGHT = ?  WHERE ID = ? ";
      return sql;
}
  protected String getUpdateBlobsSQL(){
      String sql = " UPDATE  " + getTable() + "  SET  DATE = ? , DESCRIPTION = ? , DIASTOLIC = ? , PULSE = ? , SYSTOLIC = ? , TAGS = ? , WEIGHT = ?  WHERE ID = ? ";
      return sql;
}
  protected String getDeleteSQL(){
      String sql = " DELETE FROM  " + getTable() + "  WHERE ID = ?  " ;
      return sql;
}
  protected String getDeleteBySQL(){
      String sql = " DELETE FROM " + getTable() ;
      return sql;
}

  public BpReadingDAOImpl() { 
       factory = new DefaultConnectionProvider( BpReadingJNDI.NON_XA_DATASOURCE ); 
  } 
    protected Connection openConnection() {  
     if (connectionSetByCaller) return this.con;    
     try {   
         Connection connection = factory.openConnection();   
         if (isReadOnly()) {     
             try {   
                 connection.setReadOnly(isReadOnly());   
             } catch (SQLException e) {      
                 e.printStackTrace();    
             }   
         }   
         return connection;  
       } catch (Exception e) {   
         if (logger.isEnabledFor(Level.ERROR)) {   
             logger.error(e);   
         }   
        try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }   
     }   
         return con;  
   }    
     
     
 protected void closeConnection(Connection con) {   
     if (!this.persistReadOnly) {            
         this.readOnly = false;          
     }          
     if (connectionSetByCaller) { 
        return ;    
     }    
      try {   
         factory.closeConnection(con);   
     } catch (SQLException e) {   
         logSQLException(e);   
     }   
 }   
     @Override   
     public void setReadOnly(boolean readOnly) {   
         this.readOnly = readOnly;   
     }   

    public void open() {;}  
    public void close() {;}  
    protected Logger getLogger() {  
      return logger; 
    } 

  public final BpReadingDTO [] listAllBpReading(String orderByColumn) { 
    String order = "";    
     if (orderByColumn != null && orderByColumn.trim().length() > 0) {    
        order = " ORDER BY " + orderByColumn;     
     }    
    return listBpReading(null, null, null, order); 
 } 

public final BpReadingDTO [] listAllBpReading() { 
    return listBpReading(null, null, null, null); 
 } 

public final BpReadingDTO [] customBpReading(String completeSQL ) { 
   if (logger.isEnabledFor(Level.TRACE)) {     
       logger.trace(">> listBpReading()");  
   }   
   BpReadingDTO[] _BpReading = null;  
   ResultSet rs = null;    
   PreparedStatement stmt = null;  
   Connection con = openConnection();  
   try {   
           stmt = con.prepareStatement(completeSQL);    
           stmt.setMaxRows(getMaxRows());            rs = stmt.executeQuery();       
           if (rs.next()) {        
               List list = new ArrayList();        
               do {        
                   list.add(extractBpReading(rs));        
               } while (rs.next());        
               _BpReading =      
                   (BpReadingDTO[]) list.toArray(new BpReadingDTO[list.size()]);       
           } else {        
               _BpReading = new BpReadingDTO[0];        
           }       
   } catch (SQLException e) {      
           logSQLException(e);     
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }      
   } finally {     
           if (rs != null) {       
               try {       
                   rs.close();     
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           if (stmt != null) {     
               try {       
                   stmt.close();       
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           closeConnection(con);       
   }       
   if (logger.isEnabledFor(Level.TRACE)) {     
           logger.trace("<< findBpReading()");     
   }       
   return _BpReading;        
 }  

public final BpReadingDTO [] listBpReading(String whereClause, Object[] whereParams, int[] paramTypes, String orderClause ) { 
   if (logger.isEnabledFor(Level.TRACE)) {     
       logger.trace(">> listBpReading()");  
   }   
   if (paginationStartIndex > 0) {    
      return searchPageableBpReading(whereClause, whereParams, paramTypes, orderClause, paginationStartIndex, paginationPageSize);  
   }  
   BpReadingDTO[] _BpReading = null;  
   ResultSet rs = null;    
   PreparedStatement stmt = null;  
   Connection con = openConnection();  
   if (whereClause == null) {      
       whereClause = "";     
   }           
   if (orderClause == null) {      
       orderClause = "";     
   }                   
   try {   
           stmt = con.prepareStatement(getSelectSQL() + whereClause + orderClause);    
           stmt.setMaxRows(getMaxRows());            if (whereParams != null) {      
               for (int i = 0; i < whereParams.length; i++) {      
                   if (whereParams[i] == null) { 
                       stmt.setNull(i+1, paramTypes[i]);   
                   } else { 
                       stmt.setObject(i+1, whereParams[i], paramTypes[i]);     
                   }  
               }   
           }               
           rs = stmt.executeQuery();       
           if (rs.next()) {        
               List list = new ArrayList();        
               do {        
                   list.add(extractBpReading(rs));        
               } while (rs.next());        
               _BpReading =      
                   (BpReadingDTO[]) list.toArray(new BpReadingDTO[list.size()]);       
           } else {        
               _BpReading = new BpReadingDTO[0];        
           }       
   } catch (SQLException e) {      
           logSQLException(e);     
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }      
   } finally {     
           if (rs != null) {       
               try {       
                   rs.close();     
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           if (stmt != null) {     
               try {       
                   stmt.close();       
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           closeConnection(con);       
   }       
   if (logger.isEnabledFor(Level.TRACE)) {     
           logger.trace("<< findBpReading()");     
   }       
   return _BpReading;        
 }  

public final BpReadingDTO [] searchPageableBpReading(String whereClause, Object[] whereParams, int[] paramTypes, String orderClause,int startIndex, int pageSize ) { 
   if (logger.isEnabledFor(Level.TRACE)) {     
       logger.trace(">> searchPageableBpReading()");  
   }   
   BpReadingDTO[] _BpReading = null;  
   ResultSet rs = null;    
   PreparedStatement stmt = null;  
   Connection con = openConnection();  
   if (whereClause == null) {      
       whereClause = "";     
   }           
   if (orderClause == null) {      
       orderClause = "";     
   }                   
   try {   
           int maxRowsToFetch = startIndex + pageSize-1;     
           int len = pageSize;		     
           stmt = con.prepareStatement(     
           		getSelectSQL() + whereClause + orderClause,     
           		ResultSet.TYPE_SCROLL_INSENSITIVE,     
           		ResultSet.CONCUR_READ_ONLY);		     
           stmt.setMaxRows(maxRowsToFetch);     
           stmt.setFetchSize(pageSize);     
           if (whereParams != null) {      
               for (int i = 0; i < whereParams.length; i++) {      
                   if (whereParams[i] == null) { 
                       stmt.setNull(i+1, paramTypes[i]);   
                   } else { 
                       stmt.setObject(i+1, whereParams[i], paramTypes[i]);     
                   }  
               }   
           }               
           rs = stmt.executeQuery();       
           if (rs.absolute(startIndex)) {        
               List list = new ArrayList();        
               do {        
                   list.add(extractBpReading(rs));        
               } while ((rs.next()) && (--len > 0));      
               _BpReading =      
                   (BpReadingDTO[]) list.toArray(new BpReadingDTO[list.size()]);       
           } else {        
               _BpReading = new BpReadingDTO[0];        
           }       
   } catch (SQLException e) {      
           logSQLException(e);     
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }      
   } finally {     
           if (rs != null) {       
               try {       
                   rs.close();     
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           if (stmt != null) {     
               try {       
                   stmt.close();       
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           closeConnection(con);       
   }       
   if (logger.isEnabledFor(Level.TRACE)) {     
           logger.trace("<< searchPageableBpReading()");     
   }       
   return _BpReading;        
 }  

public final int countBpReading(String whereClause, Object[] whereParams, int[] paramTypes ) { 
   if (logger.isEnabledFor(Level.TRACE)) {     
       logger.trace(">> countBpReading()");  
   }   
   int count = 0;    
   ResultSet rs = null;    
   PreparedStatement stmt = null;  
   Connection con = openConnection();  
   if (whereClause == null) {      
       whereClause = "";     
   }           
   try {   
           stmt = con.prepareStatement(getSelectCountSQL() + whereClause );    
           if (whereParams != null) {      
               for (int i = 0; i < whereParams.length; i++) {      
                   if (whereParams[i] == null) { 
                       stmt.setNull(i+1, paramTypes[i]);   
                   } else { 
                       stmt.setObject(i+1, whereParams[i], paramTypes[i]);     
                   }  
               }   
           }               
           rs = stmt.executeQuery();       
           if (rs.next()) {        
               List list = new ArrayList();        
               do {        
                   count = rs.getInt(1);   
               } while (rs.next());        
           }       
   } catch (SQLException e) {      
           logSQLException(e);     
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }      
   } finally {     
           if (rs != null) {       
               try {       
                   rs.close();     
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           if (stmt != null) {     
               try {       
                   stmt.close();       
               } catch (SQLException e) {      
                   logSQLException(e);     
               }       
           }       
           closeConnection(con);       
   }       
   if (logger.isEnabledFor(Level.TRACE)) {     
           logger.trace("<< findBpReading()");     
   }       
   return count;     
 }  

  public final BpReadingDTO [] listBpReadingByPK(long _Id) { 
     return listBpReadingByPK( _Id, null);     
 } 

  public final BpReadingDTO [] listBpReadingByPK(long _Id, String orderByColumn) { 
    String whereClause = " WHERE ID = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Long(_Id);   
    types[0] = java.sql.Types.BIGINT;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByPK(long _Id) { 
    String whereClause = " WHERE ID = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Long(_Id);   
    types[0] = java.sql.Types.BIGINT;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByDate(java.util.Date _Date) { 
     return listBpReadingByDate( _Date, null);     
 } 

  public final BpReadingDTO [] listBpReadingByDate(java.util.Date _Date, String orderByColumn) { 
    String whereClause = " WHERE DATE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.sql.Timestamp(_Date.getTime());   
    types[0] = java.sql.Types.TIMESTAMP;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByDate(java.util.Date _Date) { 
    String whereClause = " WHERE DATE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.sql.Timestamp(_Date.getTime());   
    types[0] = java.sql.Types.TIMESTAMP;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByDescription(String _Description) { 
     return listBpReadingByDescription( _Description, null);     
 } 

  public final BpReadingDTO [] listBpReadingByDescription(String _Description, String orderByColumn) { 
    String whereClause = " WHERE DESCRIPTION = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Description;   
    types[0] = java.sql.Types.VARCHAR;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByDescription(String _Description) { 
    String whereClause = " WHERE DESCRIPTION = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Description;   
    types[0] = java.sql.Types.VARCHAR;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByDiastolic(int _Diastolic) { 
     return listBpReadingByDiastolic( _Diastolic, null);     
 } 

  public final BpReadingDTO [] listBpReadingByDiastolic(int _Diastolic, String orderByColumn) { 
    String whereClause = " WHERE DIASTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Diastolic);   
    types[0] = java.sql.Types.INTEGER;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByDiastolic(int _Diastolic) { 
    String whereClause = " WHERE DIASTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Diastolic);   
    types[0] = java.sql.Types.INTEGER;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingById(long _Id) { 
     return listBpReadingById( _Id, null);     
 } 

  public final BpReadingDTO [] listBpReadingById(long _Id, String orderByColumn) { 
    String whereClause = " WHERE ID = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Long(_Id);   
    types[0] = java.sql.Types.BIGINT;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingById(long _Id) { 
    String whereClause = " WHERE ID = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Long(_Id);   
    types[0] = java.sql.Types.BIGINT;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByPulse(int _Pulse) { 
     return listBpReadingByPulse( _Pulse, null);     
 } 

  public final BpReadingDTO [] listBpReadingByPulse(int _Pulse, String orderByColumn) { 
    String whereClause = " WHERE PULSE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Pulse);   
    types[0] = java.sql.Types.INTEGER;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByPulse(int _Pulse) { 
    String whereClause = " WHERE PULSE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Pulse);   
    types[0] = java.sql.Types.INTEGER;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingBySystolic(int _Systolic) { 
     return listBpReadingBySystolic( _Systolic, null);     
 } 

  public final BpReadingDTO [] listBpReadingBySystolic(int _Systolic, String orderByColumn) { 
    String whereClause = " WHERE SYSTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Systolic);   
    types[0] = java.sql.Types.INTEGER;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingBySystolic(int _Systolic) { 
    String whereClause = " WHERE SYSTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Systolic);   
    types[0] = java.sql.Types.INTEGER;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByTags(String _Tags) { 
     return listBpReadingByTags( _Tags, null);     
 } 

  public final BpReadingDTO [] listBpReadingByTags(String _Tags, String orderByColumn) { 
    String whereClause = " WHERE TAGS = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Tags;   
    types[0] = java.sql.Types.VARCHAR;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByTags(String _Tags) { 
    String whereClause = " WHERE TAGS = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Tags;   
    types[0] = java.sql.Types.VARCHAR;   
    return countBpReading(whereClause, objs, types); 
 } 




  public final BpReadingDTO [] listBpReadingByWeight(BigDecimal _Weight) { 
     return listBpReadingByWeight( _Weight, null);     
 } 

  public final BpReadingDTO [] listBpReadingByWeight(BigDecimal _Weight, String orderByColumn) { 
    String whereClause = " WHERE WEIGHT = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Weight;   
    types[0] = java.sql.Types.DECIMAL;   
    String order = "";   
    if (orderByColumn != null && orderByColumn.trim().length() > 0) {  
        order = " ORDER BY " + orderByColumn; 
    } 
     return listBpReading(whereClause, objs, types, order); 
 } 



  public final int countBpReadingByWeight(BigDecimal _Weight) { 
    String whereClause = " WHERE WEIGHT = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Weight;   
    types[0] = java.sql.Types.DECIMAL;   
    return countBpReading(whereClause, objs, types); 
 } 









      
      
   
public void retrieveBlobDataByPK(String columnName, long _Id, OutputStream out) throws  Exception {  
}  
   
   
   
public void retrieveBlobDataByPK(String columnName, long _Id, DAOStreamReader reader) throws  Exception {  
   
    if (logger.isEnabledFor(Level.TRACE)) {  
            logger.trace(">> retrieveBlobDataByPK()");  
     }   
        ResultSet rs = null;  
        String sql = "SELECT "  + columnName + " FROM " + getTable()  
                     + "  WHERE ID = ? ";  
        PreparedStatement stmt = null;  
        Connection con = openConnection();  
        try {  
            int i = 1;  
            stmt = con.prepareStatement(sql);  
      stmt.setObject(i++, new java.lang.Long(_Id), java.sql.Types.BIGINT );   

            rs = stmt.executeQuery();  
            if (!rs.next()) {  
                throw new BpReadingFinderException("");  
            }  
            int blobColPosition = 1;
            processBlobData(reader, rs, blobColPosition );  
        } catch (SQLException e) {  
            logSQLException(sql, e);  
            throw new DAOException(e);  
        } finally {  
            if (rs != null) {  
                try {  
                    rs.close();  
                } catch (SQLException e) {  
                    logSQLException(e);  
                }  
            }  
            if (stmt != null) {  
                try {  
                    stmt.close();  
                } catch (SQLException e) {  
                    logSQLException(e);  
                }  
            }  
            closeConnection(con);  
        }  
        if (logger.isEnabledFor(Level.TRACE)) {  
            logger.trace("<< retrieveBlobDataByPK()");  
        }  
    }  
  
  public final BpReadingDTO createBpReading(BpReadingDTO _BpReading)
    throws BpReadingCreateException {  
         if (logger.isEnabledFor(Level.TRACE)) {
             logger.trace(">> createBpReading()");
         }  
             PreparedStatement stmt = null;   
             Connection con = openConnection();
         try { 
                 int i = 1;
                 stmt = con.prepareStatement(getInsertSQL(), Statement.RETURN_GENERATED_KEYS);
                  if (_BpReading.getDate() != null) { 
                     stmt.setTimestamp(i++, new java.sql.Timestamp(_BpReading.getDate().getTime()) ); 
                  } else {
                     stmt.setNull(i++, java.sql.Types.TIMESTAMP); 
                  } 
                  stmt.setString(i++, _BpReading.getDescription()); 
                  stmt.setInt(i++, _BpReading.getDiastolic()); 
                  stmt.setInt(i++, _BpReading.getPulse()); 
                  stmt.setInt(i++, _BpReading.getSystolic()); 
                  stmt.setString(i++, _BpReading.getTags()); 
                  stmt.setBigDecimal(i++, _BpReading.getWeight()); 
                 stmt.executeUpdate();     
                  int generatedId = -1;      
                  ResultSet rs = stmt.getGeneratedKeys();      
                  while (rs.next()) {      
                     generatedId = (int) rs.getLong(1);      
                  }       
                  rs.close();      
                  _BpReading.setId(generatedId);     
         } catch (SQLException e) {     
             logSQLException(e);     
             if (isSQLConstraintViolated(e)) {     
                 throw new BpReadingCreateException(e.getMessage(), e);     
             }     
            try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }     
         } finally {     
             if (stmt != null) {     
                 try {     
                     stmt.close();     
                 } catch (SQLException e) {     
                     logSQLException(e);     
                 }     
             }     
             closeConnection(con);     
         }     
         if (logger.isEnabledFor(Level.TRACE)) {     
             logger.trace("<< createBpReading()");     
         }     
         return _BpReading;     
     }       

  public final void updateBpReading(BpReadingDTO _BpReading) 
    throws BpReadingUpdateException, BpReadingFinderException  {      
    if (logger.isEnabledFor(Level.TRACE)) {         
       logger.trace(">> updateBpReading()");         
    }       
    PreparedStatement stmt = null;      
    Connection con = openConnection();      
       try {        
           int i = 1;       
           stmt = con.prepareStatement(getUpdateSQL());         
             if (_BpReading.getDate() != null) { 
                  stmt.setTimestamp(i++,  new java.sql.Timestamp(_BpReading.getDate().getTime()) ); 
             } else { 
                  stmt.setNull(i++, java.sql.Types.TIMESTAMP); 
             }
                  stmt.setString(i++,  _BpReading.getDescription() ); 
                  stmt.setInt(i++,  _BpReading.getDiastolic() ); 
                  stmt.setInt(i++,  _BpReading.getPulse() ); 
                  stmt.setInt(i++,  _BpReading.getSystolic() ); 
                  stmt.setString(i++,  _BpReading.getTags() ); 
                  stmt.setBigDecimal(i++,  _BpReading.getWeight() ); 
  /*** now set the criteria, PK values ***/ 
             stmt.setLong(i++,  _BpReading.getId() ); 
           int count = stmt.executeUpdate();        
           if (count == 0) {        
               throw new BpReadingFinderException("");       
           }        
       } catch (SQLException e) {       
           logSQLException(e);      
           if (isSQLConstraintViolated(e)) {        
               throw new BpReadingUpdateException("");       
           }        
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }       
       } finally {      
           if (stmt != null) {      
               try {        
                   stmt.close();        
               } catch (SQLException e) {       
                   logSQLException(e);      
               }        
           }        
           closeConnection(con);        
       }        
       if (logger.isEnabledFor(Level.TRACE)) {      
           logger.trace("<< updateBpReading()");         
       }        
   }        

  public final void deleteBpReading(BpReadingDTO _BpReading) 
    throws BpReadingFinderException, BpReadingDeleteException  {      
    if (logger.isEnabledFor(Level.TRACE)) {         
       logger.trace(">> deleteBpReading()");         
    }       
    PreparedStatement stmt = null;      
    Connection con = openConnection();      
       try {        
           int i = 1;       
           stmt = con.prepareStatement(getDeleteSQL());         
             stmt.setLong(i++,  _BpReading.getId() ); 
           int count = stmt.executeUpdate();        
           if (count == 0) {        
               throw new BpReadingFinderException("");       
           }        
       } catch (SQLException e) {       
           logSQLException(e);      
           if (isSQLConstraintViolated(e)) {        
               throw new BpReadingDeleteException("");       
           }        
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }       
       } finally {      
           if (stmt != null) {      
               try {        
                   stmt.close();        
               } catch (SQLException e) {       
                   logSQLException(e);      
               }        
           }        
           closeConnection(con);        
       }        
       if (logger.isEnabledFor(Level.TRACE)) {      
           logger.trace("<< deleteBpReading()");         
       }        
   }        

  public final void deleteBpReadingWhere(String whereClause, Object[] whereParams, int[] paramTypes)   
    throws BpReadingFinderException, BpReadingDeleteException  {      
   if (logger.isEnabledFor(Level.TRACE)) {     
       logger.trace(">> listBpReading()");  
   }   
   BpReadingDTO[] _BpReading = null;  
   ResultSet rs = null;    
   PreparedStatement stmt = null;  
   Connection con = openConnection();  
   if (whereClause == null) {      
       whereClause = "";     
   }           
   try {   
           stmt = con.prepareStatement(getDeleteBySQL() + whereClause);   
           if (whereParams != null) {      
               for (int i = 0; i < whereParams.length; i++) {      
                   if (whereParams[i] == null) { 
                       stmt.setNull(i+1, paramTypes[i]);   
                   } else {
                       stmt.setObject(i+1, whereParams[i], paramTypes[i]);     
                   }  
               }   
           }               
           int count = stmt.executeUpdate();        
           if (count == 0) {        
               throw new BpReadingFinderException("");       
           }        
   } catch (SQLException e) {       
           logSQLException(e);      
           if (isSQLConstraintViolated(e)) {        
               throw new BpReadingDeleteException(e.getMessage(), e);      
           }        
          try { 	throw new DAOException(e.getMessage(), e);  } catch (DAOException e1) { 	e1.printStackTrace(); }       
   } finally {      
           if (stmt != null) {      
               try {        
                   stmt.close();        
               } catch (SQLException e) {       
                   logSQLException(e);      
               }        
           }        
           closeConnection(con);        
   }        
   if (logger.isEnabledFor(Level.TRACE)) {      
       logger.trace("<< deleteBpReading()");         
   }        
   }        

  public final void deleteBpReadingByDate(java.util.Date _Date) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE DATE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.sql.Timestamp(_Date.getTime());   
    types[0] = java.sql.Types.TIMESTAMP;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingByDescription(String _Description) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE DESCRIPTION = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Description;   
    types[0] = java.sql.Types.VARCHAR;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingByDiastolic(int _Diastolic) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE DIASTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Diastolic);   
    types[0] = java.sql.Types.INTEGER;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingById(long _Id) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE ID = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Long(_Id);   
    types[0] = java.sql.Types.BIGINT;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingByPulse(int _Pulse) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE PULSE = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Pulse);   
    types[0] = java.sql.Types.INTEGER;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingBySystolic(int _Systolic) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE SYSTOLIC = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = new java.lang.Integer(_Systolic);   
    types[0] = java.sql.Types.INTEGER;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingByTags(String _Tags) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE TAGS = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Tags;   
    types[0] = java.sql.Types.VARCHAR;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




  public final void deleteBpReadingByWeight(BigDecimal _Weight) throws BpReadingFinderException, BpReadingDeleteException{ 
    String whereClause = " WHERE WEIGHT = ? ";       
    Object[] objs = new Object[1];   
    int[] types = new int[1];   
    objs[0] = _Weight;   
    types[0] = java.sql.Types.DECIMAL;   
    deleteBpReadingWhere(whereClause, objs, types); 
 } 




public final BpReadingDTO extractBpReading(ResultSet rs)       
    throws SQLException {  
         BpReadingDTO obj = new BpReadingDTO();          
         int i = 1;      
         obj.setDate( rs.getTimestamp(i++) );       
         obj.setDescription( rs.getString(i++) );       
         obj.setDiastolic( rs.getInt(i++) );       
         obj.setId( rs.getLong(i++) );       
         obj.setPulse( rs.getInt(i++) );       
         obj.setSystolic( rs.getInt(i++) );       
         obj.setTags( rs.getString(i++) );       
         obj.setWeight( rs.getBigDecimal(i++) );       
 return obj;  } 


 /*************** end *****************/
}
