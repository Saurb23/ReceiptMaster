package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import model.Product;
import model.StockReportBean;
import model.SubCategory;
import model.Unit;
import util.DBUtil;
/***
 *
 * @author Saurabh Gupta
 *
 */
public class GoodsDAO {
	ResultSet rs = null;

	public boolean addProduct(Product product) throws ClassNotFoundException {
		boolean result=false;
		String stmt = "insert into rm_product(barcode,productname,unit,hsn,sell_price,category,info,gst,cess) values('"+product.getBarcode()+"','"+ product.getProduct_name() + "','"+product.getUnit()+"',"+product.getHsnNo()+","+product.getSellPrice()+",'','',"+product.getGst()+","+product.getCess()+")";
		try {
			// DBUtil.dbConnect();
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (SQLException e) {
			System.out.println("Error occured while insert operation");
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


	public List<Product> showProduct() {
		String stmt = "select * from rm_product";
		ArrayList<Product> prodList = new ArrayList<Product>();
		try {
			rs = DBUtil.dbExecuteQuery(stmt);
			while (rs.next()) {

				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setProduct_name(rs.getString("productname"));
				product.setHsnNo(rs.getLong("hsn"));
//				product.setCategory(rs.getString("category"));
//				product.setInfo(rs.getString("info"));
				product.setBarcode(rs.getString("barcode"));
				product.setSellPrice(rs.getDouble("sell_price"));
				product.setGst(rs.getDouble("gst"));
				product.setCess(rs.getDouble("cess"));
				product.setUnit(rs.getString("unit"));
				prodList.add(product);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return prodList;
	}


	public boolean updateProduct(Product product) {
		boolean result=false;
		String stmt ="update rm_product set productname='"+product.getProduct_name()+"',barcode='"+product.getBarcode()+"',unit='"+product.getUnit()+"',hsn="+product.getHsnNo()+",sell_price="+product.getSellPrice()+",gst="+product.getGst()+
				",cess="+product.getCess()
				+ " where id="+product.getId()+"";
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


	public boolean deleteProduct(Product product) {
		boolean result=false;
		String stmt="delete from rm_product where id="+product.getId()+"";
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
	public List<Product> showProductList(){
		String stmt= "select * from rm_product";
		List<Product> prodNameList = new ArrayList<Product>();
		try {
			 rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setProduct_name(rs.getString("productname"));
				product.setBarcode(rs.getString("barcode"));
				product.setHsnNo(rs.getLong("hsn"));
				product.setUnit(rs.getString("unit"));
				product.setGst(rs.getDouble("gst"));
				product.setQuantity(rs.getInt("quantity"));
				product.setCategory(rs.getString("category"));
				product.setSubGroup(rs.getString("subgroup"));
				product.setSellPrice(rs.getDouble("sell_price"));
				product.setBuyPrice(rs.getDouble("buy_price"));
				product.setCess(rs.getDouble("cess"));


				prodNameList.add(product);
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
		return prodNameList;
	}

	public void addCategory(Category category) throws ClassNotFoundException {
		// Category category = new Category();

		String stmt = "insert into rm_category(category) values('" + category.getCategory() + "')";
		try {
			// DBUtil.dbConnect();
			DBUtil.dbExecuteUpdate(stmt);
		} catch (SQLException e) {
			System.out.println("Error occured while insert operation");
			e.printStackTrace();
		}
	}

	public List<Category> showCategory() {
		String stmt = "select * from rm_category";
		ArrayList<Category> catList = new ArrayList<Category>();
		try {
			rs = DBUtil.dbExecuteQuery(stmt);
			while (rs.next()) {
				Category category = new Category();
				category.setId(rs.getLong("id"));
				category.setCategory(rs.getString("category"));
//				category.setProductId(rs.getLong("product_id"));
//				category.setSubGroup(rs.getString("subgroup"));
				catList.add(category);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
//				if (rs != null) {
//					rs.close();
//				}
				DBUtil.dbDisconnect();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return catList;

	}

	public boolean updateCategory(Category category) {
		boolean result=false;
		String stmt = "update rm_category set category='"+category.getCategory()+"' where id="+category.getId()+"";
		System.out.println(category.getId());
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean deleteCategory(Category category) {
		boolean result=false;

		String stmt="delete from rm_category where id="+category.getId()+"";
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result=true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}




	public boolean createUnit(Unit unit) {
		boolean result= false;
		String stmt="insert into rm_unit(unit,info) values('"+unit.getUnit()+"','"+unit.getAddinfo()+"')";
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
	public List<Unit> showUnitList(){
		List<Unit> unitList= new ArrayList<>();
		String stmt="select * from rm_unit";
		try {
			 rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				Unit unit= new Unit();
				unit.setId(rs.getLong("id"));
				unit.setUnit(rs.getString("unit"));
				unit.setAddinfo(rs.getString("info"));
				unitList.add(unit);
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

		return unitList;

	}

	public boolean deleteUnit(Unit unit) {
		boolean result= false;
		String stmt="delete from rm_unit where id="+unit.getId()+"";
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

	public List<Unit> getUnitList(){
		List<Unit> unitList= new ArrayList<>();
		String stmt="select * from rm_unit";
		try {
			 rs = DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				Unit unit= new Unit();
				unit.setId(rs.getLong("id"));
				unit.setUnit(rs.getString("unit"));
				unitList.add(unit);
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
		return unitList;

	}


	public List<String> getProductNames(){
		List<String> productNames=new ArrayList<>();
		String stmt="select productname from rm_product";
		try {
			 rs= DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				String prodName=rs.getString("productname");
				productNames.add(prodName);
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

		return productNames;

	}


	public List<String> getProductBarCodes(){
		List<String> barcodeString= new ArrayList<>();
		String stmt="select barcode from rm_product";
		try {
			 rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				String barcode=rs.getString("barcode");
				barcodeString.add(barcode);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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

		return barcodeString;

	}

	public List<SubCategory> showSubCategory(){
		List<SubCategory> subCategoryList= new ArrayList<>();
		String stmt="select * from rm_subcategory";
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				SubCategory subCategory= new SubCategory();
				subCategory.setId(rs.getLong("id"));
				subCategory.setSubCategory(rs.getString("subcategory"));
				subCategoryList.add(subCategory);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return subCategoryList;


	}

public static List<StockReportBean> StockReportData(String FromDate,String ToDate){
String stmt = "select distinct p.productname,p.quantity,"+
" (select sum(pp.quantity) from rm_purchase_product pp where pp.product_id=p.id) as totalQty, "+
" (select sum(sp.quantity) from rm_sales_product sp where sp.product_id=p.id) as saleQty, "	+
"(select sum(fp.quantity) from rm_finishedproducts fp where fp.productid=p.id) as stockQty," +
"(select sum(tp.quantity) from transfer_stock_product tp where tp.productid=p.id) as transferQty,"+
" (select SUM(return_quantity) from rm_purchase_return where purchase_product_items_id=p.id AND  date_created>='"+FromDate+"' AND date_created<='"+ToDate+"') as purchaseReturn, "+
" (select SUM(return_quantity) from rm_sales_return where sales_product_items_id=p.id  AND date_created>='"+FromDate+"' AND date_created<='"+ToDate+"') as salesReturn, ((p.quantity)) as RemainingQty "+
" from rm_product p";
		List<StockReportBean> reportList=new ArrayList<>();
		try {
			ResultSet rs = DBUtil.dbExecuteQuery(stmt);
			while(rs.next()){
				StockReportBean s=new StockReportBean();
				s.setProductname(rs.getString("productname"));
				s.setQuantity(rs.getLong("totalQty"));
				s.setSaleQty(rs.getLong("saleQty"));
				s.setPurchaseReturn(rs.getLong("purchaseReturn"));
				s.setSalesReturn(rs.getLong("salesReturn"));
				s.setRemainingQty(rs.getLong("quantity"));
				s.setStockQty(rs.getLong("stockQty"));
				s.setTransferQty(rs.getLong("transferQty"));
				
				reportList.add(s);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return reportList;
	}
}