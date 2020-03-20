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
  import java.sql.Connection;      
  import java.math.BigDecimal;     
    public final class BpReadingDAOFactory {         
     //private static final BpReadingDAO dao = new BpReadingDAOImpl();      
     private BpReadingDAOFactory() {      
     }      
     public final static BpReadingDAO getDAO() {      
             return new BpReadingDAOImpl();       
         }       
     public final static BpReadingDAO getDAO(boolean external) {      
         return (external ? new BpReadingDAOImplXA(): new BpReadingDAOImpl());      
     }                 
      public final static BpReadingDAO getDAO(boolean external, Connection nestedConnection) {      
          BpReadingDAO dao = (external ? new BpReadingDAOImplXA() : new BpReadingDAOImpl());      
          dao.setConnection(nestedConnection);      
          return dao;      
      }      
      public final static BpReadingDAO getDAO(Connection nestedConnection) {      
          return getDAO(false, nestedConnection);      
      }               
    }         
