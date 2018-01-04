package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;
import util.DBUtil;

/***
 * 
 * @author Saurabh Gupta
 *
 */
public class ConnectionDAO {
	
	public boolean connectDB(String dbname,String username,String password) {
		boolean result=false;
		String stmt="truncate table dd_dbconn";
		try {
			DBUtil.dbExecuteUpdate(stmt);
			result= true;
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(result) {
		String stmt1="insert into dd_dbconn(dbname,username,password) values('"+dbname+"','"+username+"','"+password+"')";
		try {
			DBUtil.dbExecuteUpdate(stmt1);
			result= true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else {
			result=false;
		}
		
		return result;
		
	}
	
	public boolean saveCompanyDetails(Company company) {
		boolean result=false;
		System.out.println(company.getLogoPath());
		String stmt="insert into company(id,company_name,address,officeaddress,emaiId,contactNo,altcontactno,state,gstin,logoPath) values(1,'"+company.getCompanyName()+"','"+
		company.getAddress()+"','"+company.getOfficeAddr()+"','"+company.getEmailId()+"','"+company.getContactNo()+"','"+company.getAltContact()+"','"+company.getState()+"','"+company.getGstin()+"','"+company.getLogoPath()+"')"+
				" on duplicate key update company_name='"+company.getCompanyName()+"',address='"+company.getAddress()+"',officeaddress='"+company.getOfficeAddr()+"',emaiId='"+company.getEmailId()+
				"',contactno='"+company.getContactNo()+"',altcontactno='"+company.getAltContact()+"',state='"+company.getState()+"',gstin='"+company.getGstin()+"',logopath='"+company.getLogoPath()+"'";
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
	
	public Company getCompanyDetails() {
		String stmt="select * from company";
		ResultSet rs=null;
		Company company= new Company();
		try {
			rs=DBUtil.dbExecuteQuery(stmt);
			while(rs.next()) {
				company.setCompanyName(rs.getString("company_name"));
				company.setAddress(rs.getString("address"));
				company.setOfficeAddr(rs.getString("officeaddress"));
				company.setEmailId(rs.getString("emaiId"));
				company.setContactNo(rs.getString("contactno"));
				company.setAltContact(rs.getString("altcontactno"));
				company.setState(rs.getString("state"));
				company.setGstin(rs.getString("gstin"));
				company.setLogoPath(rs.getString("logopath"));
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

}
