package model;

public class Item {
	
	String CompanyName;
	String ProductName;
	String BarcodePath;
	double Price;
	
	
	public String getCompanyName() {
		return CompanyName;
	}
	public String getProductName() {
		return ProductName;
	}
	public String getBarcodePath() {
		return BarcodePath;
	}
	public double getPrice() {
		return Price;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public void setBarcodePath(String barcodePath) {
		BarcodePath = barcodePath;
	}
	public void setPrice(double price) {
		Price = price;
	}
	

}
