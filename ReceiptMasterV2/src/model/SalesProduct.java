package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class SalesProduct {
	long id;
//	long sale_entry_id;
	SalesEntry saleEntry;
//	long prod_id;
	Product product;
	String prod_name;
	long hsnNo;
	String dm_no;
	int quantity;
	long prevQty;
	String unit;
	double rate;
	double discount;
	double gst;
	double total;
	double subTotal;
	long currentStock;
	String rateString;
	String discountString;
	String totalString;
	String subTotalString;
	int arr_id;
	String cgst;
	String sgst;
	String igst;
	String cess;
	DeliveryProduct deliveryProduct;
	
	public DeliveryProduct getDeliveryProduct() {
		return deliveryProduct;
	}


	public void setDeliveryProduct(DeliveryProduct deliveryProduct) {
		this.deliveryProduct = deliveryProduct;
	}


	public SalesProduct() {
//		super();
	}
	
	
	public SalesProduct(String prod_name, long id,long hsnNo, int quantity,long prevQty, String unit, double rate, double discount,
			double total, double subTotal, String cgst, String sgst, String igst, String cess,Product product,String rateString,String discString,String subTotalString,String totalString,long currentStock) {
//		super();
		this.id=id;
		this.prod_name = prod_name;
		this.hsnNo = hsnNo;
		this.quantity = quantity;
		this.unit = unit;
		this.rate = rate;
		this.discount = discount;
		this.total = total;
		this.subTotal = subTotal;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.cess = cess;
		this.product=product;
		this.rateString=rateString;
		this.discountString=discString;
		this.subTotalString=subTotalString;
		this.totalString=totalString;
		this.prevQty=prevQty;
		this.currentStock=currentStock;
	}

	
	public SalesProduct(String prod_name, long id,long hsnNo, int quantity, String unit, double rate, double discount,
			double total, double subTotal, String cgst, String sgst, String igst, String cess,Product product,String rateString,String discString,String subTotalString,String totalString,DeliveryProduct deliveryProduct,long currentStock) {
//		super();
		this.id=id;
		this.prod_name = prod_name;
		this.hsnNo = hsnNo;
		this.quantity = quantity;
		this.unit = unit;
		this.rate = rate;
		this.discount = discount;
		this.total = total;
		this.subTotal = subTotal;
		this.cgst = cgst;
		this.sgst = sgst;
		this.igst = igst;
		this.cess = cess;
		this.product=product;
		this.rateString=rateString;
		this.discountString=discString;
		this.subTotalString=subTotalString;
		this.totalString=totalString;
		this.deliveryProduct=deliveryProduct;
		this.currentStock=currentStock;
	}
 

	public SalesProduct(String prod_name,String dm_no, int quantity, String unit, double rate, double discout, double gst, double total) {
//		super();
		this.prod_name = prod_name;
		this.dm_no = dm_no;
		this.quantity = quantity;
		this.unit = unit;
		this.rate = rate;
		this.discount = discout;
		this.gst = gst;
		this.total = total;
//		this.arr_id = arr_id;
	}
	public SalesProduct(SalesEntry saleEntry, Product product, String dm_no, int quantity, String unit, double rate,
			double discout, double gst, double total, int arr_id) {
//		super();
		this.saleEntry = saleEntry;
		this.product = product;
		this.dm_no = dm_no;
		this.quantity = quantity;
		this.unit = unit;
		this.rate = rate;
		this.discount = discout;
		this.gst = gst;
		this.total = total;
		this.arr_id = arr_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public SalesEntry getSaleEntry() {
		return saleEntry;
	}
	public void setSaleEntry(SalesEntry saleEntry) {
		this.saleEntry = saleEntry;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public String getDm_no() {
		return dm_no;
	}
	public void setDm_no(String dm_no) {
		this.dm_no = dm_no;
	}
	public long getHsnNo() {
		return hsnNo;
	}
	public void setHsnNo(long hsnNo) {
		this.hsnNo = hsnNo;
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
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public int getArr_id() {
		return arr_id;
	}
	public void setArr_id(int arr_id) {
		this.arr_id = arr_id;
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


	public String getRateString() {
		return rateString;
	}


	public void setRateString(String rateString) {
		this.rateString = rateString;
	}


	public String getDiscountString() {
		return discountString;
	}


	public void setDiscountString(String discountString) {
		this.discountString = discountString;
	}


	public String getTotalString() {
		return totalString;
	}


	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}


	public String getSubTotalString() {
		return subTotalString;
	}


	public void setSubTotalString(String subTotalString) {
		this.subTotalString = subTotalString;
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
	
}
