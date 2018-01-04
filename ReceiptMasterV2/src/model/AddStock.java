package model;

import java.sql.Date;

public class AddStock {
	long id;
	Date entryDate;
	long inwardNo;
	Branch branch;
	double total;
	String totalString;
	long srNo;
	Product product;
	Inventory inventory;
	String branchname;
	
	public AddStock() {
		
	}
	
	public AddStock(long id2, long srNo2, Date entryDate2, long inwardNo2, String branchName2, double total2,
			String total3) {
		// TODO Auto-generated constructor stub
		this.id=id2;
		this.srNo=srNo2;
		this.entryDate=entryDate2;
		this.inwardNo=inwardNo2;
		this.branchname=branchName2;
		this.total=total2;
		this.totalString=total3;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public long getInwardNo() {
		return inwardNo;
	}
	public void setInwardNo(long inwardNo) {
		this.inwardNo = inwardNo;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getTotalString() {
		return totalString;
	}

	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}

}
