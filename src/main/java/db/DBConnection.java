package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static void initConnection() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loading Success");
		} catch (ClassNotFoundException e) {
			System.out.println("DB Driver를 찾지 못했습니다");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "root");
			System.out.println("Connection Success");
		} catch (SQLException e) {
			System.out.println("DB를 연결하지 못했습니다");
			e.printStackTrace();
		}
		return conn;
	}
}
