package com.example.workflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("dbConn")
public class DBConn implements JavaDelegate {
	public void execute(DelegateExecution execution) throws Exception {
		final String driver = "org.mariadb.jdbc.Driver";
		final String DB_IP = "localhost";
		final String DB_PORT = "3306";
		final String DB_NAME = "my_db";
		final String DB_URL = 
				"jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(DB_URL, "root", "1111");
			
			if (conn != null) {
				System.out.println("Success: DB connection");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Fail: Driver load");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Fail: DB connection");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT * FROM customer";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			String userId = null;
			String name = null;
			
			while (rs.next()) {
				userId = rs.getString("c_id");
				name = rs.getString("name");
				
				System.out.println(userId);
				System.out.println(name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				
				if (pstmt != null) {
					pstmt.close();
				}
				
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
