package model;

import java.sql.Date;

/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Inventory {

	long id;
	String date;
	String product_category;
	String product_name;
	Product product;
	AddStock addStock;
	long quantity;
	String unit;
	double rate;
	double total;
	String totalString;
	String rateString;
	long srNo;
	long currentStock;
	
	
	
	public Inventory() {
//		super();
	}


	public Inventory(String category) { this.product_category = category;}
	
	
	public Inventory(long id,long srNo2, String entry_date, String proCategory, String proName, long qty, String m_unit) {
		
		this.id=id;
		this.srNo = srNo2;
		this.date = entry_date;
		this.product_category = proCategory;
		this.product_name = proName;
		this.quantity = qty;
		this.unit = m_unit;
		
	}

	public Inventory(long id,long srNo,String entry_date,String producName,long qty,double rate,String rateString) {
		this.id=id;
		this.srNo = srNo;
		this.date = entry_date;
//		this.product_category = proCategory;
		this.product_name = producName;
		this.quantity = qty;
		this.rateString=rateString;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}


	public double getRate() {
		return rate;
	}


	public double getTotal() {
		return total;
	}


	public String getTotalString() {
		return totalString;
	}


	public String getRateString() {
		return rateString;
	}


	public void setRate(double rate) {
		this.rate = rate;
	}


	public void setTotal(double total) {
		this.total = total;
	}


	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}


	public void setRateString(String rateString) {
		this.rateString = rateString;
	}


	public long getCurrentStock() {
		return currentStock;
	}


	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}


	public AddStock getAddStock() {
		return addStock;
	}


	public void setAddStock(AddStock addStock) {
		this.addStock = addStock;
	}
	
}

