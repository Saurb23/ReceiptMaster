package application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Item;
import model.Product;
import util.DBUtil;

public class BarcodeProduct {

	public static List<Item> generateBarcodeProduct(List<Product> prodList) {
		List<String> sqlStmt=new ArrayList<>();
		System.out.println("rahul1");
		System.out.println("rahul1");
		System.out.println("rahul1");
		System.out.println("rahul1");
		List<Item> itemList= new ArrayList<>();
		for(Product p:prodList) {
			for(int i=0;i<p.getQuantity();i++) {
				String path="C: jasperoutput barcodeImg "+p.getProduct_name().replaceAll(" ", "")+".png";
				Item  item= new Item();
				item.setCompanyName(Main.companyDetails);
				item.setProductName(p.getProduct_name());
				item.setBarcodePath(path);
				item.setPrice(p.getSellPrice());
				itemList.add(item);
//				String stmt="insert into barcodetemp(productname1,barcodePath,sellprice) values('"+p.getProduct_name()+"','"+path+"',"+
//				p.getSellPrice()+")";
//				sqlStmt.add(stmt);
			}
		}
		
		
		return itemList;
//		try {
//			DBUtil.executeBatch(sqlStmt);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
