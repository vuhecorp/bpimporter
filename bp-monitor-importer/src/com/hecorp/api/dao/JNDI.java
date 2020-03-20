  package com.hecorp.api.dao;
  public final class JNDI {   
      public static final String XA_DATASOURCE = "java:comp/env/jdbc/XADataSource";
      public static final String NON_XA_DATASOURCE = "java:comp/env/jdbc/NonXADataSource";
      public static final String EBILLING = "java:comp/env/jdbc/EBILLING";
      public static final String VICTORDB = "java:comp/env/jdbc/VICTOR";
      private JNDI() {
      }
  } 
