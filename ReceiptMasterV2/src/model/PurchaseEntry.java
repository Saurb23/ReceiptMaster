package model;

import java.util.Date;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class PurchaseEntry {
	long id;
	String invoiceNo;
	long purchaseId;
	long entryId;
	String purchEntryNo;
	Supplier supplier;
	String supplierName;
	Date purchaseDate;
	Date invoiceDate;
	String doucmentName;
	String documentURL;
	String material;
	String deliveryNote;
	double subTotal;
	double discount;
	double total;
	String paymentType;
//	String paymentMode;
	PaymentMode paymentMode;
	String bankName;
	String chequeNo;
	double paidAmount;
	double totalPaidAmt;
	
	String FromDate;
	String ToDate;
	String ProductName;
	String totalString;
	
	public String getTotalString() {
		return totalString;
	}


	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}


	public PurchaseEntry() {
//		super();
	}
	
	
	public PurchaseEntry(String supplierName, double total, double totalPaidAmt,Supplier supplier) {
//		super();
		this.supplierName = supplierName;
		this.total = total;
		this.totalPaidAmt = totalPaidAmt;
		this.supplier=supplier;
//		this.paidAmount = paidAmount;
//		this.purchaseDate = purchaseDate;
	}


	public PurchaseEntry(String invoiceNo, Date purchaseDate, double total, double paidAmount) {
//		super();
		this.invoiceNo = invoiceNo;
		this.purchaseDate = purchaseDate;
		this.total = total;
		this.paidAmount = paidAmount;
	}

	public PurchaseEntry(long purchId, String invoiceNo, Date purchaseDate, double total,long id,Supplier supplier,String material) {
//		super();
		this.id=id;
		this.purchaseId = purchId;
		this.invoiceNo = invoiceNo;
		this.purchaseDate = purchaseDate;
		this.total = total;
		this.supplierName = supplierName;
		this.supplier=supplier;
		this.material=material;
	}
	
	
	public long getId() {
		return id;
	}
	public PurchaseEntry(long id, String invoiceNo, long purchaseId, Supplier supplier, String supplierName,
			Date purchaseDate, Date invoiceDate, String doucmentName, String documentURL, String material,
			String deliveryNote, double subTotal, double discount, double total, String paymentType, PaymentMode paymentMode,
			String bankName, String chequeNo, double paidAmount, double totalPaidAmt) {
//		super();
		this.id = id;
		this.invoiceNo = invoiceNo;
		this.purchaseId = purchaseId;
		this.supplier = supplier;
		this.supplierName = supplierName;
		this.purchaseDate = purchaseDate;
		this.invoiceDate = invoiceDate;
		this.doucmentName = doucmentName;
		this.documentURL = documentURL;
		this.material = material;
		this.deliveryNote = deliveryNote;
		this.subTotal = subTotal;
		this.discount = discount;
		this.total = total;
		this.paymentType = paymentType;
//		this.paymentMode = paymentMode;
		this.bankName = bankName;
		this.chequeNo = chequeNo;
		this.paidAmount = paidAmount;
		this.totalPaidAmt = totalPaidAmt;
		
	}


	public PurchaseEntry(long id2, String invoiceNo2, long purchaseId2, Supplier supplier2, String supplierName2,
			Date purchaseDate2, String material2, double subTotal2, double total2, String paymentType2,
			PaymentMode paymentMode2, String bankName2, String chequeNo2, double paidAmount2,String purchEntryNo,String totalString) {
		this.id = id2;
		this.invoiceNo = invoiceNo2;
		this.purchaseId = purchaseId2;
		this.supplier = supplier2;
		this.supplierName = supplierName2;
		this.purchaseDate = purchaseDate2;
		this.material = material2;
		this.subTotal = subTotal2;
		this.total = total2;
		this.paymentType = paymentType2;
		this.paymentMode=paymentMode2;
//		this.paymentMode = paymentMode2;
		this.bankName = bankName2;
		this.chequeNo = chequeNo2;
		this.paidAmount = paidAmount2;
		this.purchEntryNo=purchEntryNo;
		this.totalString=totalString;
	}


	public PurchaseEntry(Date invoiceDate2, String invoiceNo2, String productName2, double total2) {
		// TODO Auto-generated constructor stub
		
		this.invoiceDate=invoiceDate2;
		this.invoiceNo=invoiceNo2;
		this.ProductName=productName2;
		this.total=total2;
	}


	public void setId(long id) {
		this.id = id;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public long getEntryId() {
		return entryId;
	}


	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}


	public String getPurchEntryNo() {
		return purchEntryNo;
	}


	public void setPurchEntryNo(String purchEntryNo) {
		this.purchEntryNo = purchEntryNo;
	}


	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getDoucmentName() {
		return doucmentName;
	}
	public void setDoucmentName(String doucmentName) {
		this.doucmentName = doucmentName;
	}
	public String getDocumentURL() {
		return documentURL;
	}
	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getDeliveryNote() {
		return deliveryNote;
	}
	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
public PaymentMode getPaymentMode() {
		return paymentMode;
	}


	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}


	//	public String getPaymentMode() {
//		return paymentMode;
//	}
//	public void setPaymentMode(String paymentMode) {
//		this.paymentMode = paymentMode;
//	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public double getTotalPaidAmt() {
		return totalPaidAmt;
	}

	public void setTotalPaidAmt(double totalPaidAmt) {
		this.totalPaidAmt = totalPaidAmt;
	}


	public String getFromDate() {
		return FromDate;
	}


	public String getToDate() {
		return ToDate;
	}


	public String getProductName() {
		return ProductName;
	}


	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}


	public void setToDate(String toDate) {
		ToDate = toDate;
	}


	public void setProductName(String productName) {
		ProductName = productName;
	}
	
	

}
