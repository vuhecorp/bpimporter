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
  import com.hecorp.api.dao.AbstractDA;
  import com.hecorp.api.dao.DAOStreamReader;
  import java.sql.Connection;      
  import java.io.InputStream;     
  import java.io.OutputStream;     
  import java.math.BigDecimal;      
  public interface BpReadingDAO extends AbstractDA {

     public void open();
     public void close();
     public void setConnection(Connection con); 
     public BpReadingDTO createBpReading( BpReadingDTO obj )throws BpReadingCreateException ; 
     public void updateBpReading( BpReadingDTO obj ) throws BpReadingUpdateException, BpReadingFinderException ;
     public void deleteBpReading( BpReadingDTO obj ) throws BpReadingDeleteException, BpReadingFinderException; 
     public void deleteBpReadingWhere(String whereClause, Object[] whereParams, int[] paramTypes) throws BpReadingDeleteException, BpReadingFinderException; 
     public BpReadingDTO[] listAllBpReading();
     public BpReadingDTO[] listAllBpReading(String orderByColumn);
     public BpReadingDTO [] listBpReading(String whereClause, Object[] whereParams, int[] paramTypes, String orderClause); 
     public BpReadingDTO [] customBpReading(String completeSQL); 
     public int countBpReading(String whereClause, Object[] whereParams, int[] paramTypes); 
     public BpReadingDTO [] searchPageableBpReading(String whereClause, Object[] whereParams, int[] paramTypes, String orderClause, int startIndex, int pageSize); 
public BpReadingDTO [] listBpReadingByPK(long _Id); 
public BpReadingDTO [] listBpReadingByPK(long _Id, String orderByColumn); 
public int countBpReadingByPK(long _Id); 




public BpReadingDTO [] listBpReadingByDate(java.util.Date _Date); 
public BpReadingDTO [] listBpReadingByDate(java.util.Date _Date, String orderByColumn); 
public int countBpReadingByDate(java.util.Date _Date); 

  public void deleteBpReadingByDate(java.util.Date _Date) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingByDescription(String _Description); 
public BpReadingDTO [] listBpReadingByDescription(String _Description, String orderByColumn); 
public int countBpReadingByDescription(String _Description); 

  public void deleteBpReadingByDescription(String _Description) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingByDiastolic(int _Diastolic); 
public BpReadingDTO [] listBpReadingByDiastolic(int _Diastolic, String orderByColumn); 
public int countBpReadingByDiastolic(int _Diastolic); 

  public void deleteBpReadingByDiastolic(int _Diastolic) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingById(long _Id); 
public BpReadingDTO [] listBpReadingById(long _Id, String orderByColumn); 
public int countBpReadingById(long _Id); 

  public void deleteBpReadingById(long _Id) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingByPulse(int _Pulse); 
public BpReadingDTO [] listBpReadingByPulse(int _Pulse, String orderByColumn); 
public int countBpReadingByPulse(int _Pulse); 

  public void deleteBpReadingByPulse(int _Pulse) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingBySystolic(int _Systolic); 
public BpReadingDTO [] listBpReadingBySystolic(int _Systolic, String orderByColumn); 
public int countBpReadingBySystolic(int _Systolic); 

  public void deleteBpReadingBySystolic(int _Systolic) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingByTags(String _Tags); 
public BpReadingDTO [] listBpReadingByTags(String _Tags, String orderByColumn); 
public int countBpReadingByTags(String _Tags); 

  public void deleteBpReadingByTags(String _Tags) throws BpReadingFinderException, BpReadingDeleteException; 




public BpReadingDTO [] listBpReadingByWeight(BigDecimal _Weight); 
public BpReadingDTO [] listBpReadingByWeight(BigDecimal _Weight, String orderByColumn); 
public int countBpReadingByWeight(BigDecimal _Weight); 

  public void deleteBpReadingByWeight(BigDecimal _Weight) throws BpReadingFinderException, BpReadingDeleteException; 






 /*************** end *****************/
}
