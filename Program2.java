package com.tap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program2 {

	public static void main(String[] args) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/jdbcclasses";
		String username = "root";
		String password = "root";
		
		
		Connection connection =null;
		Statement statement =null;
		ResultSet res =null;
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			display(connection,statement,res);
			//System.out.println("Connection to database successful....");
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(connection, statement, res);
			
		}

	}
	public static void display(Connection connection, Statement statement, ResultSet res) throws SQLException {
		String SQL = "SELECT * from employees";
		statement = connection.createStatement();
		res = statement.executeQuery(SQL);
		//System.out.println(res);
		System.out.println("..........................................................");

		while(res.next()) {
			int id = res.getInt("id");
			String name = res.getString("name");
			String email = res.getString("email");
			String dept = res.getString("department");
			int salary  = res.getInt("salary");
			System.out.printf("|%-3d| %-10s| %-20s| %-7s| %d|\n" ,id,name,email,dept,salary);
			//System.out.println("......................................................");
			
		}
		System.out.println("..........................................................");

	}

	public static void close(Connection connection, Statement statement, ResultSet res)throws SQLException {
		try {
			if(res != null) {
				res.close();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(statement != null) {
				statement.close();
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(connection != null) {
				connection.close();
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
