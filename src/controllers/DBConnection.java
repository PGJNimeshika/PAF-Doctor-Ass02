package controllers;

import java.sql.*;

public class DBConnection {
	
	private DBConnection() {
		
	}
	
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");  
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/apidb?serverTimezone=UTC","root","");
		return conn;

	}
	
	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	

}
