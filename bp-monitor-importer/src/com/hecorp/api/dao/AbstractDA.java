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
  public interface AbstractDA {  
      public void close();  
      public void open();  
     public void setConnection(Connection con); 
     public void setConnectionProvider(ConnectionProvider factory);  
     public void setMaxRows(int r); 
     public void setMaxRows(int r, boolean persistMaxRowCount); 
      public void setReadOnly(boolean isReadOnly); 
      public void setReadOnly(boolean isReadOnly, boolean persistReadOnly); 
      public boolean isReadOnly();
      public void setPagination(int startIndex, int pageSize);
  }  

