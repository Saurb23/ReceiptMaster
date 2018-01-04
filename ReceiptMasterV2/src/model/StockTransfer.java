package model;

import java.sql.Date;

public class StockTransfer {
	long id;
	long srNo;
	long transferNo;
	Branch branch;
	Date entryDate;
	String productname;
	String branchname;
	long quantity;
	double total;
	double rate;
	String rateString;
	String totalString;
	
	public StockTransfer() {
		
	}
	
	public StockTransfer(long id2, long srNo2,long transferNo, String branchname2, Date entryDate2, double total2,String totalString) {
		// TODO Auto-generated constructor stub
		
		this.id=id2;
		this.srNo=srNo2;
		this.branchname=branchname2;
		this.transferNo=transferNo;
		this.entryDate=entryDate2;
		this.total= total2;
		this.totalString=totalString;
	}

	public long getId() {
		return id;
	}
	public long getSrNo() {
		return srNo;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public String getProductname() {
		return productname;
	}
	public String getBranchname() {
		return branchname;
	}
	public long getQuantity() {
		return quantity;
	}
	public double getTotal() {
		return total;
	}
	public double getRate() {
		return rate;
	}
	public String getRateString() {
		return rateString;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public void setRateString(String rateString) {
		this.rateString = rateString;
	}

	public long getTransferNo() {
		return transferNo;
	}

	public String getTotalString() {
		return totalString;
	}

	public void setTransferNo(long transferNo) {
		this.transferNo = transferNo;
	}

	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
}
