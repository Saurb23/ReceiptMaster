package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */

import java.util.Set;

public class PurchaseProduct {
	long id;
	PurchaseEntry purchaseEntry;
	Product product;
	Category category;
	SubCategory subCategory;
	String group;
	String subGroup;
	String prod_name;
	long hsnNo;
	int quantity;
	double sellPrice;
	String unit;
	Unit unitObj;
//	Set<String> Unit;
	double rate;
	double discount;
	double gst;
	double cessAmt;
	double subTotal;
	double amount;
	long currentStock;
	long prevQty;
	String sellPriceString;
	String cgst;
	String sgst;
	String igst;
	String cess;
	String status;
	String quantityString;
	String rateString;
	String discString;
	String subTotalString;
	String totalString;

	
	public PurchaseProduct() {
		
	}
	 public PurchaseProduct(String prod_name,long id, long hsnNo, int quantity, String unit, double rate, double discount,
			double amount, double subTotal, String cgst, String sgst, String igst, String cess,Product product,String quanString,String rateString,String discString,String subTotalString,String totalString) {
//		super();
		this.prod_name = prod_name;
		this.hsnNo = hsnNo;
		this.quantity = quantity;
		this.unit = unit;
		this.rate = rate;
		this.discount = discount;
		this.amount = amount;
		this.subTotal = subTotal;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.cess = cess;
		this.product=product;
		this.quantityString=quanString;
		this.rateString=rateString;
		this.discString=discString;
		this.subTotalString=subTotalString;
		this.totalString=totalString;
		this.id=id;
	
	}
	 
	 public PurchaseProduct(String prod_name, long hsnNo, int quantity, String unit, double rate, double discount,
				double amount, double subTotal, String cgst, String sgst, String igst, String cess,String rateString,String discString,String subTotalString,String totalString) {
//			super();
			this.prod_name = prod_name;
			this.hsnNo = hsnNo;
			this.quantity = quantity;
			this.unit = unit;
			this.rate = rate;
			this.discount = discount;
			this.amount = amount;
			this.subTotal = subTotal;
			this.cgst = cgst;
			this.sgst = sgst;
			this.igst = igst;
			this.cess = cess;
			this.rateString=rateString;
			this.discString=discString;
			this.subTotalString=subTotalString;
			this.totalString=totalString;
		}
	 

	public PurchaseProduct(String product_name, long id2,Unit unit,Category category,SubCategory subCategory, String group,String subgroup,long hsnNo2, double sell_price,int quantity2,long prevQty, String unit2, double rate2,
			double discount2, double amount2, double subTotal2, String cgst2, String sgst2, String igst,
			String cess, Product product2, String rate3, String discString2, String subTotalString2, String total) {
		
		this.id=id2;
		this.prod_name=product_name;
		this.group=group;
		this.subGroup=subgroup;
		this.hsnNo=hsnNo2;
		this.quantity=quantity2;
		this.category= category;
		this.subCategory=subCategory;
		this.unit=unit2;
		this.rate=rate2;
		this.discount=discount2;
		this.amount=amount2;
		this.subTotal=subTotal2;
		this.cgst=cgst2;
		this.sgst=sgst2;
		this.igst=igst;
		this.cess=cess;
		this.product=product2;
		this.rateString=rate3;
		this.discString=discString2;
		this.subTotalString=subTotalString2;
		this.totalString=total;
		this.sellPrice=sell_price;
		this.prevQty=prevQty;
		this.unitObj=unit;
		
		
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Unit getUnitObj() {
		return unitObj;
	}
	public void setUnitObj(Unit unitObj) {
		this.unitObj = unitObj;
	}
//	public void setUnit(Set<String> unit) {
//		Unit = unit;
//	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getCessAmt() {
		return cessAmt;
	}
	public void setCessAmt(double cessAmt) {
		this.cessAmt = cessAmt;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getHsnNo() {
		return hsnNo;
	}
	public void setHsnNo(long hsnNo) {
		this.hsnNo = hsnNo;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public String getCgst() {
		return cgst;
	}
	public void setCgst(String cgst) {
		this.cgst = cgst;
	}
	public String getSgst() {
		return sgst;
	}
	public void setSgst(String sgst) {
		this.sgst = sgst;
	}
	public String getIgst() {
		return igst;
	}
	public void setIgst(String igst) {
		this.igst = igst;
	}
	public String getCess() {
		return cess;
	}
	public void setCess(String cess) {
		this.cess = cess;
	}
	public long getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}
	public long getPrevQty() {
		return prevQty;
	}
	public void setPrevQty(long prevQty) {
		this.prevQty = prevQty;
	}
	public String getQuantityString() {
		return quantityString;
	}
	public void setQuantityString(String quantityString) {
		this.quantityString = quantityString;
	}
	public String getRateString() {
		return rateString;
	}
	public void setRateString(String rateString) {
		this.rateString = rateString;
	}
	public String getDiscString() {
		return discString;
	}
	public void setDiscString(String discString) {
		this.discString = discString;
	}
	public String getSubTotalString() {
		return subTotalString;
	}
	public void setSubTotalString(String subTotalString) {
		this.subTotalString = subTotalString;
	}
	public String getTotalString() {
		return totalString;
	}
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public String getSellPriceString() {
		return sellPriceString;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public void setSellPriceString(String sellPriceString) {
		this.sellPriceString = sellPriceString;
	}
	

}
