package application;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;
import com.thebasics.msgclub.test.SendSmsDemo;

import model.SMSBacklog;
import model.SalesEntry;
import util.DBUtil;

public class SmsSender {
	
	static String authKey;
	static String senderId;
	static String route;
	static String mainUrl;
	static SendSmsDemo send=new SendSmsDemo();
	static String smsContentType="english";
	static    String scheduledTime="";
	static String groupName="";
	static   String signature="";
	
	public static void sendSms(SalesEntry salesEntry) throws UnknownHostException, UnsupportedEncodingException  {
		
		boolean connect=checkConnection();
		
//		boolean contactEmpty=false;
//		
//		if(salesEntry.getCustomer().getContact()==null||salesEntry.getCustomer().getContact().equals("null")) {
//	    	   contactEmpty=true;
//	       }
	//Your authentication key
    String auth_key = "c21e2b4ade4450d938647fe41b9bcf7";
    
    //Multiple mobiles numbers separated by comma
    String mobiles = salesEntry.getCustomer().getContact().toString();
   
   
    
    //Sender ID,While using route4 sender id should be 6 characters long.
    String senderId = "MSGTST";
    
    //Your message to send, Add URL encoding here.
    String message = "Hello "+salesEntry.getCustomer().getFull_name()+", " + 
    		 
    		"Thank you for recent bussiness with us.Invoice No. "+ salesEntry.getInvoice_no()+ "  of amount Rs. "+salesEntry.getTotal() +" Successfully generated. " + 
    		 
    		" Have nice day !";
    

    //define route
    String route="8";
    
    //encoding message
    String encoded_message = null;
	encoded_message = URLEncoder.encode(message,"utf-8");

    //Send SMS API
    String mainUrl= "http://msg.msgclub.net/rest/services/sendSMS/sendGroupSms";
  
	
   // String s=send.sendSmsPost(mainUrl, authKey, encoded_message, senderId, route, mobiles,smsContentType,scheduledTime,signature,groupName);
	if(connect) {
    try
    { 
    	System.out.println("Reached here ");
    	
        String s=send.sendSmsPost(mainUrl, auth_key, message, senderId, route, mobiles,smsContentType,scheduledTime,signature,groupName);
        System.out.println(s +"   massage");
    }
    catch (Exception e)
    {
    	 if(e instanceof java.net.UnknownHostException){
    	      System.out.println("Unknown Host Ex");
    	  }else{
    	      System.out.println("OTHER ERROR");
    	  }
            //e.printStackTrace();
    }
		}
	else {
			String stmt="insert into rm_sms_backlog(contactNo,messageText) values('"+mobiles+"','"+message+"')";
			try {
				DBUtil.dbExecuteUpdate(stmt);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    
}
	
	public static boolean checkConnection()  {
		boolean connect=false;
		try {
		        URL url = new URL("http://www.google.com/");
		 
		        URLConnection connection = url.openConnection();
		        connection.connect();   
		        
		       System.out.println("Internet Connected");
		        connect=true;
		}
		catch(Exception e) {
			connect=false;
			System.out.println("No connection");
		}
		
		return connect;
	}
	
	
	public static void initializeSMS(List<SMSBacklog> smsList) {
		 authKey = "c21e2b4ade4450d938647fe41b9bcf7";
		 route="8";
		 senderId = "MSGTST";
		 mainUrl="http://msg.msgclub.net/rest/services/sendSMS/sendGroupSms";
		 
		 boolean connect= checkConnection();
		 if(connect) {
		 for(SMSBacklog sms:smsList) {
			 

		    //encoding message
		    String message = null;
			message = sms.getMessage();
			String s=send.sendSmsPost(mainUrl,  authKey, message, senderId, route, sms.getContactNo(),smsContentType,scheduledTime,signature,groupName);
			
		    try
		    {
		        String stmt="delete from rm_sms_backlog where id="+sms.getId()+"";
		        DBUtil.dbExecuteUpdate(stmt);
		        //finally close connection
		    
		    }
		    
		    catch (Exception e)
		    {
		    	
		    	 if(e instanceof java.net.UnknownHostException){
		    	      System.out.println("Unknown Host Ex");
		    	  }else{
		    	      System.out.println("Database issue/Connection Error");
		    	  }
		            //e.printStackTrace();
		    }
			}
		
	}
	}
}

