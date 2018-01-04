package model;

public class StockTransferProduct {
	long id;
	StockTransfer stockTransfer;
	Product product;
	String prod_name;
	int quantity;
	String unit;
	double rate;
	double total;
	String rateString;
	String totalString;
	long currentStock;
	
	public long getId() {
		return id;
	}
	public StockTransfer getStockTransfer() {
		return stockTransfer;
	}
	public Product getProduct() {
		return product;
	}
	public String getProd_name() {
		return prod_name;
	}
	public int getQuantity() {
		return quantity;
	}
	public String getUnit() {
		return unit;
	}
	public double getRate() {
		return rate;
	}
	public double getTotal() {
		return total;
	}
	public String getRateString() {
		return rateString;
	}
	public String getTotalString() {
		return totalString;
	}
	public long getCurrentStock() {
		return currentStock;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStockTransfer(StockTransfer stockTransfer) {
		this.stockTransfer = stockTransfer;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setRateString(String rateString) {
		this.rateString = rateString;
	}
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}

}
