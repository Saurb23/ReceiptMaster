package model;

import java.sql.Date;

public class SalesReturn {
	
	long srNo;
	long id;
	SalesProduct salesProduct;
	SalesEntry salesEntry;
	Product product;
	long returnQuantity;
	Date returnDate;
	String prodName;
	String customerName;
	String invoiceNo;
	
	
	public SalesReturn(SalesProduct salesProduct, int quantity, Product product) {
		this.salesProduct=salesProduct;
		this.returnQuantity=quantity;
		this.product=product;
	}

	public SalesReturn(long srNo, long id, String prodName, String invoiceNo, Date returnDate,
			long returnQuantity, Product product) {
		this.srNo=srNo;
		this.id=id;
		this.prodName=prodName;
		this.invoiceNo=invoiceNo;
		this.returnDate=returnDate;
		this.returnQuantity=returnQuantity;
		this.product=product;
		
	}

	public long getSrNo() {
		return srNo;
	}


	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public SalesProduct getSalesProduct() {
		return salesProduct;
	}
	public void setSalesProduct(SalesProduct salesProduct) {
		this.salesProduct = salesProduct;
	}
	public SalesEntry getSalesEntry() {
		return salesEntry;
	}
	public void setSalesEntry(SalesEntry salesEntry) {
		this.salesEntry = salesEntry;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(long returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}


	public String getProdName() {
		return prodName;
	}


	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	
	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public SalesReturn() {
		
	}


	


	public String getInvoiceNo() {
		return invoiceNo;
	}


	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	
	

}
