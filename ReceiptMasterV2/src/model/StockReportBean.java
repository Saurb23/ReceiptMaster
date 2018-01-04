package model;

import java.math.BigDecimal;

public class StockReportBean {
	String productname;

	long quantity;
	long saleQty;
	long PurchaseReturn;
	long salesReturn;
	long RemainingQty;
	long stockQty;
	long transferQty;
	String FromDate;
	String ToDate;

	public StockReportBean(String productname2, long quantity2, long saleQty,long salesReturn2, long purchaseReturn2,
			long remainingQty2,long stockQty,long transferQty) {

		// this.FromDate=fromDate2;
		this.productname = productname2;
		this.quantity = quantity2;
		this.salesReturn = salesReturn2;
		this.PurchaseReturn = purchaseReturn2;
		this.saleQty=saleQty;
		this.RemainingQty = remainingQty2;
		this.stockQty=stockQty;
		this.transferQty=transferQty;
		// TODO Auto-generated constructor stub
	}

	public StockReportBean() {
		// TODO Auto-generated constructor stub
	}

	public String getFromDate() {
		return FromDate;
	}

	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}

	public String getToDate() {
		return ToDate;
	}

	public void setToDate(String toDate) {
		ToDate = toDate;
	}

	

	public long getRemainingQty() {
		return RemainingQty;
	}

	public void setRemainingQty(long remainingQty) {
		RemainingQty = remainingQty;
	}

	public long getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(long saleQty) {
		this.saleQty = saleQty;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long l) {
		this.quantity = l;
	}

	public long getPurchaseReturn() {
		return PurchaseReturn;
	}

	public void setPurchaseReturn(long l) {
		PurchaseReturn = l;
	}

	public long getSalesReturn() {
		return salesReturn;
	}

	public void setSalesReturn(long l) {
		this.salesReturn = l;
	}

	public long getStockQty() {
		return stockQty;
	}

	public void setStockQty(long stockQty) {
		this.stockQty = stockQty;
	}

	public long getTransferQty() {
		return transferQty;
	}

	public void setTransferQty(long transferQty) {
		this.transferQty = transferQty;
	}

}
