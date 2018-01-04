package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Category {
	long id;
	String category;
	long productId;
	String subGroup;
	long srNo;
	
	public Category() {
//		super();
	}
	public Category(long id, String category,long srNO) {
//		super();
		this.id = id;
		this.category = category;
		this.srNo=srNO;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}
	

}
