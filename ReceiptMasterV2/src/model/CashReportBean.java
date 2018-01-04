package model;

import java.util.Date;

public class CashReportBean {
String salesPurchase;
Date CashDate;
Double CashInHAnd;
Double CashReceived;
Double CashPayment;
String FromDate;
public CashReportBean(Date cashDate2, String salesPurchase2, Double cashPayment2, Double cashReceived2,
		Double cashInHAnd2) {

	this.CashDate=cashDate2;
	this.salesPurchase=salesPurchase2;
	this.CashPayment=cashPayment2;
	this.CashReceived=cashReceived2;
	this.CashInHAnd=cashInHAnd2;
}
public CashReportBean() {

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


public String getSalesPurchase() {
	return salesPurchase;
}
public void setSalesPurchase(String salesPurchase) {
	this.salesPurchase = salesPurchase;
}
public Date getCashDate() {
	return CashDate;
}
public void setCashDate(Date cashDate) {
	CashDate = cashDate;
}
public Double getCashInHAnd() {
	return CashInHAnd;
}
public void setCashInHAnd(Double cashInHAnd) {
	CashInHAnd = cashInHAnd;
}
public Double getCashReceived() {
	return CashReceived;
}
public void setCashReceived(Double cashReceived) {
	CashReceived = cashReceived;
}
public Double getCashPayment() {
	return CashPayment;
}
public void setCashPayment(Double cashPayment) {
	CashPayment = cashPayment;
}

}
