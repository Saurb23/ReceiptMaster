package model;

public class PaymentMode {
	long id;
	long srNo;
	SalesEntry salesEntry;
	String cashMode;
	double cashAmount;
	
	String bankMode;
	String bankName;
	String chequeNo;
	double bankAmount;
	
	String cardMode;
	String transId;
	double cardAmount;
	
	String voucherMode;
	String voucherWalletCode;
	double voucherAmt;
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
	public SalesEntry getSalesEntry() {
		return salesEntry;
	}
	public void setSalesEntry(SalesEntry salesEntry) {
		this.salesEntry = salesEntry;
	}
	public String getCashMode() {
		return cashMode;
	}
	public void setCashMode(String cashMode) {
		this.cashMode = cashMode;
	}
	public double getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	public String getBankMode() {
		return bankMode;
	}
	public void setBankMode(String bankMode) {
		this.bankMode = bankMode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public double getBankAmount() {
		return bankAmount;
	}
	public void setBankAmount(double bankAmount) {
		this.bankAmount = bankAmount;
	}
	public String getCardMode() {
		return cardMode;
	}
	public void setCardMode(String cardMode) {
		this.cardMode = cardMode;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public double getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(double cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getVoucherMode() {
		return voucherMode;
	}
	public void setVoucherMode(String voucherMode) {
		this.voucherMode = voucherMode;
	}
	public String getVoucherWalletCode() {
		return voucherWalletCode;
	}
	public void setVoucherWalletCode(String voucherWalletCode) {
		this.voucherWalletCode = voucherWalletCode;
	}
	public double getVoucherAmt() {
		return voucherAmt;
	}
	public void setVoucherAmt(double voucherAmt) {
		this.voucherAmt = voucherAmt;
	}
	
	
}
