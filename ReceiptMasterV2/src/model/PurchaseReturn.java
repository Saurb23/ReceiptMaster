package model;

import java.sql.Date;

public class PurchaseReturn {
	long srNo;
	long id;
	PurchaseEntry purchaseEntry;
	PurchaseProduct purchaseProduct;
	Product product;
	Date returnDate;
	long returnQuantity;
	Supplier supplier;
	String supplierName;
	String prod_name;
	
	
	public PurchaseReturn() {
		// TODO Auto-generated constructor stub
	}
	
	public PurchaseReturn(PurchaseProduct purchaseProduct,long returnQuantity,Product product) {
		// TODO Auto-generated constructor stub
		this.purchaseProduct=purchaseProduct;
		this.returnQuantity=returnQuantity;
		this.product=product;
	}
	
	public PurchaseReturn(long srNo, long id,Date returnDate, String supplierName, String prod_name, Product product,
			long returnQty) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.srNo=srNo;
		this.returnDate=returnDate;
		this.product=product;
		this.supplierName=supplierName;
		this.prod_name=prod_name;
		this.returnQuantity=returnQty;
		
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public PurchaseEntry getPurchaseEntry() {
		return purchaseEntry;
	}
	public void setPurchaseEntry(PurchaseEntry purchaseEntry) {
		this.purchaseEntry = purchaseEntry;
	}
	public PurchaseProduct getPurchaseProduct() {
		return purchaseProduct;
	}
	public void setPurchaseProduct(PurchaseProduct purchaseProduct) {
		this.purchaseProduct = purchaseProduct;
	}
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public long getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(long returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public long getSrNo() {
		return srNo;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getProd_name() {
		return prod_name;
	}

	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	
}
