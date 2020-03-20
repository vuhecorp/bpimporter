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
      
    
public class DefaultConnectionProvider implements ConnectionProvider {    
    
  String jndiName = null;    
  public DefaultConnectionProvider(String jndi) {    
      jndiName = jndi;    
  }    
  public void closeConnection(Connection con) throws SQLException {    
      con.close();    
  }    
  public Connection openConnection() throws Exception {    
      return ConnectionUtil.openConnection(jndiName);    
  }    
  public void setJNDIName(String jndi) {    
      jndiName = jndi;    
  }    
}    
