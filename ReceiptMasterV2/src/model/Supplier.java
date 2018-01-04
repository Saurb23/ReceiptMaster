package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Supplier {
	long supplier_id;
	long srNo;
	String supplierName;
	String ownerName;
	String vatTinNo;
	String address;
	String contactNO;
	String email;
	String gstState;
	
	
	public Supplier() {
//		super();
	}
	
	
	public Supplier(String supplierName) {
		super();
		this.supplierName = supplierName;
	}


	public Supplier(long supplier_id, String supplierName, String ownerName, String vatTinNo, String address,
			String contactNO, String gstState,long srNo) {
//		super();
		this.supplier_id = supplier_id;
		this.supplierName = supplierName;
		this.ownerName = ownerName;
		this.vatTinNo = vatTinNo;
		this.address = address;
		this.contactNO = contactNO;
		this.gstState = gstState;
		this.srNo=srNo;
	}

	public long getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(long supplier_id) {
		this.supplier_id = supplier_id;
	}
	public long getSrNo() {
		return srNo;
	}


	public void setSrNo(long srNo) {
		this.srNo = srNo;
	}


	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getVatTinNo() {
		return vatTinNo;
	}
	public void setVatTinNo(String vatTinNo) {
		this.vatTinNo = vatTinNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNO() {
		return contactNO;
	}
	public void setContactNO(String contactNO) {
		this.contactNO = contactNO;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGstState() {
		return gstState;
	}
	public void setGstState(String gstState) {
		this.gstState = gstState;
	}
	

}
