package com.psl.training.assignment.beans;

import java.sql.Connection;
import java.sql.DriverManager;
public class MyDatabase {
	public static Connection con;
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String userName = "db_user1";
	static String userPass = "db_user1";
	public MyDatabase() {
		
	}
	public static Connection getConnection() {
		if(con == null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(url,userName,userPass);	
			}catch(Exception e){				
				e.printStackTrace();
			}
		}
		return con;
	}
	
	public static void close() {
		try {			
			con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
