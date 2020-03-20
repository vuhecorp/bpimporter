package com.hersa.bp.importer;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.hecorp.api.dao.ApplicationException;
import com.hersa.bp.importer.bom.bpreading.BloodPressure;
import com.hersa.bp.importer.bom.bpreading.BloodPressureManager;

public class BloodPressureImporter {
	private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    
	public static void main(String[] args) {
		
		 	String csvFile = "C:\\Users\\v\\Documents\\Blood_Pressure-Victor-8_17_19.csv";
	        Scanner scanner;
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
	        System.out.println("Importing...");
	        
	        try {
	        	
	        	Connection connection = getConnection();  
	        	
	        	//purge bp readings table. 
	        	PreparedStatement ps = connection.prepareStatement("DELETE FROM BP_READING");
	        	ps.execute();
	        	
				scanner = new Scanner(new File(csvFile));
				BloodPressureManager bpm = new BloodPressureManager();
				
				int count = 0;
				while (scanner.hasNext()) {
		            
					List<String> line = parseLine(scanner.nextLine());
		            BloodPressure bp  = new BloodPressure();
		            
		            Date date     = sdf.parse(line.get(0));
		            int systolic  = new Integer(line.get(1));
		            int diastolic = new Integer(line.get(2));
		            int pulse     = new Integer(line.get(3));
		            String desc   = line.get(4);
		            String tags   = line.get(5);
		            BigDecimal weight    = new BigDecimal(line.get(6));
		            
		            bp.setDate(date);
		            bp.setSystolic(systolic);
		            bp.setDiastolic(diastolic);
		            bp.setPulse(pulse);
		            bp.setDescription(desc);
		            bp.setTags(tags);
		            bp.setWeight(weight);
		            
		            bpm.create(bp, connection);
		            
		            count ++;
				}
				
		        scanner.close();
		        System.out.println("Finished importing " + count + " records.");
		        
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	        
	}
	
	   public static List<String> parseLine(String cvsLine) {
	        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	    }

	    public static List<String> parseLine(String cvsLine, char separators) {
	        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	    }

	    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

	        List<String> result = new ArrayList<>();

	        //if empty, return!
	        if (cvsLine.isEmpty()) {
	            return result;
	        }

	        if (customQuote == ' ') {
	            customQuote = DEFAULT_QUOTE;
	        }

	        if (separators == ' ') {
	            separators = DEFAULT_SEPARATOR;
	        }

	        StringBuffer curVal = new StringBuffer();
	        boolean inQuotes = false;
	        boolean startCollectChar = false;
	        boolean doubleQuotesInColumn = false;

	        char[] chars = cvsLine.toCharArray();

	        for (char ch : chars) {

	            if (inQuotes) {
	                startCollectChar = true;
	                if (ch == customQuote) {
	                    inQuotes = false;
	                    doubleQuotesInColumn = false;
	                } else {

	                    //Fixed : allow "" in custom quote enclosed
	                    if (ch == '\"') {
	                        if (!doubleQuotesInColumn) {
	                            curVal.append(ch);
	                            doubleQuotesInColumn = true;
	                        }
	                    } else {
	                        curVal.append(ch);
	                    }

	                }
	            } else {
	                if (ch == customQuote) {

	                    inQuotes = true;

	                    //Fixed : allow "" in empty quote enclosed
	                    if (chars[0] != '"' && customQuote == '\"') {
	                        curVal.append('"');
	                    }

	                    //double quotes in column will hit this!
	                    if (startCollectChar) {
	                        curVal.append('"');
	                    }

	                } else if (ch == separators) {

	                    result.add(curVal.toString());

	                    curVal = new StringBuffer();
	                    startCollectChar = false;

	                } else if (ch == '\r') {
	                    //ignore LF characters
	                    continue;
	                } else if (ch == '\n') {
	                    //the end, break!
	                    break;
	                } else {
	                    curVal.append(ch);
	                }
	            }

	        }

	        result.add(curVal.toString());

	        return result;
	    }
	    
	    private static Connection getConnection() {
	    	
	    	 String url;
	    	 String user;
	    	 String password;
	    	 Connection con = null;
	    	 
	    	 url = "jdbc:db2://192.168.1.9:50000/SAMPLE:currentSchema=DB2INST1;retrieveMessagesFromServerOnGetMessage=true;";
	    	 user = "";
	    	 password = "";
	    	 try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				
				con = DriverManager.getConnection (url, user, password);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	 
	    	return con;
	    }
}
