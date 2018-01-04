package model;

public class Company {

	String companyName;
	String address;
	String officeAddr;
	String emailId;
	String contactNo;
	String altContact;
	String state;
	String logoPath;
	String gstin;
	
	public String getGstin() {
		return gstin;
	}
	public void setGstin(String gstin) {
		this.gstin = gstin;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getAddress() {
		return address;
	}
	public String getOfficeAddr() {
		return officeAddr;
	}
	public String getEmailId() {
		return emailId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public String getAltContact() {
		return altContact;
	}
	public String getState() {
		return state;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setOfficeAddr(String officeAddr) {
		this.officeAddr = officeAddr;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public void setAltContact(String altContact) {
		this.altContact = altContact;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
