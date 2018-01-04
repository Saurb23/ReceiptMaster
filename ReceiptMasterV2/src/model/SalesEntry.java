package model;

import java.util.Date;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class SalesEntry {
	long id;
	long invoice_no;
	long entryId;
//	long cust_id;
	public static String companyName;
	String invoice;
	Customer customer;
	String custName;
	double sub_total;
	double discount;
	double total;
	Date entry_date;
	Date date_created;
	String payment_type;
//	String payment_mode;
	PaymentMode paymentMode;
	String bank_name;
	String cheque_no;
	double paid_amount;
	double totalPaidAmt;
	double unPaidAmt;
	DeliveryMemo deliveryMemo;
	String totalString;
	SalesMan salesMan;
	String salesManName;
	
	public DeliveryMemo getDeliveryMemo() {
		return deliveryMemo;
	}
	public void setDeliveryMemo(DeliveryMemo deliveryMemo) {
		this.deliveryMemo = deliveryMemo;
	}
	public double getUnPaidAmt() {
		return unPaidAmt;
	}
	public void setUnPaidAmt(double unPaidAmt) {
		this.unPaidAmt = unPaidAmt;
	}
	public SalesEntry() {
//		super();
	}
	public SalesEntry(long id,long invoice_no, String custName, Date entry_date, double total,String invoice,String payment_type,PaymentMode payment_mode,String bankName,String cheque,double paid_amount,Customer customer,String totalString,String salesManName) {
//		super();
		this.id=id;
		this.invoice_no = invoice_no;
		this.custName = custName;
		this.entry_date = entry_date;
		this.total = total;
		this.invoice=invoice;
		this.paymentMode=payment_mode;
		this.payment_type=payment_type;
		this.bank_name=bankName;
		this.cheque_no=cheque;
		this.paid_amount=paid_amount;
		this.customer=customer;
		this.totalString=totalString;
		this.salesManName=salesManName;
		
	}
	
	public SalesEntry(long invoice_no,Customer customer,String custName, double total, double totalPaidAmt,Date entry_date,String payment_type,double paid_amount) {
//		super();
		this.invoice_no = invoice_no;
		this.customer = customer;
		this.custName = custName;
		this.total = total;
		this.totalPaidAmt = totalPaidAmt;
		this.entry_date = entry_date;
		this.payment_type = payment_type;
		this.paid_amount = paid_amount;
	}
	
	public SalesEntry(String custName, double total, double totalPaidAmt,Customer customer) {
//		super();
		this.custName = custName;
		this.total = total;
		this.totalPaidAmt = totalPaidAmt;
		this.customer=customer;
	}
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((bank_name == null) ? 0 : bank_name.hashCode());
//		result = prime * result + ((cheque_no == null) ? 0 : cheque_no.hashCode());
//		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
//		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
//		result = prime * result + ((date_created == null) ? 0 : date_created.hashCode());
//		long temp;
//		temp = Double.doubleToLongBits(discount);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result + ((entry_date == null) ? 0 : entry_date.hashCode());
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((invoice_no == null) ? 0 : invoice_no.hashCode());
//		temp = Double.doubleToLongBits(paid_amount);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		result = prime * result + ((payment_mode == null) ? 0 : payment_mode.hashCode());
//		result = prime * result + ((payment_type == null) ? 0 : payment_type.hashCode());
//		result = prime * result + ((recoveryDetails == null) ? 0 : recoveryDetails.hashCode());
//		temp = Double.doubleToLongBits(sub_total);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		temp = Double.doubleToLongBits(total);
//		result = prime * result + (int) (temp ^ (temp >>> 32));
//		return result;
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		SalesEntry other = (SalesEntry) obj;
//		if (bank_name == null) {
//			if (other.bank_name != null)
//				return false;
//		} else if (!bank_name.equals(other.bank_name))
//			return false;
//		if (cheque_no == null) {
//			if (other.cheque_no != null)
//				return false;
//		} else if (!cheque_no.equals(other.cheque_no))
//			return false;
//		if (custName == null) {
//			if (other.custName != null)
//				return false;
//		} else if (!custName.equals(other.custName))
//			return false;
//		if (customer == null) {
//			if (other.customer != null)
//				return false;
//		} else if (!customer.equals(other.customer))
//			return false;
//		if (date_created == null) {
//			if (other.date_created != null)
//				return false;
//		} else if (!date_created.equals(other.date_created))
//			return false;
//		if (Double.doubleToLongBits(discount) != Double.doubleToLongBits(other.discount))
//			return false;
//		if (entry_date == null) {
//			if (other.entry_date != null)
//				return false;
//		} else if (!entry_date.equals(other.entry_date))
//			return false;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (invoice_no == null) {
//			if (other.invoice_no != null)
//				return false;
//		} else if (!invoice_no.equals(other.invoice_no))
//			return false;
//		if (Double.doubleToLongBits(paid_amount) != Double.doubleToLongBits(other.paid_amount))
//			return false;
//		if (payment_mode == null) {
//			if (other.payment_mode != null)
//				return false;
//		} else if (!payment_mode.equals(other.payment_mode))
//			return false;
//		if (payment_type == null) {
//			if (other.payment_type != null)
//				return false;
//		} else if (!payment_type.equals(other.payment_type))
//			return false;
//		if (recoveryDetails == null) {
//			if (other.recoveryDetails != null)
//				return false;
//		} else if (!recoveryDetails.equals(other.recoveryDetails))
//			return false;
//		if (Double.doubleToLongBits(sub_total) != Double.doubleToLongBits(other.sub_total))
//			return false;
//		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
//			return false;
//		return true;
//	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getInvoice_no() {
		return invoice_no;
	}
	public void setInvoice_no(long invoice_no) {
		this.invoice_no = invoice_no;
	}
	
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public long getEntryId() {
		return entryId;
	}
	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public double getSub_total() {
		return sub_total;
	}
	public void setSub_total(double sub_total) {
		this.sub_total = sub_total;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	
	public PaymentMode getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getCheque_no() {
		return cheque_no;
	}
	public void setCheque_no(String cheque_no) {
		this.cheque_no = cheque_no;
	}
	public double getPaid_amount() {
		return paid_amount;
	}
	public void setPaid_amount(double paid_amount) {
		this.paid_amount = paid_amount;
	}
	public double getTotalPaidAmt() {
		return totalPaidAmt;
	}
	public void setTotalPaidAmt(double totalPaidAmt) {
		this.totalPaidAmt = totalPaidAmt;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTotalString() {
		return totalString;
	}
	public void setTotalString(String totalString) {
		this.totalString = totalString;
	}
	public SalesMan getSalesMan() {
		return salesMan;
	}
	public String getSalesManName() {
		return salesManName;
	}
	public void setSalesMan(SalesMan salesMan) {
		this.salesMan = salesMan;
	}
	public void setSalesManName(String salesManName) {
		this.salesManName = salesManName;
	}
	
}
