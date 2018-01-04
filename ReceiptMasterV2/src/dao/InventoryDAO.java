package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.AddStock;
import model.Branch;
//import javafx.scene.control.Alert;
//import model.Account;
import model.Inventory;
import model.Product;
import model.StockTransfer;
import model.StockTransferProduct;
import util.DBUtil;
/***
 * 
 * @author Saurabh Gupta
 *
 */
public class InventoryDAO {

	
	public ObservableList<Inventory> stockQtyData = FXCollections.observableArrayList( );
//	public final ObservableList<String> proCatList = FXCollections.observableArrayList();
	public final ObservableList<String> proList = FXCollections.observableArrayList();
	ResultSet rs=null;
	
//	public ObservableList<String> retriveAccountTypeList(Inventory inventory) {
//		
//		String query = "Select category from rm_category";
//		//
//		
//		ResultSet rs = null;
//		
//		try {
//			rs = DBUtil.dbExecuteQuery(query);
//			while(rs.next()) {
//				proCatList.add(new String(rs.getString(1)));
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
////		try {
//		
//			
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		return proCatList;
//	}


	public ObservableList<String> retriveAccountList(Inventory inventory) {
		
		String query1 = "Select productname from rm_product";
		ResultSet rs1;
		try {
			rs1 = DBUtil.dbExecuteQuery(query1);
			while(rs1.next()) {
				proList.add(new String(rs1.getString(1)));
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
		
//		try {
			
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return proList;
	}


	public boolean addNewStock(AddStock addStock,ObservableList<Inventory> prodData) {
		List<String> sqlStmt= new ArrayList<>();
		boolean result= false;
		long lastBrachId=0;
		boolean insertUpdateFlag=false;
		long lastStockId=0;
		boolean result1=false;
		long quantity=0;
		boolean endResult=false;
		
		if(addStock.getBranch().getId()==0) {
			String stmt="insert into rm_branch_details(branchname,address,state) values('"+addStock.getBranch().getBranchName()+"','"+addStock.getBranch().getAddress()+"','"
					+addStock.getBranch().getState()+"')";
			try {
				lastBrachId=DBUtil.dbExecuteUpdateandReturn(stmt);
				result=true;
				insertUpdateFlag=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result=false;
				e.printStackTrace();
			} finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else {
			String stmt="update rm_branch_details set address='"+addStock.getBranch().getAddress()+"',state='"+addStock.getBranch().getState()+"'"+
		" where id="+addStock.getBranch().getId()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt);
				lastBrachId=addStock.getBranch().getId();
				insertUpdateFlag=false;
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
		}
		
		if(result) {
			String stmt="insert into rm_addStock(inwardno,entrydate,branchid,total) values("+addStock.getInwardNo()+",'"+
					addStock.getEntryDate()+"',"+lastBrachId+","+addStock.getTotal()+")";
			try {
				lastStockId=DBUtil.dbExecuteUpdateandReturn(stmt);
				result1= true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result1= false;
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
			for(Inventory st:prodData) {
				
				String stmt3="insert into rm_finishedproducts(finishedproduct_id,productid,quantity,rate,total) values("+lastStockId+","+st.getProduct().getId()+","+
			+st.getQuantity()+","+st.getRate()+","+st.getTotal()+")";
				sqlStmt.add(stmt3);
				quantity=st.getCurrentStock()+st.getQuantity();
				String stmt4="update rm_product set quantity="+quantity+" where id="+st.getProduct().getId()+"";
				sqlStmt.add(stmt4);
			}
			
			try {
				DBUtil.executeBatch(sqlStmt);
				endResult=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				endResult=false;
				e.printStackTrace();
			}
		}
		
		return endResult;
		
		
//			if() {
//				
//				Alert alert = new Alert(Alert.AlertType.INFORMATION);
//				alert.setTitle("Success");
//				alert.setHeaderText("Hi...");
//				alert.setContentText("Record stored successfuly!");
//				alert.showAndWait();
//			}
//			else {
//				
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				alert.setTitle("Fail");
//				alert.setHeaderText("Could not connect to database");
//				alert.setContentText("Please check database...");
//				alert.showAndWait();
//			}
	}


	public List<AddStock> retriveStockQtyList() {
		
		String stmt = "select a.id,inwardNo,entrydate,branchid,branchname,total from rm_addStock a inner join rm_branch_details b on b.id=a.branchid";
		List<AddStock> inventoryList= new ArrayList<>();
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				AddStock inventory= new AddStock();
				inventory.setId(rs.getLong("a.id"));
				inventory.setEntryDate(rs.getDate("entrydate"));
				inventory.setInwardNo(rs.getLong("inwardno"));
				inventory.setBranch(new Branch());
				inventory.getBranch().setId(rs.getLong("branchid"));
				inventory.getBranch().setBranchName(rs.getString("branchname"));
				inventory.setTotal(rs.getDouble("total"));
				inventoryList.add(inventory);
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
		  
		return inventoryList;
	}
	
	public boolean deleteStock(AddStock inventory) {
		boolean result=false;
		String stmt="delete from rm_addStock where id="+inventory.getId()+"";
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
	
	public List<Product> showCurrentStocks(){
		List<Product> stockList = new ArrayList<>();
		String stmt = "select * from rm_product";
		ResultSet rs=null;
		try {
			rs = DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setProduct_name(rs.getString("productname"));
//				product.setCategory(rs.getString("category"));
				product.setQuantity(rs.getInt("quantity"));
//				product.setInfo(info);
				stockList.add(product);
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
		return stockList;
		
		
	}
	
	public List<StockTransfer> showTransferList(){
		String stmt="select t.id,transfer_no,entrydate,branchid,branchname,total from transferStock t inner join"
				+ " rm_branch_details b on b.id=t.branchid";
		List<StockTransfer> transferList=new ArrayList<>();
		ResultSet rs= null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				StockTransfer stockTransfer= new StockTransfer();
				stockTransfer.setId(rs.getLong("id"));
				stockTransfer.setBranch(new Branch());
				stockTransfer.getBranch().setId(rs.getLong("branchid"));
				stockTransfer.setBranchname(rs.getString("branchname"));
				stockTransfer.setEntryDate(rs.getDate("entrydate"));
				stockTransfer.setTotal(rs.getDouble("total"));
				stockTransfer.setTransferNo(rs.getLong("transfer_no"));
				transferList.add(stockTransfer);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return transferList;
	}
	
	public boolean deleteTransfer(StockTransfer stockTransfer) {
		String stmt="delete from transferStock where id="+stockTransfer.getId()+"";
		boolean result=false;
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			result=false;
			e.printStackTrace();
		}
		
		return result;
	}
	
	public long getTransferId() {
		String stmt="select max(transfer_no) from transferStock";
		long transferId=0;
		ResultSet rs=null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				transferId=rs.getLong(1);
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
		
		return transferId;
	}
	
	public long getInwardStockId() {
		long inwardId=0;
		String stmt= "select max(inwardNo) from rm_addStock";
		ResultSet rs= null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				inwardId=rs.getLong(1);
			}
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
		
		return inwardId;
		
	}
	
	public List<Branch> getBranchName(){
		String stmt="select id,branchname,address,state from rm_branch_details";
		List<Branch> branchList = new ArrayList<>();
		ResultSet rs= null;
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				Branch branch= new Branch();
				branch.setId(rs.getLong("id"));
				branch.setBranchName(rs.getString("branchname"));
				branch.setAddress(rs.getString("address"));
				branch.setState(rs.getString("state"));
				branchList.add(branch);
			}
			
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
		return  branchList;
	}
	
	public boolean createTransferStock(StockTransfer stockTransfer,ObservableList<StockTransferProduct> prodData) {
		List<String> sqlStmt= new ArrayList<>();
		boolean result= false;
		long lastBrachId=0;
		boolean insertUpdateFlag=false;
		long lastTransferId=0;
		boolean result1=false;
		long quantity=0;
		boolean endResult=false;
		
		if(stockTransfer.getBranch().getId()==0) {
			String stmt="insert into rm_branch_details(branchname,address,state) values('"+stockTransfer.getBranch().getBranchName()+"','"+stockTransfer.getBranch().getAddress()+"','"
					+stockTransfer.getBranch().getState()+"')";
			try {
				lastBrachId=DBUtil.dbExecuteUpdateandReturn(stmt);
				result=true;
				insertUpdateFlag=true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result=false;
				e.printStackTrace();
			} finally {
				try {
					
					DBUtil.dbDisconnect();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}else {
			String stmt="update rm_branch_details set address='"+stockTransfer.getBranch().getAddress()+"',state='"+stockTransfer.getBranch().getState()+"'"+
		" where id="+stockTransfer.getBranch().getId()+"";
			try {
				DBUtil.dbExecuteUpdate(stmt);
				lastBrachId=stockTransfer.getBranch().getId();
				insertUpdateFlag=false;
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
		}
		
		if(result) {
			String stmt="insert into transferstock(transfer_no,entrydate,branchid,total) values("+stockTransfer.getTransferNo()+",'"+
		stockTransfer.getEntryDate()+"',"+lastBrachId+","+stockTransfer.getTotal()+")";
			try {
				lastTransferId=DBUtil.dbExecuteUpdateandReturn(stmt);
				result1= true;
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				result1= false;
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
			for(StockTransferProduct st:prodData) {
				String stmt3="insert into transfer_stock_product(transfer_id,productid,rate,quantity,total) values("+lastTransferId+","+st.getProduct().getId()+","+
			st.getRate()+","+st.getQuantity()+","+st.getTotal()+")";
				sqlStmt.add(stmt3);
				quantity=st.getCurrentStock()-st.getQuantity();
				String stmt4="update rm_product set quantity="+quantity+" where id="+st.getProduct().getId()+"";
				sqlStmt.add(stmt4);
			}
			
			try {
				DBUtil.executeBatch(sqlStmt);
				endResult=true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				endResult=false;
				e.printStackTrace();
			}
		}
		
		return endResult;
		
		
	}

}
