package model;

public class SMSBacklog {
	long id;
	String contactNo;
	String message;
	
	
	public long getId() {
		return id;
	}
	public String getContactNo() {
		return contactNo;
	}
	public String getMessage() {
		return message;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
