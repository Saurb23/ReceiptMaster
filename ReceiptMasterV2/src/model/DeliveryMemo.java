package model;

import java.sql.Date;

public class DeliveryMemo {

	long id;
	long srNo;
	long entryId;
	String dm_no;
	Customer customer;
	String custName;
	Date entry_date;
	long invoiceNo;
	double total;
	double advanceAmt;
	DeliveryProduct deliveryProduct;
	int status;
	String advance;
	
	
	public String getAdvance() {
		return advance;
	}
	public void setAdvance(String advance) {
		this.advance = advance;
	}
	public DeliveryMemo() {
		
	}
	public DeliveryMemo(long srNo,long id, String dm_no, String custName, Customer customer, Date entry_date,double total,
			double advanceAmt,int status,String advance,long invoiceNo) {
		// TODO Auto-generated constructor stub
		this.srNo=srNo;
		this.id=id;
		this.dm_no=dm_no;
		this.custName=custName;
		this.customer=customer;
		this.srNo=srNo;
		this.entry_date=entry_date;
		this.total= total;
		this.advanceAmt=advanceAmt;
		this.status=status;
		this.advance=advance;
		this.invoiceNo= invoiceNo;
	}
	public long getId() {
		return id;
	}
	public String getDm_no() {
		return dm_no;
	}
	
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Date getEntry_date() {
		return entry_date;
	}
	public double getAdvanceAmt() {
		return advanceAmt;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDm_no(String dm_no) {
		this.dm_no = dm_no;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}
	public void setAdvanceAmt(double advanceAmt) {
		this.advanceAmt = advanceAmt;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public long getEntryId() {
		return entryId;
	}
	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}
	public DeliveryProduct getDeliveryProduct() {
		return deliveryProduct;
	}
	public void setDeliveryProduct(DeliveryProduct deliveryProduct) {
		this.deliveryProduct = deliveryProduct;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
}
