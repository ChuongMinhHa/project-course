package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Connection.DbCon;
import Model.Account;
import Model.Product;

public class SanPhamDao {
	Connection con = null;
	PreparedStatement ps = null; //nem cau lenh query sang sql server
	ResultSet rs = null;
	public SanPhamDao() {
		// TODO Auto-generated constructor stub
	}
	public List<Product> AllSanPham() {
		List<Product> ds = new ArrayList();
		String sql = "SELECT * FROM product";
		try {
			con = DbCon.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				Product s=new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5));
				ds.add(s);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ds;
	}
	
	public Product getProductById(int id) {
		Product p=null;
		String sql = "SELECT * FROM product where id=?";
		try {
			con = DbCon.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				p=new Product(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return p;
	}
	
	public void AddNew(String tenSanPham, String gia, String hinhAnh,String des) {
		String sql = "INSERT INTO `webbanhang`.`product` (`name`, `img`, `description`, `price`) VALUES (?, ?, ?, ?);";
		try {
			con=DbCon.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, tenSanPham);
			ps.setString(2, hinhAnh);
			ps.setString(3, des);
			ps.setInt(4, Integer.parseInt(gia));
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Account login(String user, String pass) {
		Account account=null;
		String query = "SELECT * FROM account where user=? and pass=?";
		try {
			con=DbCon.getConnection();
			ps=con.prepareStatement(query);
			ps.setString(1, user);
			ps.setString(2, pass);
			rs=ps.executeQuery();
			if(rs.next()) {
				account = new Account(rs.getInt(1), 
						rs.getString(2), 
						rs.getString(3), 
						rs.getInt(4));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	return account;
	}
	
	public boolean CheckUser(String user) {
		String query = "SELECT * FROM webbanhang.account where user=?";
		try {
			con=DbCon.getConnection();
			ps=con.prepareStatement(query);
			ps.setString(1, user);
			rs=ps.executeQuery();
			while(rs.next()) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	return false;
	}
	
	public void AddUser(String user, String password) {
		String sql = "INSERT INTO `webbanhang`.`account` (`user`, `pass`, `isSell`, `isAdmin`) VALUES (?, ?, ?, ?)";
		
		try {
			con=DbCon.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, password);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void DeleteSanPham(String id) {
		String sql = "DELETE FROM `webbanhang`.`product` WHERE (`id` = ?)";
		
		try {
			con=DbCon.getConnection();
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		SanPhamDao dao=new SanPhamDao();
		System.out.println(dao.login("chuong", "123"));
	}
}
