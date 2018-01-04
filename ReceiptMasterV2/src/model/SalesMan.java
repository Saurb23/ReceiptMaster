package model;

public class SalesMan {
	
	long id;
	long srNo;
	String salesManName;
	String contact;
	String address;
	long status;
	public SalesMan() {
		
	}
	
	public SalesMan(long id, String salesManName, String contactNO, String address, long srNo) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.srNo=srNo;
		this.salesManName=salesManName;
		this.contact=contactNO;
		this.address=address;
	}
	public long getId() {
		return id;
	}
	public long getSrNo() {
		return srNo;
	}
	public String getSalesManName() {
		return salesManName;
	}
	public String getContact() {
		return contact;
	}
	public String getAddress() {
		return address;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public void setSalesManName(String salesManName) {
		this.salesManName = salesManName;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
	
}
