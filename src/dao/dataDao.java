package dao;

import java.sql.*;

public class dataDao {
	private static String drv="com.mysql.jdbc.Driver";//数据库类型
	private static String url="jdbc:mysql://:3306/news?useSSL=false&useUnicode=true&characterEncoding=UTF-8";//数据库网址
	private static String usr="can";//用户名
	private static String pwd="123456";//密码
	Connection connect = null;
	Statement stmt=null;
	ResultSet rs = null;
	
	public dataDao()  throws Exception{
		Class.forName(drv);
		connect = DriverManager.getConnection(url, usr, pwd);
		stmt = connect.createStatement();			
	}

	public void  query(String sql) throws SQLException{
		rs = stmt.executeQuery(sql);
	}
	
	public Integer update(String sql) throws SQLException {
		return stmt.executeUpdate(sql);
	}
	
	public boolean next() throws SQLException{
		return rs.next();
	}
	
	public String getString(String field) throws SQLException{
		return rs.getString(field);
	}
	
	public Integer getInt(String field) throws SQLException{
		return rs.getInt(field);
	}
}
