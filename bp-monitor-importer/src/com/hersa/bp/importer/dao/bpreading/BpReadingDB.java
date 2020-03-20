package com.hersa.bp.importer.dao.bpreading;
public final class BpReadingDB   {
  public static final String ORDERBY_DATE = "DATE";    
 
  public static final String ORDERBY_DESCRIPTION = "DESCRIPTION";    
 
  public static final String ORDERBY_DIASTOLIC = "DIASTOLIC";    
 
  public static final String ORDERBY_ID = "ID";    
 
  public static final String ORDERBY_PULSE = "PULSE";    
 
  public static final String ORDERBY_SYSTOLIC = "SYSTOLIC";    
 
  public static final String ORDERBY_TAGS = "TAGS";    
 
  public static final String ORDERBY_WEIGHT = "WEIGHT";    
 
      
  private static final int DESC = 0;    
  private static final int ASC  = 1;    
  private String orderBys = ""; 
      
  /** Allows the user to easily construct a list of ORDER BY columns   
   *  Example:   
   *      BpReadingDB helper = new BpReadingDB();   
   *      String order = helper.add(ORDERBY_DISPLAYNAME).add(ORDERBY_DISPLAYORDER).toString();   
   * **/   
    public  BpReadingDB add(String column, int type) {   
        if (orderBys.length() > 0) {   
            orderBys += ",";   
        }   
        orderBys += column;   
        if (type == DESC) {   
            orderBys += " DESC";   
        }   
        return this;             
    }   
    public  BpReadingDB add(String column) {   
        return add(column, ASC);             
    }   
    public String toString() {   
        return orderBys;   
    }   
      
} 
