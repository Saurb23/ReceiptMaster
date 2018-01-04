package model;

public class Branch {
	long id;
	String branchName;
	String address;
	String state;
	
	
	public long getId() {
		return id;
	}
	public String getBranchName() {
		return branchName;
	}
	public String getAddress() {
		return address;
	}
	public String getState() {
		return state;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setState(String state) {
		this.state = state;
	}

}
