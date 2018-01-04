package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Product {
	long id;
	String barcode;
	long srNo;
	String product_name;
	String unit;
	long hsnNo;
	long quantity;
	double gst;
	double cess;
	double buyPrice;
	double sellPrice;
	String category;
	String subGroup;
	String info;
	String barCodePath;
	
	
	
	public String getBarCodePath() {
		return barCodePath;
	}
	public void setBarCodePath(String barCodePath) {
		this.barCodePath = barCodePath;
	}
	public Product() {
//		super();
	}
	public Product(long id, String product_name,long hsn, String category, String info,long srNo) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.hsnNo=hsn;
		this.category = category;
		
		this.info = info;
		this.srNo=srNo;
	}
	public Product(long id, String product_name, String unit, long hsnNo, double gst, double cess,
			double sellPrice, String barcode,long srNo) {
//		super();
		this.id = id;
		this.srNo = srNo;
		this.product_name = product_name;
		this.unit = unit;
		this.hsnNo = hsnNo;
		this.gst = gst;
		this.cess = cess;
		this.sellPrice = sellPrice;
//		this.category = category;
		this.barcode=barcode;
	}
	public Product(long id, String product_name, long quantity, String category, String info) {
//		super();
		this.id = id;
		this.product_name = product_name;
		this.quantity = quantity;
		this.category = category;
		this.info = info;
	}
	
	
	public Product(long id, String product_name, long quantity,long srNo) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.quantity = quantity;
//		this.category = category;
		this.srNo=srNo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public long getHsnNo() {
		return hsnNo;
	}
	public void setHsnNo(long hsnNo) {
		this.hsnNo = hsnNo;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getCess() {
		return cess;
	}
	public void setCess(double cess) {
		this.cess = cess;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

}
