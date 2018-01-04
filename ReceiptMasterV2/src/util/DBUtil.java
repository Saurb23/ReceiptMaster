package util;

import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.sun.rowset.CachedRowSetImpl;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class DBUtil {
	//Declare JDBC Driver
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
 
    //Connection
    private static Connection conn = null;
    public static String dbName;
    public static String userName;
    public static String password;
    
    
 
    //Connection String
    //String connStr = "jdbc:oracle:thin:Username/Password@IP:Port/SID";
    //Username=HR, Password=HR, IP=localhost, IP=1521, SID=xe
//    private static final String connStr = "jdbc:mysql:thin:banikri_receipt_master_desktop/@localhost:3306/xe";
 
 
    public static void initDB(String database,String username,String pass) {
    	dbName=database;
    	userName=username;
    	password=pass;
    }
    //Connect to DB
    public static Connection dbConnect() throws SQLException, ClassNotFoundException {
        //Setting Oracle JDBC Driver
//    	initDB(database, username, pass);
    	
    	if(dbName==null && userName==null) {
    		String path="C:/rconnection/connection_file.txt";
    		File file= new File(path);
    		if(file.exists()) {
    			try {
					Scanner scanner= new Scanner(file);
//					String line;
					while(scanner.hasNextLine()) {
//						System.out.println(scanner.);
						dbName=scanner.nextLine();
//						System.out.println(dbName);
			    		userName=scanner.nextLine();
			    		String pass=scanner.nextLine();
			    		if(pass.equals("null")) {
			    			password="";
			    		}else {
			    		password=pass;
			    		}
					}
					scanner.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
//    			catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
    			catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		
    	}
//    		String stmt="select * from dd_dbconn";
//    		Class.forName(JDBC_DRIVER);
//    		while(rs.next()) {
//    			dbName=rs.getString("dbname");
//    			userName=rs.getString("username");
//    			password=rs.getString("password");
//    		}
//    	}
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
 
        System.out.println("Oracle JDBC Driver Registered!");
 
        //Establish the Oracle Connection using Connection String
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,userName,password);
            
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console" + e);
            e.printStackTrace();
            throw e;
        }
        
        return conn;
    }
 
    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
    
  //DB Execute Query Operation
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
//        CachedRowSet crs = null;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");
 
            //Create statement
            stmt = conn.createStatement();
 
            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);
 
            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
//            crs = new CachedRowSet();
//            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            e.printStackTrace();
        } 
       
        //Return CachedRowSet
        return resultSet;
    }
// 
    //DB Execute Update (For Update/Insert/Delete) Operation
    public static int dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        int count =0;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Statement "+sqlStmt+"\n");
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            count=stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        return count;
    }

    
    public static int[] executeBatch(List<String> sqlStmt) throws SQLException{
    	Statement statement = null;
    	int[] count = null;
    	try {
			dbConnect();
			statement = conn.createStatement();
			for(String s:sqlStmt) {
				System.out.println("Batch statement  "+s+"\n");
				statement.addBatch(s);
			}
			statement.executeBatch();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            if (statement != null) {
             
            	//Close statement
                statement.close();
            }
            //Close connection
            dbDisconnect();
        }
    	return count;
    }
    
    public static long dbExecuteUpdateandReturn(String sqlStmt) throws ClassNotFoundException, SQLException {
    	Statement stmt = null;
        int count =0;
        long lastId=0;
        try {
            //Connect to DB (Establish Oracle Connection)
            dbConnect();
            System.out.println("Statement "+sqlStmt+"\n");
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            count=stmt.executeUpdate(sqlStmt,Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs=stmt.getGeneratedKeys();
            if(rs.next()) {
            	 lastId=rs.getLong(1);
            }
           
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
        
        return lastId;
    }
//	public static ResultSet dbExecuteQuery(String stmt) {
//		// TODO Auto-generated method stub
//		
//		return null;
//	}	
}
