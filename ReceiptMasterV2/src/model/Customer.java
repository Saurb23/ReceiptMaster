package model;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class Customer {
		
		long cust_id;
		long srNo;
		String shopName;
		String agency;
		String gender;
		String full_name;
		String address;
		String area;
		String city;
		String state;
		String contact;
		String altcontno;
		String email;
		String gstin;
//		private Customer customer;
//		Date birth_date;
//		Date anni_date;
		
		public Customer(){
			
		}

		public long getCust_id() {
			return cust_id;
		}

		public void setCust_id(long cust_id) {
			this.cust_id = cust_id;
		}

		public long getSrNo() {
			return srNo;
		}

		public void setSrNo(long srNo) {
			this.srNo = srNo;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getAgency() {
			return agency;
		}

		public void setAgency(String agency) {
			this.agency = agency;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getFull_name() {
			return full_name;
		}

		public void setFull_name(String full_name) {
			this.full_name = full_name;
		}

		public String getGstin() {
			return gstin;
		}

		public void setGstin(String gstin) {
			this.gstin = gstin;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getAltcontno() {
			return altcontno;
		}

		public void setAltcontno(String altcontno) {
			this.altcontno = altcontno;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Customer(long cust_id, String shopName, String agency, String gender, String full_name, String address,
				String state, String city, String contact, String altcontno, String email,String gstin,long srNo) {
			super();
			this.cust_id = cust_id;
			this.shopName = shopName;
			this.agency = agency;
			this.gender = gender;
			this.full_name = full_name;
			this.address = address;
			this.state = state;
			this.city = city;
			this.contact = contact;
			this.altcontno = altcontno;
			this.email = email;
			this.gstin=gstin;
			this.srNo=srNo;
		}

//		public Customer(String shopName2, String agency2, String contact2, String area2) {
//			// TODO Auto-generated constructor stub
//			
//		}

		public Customer(String shopName, String agency, String city, String contact) {
			super();
			this.shopName = shopName;
			this.agency = agency;
			this.city = city;
			this.contact = contact;
		}
		
		
//		public Customer(Customer customer) {
//			this.setCustomer(customer);
//		}
//		public Customer(String cust_id, String shopName, String agency, String gender, String full_name, String address,
//				String area, String city, String contact, String altcontno, String email) {
//			super();
//			this.cust_id = cust_id);
//			this.shopName = new SimpleStringProperty(shopName);
//			this.agency = new SimpleStringProperty(agency);
//			this.gender = new SimpleStringProperty(gender);
//			this.full_name = new SimpleStringProperty(full_name);
//			this.address = new SimpleStringProperty(address);
//			this.area = new SimpleStringProperty(area);
//			this.city = new SimpleStringProperty(city);
//			this.contact = new SimpleStringProperty(contact);
//			this.altcontno = new SimpleStringProperty(altcontno);
//			this.email = new SimpleStringProperty(email);
////			this.birth_date = birth_date;
////			this.anni_date = anni_date;
//		}
//		public String getCust_id() {
//			return cust_id;
//		}
//		public void setCust_id(String cust_id) {
//			this.cust_id = cust_id;
//			cust_id.set(cus_id);
//		}
//		public String getshopName() {
//			return shopName.get();
//		}
//		public void setShopName(String shoName) {
////			this.shopName = shopName;
//			this.shopName = shoName;
//			shopName.set);
//		}
//		public String getAgency() {
//			return agency.get();
//		}
//		public void setAgency(String agencyName) {
////			this.agency = agency;
//			agency.set(agencyName);
//		}
//		public String getGender() {
//			return gender.get();
//		}
//		public void setGender(String gendr) {
////			this.gender = gender;
//			gender.set(gendr);
//		}
//		public String getFull_name() {
//			return full_name.get();
//		}
//		public void setFull_name(String fullName) {
////			this.full_name = full_name;
//			full_name.set(fullName);
//		}
//		public String getAddress() {
//			return address.get();
//		}
//		public void setAddress(String adress) {
////			this.address = address;
//			address.set(adress);
//		}
//		public String getArea() {
//			return area.get();
//		}
//		public void setArea(String areas) {
////			this.area = area;
//			area.set(areas);
//		}
//		public String getCity() {
//			return city.get();
//		}
//		public void setCity(String city1) {
////			this.city = city;
//			city.set(city1);
//		}
//		public String getContact() {
//			return contact.get();
//		}
//		public void setContact(String contact1) {
////			this.contact = contact;
//			contact.set(contact1);
//		}
//		public String getAltcontno() {
//			return altcontno.get();
//		}
//		public void setAltcontno(String altcontNo) {
////			this.altcontno = altcontno;
//			altcontno.set(altcontNo);
//		}
//		public String getEmail() {
//			return email.get();
//		}
//		public void setEmail(String emails) {
////			this.email = email;
//			email.set(emails);
//		}
//		public Date getBirth_date() {
//			return birth_date;
//		}
//		public void setBirth_date(Date birth_date) {
//			this.birth_date = birth_date;
//		}
//		public Date getAnni_date() {
//			return anni_date;
//		}
//		public void setAnni_date(Date anni_date) {
//			this.anni_date = anni_date;
//		}	
//		public Customer getCustomer() {
//			return customer;
//		}
//		public void setCustomer(Customer customer) {
//			this.customer = customer;
//		}
		
	
}
