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
  import java.sql.SQLException;    
  /**    
   * 
   * Description: Interface for the DAO to call for allowing the consumer of    
   *  the DAO's to provide their own factory for retrieving and managing     
   *  connections.     
   */    
  public interface ConnectionProvider {    

      public Connection openConnection() throws Exception;    
      public void closeConnection(Connection con) throws SQLException;    

  }    
     
