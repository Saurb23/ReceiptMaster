package model;

import java.sql.Date;

public class SalesManReportBean {
	long srNo;
	String salesManName;
	long invoiceNo;
	Date invoice_date;
	String from_date;
	String to_date;
	double total;
	
	public SalesManReportBean() {
		
	}
	
	public SalesManReportBean(long invoiceNo2, long srNo2, Date invoice_date2, String fullname, double total2) {
		// TODO Auto-generated constructor stub
		
		this.invoiceNo=invoiceNo2;
		this.srNo=srNo2;
		this.invoice_date=invoice_date2;
		this.salesManName=fullname;
		this.total=total2;
	}
	public long getSrNo() {
		return srNo;
	}
	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}
	public String getSalesManName() {
		return salesManName;
	}
	public long getInvoiceNo() {
		return invoiceNo;
	}
	public String getFrom_date() {
		return from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public Date getInvoice_date() {
		return invoice_date;
	}
	public void setInvoice_date(Date invoice_date) {
		this.invoice_date = invoice_date;
	}
	public void setSalesManName(String salesManName) {
		this.salesManName = salesManName;
	}
	public void setInvoiceNo(long invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}
	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
