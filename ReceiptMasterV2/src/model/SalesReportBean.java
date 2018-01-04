package model;

import java.sql.Date;

public class SalesReportBean {

	 String FromDate;
	 String fullname;

	 String customer_add;
	 String area;
	 String city;
	 String customer_state;
	 String customerContact;
	 String email;
	 long invoice_no;
	 Date invoice_date;
	 String productname;
	 String product_id;
	 String saeles_product_qty;
	 String saeles_product_rate;
	 long HSn;
	 double SGSTamt;
	 double CGSTRate;
	 double IGSTRate;
	 double CGSTamt;
	 double IGSTamt;
	 double Total;
	 
	 double SGSTrate;
	 
	 public SalesReportBean(long invoice_no, Date invoice_date, String fullname,double total) {
	  this.invoice_no=invoice_no;
	  this.invoice_date=invoice_date;
	  this.fullname=fullname;
	  this.Total=total;
	 }

	 public SalesReportBean() {

	 }

	 public double getSGSTrate() {
	  return SGSTrate;
	 }

	 public void setSGSTrate(double d) {
	  SGSTrate = d;
	 }

	 public double getSGSTamt() {
	  return SGSTamt;
	 }

	 public void setSGSTamt(double d) {
	  SGSTamt = d;
	 }

	 public double getCGSTRate() {
	  return CGSTRate;
	 }

	 public void setCGSTRate(double d) {
	  CGSTRate = d;
	 }

	 public double getIGSTRate() {
	  return IGSTRate;
	 }

	 public void setIGSTRate(double d) {
	  IGSTRate = d;
	 }

	 public double getCGSTamt() {
	  return CGSTamt;
	 }

	 public void setCGSTamt(double d) {
	  CGSTamt = d;
	 }

	 public double getIGSTamt() {
	  return IGSTamt;
	 }

	 public void setIGSTamt(double d) {
	  IGSTamt = d;
	 }

	 

	 public String getFullname() {
	  return fullname;
	 }

	 public void setFullname(String fullname) {
	  this.fullname = fullname;
	 }

	 public String getCustomer_add() {
	  return customer_add;
	 }

	 public void setCustomer_add(String customer_add) {
	  this.customer_add = customer_add;
	 }

	 public String getArea() {
	  return area;
	 }

	 public void setArea(String area) {
	  this.area = area;
	 }

	 public String getCity() {
	  return city;
	 }

	 public void setCity(String city) {
	  this.city = city;
	 }

	 public String getCustomer_state() {
	  return customer_state;
	 }

	 public void setCustomer_state(String customer_state) {
	  this.customer_state = customer_state;
	 }

	 public String getCustomerContact() {
	  return customerContact;
	 }

	 public void setCustomerContact(String customerContact) {
	  this.customerContact = customerContact;
	 }

	 public String getEmail() {
	  return email;
	 }

	 public void setEmail(String email) {
	  this.email = email;
	 }

	 public long getInvoice_no() {
	  return invoice_no;
	 }

	 public void setInvoice_no(long l) {
	  this.invoice_no = l;
	 }

	 public Date getInvoice_date() {
	  return invoice_date;
	 }

	 public void setInvoice_date(Date date) {
	  this.invoice_date = date;
	 }

	 public String getProductname() {
	  return productname;
	 }

	 public void setProductname(String productname) {
	  this.productname = productname;
	 }

	 public String getProduct_id() {
	  return product_id;
	 }

	 public void setProduct_id(String product_id) {
	  this.product_id = product_id;
	 }

	 public String getSaeles_product_qty() {
	  return saeles_product_qty;
	 }

	 public void setSaeles_product_qty(String saeles_product_qty) {
	  this.saeles_product_qty = saeles_product_qty;
	 }

	 public String getSaeles_product_rate() {
	  return saeles_product_rate;
	 }

	 public void setSaeles_product_rate(String saeles_product_rate) {
	  this.saeles_product_rate = saeles_product_rate;
	 }

	 public long getHSn() {
	  return HSn;
	 }

	 public void setHSn(long l) {
	  HSn = l;
	 }



	 public double getTotal() {
	  return Total;
	 }

	 public void setTotal(double d) {
	  Total = d;
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

	 String ToDate;
}
