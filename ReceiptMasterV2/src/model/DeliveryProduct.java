package model;

public class DeliveryProduct {
	long id;
	DeliveryMemo deliveryMemo;
	Product product;
	String prod_name;
	int quantity;
	String unit;
	double rate;
	double total;
	String rateString;
	String totalString;
	long currentStock;
	
	public DeliveryProduct() {
		
	}
	
	public DeliveryProduct(long id2, String product_name,String unit,double rate2, int quantity2, double total2, String rate3,
			String total3) {
		// TODO Auto-generated constructor stub
		
		this.id=id2;
		this.prod_name= product_name;
		this.rate= rate2;
		this.quantity=quantity2;
		this.total= total2;
		this.totalString=total3;
		this.rateString=rate3;
		this.unit= unit;
	}
	public long getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(long currentStock) {
		this.currentStock = currentStock;
	}
	public long getId() {
		return id;
	}
	public DeliveryMemo getDeliveryMemo() {
		return deliveryMemo;
	}
	public Product getProduct() {
		return product;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setDeliveryMemo(DeliveryMemo deliveryMemo) {
		this.deliveryMemo = deliveryMemo;
	}
	public void setProduct(Product product) {
		this.product = product;
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

}
