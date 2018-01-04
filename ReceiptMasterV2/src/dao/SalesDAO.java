package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.QualifiedNameable;

import javafx.collections.ObservableList;
import model.CashReportBean;
//import model.AccountEntries;
import model.Customer;
import model.DeliveryMemo;
import model.DeliveryProduct;
import model.PaymentMode;
import model.Product;
import model.PurchaseProduct;
//import model.RecoveryDetails;
import model.SalesEntry;
import model.SalesMan;
import model.SalesManReportBean;
import model.SalesProduct;
import model.SalesReportBean;
import model.SalesReturn;
import util.DBUtil;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class SalesDAO {
		public boolean createCustomer(Customer customer) {
			//System.out.println("Reached");
			boolean result=false;
			boolean result1=false;
			String stmt = "insert into rm_customer(fname,lname,gstin,gender,fullname,address,area,city,state,contactno,altcontactno,email) values" + "('','','"+customer.getGstin()+"','"+ customer.getGender() + "','" + customer.getFull_name() + "','"
					+ customer.getAddress() + "','" + customer.getArea() + "','" + customer.getCity() + "','"+customer.getState()+"','"
					+ customer.getContact() + "','" + customer.getAltcontno()+"','"+customer.getEmail()+"')";
			try {
//				DBUtil.dbConnect();
				DBUtil.dbExecuteUpdate(stmt);
				result=true;
			} catch (SQLException |ClassNotFoundException e) {
				System.out.println("Error occured while insert operation");
				e.printStackTrace();
			}finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			return result;
		}
		 
		public List<Customer> showCust()
		{
			String stmt = "Select * from rm_customer";
//			Long cust_id;
			ResultSet rs =null;
			ArrayList<Customer> custList = new ArrayList<Customer>();
//			ObservableList<Customer> custData =FXCollections.observableArrayList();
			try
			{
				 rs = DBUtil.dbExecuteQuery(stmt);
				
				while(rs.next()){
					Customer customer = new Customer();
					customer.setCust_id(rs.getLong("id"));
					customer.setShopName(rs.getString("fname"));
					customer.setAgency(rs.getString("lname"));
					customer.setGender(rs.getString("gender"));
					customer.setFull_name(rs.getString("fullname"));
					customer.setAddress(rs.getString("address"));
					customer.setArea(rs.getString("area"));
					customer.setCity(rs.getString("city"));
					customer.setState(rs.getString("state"));
					customer.setContact(rs.getString("contactno"));
					customer.setAltcontno(rs.getString("altcontactno"));
					customer.setGstin(rs.getString("gstin"));
					customer.setEmail(rs.getString("email"));
//					customer.setBirth_date(rs.getDate("birth_date"));
//					customer.setAnni_date(rs.getDate("anni_date"));
					custList.add(customer);
				}
				
			}catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if(rs!=null) {
					try {
						rs.close();
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			return custList;
		}
		
		public boolean updateCustomer(Customer customer) {
			boolean result=false;
			String custName=null;
//			String stmt="select fname,lname from rm_customer where id="+customer.getCust_id()+"";
//			try {
//				ResultSet rs =DBUtil.dbExecuteQuery(stmt);
//				while(rs.next()) {
//					custName=rs.getString("fname")+rs.getString("lname");
//				}
//			} catch (ClassNotFoundException | SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
			
			String stmt1="update rm_customer set fname='"+customer.getShopName()+"',lname='"+customer.getAgency()+"',gender='"+customer.getGender()+"',fullname='"+customer.getFull_name()+"',"
					+ "address='"+customer.getAddress()+"',area='"+customer.getArea()+"',city='"+customer.getCity()+"',state='"+customer.getState()+"',contactno='"+customer.getContact()+"',altcontactno='"+customer.getAltcontno()+"',email='"
							+customer.getEmail()+"' where id="+customer.getCust_id()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt1);
				result=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			return result;
			
		}
		
		public boolean deleteCustomer(Customer customer) {
			boolean result=false;
			String stmt="delete from rm_customer where id="+customer.getCust_id()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt);
				result=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			return result;
		}
		public Customer searchCustomer(String mobileNo,String shopName){
			String stmt = "select * from rm_customer where fname='"+shopName+"' and contactno='"+mobileNo+"'";
//			ArrayList<Customer> cu
			Customer customer = null;
			ResultSet rs =null;
			try {
				rs= DBUtil.dbExecuteQuery(stmt);
				
				while(rs.next()){
					customer = new Customer();
					customer.setCust_id(rs.getLong("id"));
					customer.setShopName(rs.getString("fname"));
					customer.setAgency(rs.getString("lname"));
					customer.setGender(rs.getString("gender"));
					customer.setFull_name(rs.getString("fullname"));
					customer.setAddress(rs.getString("address"));
					customer.setArea(rs.getString("area"));
					customer.setCity(rs.getString("city"));
					customer.setContact(rs.getString("contactno"));
					customer.setAltcontno(rs.getString("altcontactno"));
					customer.setEmail(rs.getString("email"));
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					if(rs!=null) {
						rs.close();
					}
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return customer;
		}
		public boolean createNewInvoice(SalesEntry salesEntry,ObservableList<SalesProduct> saleProdList){
			boolean result=false;
			boolean result1=false;
			boolean finalResult=false;
			boolean endResult=false;
		
			boolean insertUpdateFlag=false;
			String stmt=null;
//			boolean updateFlag=false;
//			long count=0;
			long quantity=0;
			double discount=0;
			long lastCustId=0;
			long lastSaleId=0;
			long lastSalesManId=0;
			
			List<String> sqlStmt = new ArrayList<>();
			Date sqlDate=new Date(salesEntry.getEntry_date().getTime());
			
			String stmt9 =null;
			if(salesEntry.getCustomer().getCust_id()==0) {
//				if(salesEntry.getCustomer().getFull_name()!=null) {
				 stmt9="insert into rm_customer(gstin,fullname,address,state,contactno) values('"+salesEntry.getCustomer().getGstin()+"','"+salesEntry.getCustomer().getFull_name()+"','"+salesEntry.getCustomer().getAddress()+"','"+salesEntry.getCustomer().getState()+"','"+salesEntry.getCustomer().getContact()+"')";
				 try {
						lastCustId=DBUtil.dbExecuteUpdateandReturn(stmt9);
						result=true;
						insertUpdateFlag=true;
					} catch (ClassNotFoundException | SQLException e2) {
						// TODO Auto-generated catch block
						result=false;
						e2.printStackTrace();
					}
				 finally {
						try {
						
							DBUtil.dbDisconnect();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
//				}
			}
			else {
				 stmt9="update rm_customer set gstin='"+salesEntry.getCustomer().getGstin()+"',fullname='"+salesEntry.getCustomer().getFull_name()+"',address='"+salesEntry.getCustomer().getAddress()+"',state='"+salesEntry.getCustomer().getState()+
						"',contactno='"+salesEntry.getCustomer().getContact()+"' where id="+salesEntry.getCustomer().getCust_id()+"";
				 try {
					DBUtil.dbExecuteUpdate(stmt9);
					result=true;
					insertUpdateFlag=false;
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					result=false;
					e.printStackTrace();
				}finally {
					try {
						
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(salesEntry.getSalesMan().getId()==0) {
				String stmt10="insert into rm_salesman(salesmanName,mobileNo,address,status) values('"+salesEntry.getSalesMan().getSalesManName()+"','"+salesEntry.getSalesMan().getContact()+"','"+
						salesEntry.getSalesMan().getAddress()+"',1)";
				try {
					lastSalesManId=DBUtil.dbExecuteUpdateandReturn(stmt10);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else {
				String stmt10="update rm_salesman set salesmanName='"+salesEntry.getSalesMan().getSalesManName()+"' where id="+salesEntry.getSalesMan().getId()+"";
				try {
					DBUtil.dbExecuteUpdate(stmt10);
					lastSalesManId=salesEntry.getSalesMan().getId();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(result) {
			if(insertUpdateFlag) {
				
				 stmt= "insert into rm_sales_entry(invoice_no,dm_id,customer_id,salesmanId,sub_total,discount,total,user_login_id,entry_date,date_created,payment_type,paid_amount,status) "
						+ "values("+salesEntry.getInvoice_no()+","+salesEntry.getDeliveryMemo().getId()+","+lastCustId+","+lastSalesManId+","+salesEntry.getSub_total()+","+salesEntry.getDiscount()+","+
						salesEntry.getTotal()+",0,'"+sqlDate+"','"+new Date(System.currentTimeMillis())+"','"+salesEntry.getPayment_type()+"',"+salesEntry.getPaid_amount()+",1)";
			}else {
				 stmt= "insert into rm_sales_entry(invoice_no,dm_id,customer_id,salesmanId,sub_total,discount,total,user_login_id,entry_date,date_created,payment_type,paid_amount,status) "
						+ "values("+salesEntry.getInvoice_no()+","+salesEntry.getDeliveryMemo().getId()+","+salesEntry.getCustomer().getCust_id()+","+lastSalesManId+","+salesEntry.getSub_total()+","+salesEntry.getDiscount()+","+
						salesEntry.getTotal()+",0,'"+sqlDate+"','"+new Date(System.currentTimeMillis())+"','"+salesEntry.getPayment_type()+"',"+salesEntry.getPaid_amount()+",1)";
			}
			
			
			try {
				lastSaleId=DBUtil.dbExecuteUpdateandReturn(stmt);
				result1=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result1=false;
				e.printStackTrace();
			}finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(result1) {
				String stmt8="insert into rm_payment_modes(sales_entry_id,cash_mode,cash_amt,bank_mode,bank_amt,card_mode,card_amt,voucher_mode,voucher_amt,voucher,bankname,chequeno,transId) "
						+ "values("+lastSaleId+",'"+salesEntry.getPaymentMode().getCashMode()+"',"+salesEntry.getPaymentMode().getCashAmount()+",'"+salesEntry.getPaymentMode().getBankMode()+"',"+
						salesEntry.getPaymentMode().getBankAmount()+",'"+salesEntry.getPaymentMode().getCardMode()+"',"+salesEntry.getPaymentMode().getCardAmount()+",'"+salesEntry.getPaymentMode().getVoucherMode()+"',"+
						salesEntry.getPaymentMode().getVoucherAmt()+",'"+salesEntry.getPaymentMode().getVoucherWalletCode()+"','"+salesEntry.getPaymentMode().getBankName()+"','"+salesEntry.getPaymentMode().getChequeNo()+"','"+
						salesEntry.getPaymentMode().getTransId()+"')";
				sqlStmt.add(stmt8);
			}
			for(SalesProduct sp:saleProdList){
		
			String stmt1 = "insert into rm_sales_product(sales_entry_id,product_id,dmno,quantity,unit,rate,discount,vat,subtotal,total,arr_id,status)"
					+ " values("+lastSaleId+","+sp.getProduct().getId()+",'',"+sp.getQuantity()+",'"+sp.getUnit()+"',"+
									sp.getRate()+","+sp.getDiscount()+","+sp.getGst()+","+sp.getSubTotal()+","+sp.getTotal()+",1,1)";
			sqlStmt.add(stmt1);
			if(sp.getId()==0) {
				quantity=sp.getCurrentStock()-sp.getQuantity();
//				discount=discount+sp.getDiscount();
				}else {
				
					quantity=sp.getCurrentStock()-sp.getQuantity()+sp.getDeliveryProduct().getQuantity();
				}
		
			String stmt4="update rm_product set quantity="+quantity+" where id="+sp.getProduct().getId()+"";
			sqlStmt.add(stmt4);		
			
			}
			if(salesEntry.getDeliveryMemo()!=null) {
				String stmt5="update rm_delivery_memo set status=0 where id="+salesEntry.getDeliveryMemo().getId()+"";
				sqlStmt.add(stmt5);
			}
			
			try {
//				DBUtil.dbConnect();
				DBUtil.executeBatch(sqlStmt);
				endResult=true;
				
			} catch (SQLException e) {
				endResult=false;
				System.out.println("Error occured while inserting sales entry in database");
				e.printStackTrace();
			}finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
//				if(salesEntry.getPayment_type().equals("Part Payment")) {
//					double remainAmt=salesEntry.getTotal()-salesEntry.getPaid_amount();
//					String stmt5="insert into rm_recovery_details(customer_id,invoice_no,installmentpaydate,payamount) values("+salesEntry.getCustomer().getCust_id()+","
//							+ salesEntry.getInvoice()+",'"+salesEntry.getEntry_date()+"',"+salesEntry.getPaid_amount()+")";
//					DBUtil.dbExecuteUpdate(stmt5);
//					
//				}
//			}
	
			
			}
			return endResult;
			
		}

			public List<SalesEntry> showInvoice(){
				String stmt = "select s.id,customer_id,invoice_no,gstin,fullname,state,c.address,c.contactno,salesmanId,sm.salesmanName,total,entry_date,payment_type,paid_amount from rm_sales_entry s "
						+ "inner join rm_customer c on s.customer_id=c.id"
						+ " inner join rm_salesman sm on sm.id=s.salesmanId"
						+ " where s.status=1"+
						" order by invoice_no";
//				ArrayList<String> saleList = new ArrayList<String>();
				List<SalesEntry> saleList = new ArrayList<SalesEntry>();
				ResultSet rs=null;
//				List<Customer> custList = new ArrayList<Customer>();
				
				try {
					rs = DBUtil.dbExecuteQuery(stmt);
					
					while(rs.next()){
//						SalesProduct saleProd = new SalesProduct();
						SalesEntry salesEntry = new SalesEntry();
						Customer customer = new Customer();
						salesEntry.setCustomer(customer);
						salesEntry.setId(rs.getLong("s.id"));
						salesEntry.setInvoice_no(rs.getInt("invoice_no"));
						Date date = rs.getDate("entry_date");
						Date utilDate = new Date(date.getTime());
						salesEntry.setEntry_date(utilDate);
//						salesEntry.setPaid_amount(rs.getDouble("paid_amount"));
						salesEntry.setTotal(rs.getDouble("total"));
//						salesEntry.setPayment_mode(rs.getString("payment_mode"));
						salesEntry.setPayment_type(rs.getString("payment_type"));
//						salesEntry.setBank_name(rs.getString("bank_name"));
//						salesEntry.setCheque_no(rs.getString("cheque_no"));
						salesEntry.setPaid_amount(rs.getDouble("paid_amount"));
//						saleList.add(ssalesEntry);
//						System.out.println(rs.getString("fname"));
						salesEntry.getCustomer().setFull_name(rs.getString("fullname"));
						salesEntry.getCustomer().setCust_id(rs.getLong("customer_id"));
//						salesEntry.getCustomer().setAgency(rs.getString("lname"));
						salesEntry.getCustomer().setState(rs.getString("state"));
						salesEntry.getCustomer().setAddress(rs.getString("address"));
						salesEntry.getCustomer().setGstin(rs.getString("gstin"));
						salesEntry.getCustomer().setContact(rs.getString("contactno"));
						salesEntry.setSalesMan(new SalesMan());
						salesEntry.getSalesMan().setId(rs.getLong("salesmanId"));
						salesEntry.getSalesMan().setSalesManName(rs.getString("salesmanName"));
						salesEntry.setSalesManName(rs.getString("salesmanName"));
						saleList.add(salesEntry);
						
//						custList.add(customer);
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						if(rs!=null) {
							rs.close();
						}
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return saleList;
			}
			
			
			public boolean updateSalesEntry(SalesEntry salesEntry,ObservableList<SalesProduct> saleProdList) {
				int count=0;
//				int count1[]=null;
				boolean result=false;
				boolean result1=false;
				boolean finalResult=false;
				boolean endResult=false;
				long quantity=0;
				double discount=0;
				long lastCustId=0;
				long lastSaleId=0;
				long lastSalesManId=0;
				
				boolean insertUpdateFlag=false;
				List<String> sqlStmt= new ArrayList<String>();
				Date sqlDate= new Date(salesEntry.getEntry_date().getTime());
				
				if(salesEntry.getSalesMan().getId()==0) {
					String stmt10="insert into rm_salesman(salesmanName,mobileNo,address) values('"+salesEntry.getSalesMan().getSalesManName()+"','"+salesEntry.getSalesMan().getContact()+"','"+
							salesEntry.getSalesMan().getAddress()+"')";
					try {
						lastSalesManId=DBUtil.dbExecuteUpdateandReturn(stmt10);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					String stmt10="update rm_salesman set salesmanName='"+salesEntry.getSalesMan().getSalesManName()+"' where id="+salesEntry.getSalesMan().getId()+"";
					try {
						DBUtil.dbExecuteUpdate(stmt10);
						lastSalesManId=salesEntry.getSalesMan().getId();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(salesEntry.getCustomer().getCust_id()!=0) {
					
					String stmt="update rm_customer set gstin='"+salesEntry.getCustomer().getGstin()+"',fullname='"+salesEntry.getCustomer().getFull_name()+"',address='"+salesEntry.getCustomer().getAddress()+"',state='"+salesEntry.getCustomer().getState()+
							"',contactno='"+salesEntry.getCustomer().getContact()+"' where id="+salesEntry.getCustomer().getCust_id()+"";
				
						sqlStmt.add(stmt);
						
				String stmt1="update rm_sales_entry"
						+ " set customer_id="+salesEntry.getCustomer().getCust_id()+",salesmanId="+lastSalesManId+",sub_total="+salesEntry.getSub_total()+",total="+salesEntry.getTotal()+",entry_date='"+sqlDate+
						"' where id="+salesEntry.getId()+"";
				sqlStmt.add(stmt1);
				
				try {
					DBUtil.executeBatch(sqlStmt);
					result=true;
				} catch ( SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				}
				else {
					String stmt2="insert into rm_customer(gstin,fullname,address,state,contactno) values('"+salesEntry.getCustomer().getGstin()+"','"+salesEntry.getCustomer().getFull_name()+"','"+salesEntry.getCustomer().getAddress()+"','"+salesEntry.getCustomer().getState()+"','"+salesEntry.getCustomer().getContact()+"')";
					 try {
							lastCustId=DBUtil.dbExecuteUpdateandReturn(stmt2);
							result1=true;
//							insertUpdateFlag=true;
						} catch (ClassNotFoundException | SQLException e2) {
							// TODO Auto-generated catch block
							result1=false;
							e2.printStackTrace();
						}finally {
							try {
								
								DBUtil.dbDisconnect();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					 
					 
					 String stmt3="update rm_sales_entry"
								+ " set customer_id="+lastCustId+",salesmanId="+lastSalesManId+",sub_total="+salesEntry.getSub_total()+",total="+salesEntry.getTotal()+",entry_date='"+sqlDate+
								"' where id="+salesEntry.getId()+"";
					 try {
						DBUtil.dbExecuteUpdate(stmt3);
						result=true;
					} catch (ClassNotFoundException | SQLException e) {
						result=false;
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						try {
							
							DBUtil.dbDisconnect();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					 
				}
				
				
				
				
				sqlStmt.clear();
				if(result) {
						String stmt="delete from rm_payment_modes where sales_entry_id="+salesEntry.getId()+"";
						sqlStmt.add(stmt);
						String stmt8="insert into rm_payment_modes(sales_entry_id,cash_mode,cash_amt,bank_mode,bank_amt,card_mode,card_amt,voucher_mode,voucher_amt,voucher,bankname,chequeno,transId) "
								+ "values("+salesEntry.getId()+",'"+salesEntry.getPaymentMode().getCashMode()+"',"+salesEntry.getPaymentMode().getCashAmount()+",'"+salesEntry.getPaymentMode().getBankMode()+"',"+
								salesEntry.getPaymentMode().getBankAmount()+",'"+salesEntry.getPaymentMode().getCardMode()+"',"+salesEntry.getPaymentMode().getCardAmount()+",'"+salesEntry.getPaymentMode().getVoucherMode()+"',"+
								salesEntry.getPaymentMode().getVoucherAmt()+",'"+salesEntry.getPaymentMode().getVoucherWalletCode()+"','"+salesEntry.getPaymentMode().getBankName()+"','"+salesEntry.getPaymentMode().getChequeNo()+"','"+
								salesEntry.getPaymentMode().getTransId()+"')";
						sqlStmt.add(stmt8);
						
				
//				String stmt1="delete from rm_sales_product where sales_entry_id="+salesEntry.getId()+"";
//				try {
//					count=DBUtil.dbExecuteUpdate(stmt1);
//					
//				} catch (ClassNotFoundException | SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				finally {
//					try {
//						
//						DBUtil.dbDisconnect();
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
					for(SalesProduct sp:saleProdList){
						if(sp.getId()==0) {
//							System.out.println("salesProductId"+sp.getId());
						String stmt3 = "insert into rm_sales_product(sales_entry_id,product_id,dmno,quantity,unit,rate,discount,vat,subtotal,total,arr_id,status)"
								+ " values("+salesEntry.getId()+","+sp.getProduct().getId()+",'',"+sp.getQuantity()+",'"+sp.getUnit()+"',"+
												sp.getRate()+","+sp.getDiscount()+","+sp.getGst()+","+sp.getSubTotal()+","+sp.getTotal()+",1,1)";
						sqlStmt.add(stmt3);
						quantity=sp.getCurrentStock()-sp.getQuantity();
						}else {
							String stmt3="update rm_sales_product set product_id="+sp.getProduct().getId()+",quantity="+sp.getQuantity()+",unit='"+sp.getUnit()+"',rate="+sp.getRate()+",discount="+
						sp.getDiscount()+",vat="+sp.getGst()+",subtotal="+sp.getSubTotal()+",total="+sp.getTotal()+" where id="+sp.getId()+"";
							sqlStmt.add(stmt3);
							quantity=sp.getCurrentStock()-sp.getQuantity()+sp.getPrevQty();
						}
						
						String stmt4="update rm_product set quantity="+quantity+" where id="+sp.getProduct().getId()+"";
						sqlStmt.add(stmt4);		
						
						}
				
				try {
					DBUtil.executeBatch(sqlStmt);
					result1=true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					result1=false;
					e.printStackTrace();
				}
				finally {
					try {
						
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				sqlStmt.clear();
//				if(finalResult) {
//					for(SalesProduct sp:saleProdList) {
//						String stmt3="select quantity from rm_product where id="+sp.getProduct().getId()+"";
//						ResultSet rs=null;
//						try {
//							 rs= DBUtil.dbExecuteQuery(stmt3);
//							while(rs.next()) {
//								quantity=rs.getInt("quantity");
//							}
//							quantity=quantity-sp.getQuantity();
//							String stmt4="update rm_product set quantity="+quantity+" where id="+sp.getProduct().getId()+"";
//							sqlStmt.add(stmt4);
//							
//						} catch (ClassNotFoundException | SQLException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}finally {
//							try {
//								if(rs!=null) {
//									rs.close();
//								}
//								DBUtil.dbDisconnect();
//							} catch (SQLException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						
//						
//					}
//					work from here
//					System.out.println("Invoice No"+salesEntry.getInvoice_no());
					
					
//					}
				
				}
				
				
				return result1;
					
			}
			public boolean deleteSalesEntry(SalesEntry salesEntry) {
				boolean result=false;
				String stmt="update rm_sales_entry set status=0 where id="+salesEntry.getId()+"";
				try {
					DBUtil.dbExecuteUpdate(stmt);
					result=true;
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally {
					try {
						
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return result;
				
			}
			
			public List<Customer> getCustomerNames(){
				String stmt="select id,gstin,fullname,state,address,city,contactno from rm_customer";
				List<Customer> custList = new ArrayList<Customer>();
				ResultSet rs=null;
				try {
					rs = DBUtil.dbExecuteQuery(stmt);
					while(rs.next()) {
						Customer customer = new Customer();
						customer.setCust_id(rs.getLong("id"));
//						customer.setShopName(rs.getString("fname"));
//						customer.setAgency(rs.getString("lname"));
						customer.setFull_name(rs.getString("fullname"));
						customer.setState(rs.getString("state"));
						customer.setCity(rs.getString("city"));
						customer.setAddress(rs.getString("address"));
						customer.setContact(rs.getString("contactno"));
						customer.setGstin(rs.getString("gstin"));
						custList.add(customer);
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						if(rs!=null) {
							rs.close();
						}
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return custList;
				
			}
			

			

	public long getSalesInvoiceId() {
		
	String stmt="select max(invoice_no) from rm_sales_entry";
	ResultSet rs=null;
		long count=0;
		try {
		rs = DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				count=rs.getLong(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
		
	}
	
	
	public List<SalesProduct> getProductDetails(SalesEntry salesEntry){
		List<SalesProduct> salesList=new ArrayList<SalesProduct>();
		String stmt= "select rp.id,p.productname,hsn,p.quantity,sell_price,gst,cess,product_id,rp.quantity,rp.unit,rate,discount,vat,subtotal,total from rm_sales_product rp inner join rm_product p on rp.product_id= p.id"
				+ " where sales_entry_id="+salesEntry.getId();
		ResultSet rs =null;
		try {
			rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesProduct salesProduct = new SalesProduct();
				Product product = new Product();
				salesProduct.setProduct(product);
				salesProduct.setSaleEntry(salesEntry);
				salesProduct.getSaleEntry().setId(salesEntry.getId());
				salesProduct.setId(rs.getLong("rp.id"));
				salesProduct.setProd_name(rs.getString("productname"));
				salesProduct.getProduct().setQuantity(rs.getLong("p.quantity"));
				salesProduct.getProduct().setId(rs.getLong("product_id"));
				salesProduct.getProduct().setGst(rs.getDouble("gst"));
				salesProduct.getProduct().setCess(rs.getDouble("cess"));
				salesProduct.getProduct().setHsnNo(rs.getLong("hsn"));
				salesProduct.setUnit(rs.getString("unit"));
				salesProduct.setQuantity(rs.getInt("rp.quantity"));
				salesProduct.setPrevQty(rs.getLong("rp.quantity"));
				salesProduct.setRate(rs.getDouble("rate"));
				salesProduct.setGst(rs.getDouble("vat"));
				salesProduct.setDiscount(rs.getDouble("discount"));
//				System.out.println("Subtotal"+rs.getDouble("subtotal"));
				salesProduct.setSubTotal(rs.getDouble("subtotal"));
				salesProduct.setTotal(rs.getDouble("total"));
				salesProduct.getProduct().setSellPrice(rs.getDouble("sell_price"));
				salesList.add(salesProduct);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return salesList;
	}

	public PaymentMode getPaymentModes(SalesEntry salesEntry){
//		List<PaymentMode> payList= new ArrayList<>();
		
		String stmt="select * from rm_payment_modes where sales_entry_id="+salesEntry.getId();
		ResultSet rs=null;
		PaymentMode paymentMode= new PaymentMode();
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				
				paymentMode.setId(rs.getLong("id"));
				paymentMode.setCashMode(rs.getString("cash_mode"));
				paymentMode.setCashAmount(rs.getDouble("cash_amt"));
				paymentMode.setBankMode(rs.getString("bank_mode"));
				paymentMode.setBankName(rs.getString("bankname"));
				paymentMode.setChequeNo(rs.getString("chequeno"));
				paymentMode.setBankAmount(rs.getDouble("bank_amt"));
				paymentMode.setCardMode(rs.getString("card_mode"));
				paymentMode.setTransId(rs.getString("transId"));
				paymentMode.setCardAmount(rs.getDouble("card_amt"));
				paymentMode.setVoucherMode(rs.getString("voucher_mode"));
				paymentMode.setVoucherWalletCode(rs.getString("voucher"));
				paymentMode.setVoucherAmt(rs.getDouble("voucher_amt"));
//				payList.add(paymentMode);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return paymentMode;
		
	}
	
	public List<SalesReturn> getReturnDetails(SalesEntry salesEntry){
		List<SalesReturn> returnList= new ArrayList<>();
		String stmt="select * from rm_sales_return where sales_entry_id="+salesEntry.getId()+"";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesReturn salesReturn= new SalesReturn();
				SalesProduct salesProduct= new SalesProduct();
				salesReturn.setSalesProduct(salesProduct);
				salesReturn.setSalesEntry(salesEntry);
				salesReturn.setId(rs.getLong("id"));
				salesReturn.getSalesProduct().setId(rs.getLong("sales_product_id"));
				Product product= new Product();
				salesReturn.setProduct(product);
				salesReturn.getProduct().setId(rs.getLong("sales_product_items_id"));
				salesReturn.setReturnQuantity(rs.getLong("return_quantity"));
				salesReturn.setReturnDate(rs.getDate("date_created"));
				returnList.add(salesReturn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnList;
	}
	
	
	public boolean returnSales(SalesEntry salesEntry,ObservableList<SalesReturn> returnList) {
		boolean result=false;
		List<String> sqlStmt= new ArrayList<>();
		long quantity=0;
		for(SalesReturn sr:returnList) {
			String stmt="insert into rm_sales_return(sales_entry_id,sales_product_id,sales_product_items_id,return_quantity,date_created,status) values("+
					salesEntry.getId()+","+sr.getSalesProduct().getId()+","+sr.getProduct().getId()+","+sr.getReturnQuantity()+",'"+new Date(System.currentTimeMillis())+"',1)";
			sqlStmt.add(stmt);
			
			quantity=sr.getProduct().getQuantity()+sr.getReturnQuantity();
			String stmt1="update rm_product set quantity="+quantity+" where id="+sr.getProduct().getId()+"";
			sqlStmt.add(stmt1);
			quantity=0;
		}
		
		try {
			DBUtil.executeBatch(sqlStmt);
			result=true;
		} catch (SQLException e) {
			result=false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

	public List<SalesEntry> getSalesInvoiceList(){
		List<SalesEntry> invoiceList= new ArrayList<>();
		String stmt="select se.id,invoice_no,customer_id,entry_date,fullname from rm_sales_entry se inner join rm_customer c on c.id=se.customer_id";
		ResultSet rs= null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesEntry salesEntry= new SalesEntry();
				salesEntry.setId(rs.getLong("id"));
				salesEntry.setInvoice_no(rs.getLong("invoice_no"));
				salesEntry.setCustomer(new Customer());
				salesEntry.getCustomer().setCust_id(rs.getLong("customer_id"));
				salesEntry.getCustomer().setFull_name(rs.getString("fullname"));
				salesEntry.setEntry_date(rs.getDate("entry_date"));
				invoiceList.add(salesEntry);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return invoiceList;
	
	}
	public  List<SalesReportBean> SalesReportData(String FromDate,String ToDate){
		   String stmt = " SELECT c.fname, c.lname, c.gstin as customer_gstin, c.fullname, c.address as customer_add, c.area, c.city, c.state as customer_state, c.contactno as customerContact,c.email, s.invoice_no, s.customer_id,s.payment_mode,"+
		" sp.sales_entry_id, sp.product_id, sp.quantity as saeles_product_qty,sp.rate as saeles_product_rate,s.entry_date as invoice_date,"+
		" sp.discount as saeles_product_discount,"+
		" sp.unit as saeles_product_unit,p.productname, p.unit, p.hsn, p.quantity, p.sell_price, p.gst, p.cess,"+
		" (select((saeles_product_rate*saeles_product_qty)-saeles_product_discount))  as taxable_ammount,"+
		" (select (CASE"+
		  " when c.state<>'maharashtra'  then (select(p.gst)) else 0"+
		  "  END) )as other_state_igst_rate,"+
		 " (select (CASE c.state"+
		 "  when 'maharashtra'  then (select(p.gst/2)) else 0"+
		" END) )as maha_sgst_rate,"+
		" (select (CASE c.state"+
		 "  when 'maharashtra'  then (select(taxable_ammount*(maha_sgst_rate/100))) else 0"+
		" END) )as maha_ammount,"+
		" (select (CASE"+
		  " when  c.state<>  'maharashtra'  then (select(taxable_ammount*(other_state_igst_rate/100))) else 0"+
		" END) )as other_ammount,"+
		" (select (CASE"+
		 "  when  c.state<>  'maharashtra'  then (select(taxable_ammount+other_ammount)) else (select(taxable_ammount+maha_ammount+maha_ammount))"+

		" END) ) as Goss_Ammount,"+
		" (select ROUND(sum(goss_Ammount))) as total_Grand"+
		" FROM rm_sales_entry s"+
		" INNER JOIN rm_sales_product sp ON s.id = sp.sales_entry_id"+
		" LEFT JOIN rm_customer c ON s.customer_id=c.id"+
		" LEFT JOIN rm_product p ON sp.product_id = p.id "+
		" where s.entry_date>= '"+FromDate+ "' and s.entry_date<= '"+ToDate+"'";
		//   ArrayList<Customer> cu
		   List<SalesReportBean> reportList=new ArrayList<>();
		   try {
		    ResultSet rs = DBUtil.dbExecuteQuery(stmt);

		    while(rs.next()){
		     SalesReportBean s=new SalesReportBean();
		     s.setInvoice_date(rs.getDate("invoice_date"));
		     s.setInvoice_no(rs.getLong("invoice_no"));
		     s.setFullname(rs.getString("fullname"));
		     s.setCity(rs.getString("city"));
		     s.setCustomer_state(rs.getString("customer_state"));
		     s.setProductname(rs.getString("productname"));
		     s.setHSn(rs.getLong("hsn"));
		     s.setSGSTamt(rs.getDouble("maha_ammount"));
		     s.setSGSTrate(rs.getDouble("maha_sgst_rate"));
		     s.setCGSTRate(rs.getDouble("maha_sgst_rate"));
		     s.setCGSTamt(rs.getDouble("maha_ammount"));
		     s.setIGSTRate(rs.getDouble("other_state_igst_rate"));
		     s.setIGSTamt(rs.getDouble("other_ammount"));
		     s.setTotal(rs.getDouble("Goss_Ammount"));
		     reportList.add(s);


		    }
		   } catch (ClassNotFoundException | SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		   return reportList;
		  }
	
	public static List<CashReportBean> CashReportData(String FromDate,String ToDate){
		String stmt = " select "+
"(case"+
  "  when se.id is null"+
 "   then 'Purchase' "+
 "   when pe.id is null "+
 "   then 'Sales' end) as SalesPurchase,"+
" (CASE "+
" when se.entry_date is null "+
 "   then pe.purchase_date "+
 "   when pe.purchase_date is null "+
  "  then se.entry_date end) CashDate,"+

" (CASE 'Cash Received '"+
" when se.id is null "+
   "  then pay.cash_amt "+
	" end) as CashReceived,"+

" (CASE 'Cash Payment '"+
" when pe.id is null "+
 "    then pay.cash_amt "+
" 	end) as CashPayment,"+

" (CASE 'Cash In HAnd '"+
" when pe.id is null "+
 "    then pay.cash_amt "+
" when se.id is null "+
 "    then pay.cash_amt "+
" 	end) as CashInHAnd,"+
" pay.id From rm_payment_modes pay "+
" LEFT JOIN  rm_sales_entry se on pay.sales_entry_id=se.id AND se.entry_date>='"+FromDate+"' AND  se.entry_date<='"+ToDate+"' "+
" LEFT JOIN rm_purchase_entry pe on pay.purchase_entry_id=pe.id AND pe.purchase_date>='"+FromDate+"' AND  pe.purchase_date<='"+ToDate+"' group by pe.id,se.id ";
//		ArrayList<Customer> cu
		List<CashReportBean> reportList=new ArrayList<>();
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(stmt);

			while(rs.next()){
				CashReportBean c=new CashReportBean();
				c.setCashDate(rs.getDate("CashDate"));
				c.setSalesPurchase(rs.getString("SalesPurchase"));
				c.setCashReceived(rs.getDouble("CashReceived"));
				c.setCashPayment(rs.getDouble("CashPayment"));
				c.setCashInHAnd(rs.getDouble("CashInHAnd"));
				reportList.add(c);


			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return reportList;
	}
	
	public static List<CashReportBean> CashReportDataDayWise(String FromDate,String ToDate){
		  String stmt = " select "+
		  "(case"+
		    "  when se.id is null"+
		   "   then 'purchase' "+
		   "   when pe.id is null "+
		   "   then 'Sales' end) as SalesPurchase,"+
		  " (CASE "+
		  " when se.entry_date is null "+
		   "   then pe.purchase_date "+
		   "   when pe.purchase_date is null "+
		    "  then se.entry_date end) CashDate,"+

		  " (CASE 'Cash Received '"+
		  " when se.id is null "+
		     "  then pay.cash_amt "+
		   " end) as CashReceived,"+

		  " (CASE 'Cash Payment '"+
		  " when pe.id is null "+
		   "    then pay.cash_amt "+
		  "  end) as CashPayment,"+

		  " (CASE 'Cash In HAnd '"+
		  " when pe.id is null "+
		   "    then pay.cash_amt "+
		  " when se.id is null "+
		   "    then pay.cash_amt "+
		  "  end) as CashInHAnd,"+
		  " pay.id From rm_payment_modes pay "+
		  " LEFT JOIN  rm_sales_entry se on pay.sales_entry_id=se.id AND se.entry_date>='"+FromDate+"' AND  se.entry_date<='"+ToDate+"' "+
		  " LEFT JOIN rm_purchase_entry pe on pay.purchase_entry_id=pe.id AND pe.purchase_date>='"+FromDate+"' AND  pe.purchase_date<='"+ToDate+"'  GROUP BY pe.purchase_date,se.entry_date ";
//		    ArrayList<Customer> cu
		    List<CashReportBean> reportList=new ArrayList<>();
		    try {
		     ResultSet rs = DBUtil.dbExecuteQuery(stmt);

		     while(rs.next()){
		      CashReportBean c=new CashReportBean();
		      c.setCashDate(rs.getDate("CashDate"));
		      c.setSalesPurchase(rs.getString("SalesPurchase"));
		      c.setCashReceived(rs.getDouble("CashReceived"));
		      c.setCashPayment(rs.getDouble("CashPayment"));
		      c.setCashInHAnd(rs.getDouble("CashInHAnd"));
		      reportList.add(c);


		     }
		    } catch (ClassNotFoundException | SQLException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		    return reportList;
		   }
	
	
	public List<DeliveryMemo> showDeliveryMemo(){
		List<DeliveryMemo> deliveryList=new ArrayList<>();
		String stmt="select d.id,dm_no,s.invoice_no,d.customer_id,d.entry_date,d.total,advance_amt,d.status,gstin,fullname,address,state,contactno from rm_delivery_memo d left join rm_sales_entry s on s.dm_id=d.id inner join rm_customer c on c.id=d.customer_id";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				DeliveryMemo deliveryMemo= new DeliveryMemo();
				deliveryMemo.setId(rs.getLong("id"));
				deliveryMemo.setDm_no(rs.getString("dm_no"));
				deliveryMemo.setCustomer(new Customer());
				deliveryMemo.setCustName(rs.getString("fullname"));
				deliveryMemo.setInvoiceNo(rs.getLong("invoice_no"));
				deliveryMemo.getCustomer().setCust_id(rs.getLong("customer_id"));
				deliveryMemo.getCustomer().setGstin(rs.getString("gstin"));
				deliveryMemo.getCustomer().setFull_name(rs.getString("fullname"));
				deliveryMemo.getCustomer().setAddress(rs.getString("address"));
				deliveryMemo.getCustomer().setState(rs.getString("state"));
				deliveryMemo.getCustomer().setContact(rs.getString("contactno"));
				deliveryMemo.setAdvanceAmt(rs.getDouble("advance_amt"));
				deliveryMemo.setEntry_date(rs.getDate("entry_date"));
				deliveryMemo.setStatus(rs.getInt("d.status"));
				deliveryMemo.setTotal(rs.getDouble("total"));
				deliveryList.add(deliveryMemo);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return deliveryList;
 	}
	public boolean deletedeliveryMemo(DeliveryMemo deliveryMemo) {
		boolean result= false;
		boolean endResult= false;
		List<String> sqlStmt=new ArrayList<>();
		List<DeliveryProduct> deliverPrdList=new ArrayList<>();
		String stmt="delete from rm_delivery_memo where id="+deliveryMemo.getId()+"";
		sqlStmt.add(stmt);
			
			String stmt1="select product_id,dp.quantity,p.quantity from rm_dm_product dp inner join rm_product p on p.id=dp.product_id where dm_id="+deliveryMemo.getId()+"";
			try {
				ResultSet rs=DBUtil.dbExecuteQuery(stmt1);
				while(rs.next()) {
					DeliveryProduct deliveryProduct= new DeliveryProduct();
//					deliveryProduct.setId(rs.getLong("id"));
					deliveryProduct.setProduct(new Product());
					deliveryProduct.getProduct().setId(rs.getLong("product_id"));
					deliveryProduct.setCurrentStock(rs.getLong("p.quantity"));
					deliveryProduct.setQuantity(rs.getInt("dp.quantity"));
//					deliveryProduct.setRate(rs.getDouble("rate"));
//					deliveryProduct.setUnit(rs.getString("unit"));
					deliverPrdList.add(deliveryProduct);
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		for(DeliveryProduct dp:deliverPrdList) {
			String stmt2="update rm_product set quantity="+(dp.getCurrentStock()+dp.getQuantity())+" where id="+dp.getProduct().getId()+"";
			sqlStmt.add(stmt2);
		}
		
		try {
			DBUtil.executeBatch(sqlStmt);
			endResult=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			endResult=false;
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return endResult;
		
	}
	
	public long getDeliveryId() {
		long deliveryId=0;
		String stmt="select max(dm_no) from rm_delivery_memo";
		ResultSet rs= null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				deliveryId=rs.getLong(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return deliveryId;
	}
	
	public boolean createDeliveryMemo(DeliveryMemo deliveryMemo,ObservableList<DeliveryProduct> prodData) {
		boolean result=false;
		boolean insertUpdateFlag=false;
		long lastDmId=0;
		long lastCustId=0;
		boolean result1=false;
		boolean endResult=false;
		long quantity=0;
		List<String> sqlStmt= new ArrayList<>();
		if(deliveryMemo.getCustomer().getCust_id()==0) {
//			if(salesEntry.getCustomer().getFull_name()!=null) {
			String stmt="insert into rm_customer(gstin,fullname,address,state,contactno) values('"+deliveryMemo.getCustomer().getGstin()+"','"+deliveryMemo.getCustomer().getFull_name()+"','"+deliveryMemo.getCustomer().getAddress()+"','"+deliveryMemo.getCustomer().getState()+"','"+deliveryMemo.getCustomer().getContact()+"')";
			 try {
					lastCustId=DBUtil.dbExecuteUpdateandReturn(stmt);
					result=true;
					insertUpdateFlag=true;
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					result=false;
					e2.printStackTrace();
				}
			 finally {
					try {
					
						DBUtil.dbDisconnect();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//			}
		}
		else {
			String stmt1="update rm_customer set gstin='"+deliveryMemo.getCustomer().getGstin()+"',fullname='"+deliveryMemo.getCustomer().getFull_name()+"',address='"+deliveryMemo.getCustomer().getAddress()+"',state='"+deliveryMemo.getCustomer().getState()+
					"',contactno='"+deliveryMemo.getCustomer().getContact()+"' where id="+deliveryMemo.getCustomer().getCust_id()+"";
			 try {
				DBUtil.dbExecuteUpdate(stmt1);
				lastCustId=deliveryMemo.getCustomer().getCust_id();
				result=true;
				insertUpdateFlag=false;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result=false;
				e.printStackTrace();
			}finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(result) {
		String stmt="insert into rm_delivery_memo(dm_no,customer_id,entry_date,total,advance_amt,status) values('"+deliveryMemo.getDm_no()+"',"+lastCustId+",'"+deliveryMemo.getEntry_date()+"',"+
		deliveryMemo.getTotal()+","+deliveryMemo.getAdvanceAmt()+",1)";
		try {
			lastDmId=DBUtil.dbExecuteUpdateandReturn(stmt);
			result1=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			result1=false;
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		}
		
		if(result1) {
			for(DeliveryProduct dp:prodData) {
				String stmt3="insert into rm_dm_product(dm_id,product_id,unit,quantity,rate,total) values("+lastDmId+","+dp.getProduct().getId()+",'"+dp.getUnit()+"',"+dp.getQuantity()+","+dp.getRate()+","+
			dp.getTotal()+")";
				sqlStmt.add(stmt3);
				quantity=dp.getCurrentStock()-dp.getQuantity();
//				discount=discount+sp.getDiscount();
				String stmt4="update rm_product set quantity="+quantity+" where id="+dp.getProduct().getId()+"";
				sqlStmt.add(stmt4);		
			}
			
			try {
				DBUtil.executeBatch(sqlStmt);
				endResult=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				endResult=false;
				e.printStackTrace();
			}finally {
				try {
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return endResult;
	}
	
	public List<DeliveryProduct> getDeliveryProduct(DeliveryMemo deliveryMemo){
		List<DeliveryProduct> deliveryList=new ArrayList<>();
		String stmt="select dp.id,product_id,dp.unit,dp.quantity,rate,total,productname,hsn,p.quantity,sell_price,category,subgroup,gst from rm_dm_product dp inner join"
				+ " rm_product p on p.id=dp.product_id where dm_id="+deliveryMemo.getId()+"";
		try {
			ResultSet rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				DeliveryProduct deliveryProduct= new DeliveryProduct();
				deliveryProduct.setProduct(new Product());
				deliveryProduct.setId(rs.getLong("dp.id"));
				deliveryProduct.getProduct().setId(rs.getLong("product_id"));
				deliveryProduct.getProduct().setProduct_name(rs.getString("productname"));
				deliveryProduct.getProduct().setHsnNo(rs.getLong("hsn"));
				deliveryProduct.getProduct().setQuantity(rs.getLong("p.quantity"));
				deliveryProduct.getProduct().setSellPrice(rs.getDouble("sell_price"));
				deliveryProduct.getProduct().setCategory(rs.getString("category"));
				deliveryProduct.getProduct().setSubGroup(rs.getString("subgroup"));
				deliveryProduct.getProduct().setGst(rs.getDouble("gst"));
				deliveryProduct.setQuantity(rs.getInt("dp.quantity"));
				deliveryProduct.setRate(rs.getDouble("rate"));
				deliveryProduct.setTotal(rs.getDouble("total"));
				deliveryProduct.setUnit(rs.getString("dp.unit"));
				deliveryList.add(deliveryProduct);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return deliveryList;
	}
	
	public boolean addStock(SalesProduct salesProduct) {
		boolean result=false;
		
		String stmt="update rm_product set quantity="+(salesProduct.getProduct().getQuantity()+salesProduct.getQuantity())+" where id="+salesProduct.getProduct().getId()+"";
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	public boolean createSalesMan(SalesMan salesMan) {
		boolean result=false;
		String stmt="insert into rm_salesman(salesmanName,mobileNo,address,status) values('"+salesMan.getSalesManName()+"','"+salesMan.getContact()+"','"+salesMan.getAddress()+"',1)";
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public List<SalesMan> showSalesManList(){
		List<SalesMan> saleManList= new ArrayList<>();
		String stmt="select id,salesmanName,mobileNo,address,status from rm_salesman";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesMan salesMan= new SalesMan();
				salesMan.setId(rs.getLong("id"));
				salesMan.setSalesManName(rs.getString("salesmanName"));
				salesMan.setContact(rs.getString("mobileNo"));
				salesMan.setAddress(rs.getString("address"));
				salesMan.setStatus(rs.getLong("status"));
				saleManList.add(salesMan);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return saleManList;
	}
	public List<SalesMan> getSalesManList(){
		List<SalesMan> saleManList= new ArrayList<>();
		String stmt="select id,salesmanName,mobileNo,address from rm_salesman where status=1";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesMan salesMan= new SalesMan();
				salesMan.setId(rs.getLong("id"));
				salesMan.setSalesManName(rs.getString("salesmanName"));
				salesMan.setContact(rs.getString("mobileNo"));
				salesMan.setAddress(rs.getString("address"));
				saleManList.add(salesMan);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return saleManList;
	}
	
	public boolean deleteSalesMan(SalesMan man) {
		boolean result=false;
		String stmt="update rm_salesman set status=0 where id="+man.getId();
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean updateSalesMan(SalesMan salesMan) {
		boolean result= false;
		String stmt="update rm_salesman set salesmanName='"+salesMan.getSalesManName()+"',mobileNo='"+salesMan.getContact()+"',address='"+salesMan.getAddress()+
		"' where id="+salesMan.getId();
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public List<SalesManReportBean> SalesManReportData(String fromDate,String toDate){
		List<SalesManReportBean> salesManList= new ArrayList<>();
		String stmt="select s.salesmanId,sm.salesmanName,s.invoice_no, s.total,s.entry_date from rm_sales_entry s " + 
				"inner join rm_salesman sm on s.salesmanId=sm.id where s.entry_date>='"+fromDate+"' and s.entry_date<='"+toDate+"'";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesManReportBean salesMan= new SalesManReportBean();
				salesMan.setInvoice_date(rs.getDate("entry_date"));
				salesMan.setSalesManName(rs.getString("salesmanName"));
				salesMan.setInvoiceNo(rs.getLong("invoice_no"));
				salesMan.setTotal(rs.getDouble("total"));
				salesManList.add(salesMan);
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return salesManList;
		
	}
	
//	public boolean createNewDMInvoice(DeliveryMemo deliveryMemo,SalesEntry salesEntry,ObservableList<SalesProduct> saleProdList) {
//		boolean result=false;
//		boolean result1=false;
//		boolean finalResult=false;
//		boolean endResult=false;
//	
//		boolean insertUpdateFlag=false;
//		String stmt=null;
////		boolean updateFlag=false;
////		long count=0;
//		long quantity=0;
//		double discount=0;
//		long lastCustId=0;
//		long lastSaleId=0;
//		
//		List<String> sqlStmt = new ArrayList<>();
//		Date sqlDate=new Date(salesEntry.getEntry_date().getTime());
//		
//		 String stmt9="update rm_customer set gstin='"+salesEntry.getCustomer().getGstin()+"',fullname='"+salesEntry.getCustomer().getFull_name()+"',address='"+salesEntry.getCustomer().getAddress()+"',state='"+salesEntry.getCustomer().getState()+
//					"',contactno='"+salesEntry.getCustomer().getContact()+"' where id="+salesEntry.getCustomer().getCust_id()+"";
//			 try {
//				DBUtil.dbExecuteUpdate(stmt9);
//				result=true;
////				insertUpdateFlag=false;
//			} catch (ClassNotFoundException | SQLException e) {
//				// TODO Auto-generated catch block
//				result=false;
//				e.printStackTrace();
//			}finally {
//				try {
//					
//					DBUtil.dbDisconnect();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		
//		
//		if(result) {
//			 stmt= "insert into rm_sales_entry(invoice_no,dm_id,customer_id,sub_total,discount,total,user_login_id,entry_date,date_created,payment_type,paid_amount,status) "
//					+ "values("+salesEntry.getInvoice_no()+","+salesEntry.getDeliveryMemo().getId()+","+salesEntry.getCustomer().getCust_id()+","+salesEntry.getSub_total()+","+salesEntry.getDiscount()+","+
//					salesEntry.getTotal()+",0,'"+sqlDate+"','"+new Date(System.currentTimeMillis())+"','"+salesEntry.getPayment_type()+"',"+salesEntry.getPaid_amount()+",1)";
//		
//		try {
//			lastSaleId=DBUtil.dbExecuteUpdateandReturn(stmt);
//			result1=true;
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			result1=false;
//			e.printStackTrace();
//		}finally {
//			try {
//				
//				DBUtil.dbDisconnect();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if(result1) {
//			String stmt8="insert into rm_payment_modes(sales_entry_id,cash_mode,cash_amt,bank_mode,bank_amt,card_mode,card_amt,voucher_mode,voucher_amt,voucher,bankname,chequeno,transId) "
//					+ "values("+lastSaleId+",'"+salesEntry.getPaymentMode().getCashMode()+"',"+salesEntry.getPaymentMode().getCashAmount()+",'"+salesEntry.getPaymentMode().getBankMode()+"',"+
//					salesEntry.getPaymentMode().getBankAmount()+",'"+salesEntry.getPaymentMode().getCardMode()+"',"+salesEntry.getPaymentMode().getCardAmount()+",'"+salesEntry.getPaymentMode().getVoucherMode()+"',"+
//					salesEntry.getPaymentMode().getVoucherAmt()+",'"+salesEntry.getPaymentMode().getVoucherWalletCode()+"','"+salesEntry.getPaymentMode().getBankName()+"','"+salesEntry.getPaymentMode().getChequeNo()+"','"+
//					salesEntry.getPaymentMode().getTransId()+"')";
//			sqlStmt.add(stmt8);
//		}
//		for(SalesProduct sp:saleProdList){
//			
//		String stmt1 = "insert into rm_sales_product(sales_entry_id,product_id,dmno,quantity,unit,rate,discount,vat,subtotal,total,arr_id,status)"
//				+ " values("+lastSaleId+","+sp.getProduct().getId()+",'',"+sp.getQuantity()+",'"+sp.getUnit()+"',"+
//								sp.getRate()+","+sp.getDiscount()+","+sp.getGst()+","+sp.getSubTotal()+","+sp.getTotal()+",1,1)";
//		sqlStmt.add(stmt1);
//		if(sp.getId()==0) {
//		quantity=sp.getCurrentStock()-sp.getQuantity();
////		discount=discount+sp.getDiscount();
//		}else {
//			quantity=sp.getCurrentStock()-sp.getQuantity()+sp.getDeliveryProduct().getQuantity();
//		}
//		
//		String stmt4="update rm_product set quantity="+quantity+" where id="+sp.getProduct().getId()+"";
//		sqlStmt.add(stmt4);		
//		
//		}
//		
//		try {
////			DBUtil.dbConnect();
//			DBUtil.executeBatch(sqlStmt);
//			endResult=true;
//			
//		} catch (SQLException e) {
//			endResult=false;
//			System.out.println("Error occured while inserting sales entry in database");
//			e.printStackTrace();
//		}finally {
//			try {
//				
//				DBUtil.dbDisconnect();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		}
//		
//	}
//	
	
	
	
}


