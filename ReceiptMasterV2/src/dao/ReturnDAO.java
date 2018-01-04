package dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import model.PurchaseEntry;
import model.PurchaseProduct;
import model.PurchaseReturn;
import model.SalesEntry;
import model.SalesProduct;
import model.SalesReturn;
import model.Supplier;
import util.DBUtil;

public class ReturnDAO {
	
	ResultSet rs= null;
	
	public List<SalesReturn> showSalesReturn(){
		List<SalesReturn> returnList= new ArrayList<>();
		String stmt="select sr.id,sr.invoice_no,sr.sales_product_id,sr.sales_product_items_id,sr.return_quantity,sr.date_created,"
				+ "p.productname from rm_sales_return sr inner join rm_product p on p.id=sr.sales_product_items_id";
		
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SalesReturn salesReturn= new SalesReturn();
				salesReturn.setId(rs.getLong("id"));
				salesReturn.setSalesEntry(new SalesEntry());
				salesReturn.getSalesEntry().setId(rs.getLong("invoice_no"));
				salesReturn.setSalesProduct(new SalesProduct());
				salesReturn.getSalesProduct().setId(rs.getLong("sales_product_id"));
				salesReturn.setProduct(new Product());
				salesReturn.getProduct().setId(rs.getLong("sales_product_items_id"));
				salesReturn.getProduct().setProduct_name(rs.getString("productname"));
				salesReturn.setReturnDate(rs.getDate("date_created"));
				salesReturn.setReturnQuantity(rs.getLong("return_quantity"));
				returnList.add(salesReturn);
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
		return returnList;
	}
	
	public List<PurchaseReturn> showPurchaseReturn(){
		List<PurchaseReturn> returnList=new ArrayList<>();
		String stmt="select pr.id,pr.supplier_id,pr.purchase_entry_id,pr.purchase_product_id,pr.purchase_product_items_id,return_quantity,date_created,p.productname,s.supplier_name from"
				+ " rm_purchase_return pr inner join rm_product p on p.id=pr.purchase_product_items_id"
				+ " inner join rm_supplier s on s.id=pr.supplier_id";
				
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				PurchaseReturn purchaseReturn= new PurchaseReturn();
				purchaseReturn.setId(rs.getLong("pr.id"));
				purchaseReturn.setPurchaseEntry(new PurchaseEntry());
				purchaseReturn.getPurchaseEntry().setId(rs.getLong("purchase_entry_id"));
				purchaseReturn.setPurchaseProduct(new PurchaseProduct());
				purchaseReturn.getPurchaseProduct().setId(rs.getLong("purchase_product_id"));
				purchaseReturn.setProduct(new Product());
				purchaseReturn.getProduct().setId(rs.getLong("purchase_product_items_id"));
				purchaseReturn.setReturnDate(rs.getDate("date_created"));
				purchaseReturn.setReturnQuantity(rs.getLong("return_quantity"));
				purchaseReturn.getProduct().setProduct_name(rs.getString("productname"));
				purchaseReturn.getPurchaseEntry().setSupplier(new Supplier());
				purchaseReturn.getPurchaseEntry().getSupplier().setSupplier_id(rs.getLong("supplier_id"));
				purchaseReturn.setProd_name(rs.getString("productname"));
				purchaseReturn.setSupplierName(rs.getString("supplier_name"));
				returnList.add(purchaseReturn);
				
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
		
		return	returnList;
		
	}
	public boolean returnSalesGoods(SalesReturn salesReturn) {
		boolean result=false;
		long quantity=0;
		List<String> sqlStmt=new ArrayList<>();
	
		String stmt="insert into rm_sales_return(invoice_no,sales_product_id,sales_product_items_id,return_quantity,date_created,status)"
			+ " values("+salesReturn.getSalesEntry().getId()+","+salesReturn.getSalesProduct().getId()+","+salesReturn.getProduct().getId()+","+salesReturn.getReturnQuantity()+
			",'"+new Date(System.currentTimeMillis())+"',1)";
		sqlStmt.add(stmt);
		
		quantity=salesReturn.getProduct().getQuantity()+salesReturn.getReturnQuantity();
		String stmt1="update rm_product set quantity="+quantity+" where id="+salesReturn.getProduct().getId()+"";
		sqlStmt.add(stmt1);
		try {
			DBUtil.executeBatch(sqlStmt);
			result=true;
		} catch ( SQLException e) {
			// TODO Auto-generated catch block
			result=false;
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
	
		return result;
	
	}

	public List<SalesReturn> getReturnDetails(long invoice){
	List<SalesReturn> returnList= new ArrayList<>();
	String stmt="select * from rm_sales_return where invoice_no="+invoice+"";
//	ResultSet rs=null;
	try {
		rs=DBUtil.dbExecuteQuery(stmt);
		while(rs.next()) {
			SalesReturn salesReturn= new SalesReturn();
			SalesProduct salesProduct= new SalesProduct();
			salesReturn.setSalesProduct(salesProduct);
			salesReturn.setSalesEntry(new SalesEntry());
			salesReturn.getSalesEntry().setId(rs.getLong("invoice_no"));
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
	
	return returnList;
}
	
	public boolean returnPurchaseGoods(PurchaseReturn purchaseReturn) {
		boolean result= false;
		long quantity=0;
		List<String> sqlStmt=new ArrayList<>();
		
		String stmt="insert into rm_purchase_return(supplier_id,purchase_entry_id,purchase_product_id,purchase_product_items_id,return_quantity,date_created,status) "
				+ "values("+purchaseReturn.getSupplier().getSupplier_id()+","+purchaseReturn.getPurchaseEntry().getId()+","+purchaseReturn.getPurchaseProduct().getId()+","+
				purchaseReturn.getProduct().getId()+","+purchaseReturn.getReturnQuantity()+",'"+new Date(System.currentTimeMillis())+"',1)";
		sqlStmt.add(stmt);
		
		quantity=purchaseReturn.getProduct().getQuantity()-purchaseReturn.getReturnQuantity();
		String stmt1="update rm_product set quantity="+quantity+" where id="+purchaseReturn.getProduct().getId()+"";
		sqlStmt.add(stmt1);
		
		try {
			DBUtil.executeBatch(sqlStmt);
			result=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=false;
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
		return	result;
		
	}

	public List<PurchaseReturn> getPurchaseReturn(PurchaseReturn purchaseReturn){
		List<PurchaseReturn> returnList= new ArrayList<>();
		String stmt="select supplier_id,purchase_product_items_id,return_quantity from rm_purchase_return where supplier_id="+purchaseReturn.getSupplier().getSupplier_id()+
				" and purchase_product_items_id="+purchaseReturn.getProduct().getId()+"";
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				PurchaseReturn purchaseReturn2= new PurchaseReturn();
//				purchaseReturn2=purchaseReturn;
				purchaseReturn2.setReturnQuantity(rs.getLong("return_quantity"));
//				System.out.println(purchaseReturn.getReturnQuantity());
				returnList.add(purchaseReturn2);
//				System.out.println(returnList.get(0).getReturnQuantity());
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
		
		
		return returnList;
		
		
	}
}
