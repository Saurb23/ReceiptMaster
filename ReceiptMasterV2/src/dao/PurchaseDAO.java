package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.BarcodeGenerator;
import application.BarcodeProduct;
import controller.PurchaseController;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.Category;
import model.PaymentMode;
import model.Product;
import model.PurchaseEntry;
import model.PurchaseProduct;
import model.PurchaseReturn;
import model.SubCategory;
import model.Supplier;
import model.Unit;
import util.DBUtil;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class PurchaseDAO {
	
/***********************************
 * createSupplier()
 * @param supplier
 * @return boolean
 ***********************************/
	
	public boolean createSupplier(Supplier supplier){
		boolean result = false;
		boolean result1=false;
		String stmt = "insert into rm_supplier(supplier_name,owner_name,vattinno,address,contactno,email,gststate,status)"+
					" values('"+supplier.getSupplierName()+"','"+supplier.getOwnerName()+"','"+supplier.getVatTinNo()+"','"+
					supplier.getAddress()+"','"+supplier.getContactNO()+"','','"+supplier.getGstState()+"',1)";
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result = true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
	
/********************************
 * showSupplier()
 * @return List<Supplier>
 *********************************/
	
	public List<Supplier> showSupplier(){
		String stmt = "select * from rm_supplier where status=1";
		
		List<Supplier> supplierList = new ArrayList<Supplier>();
		ResultSet rs=null;
		
		try {
			rs = DBUtil.dbExecuteQuery(stmt);
			
			while(rs.next()){
				Supplier supplier = new Supplier();
				supplier.setSupplier_id(rs.getLong("id"));
				supplier.setSupplierName(rs.getString("supplier_name"));
				supplier.setOwnerName(rs.getString("owner_name"));
				supplier.setAddress(rs.getString("address"));
				supplier.setVatTinNo(rs.getString("vattinno"));
				supplier.setContactNO(rs.getString("contactno"));
				supplier.setGstState(rs.getString("gststate"));
				supplierList.add(supplier);
				
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
		return supplierList;
		
		
	}
	
	/****************************
	 * getSupplierNames()
	 * @return List<Supplier>
	 ******************************/
	public List<Supplier> getSupplierNames(){
		String stmt = "select id,supplier_name,vattinno,contactno,gststate from rm_supplier where status=1";
		List<Supplier> supplierList= new ArrayList<Supplier>();
		ResultSet rs=null;
		try {
			rs = DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
				Supplier supplier = new Supplier();
				supplier.setSupplier_id(rs.getLong("id"));
				supplier.setSupplierName(rs.getString("supplier_name"));
				supplier.setGstState(rs.getString("gststate"));
				supplier.setContactNO(rs.getString("contactno"));
				supplier.setVatTinNo(rs.getString("vattinno"));
				supplierList.add(supplier);
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
		return supplierList;
		
	}
	
/********************************
 * updateSupplier()
 * @param supplier
 * @return boolean
 ****************************/
	public boolean updateSupplier(Supplier supplier){
		boolean result =false;
		String supplier_name=null;
		ResultSet rs=null;
		
		String stmt="select supplier_name from rm_supplier where id="+supplier.getSupplier_id()+"";
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				supplier_name=rs.getString("supplier_name");
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		
		
		String stmt1 ="update rm_supplier "
				+ "set supplier_name='"+supplier.getSupplierName()+"',owner_name='"+supplier.getOwnerName()+"',contactno='"+supplier.getContactNO()+"',"+
				"vattinno='"+supplier.getVatTinNo()+"',address='"+supplier.getAddress()+"',gststate='"+supplier.getGstState()+"'"
						+ " where id="+supplier.getSupplier_id()+"";
		
			try {
				DBUtil.dbExecuteUpdate(stmt1);
				result=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				System.out.print("Error occurred while UPDATE Operation: ");
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
	
/*********************************
 * deleteSupplier()	
 * @param supplier
 * @return boolean
 *******************************/
	public boolean deleteSupplier(Supplier supplier){
		boolean result = false;
		List<String> sqlStmt=new ArrayList<>();
		
		String stmt = "update rm_supplier set status=0 where id="+supplier.getSupplier_id()+"";
		sqlStmt.add(stmt);
		
		try {
			DBUtil.executeBatch(sqlStmt);
			result = true;
		} catch ( SQLException e) {
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
	
/*************
 * createPurchaseEntry()	
 * @return
 */
	public boolean createPurchaseEntry(PurchaseEntry purchaseEntry,ObservableList<PurchaseProduct> prodData){
		boolean result=false;
		boolean result1=false;
//		boolean finalResult=false;
		boolean endResult=false;
//		long count=0;
		long quantity=0;
//		double discount=0;
		long lastpurchId=0;
		long lastSupplierId=0;
		long lastProdId=0;
		boolean insertUpdateFlag=false;
//		String unit=null;
		String stmt1=null;
		List<String> sqlStmt= new ArrayList<String>();
		Date sqlDate=new Date(purchaseEntry.getPurchaseDate().getTime());
//		List<Long> productIdList=new ArrayList<>();
		List<Product> productList=new ArrayList<>();
		PurchaseController.productIdList.clear();
		
		if(purchaseEntry.getSupplier().getSupplier_id()==0) {
			String stmt="insert into rm_supplier(supplier_name,owner_name,vattinno,address,contactno,email,gststate,status)"+
					" values('"+purchaseEntry.getSupplier().getSupplierName()+"','"+purchaseEntry.getSupplier().getOwnerName()+"','"+purchaseEntry.getSupplier().getVatTinNo()+"','"+
					purchaseEntry.getSupplier().getAddress()+"','"+purchaseEntry.getSupplier().getContactNO()+"','','"+purchaseEntry.getSupplier().getGstState()+"',1)";
			try {
				lastSupplierId=DBUtil.dbExecuteUpdateandReturn(stmt);
				insertUpdateFlag=true;
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
			
		}else {
			String stmt="update rm_supplier set supplier_name='"+purchaseEntry.getSupplier().getSupplierName()+"',contactno='"+purchaseEntry.getSupplier().getContactNO()+
					"',gststate='"+purchaseEntry.getSupplier().getGstState()+"',vattinno='"+purchaseEntry.getSupplier().getVatTinNo()+"' where id="+purchaseEntry.getSupplier().getSupplier_id()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt);
				insertUpdateFlag=false;
				result=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result=false;
				e.printStackTrace();
			}
		}
		if(result) {
		if(insertUpdateFlag) {
		 stmt1 = "insert into rm_purchase_entry(purchase_invoice_no,purchase_entry_id,supplier_id,purchase_date,invoice_date,material,subtotal,discount,total,payment_type,paid_amount,status)"
				+ " values('"+purchaseEntry.getInvoiceNo()+"',"+purchaseEntry.getPurchaseId()+","+lastSupplierId+",'"+sqlDate+"','"+new Date(System.currentTimeMillis())+"','"+purchaseEntry.getMaterial()+"',"+
				
				purchaseEntry.getSubTotal()+",0,"+purchaseEntry.getTotal()+",'"+purchaseEntry.getPaymentType()+"',"+
				purchaseEntry.getPaidAmount()+",1)";
		
		}else {
			 stmt1 = "insert into rm_purchase_entry(purchase_invoice_no,purchase_entry_id,supplier_id,purchase_date,invoice_date,material,subtotal,discount,total,payment_type,paid_amount,status)"
					+ " values('"+purchaseEntry.getInvoiceNo()+"',"+purchaseEntry.getPurchaseId()+","+purchaseEntry.getSupplier().getSupplier_id()+",'"+sqlDate+"','"+new Date(System.currentTimeMillis())+"','"+purchaseEntry.getMaterial()+"',"+
					
					purchaseEntry.getSubTotal()+",0,"+purchaseEntry.getTotal()+",'"+purchaseEntry.getPaymentType()+"',"+
					purchaseEntry.getPaidAmount()+",1)";
			
			
		}
		
		try {
			lastpurchId=DBUtil.dbExecuteUpdateandReturn(stmt1);
			result1=true;
		} catch (ClassNotFoundException | SQLException e) {
			result1=false;
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
		}
		
		if(result1) {
			String stmt2="insert into rm_payment_modes(purchase_entry_id,cash_mode,cash_amt,bank_mode,bank_amt,card_mode,card_amt,voucher_mode,voucher_amt,voucher,bankname,chequeno,transId) "
						+ "values("+lastpurchId+",'"+purchaseEntry.getPaymentMode().getCashMode()+"',"+purchaseEntry.getPaymentMode().getCashAmount()+",'"+purchaseEntry.getPaymentMode().getBankMode()+"',"+
						purchaseEntry.getPaymentMode().getBankAmount()+",'"+purchaseEntry.getPaymentMode().getCardMode()+"',"+purchaseEntry.getPaymentMode().getCardAmount()+",'"+purchaseEntry.getPaymentMode().getVoucherMode()+"',"+
						purchaseEntry.getPaymentMode().getVoucherAmt()+",'"+purchaseEntry.getPaymentMode().getVoucherWalletCode()+"','"+purchaseEntry.getPaymentMode().getBankName()+"','"+purchaseEntry.getPaymentMode().getChequeNo()+"','"+
						purchaseEntry.getPaymentMode().getTransId()+"')";
				sqlStmt.add(stmt2);
	
			
		for(PurchaseProduct p:prodData){
			
			if(p.getCategory().getId()==0) {
				String stmt3="insert into rm_category(category) values('"+p.getGroup()+"')";
				sqlStmt.add(stmt3);
			}
			
			if(p.getSubCategory().getId()==0) {
				String stmt4="insert into rm_subcategory(subcategory) values('"+p.getSubGroup()+"')";
				sqlStmt.add(stmt4);
			}
			if(p.getUnitObj().getId()==0) {
				String stmt5="insert into rm_unit(unit) values('"+p.getUnit()+"')";
				sqlStmt.add(stmt5);
			}
			if(p.getProduct().getId()==0) {
				String barcode=BarcodeGenerator.generateBarcode(p.getProduct());
				String path="C: jasperoutput barcodeImg "+p.getProduct().getProduct_name().replaceAll(" ", "")+".png";
				String stmt6 = "insert into rm_product(barcode,productname,unit,hsn,quantity,buy_price,sell_price,category,subgroup,info,gst,cess,barcodePath) values('"+barcode+"','"+ p.getProduct().getProduct_name() + "','"+p.getProduct().getUnit()+"',"+
						p.getProduct().getHsnNo()+","+p.getProduct().getQuantity()+","+p.getProduct().getBuyPrice()+","+p.getProduct().getSellPrice()+",'"+p.getProduct().getCategory()+"','"+p.getProduct().getSubGroup()+"','',"+p.getProduct().getGst()+","+p.getProduct().getCess()+
						",'"+path+"')";
				
				try {
					lastProdId=DBUtil.dbExecuteUpdateandReturn(stmt6);
//					PurchaseController.productIdList.add(lastProdId);
					productList.add(p.getProduct());
					insertUpdateFlag=true;
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					insertUpdateFlag=false;
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
				quantity=p.getCurrentStock()+p.getProduct().getQuantity();
				String stmt7="update rm_product set productname='"+p.getProduct().getProduct_name()+"',unit='"+p.getProduct().getUnit()+"',hsn="+p.getProduct().getHsnNo()+",quantity="+quantity+",sell_price="+p.getProduct().getSellPrice()+
						",category='"+p.getProduct().getCategory()+"',subgroup='"+p.getProduct().getSubGroup()+"',gst="+p.getProduct().getGst()+" where id="+p.getProduct().getId()+"";
				lastProdId=p.getProduct().getId();
				sqlStmt.add(stmt7);
				insertUpdateFlag=true;
			}
			
			if(insertUpdateFlag) {
				
			String stmt8 = "insert into rm_purchase_product(purchase_entry_id,product_id,quantity,unit,rate,discount,vat,subtotal,amount,arr_id,status)"
					+ " values("+lastpurchId+","+lastProdId+","+p.getQuantity()+",'"+p.getUnit()+"',"+p.getRate()+","+p.getDiscount()+","+p.getProduct().getGst()+","+p.getSubTotal()+","+p.getAmount()+",1,1)";
						
					sqlStmt.add(stmt8);
					
			}
			
			
		}
		
//		String stmt9="truncate barcodetemp";
//		sqlStmt.add(stmt9);
		
		try {
			DBUtil.executeBatch(sqlStmt);
			endResult=true;
		} catch (SQLException e) {
			endResult=false;
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
		PurchaseController.itemList.clear();
		PurchaseController.itemList=BarcodeProduct.generateBarcodeProduct(productList);
		}
		
		return endResult;
	}
	public List<PurchaseEntry> showPurchaseEntry(){
		List<PurchaseEntry> purchaseList = new ArrayList<PurchaseEntry>();
		String stmt = "select p.id,purchase_invoice_no,purchase_entry_id,supplier_id,supplier_name,vattinno,contactno,gststate,purchase_date,subtotal,"
				+ "total from  rm_purchase_entry p inner join rm_supplier s on s.id=p.supplier_id ORDER BY p.purchase_entry_id asc";
		ResultSet rs=null;
		
		try {
			 rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
				PurchaseEntry purchaseEntry = new PurchaseEntry();
				Supplier supplier =new Supplier();
				purchaseEntry.setId(rs.getLong("id"));
				purchaseEntry.setInvoiceNo(rs.getString("purchase_invoice_no"));
				purchaseEntry.setPurchaseId(rs.getLong("purchase_entry_id"));
				purchaseEntry.setSupplier(supplier);
				purchaseEntry.getSupplier().setSupplier_id(rs.getLong("supplier_id"));
				purchaseEntry.setSupplierName(rs.getString("supplier_name"));
				purchaseEntry.getSupplier().setVatTinNo(rs.getString("vattinno"));
				purchaseEntry.getSupplier().setContactNO(rs.getString("contactno"));
				purchaseEntry.getSupplier().setGstState(rs.getString("gststate"));
				purchaseEntry.setPurchaseDate(rs.getDate("purchase_date"));
//				purchaseEntry.setMaterial(rs.getString("material"));
//				purchaseEntry.setInvoiceDate(rs.getDate("invoice_date"));
//				purchaseEntry.setInvoiceDate(null);
				purchaseEntry.setSubTotal(rs.getDouble("subtotal"));
//				purchaseEntry.setDiscount(rs.getDouble("discount"));
				purchaseEntry.setTotal(rs.getDouble("total"));
//				purchaseEntry.setPaymentType(rs.getString("payment_type"));
//				purchaseEntry.setPaymentMode(rs.getString("payment_mode"));
//				purchaseEntry.setPaidAmount(rs.getDouble("paid_amount"));
				purchaseList.add(purchaseEntry);
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
		return purchaseList;
	}
	
	
/*********
 * updatePurchaseEntry()
 * @return 
 */
	public boolean updatePurchaseEntry(PurchaseEntry purchaseEntry,ObservableList<PurchaseProduct> prodData) {
		int count=0;
//		int count1[]=null;
		boolean result=false;
		boolean result1=false;
		boolean finalResult=false;
		boolean endResult=false;
		long quantity=0;
		double discount=0;
		long lastSupplierId=0;
		long lastProdId=0;
		long lastpurchId=0;
		boolean insertUpdateFlag=false;
		List<String> sqlStmt= new ArrayList<String>();
		List<Product> productList=new ArrayList<>();
		Date sqlDate=new Date(purchaseEntry.getPurchaseDate().getTime());
//		System.out.println("Invoice"+purchaseEntry.getInvoiceNo());
//		String invoiceNo="";
//		if(!purchaseEntry.getInvoiceNo().isEmpty())
//			invoiceNo = purchaseEntry.getInvoiceNo();
		
		if(purchaseEntry.getSupplier().getSupplier_id()==0) {
			String stmt="insert into rm_supplier(supplier_name,owner_name,vattinno,address,contactno,email,gststate,status)"+
					" values('"+purchaseEntry.getSupplier().getSupplierName()+"','"+purchaseEntry.getSupplier().getOwnerName()+"','"+purchaseEntry.getSupplier().getVatTinNo()+"','"+
					purchaseEntry.getSupplier().getAddress()+"','"+purchaseEntry.getSupplier().getContactNO()+"','','"+purchaseEntry.getSupplier().getGstState()+"',1)";
			try {
				lastSupplierId=DBUtil.dbExecuteUpdateandReturn(stmt);
				insertUpdateFlag=true;
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
		}else {
			String stmt="update rm_supplier set supplier_name='"+purchaseEntry.getSupplier().getSupplierName()+"',contactno='"+purchaseEntry.getSupplier().getContactNO()+
					"',gststate='"+purchaseEntry.getSupplier().getGstState()+"',vattinno='"+purchaseEntry.getSupplier().getVatTinNo()+"' where id="+purchaseEntry.getSupplier().getSupplier_id()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt);
				lastSupplierId=purchaseEntry.getSupplier().getSupplier_id();
				insertUpdateFlag=false;
				result=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result=false;
				e.printStackTrace();
			}
		}
		sqlStmt.clear();
		if(result) {
			String stmt = "update rm_purchase_entry"
					+ " set purchase_invoice_no='"+purchaseEntry.getInvoiceNo()+"', supplier_id="+lastSupplierId+",purchase_date='"+sqlDate+"',subtotal="+purchaseEntry.getSubTotal()+","
							+ "total="+purchaseEntry.getTotal()+" where id="+purchaseEntry.getId()+"";
			sqlStmt.add(stmt);
//			String stmt3="delete from rm_payment_modes where purchase_entry_id="+purchaseEntry.getId()+"";
//			sqlStmt.add(stmt3);
			String stmt3="update rm_payment_modes set cash_mode='"+purchaseEntry.getPaymentMode().getCashMode()+"',cash_amt="+purchaseEntry.getPaymentMode().getCashAmount()+",bank_mode='"+purchaseEntry.getPaymentMode().getBankMode()+"',bank_amt="+purchaseEntry.getPaymentMode().getBankAmount()+
					",card_mode='"+purchaseEntry.getPaymentMode().getCardMode()+"',card_amt="+purchaseEntry.getPaymentMode().getCardAmount()+",voucher_mode='"+purchaseEntry.getPaymentMode().getVoucherMode()+"',voucher_amt="+purchaseEntry.getPaymentMode().getVoucherAmt()+",voucher='"+
					purchaseEntry.getPaymentMode().getVoucherWalletCode()+"',bankname='"+purchaseEntry.getPaymentMode().getBankName()+"',chequeno='"+purchaseEntry.getPaymentMode().getChequeNo()+"',transId='"+purchaseEntry.getPaymentMode().getTransId()+
					"' where purchase_entry_id="+purchaseEntry.getId()+"";
			sqlStmt.add(stmt3);
			
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
		}
		sqlStmt.clear();
		if(result1) {
			for(PurchaseProduct p:prodData) {
				if(p.getCategory().getId()==0) {
					String stmt3="insert into rm_category(category) values('"+p.getGroup()+"')";
					sqlStmt.add(stmt3);
				}
				
				if(p.getSubCategory().getId()==0) {
					String stmt4="insert into rm_subcategory(subcategory) values('"+p.getSubGroup()+"')";
					sqlStmt.add(stmt4);
				}
				if(p.getUnitObj().getId()==0) {
					String stmt5="insert into rm_unit(unit) values('"+p.getUnit()+"')";
					sqlStmt.add(stmt5);
				}
				if(p.getProduct().getId()==0) {
					String barcode=BarcodeGenerator.generateBarcode(p.getProduct());
					String path="C: jasperoutput barcodeImg "+p.getProduct().getProduct_name().replaceAll(" ", "")+".png";
					String stmt6 = "insert into rm_product(barcode,productname,unit,hsn,quantity,buy_price,sell_price,category,subgroup,info,gst,cess,barcodePath) values('"+barcode+"','"+ p.getProduct().getProduct_name() + "','"+p.getProduct().getUnit()+"',"+
							p.getProduct().getHsnNo()+","+p.getProduct().getQuantity()+","+p.getProduct().getBuyPrice()+","+p.getProduct().getSellPrice()+",'"+p.getProduct().getCategory()+"','"+p.getProduct().getSubGroup()+"','',"+p.getProduct().getGst()+","+p.getProduct().getCess()+
							",'"+path+"')";
					
					try {
						lastProdId=DBUtil.dbExecuteUpdateandReturn(stmt6);
//						PurchaseController.productIdList.add(lastProdId);
						productList.add(p.getProduct());
						insertUpdateFlag=true;
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						insertUpdateFlag=false;
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
					quantity=p.getCurrentStock()+p.getProduct().getQuantity()-p.getPrevQty();
					String stmt7="update rm_product set productname='"+p.getProduct().getProduct_name()+"',unit='"+p.getProduct().getUnit()+"',hsn="+p.getProduct().getHsnNo()+",quantity="+quantity+",sell_price="+p.getProduct().getSellPrice()+
							",category='"+p.getProduct().getCategory()+"',subgroup='"+p.getProduct().getSubGroup()+"',gst="+p.getProduct().getGst()+" where id="+p.getProduct().getId()+"";
					lastProdId=p.getProduct().getId();
					sqlStmt.add(stmt7);
					insertUpdateFlag=true;
				}
				if(p.getId()==0) {	
					String stmt8 = "insert into rm_purchase_product(purchase_entry_id,product_id,quantity,unit,rate,discount,vat,subtotal,amount,arr_id,status)"
							+ " values("+purchaseEntry.getId()+","+lastProdId+","+p.getQuantity()+",'"+p.getUnit()+"',"+p.getRate()+","+p.getDiscount()+","+p.getProduct().getGst()+","+p.getSubTotal()+","+p.getAmount()+",1,1)";
								
							sqlStmt.add(stmt8);
							
					}else {
						String stmt8="update rm_purchase_product set product_id="+lastProdId+",quantity="+p.getQuantity()+",unit='"+p.getUnit()+"',rate="+p.getRate()+
								",discount="+p.getDiscount()+",vat="+p.getProduct().getGst()+",subtotal="+p.getSubTotal()+",amount="+p.getAmount()+" where id="+p.getId()+"";
						sqlStmt.add(stmt8);
					}
				
				
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
			PurchaseController.itemList.clear();
			PurchaseController.itemList=BarcodeProduct.generateBarcodeProduct(productList);
			
		}
		
		
		return endResult;
	}

/***************************************
 * deletePurchaseEntry()
 * @param purchaseEntry
 * @return boolean
 ***********************************/
	public boolean deletePurchaseEntry(PurchaseEntry purchaseEntry) {
		String stmt = "delete from rm_purchase_entry where purchase_entry_id="+purchaseEntry.getPurchaseId()+"";
		boolean result= false;
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error occured during delete operation");
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
	

	
	
/*********************************************
 * 	getSuplierList()
 * @return List<PurchaseEntry>
 ********************************************/
	public List<PurchaseEntry> getSupplierList(){
		List<PurchaseEntry> supplierList = new ArrayList<PurchaseEntry>();
		String stmt = "select p.supplier_id,p.id,s.supplier_name from rm_purchase_entry p inner join rm_supplier s on p.supplier_id=s.id";
		ResultSet rs=null;
		try {
			rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
				PurchaseEntry purchaseEntry = new PurchaseEntry();
				Supplier supplier =new Supplier();
				purchaseEntry.setSupplier(supplier);
				purchaseEntry.setId(rs.getLong("id"));
				purchaseEntry.getSupplier().setSupplier_id(rs.getLong("supplier_id"));
				purchaseEntry.getSupplier().setSupplierName(rs.getString("supplier_name"));
				supplierList.add(purchaseEntry);
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
		return supplierList;
	}
	
//	public List<PurchaseEntry> getPaymentList(PurchaseEntry purchaseEntry){
//		
//	}
	public long getPurchaseEntryId(){
		String stmt ="select max(purchase_entry_id) from rm_purchase_entry";
		long count = 0;
		ResultSet rs=null;
		try {
		 rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
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
	
	public List<PurchaseProduct> getProductDetails(PurchaseEntry purchaseEntry){
		List<PurchaseProduct> purchaseList = new ArrayList<PurchaseProduct>();
		String stmt = "select rp.id,p.productname,product_id,hsn,gst,cess,buy_price,sell_price,category,subgroup,p.quantity,rp.quantity,rp.unit,rate,discount,vat,subtotal,amount,status from rm_purchase_product rp inner join rm_product p on rp.product_id= p.id"
				+ " where purchase_entry_id="+purchaseEntry.getId()+"";
			ResultSet rs=null;
	try {
		rs= DBUtil.dbExecuteQuery(stmt);
		while(rs.next()) {
			PurchaseProduct purchaseProduct = new PurchaseProduct();
			Product product = new Product();
			purchaseProduct.setProduct(product);
			purchaseProduct.setPurchaseEntry(purchaseEntry);
			purchaseProduct.getPurchaseEntry().setId(purchaseEntry.getId());
			purchaseProduct.setId(rs.getLong("rp.id"));
			purchaseProduct.setProd_name(rs.getString("productname"));
			purchaseProduct.getProduct().setId(rs.getLong("product_id"));
			purchaseProduct.getProduct().setProduct_name(rs.getString("productname"));
//			System.out.println(rs.getLong("product_id"));
			purchaseProduct.setCategory(new Category());
			purchaseProduct.getCategory().setCategory(rs.getString("category"));
			purchaseProduct.setSubCategory(new SubCategory());
			purchaseProduct.getSubCategory().setSubCategory(rs.getString("subgroup"));
			purchaseProduct.getProduct().setBuyPrice(rs.getDouble("buy_price"));
			purchaseProduct.getProduct().setHsnNo(rs.getLong("hsn"));
			purchaseProduct.getProduct().setGst(rs.getDouble("gst"));
			purchaseProduct.getProduct().setCess(rs.getDouble("cess"));
			purchaseProduct.getProduct().setSellPrice(rs.getDouble("sell_price"));
			purchaseProduct.getProduct().setCategory(rs.getString("category"));
			purchaseProduct.getProduct().setSubGroup(rs.getString("subgroup"));
			purchaseProduct.getProduct().setQuantity(rs.getInt("p.quantity"));
			purchaseProduct.setUnit(rs.getString("unit"));
			purchaseProduct.setUnitObj(new Unit());
			purchaseProduct.getUnitObj().setUnit(rs.getString("unit"));
			purchaseProduct.getProduct().setUnit(rs.getString("unit"));
			purchaseProduct.setQuantity(rs.getInt("rp.quantity"));
			purchaseProduct.setPrevQty(rs.getLong("rp.quantity"));
			purchaseProduct.setRate(rs.getDouble("rate"));
			purchaseProduct.setGst(rs.getDouble("vat"));
			purchaseProduct.setDiscount(rs.getDouble("discount"));
			purchaseProduct.setSubTotal(rs.getDouble("subtotal"));
			purchaseProduct.setStatus(rs.getString("status"));
			
			purchaseProduct.setAmount(rs.getDouble("amount"));
			purchaseList.add(purchaseProduct);
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
	
		return purchaseList;
		
	}
	
	public PaymentMode getPaymentModes(PurchaseEntry purchaseEntry){
//		List<PaymentMode> payList= new ArrayList<>();
		
		String stmt="select * from rm_payment_modes where purchase_entry_id="+purchaseEntry.getId();
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
		
		return paymentMode;
		
	}
	
	public boolean returnPurchase(PurchaseEntry purchaseEntry,ObservableList<PurchaseReturn> returnData) {
		boolean result= false;
		List<String> sqlStmt=new ArrayList<>();
		long quantity=0;
	
		for(PurchaseReturn pr:returnData) {
//			System.out.println(pr.getProduct().getId());
		String stmt="insert into rm_purchase_return(purchase_entry_id,purchase_product_id,purchase_product_items_id,return_quantity,date_created,status) values("+
		purchaseEntry.getId()+","+pr.getPurchaseProduct().getId()+","+pr.getProduct().getId()+","+pr.getReturnQuantity()+",'"+new Date(System.currentTimeMillis())+"',1)";
		sqlStmt.add(stmt);
		quantity=pr.getProduct().getQuantity()-pr.getReturnQuantity();
		System.out.println(pr.getProduct().getQuantity());
		String stmt1="update rm_product set quantity="+quantity+" where id="+pr.getProduct().getId()+"";
		sqlStmt.add(stmt1);
		quantity=0;
		}
		
		try {
			DBUtil.executeBatch(sqlStmt);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	public List<PurchaseReturn> getReturnDetails(PurchaseEntry purchaseEntry){
		List<PurchaseReturn> returnList= new ArrayList<>();
		String stmt="select * from rm_purchase_return where purchase_entry_id="+purchaseEntry.getId()+"";
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				PurchaseReturn purchaseReturn= new PurchaseReturn();
				PurchaseProduct purchaseProduct= new PurchaseProduct();
				purchaseReturn.setPurchaseProduct(purchaseProduct);
				purchaseReturn.setPurchaseEntry(purchaseEntry);
				purchaseReturn.setId(rs.getLong("id"));
				purchaseReturn.getPurchaseProduct().setId(rs.getLong("purchase_product_id"));
				Product product= new Product();
				purchaseReturn.setProduct(product);
				purchaseReturn.getProduct().setId(rs.getLong("purchase_product_items_id"));
				purchaseReturn.setReturnQuantity(rs.getLong("return_quantity"));
				purchaseReturn.setReturnDate(rs.getDate("date_created"));
				returnList.add(purchaseReturn);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnList;
				
	}
	
	public static List<PurchaseEntry> PurchaseReportData(String FromDate,String ToDate){
		  String stmt = " Select Pe.purchase_invoice_no, "+
		"Pe.purchase_entry_id, "+
		"Pe.supplier_id, "+
		"Pe.purchase_date, "+
		"Pe.invoice_date, "+
		"Pe.subtotal, "+
		"Pe.discount, "+
		"Pe.total, "+
		"Pe.paid_amount, "+

		"pp.purchase_entry_id, "+
		"pp.product_id, "+
		"pp.quantity, "+
		"pp.unit, "+
		"pp.rate, "+
		"pp.discount, "+
		"pp.subtotal, "+
		"pp.amount, "+

		"p.productname, "+
		"p.unit, "+
		"p.hsn, "+
		"p.quantity, "+
		"p.sell_price, "+
		"p.category, "+
		"p.info, "+
		"p.gst, "+
		"p.cess From   rm_product p "+
		"INNER JOIN rm_purchase_product  pp ON p.id=pp. product_id "+
		"INNER JOIN rm_purchase_entry  Pe ON pp. purchase_entry_id=Pe. Id "+
		"WHERE pe.invoice_date>= '"+FromDate+ "' and pe.invoice_date<= '"+ToDate+"'";
		//  ArrayList<Customer> cu
		  List<PurchaseEntry> reportList=new ArrayList<>();
		  try {
		   ResultSet rs = DBUtil.dbExecuteQuery(stmt);

		   while(rs.next()){
		    PurchaseEntry s=new PurchaseEntry();
		    s.setInvoiceDate(rs.getDate("invoice_date"));
		    s.setInvoiceNo(rs.getString("purchase_invoice_no"));
		    s.setProductName(rs.getString("productname"));
		    s.setTotal(rs.getDouble("total"));
		    reportList.add(s);


		   }
		  } catch (ClassNotFoundException | SQLException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }

		  return reportList;
		 }
	
	public boolean removeStock(PurchaseProduct purchaseProduct) {
		boolean result=false;
		String stmt="update rm_product set quantity="+(purchaseProduct.getProduct().getQuantity()-purchaseProduct.getQuantity())+" where id="+purchaseProduct.getProduct().getId()+"";
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
	
//	public List<PurchaseEntry> getPurchaseList(long supplierId){
//		List<PurchaseEntry> purchaseList= new ArrayList<>();
//		String stmt="select pe.id,pp.id,pp.product_id,pp.quantity,p."
//	}
	
}
