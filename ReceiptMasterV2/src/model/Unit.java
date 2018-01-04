package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Unit {
	long id;
	String unit;
	String addinfo;
	
	
	public Unit() {
//		super();
	}


	public Unit(long id, String unit, String addinfo) {
//		super();
		this.id = id;
		this.unit = unit;
		this.addinfo = addinfo;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAddinfo() {
		return addinfo;
	}
	public void setAddinfo(String addinfo) {
		this.addinfo = addinfo;
	}
	

}
