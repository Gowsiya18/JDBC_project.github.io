package com.tap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Alloperation {
	
	private static Connection connection;
	private static Statement statement;
	private static ResultSet res;
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/jdbcclasses";
		String username = "root";
		String password = "root";
		
		try {
			connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Data in the table:");
			Program2.display(connection,statement,null);
			
			options();
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	static void options() throws SQLException {
		System.out.println("Enter your choice:\n" + "1. Update\n2. Insert\n3. Delete\n4. Display\n5. Close");
		int n = scan.nextInt();
		switch (n) {
		case 1:
			update();
			break;
		case 2:
			insert();
			break;
		case 3:
			delete();
			break;
		case 4:
			display();
			break;
		case 5:
			Program2.close(connection,statement,null);
		}

	}
	
	private static void insert() {
		String choice;
		String SQL= "Insert into `employees`(`id`, `name` , `email`, `department`, `salary`) "
				+ "value(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			
			do {
				//statement = connection.prepareStatement(SQL);
				System.out.println("Enter ID:");
				int id = scan.nextInt();
				
				System.out.println("Enter Name:");
				String name = scan.next();
				
				System.out.println("Enter Email:");
				String email = scan.next();
				
				System.out.println("Enter Department:");
				String dept = scan.next();
				
				System.out.println("Enter Slary:");
				int salary = scan.nextInt();
				
				pstmt.setInt(1, id);
				pstmt.setString(2, name);
				pstmt.setString(3, email);
				pstmt.setString(4, dept);
				pstmt.setInt(5, salary);
				
				//int i = pstmt.executeUpdate();
				pstmt.addBatch();
				System.out.println("do you want to enter more employees(yes/no)");
				
			}
			while( scan.next().equalsIgnoreCase("yes") );
			int[] i= pstmt.executeBatch();

			options();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
			
	}
	private static void update() {
		System.out.println("Choose the field to update:");
		System.out.println("Name");
		System.out.println("Email");
		System.out.println("Department");
		System.out.println("Salary");
		System.out.print("Enter your choice: ");
		String choice = scan.next();
		
		System.out.print("Enter the new value: ");
		String newValue = scan.next();
		
		System.out.println("Enter the column name:");
		String Column=scan.next();
		
		
		System.out.print("Enter the value for the WHERE clause: ");
		String condition=scan.nextLine();
		
		String conditionValue = scan.nextLine();
		
		String sql = "UPDATE employees SET " + choice + " = ? WHERE " + Column + " = ?";
		try {

			PreparedStatement pstmt = connection.prepareStatement(sql);
			if(choice=="salary")
			{
				int newVal=Integer.parseInt(newValue);
				pstmt.setInt(1, newVal);
			}

			pstmt.setString(1, newValue);
			pstmt.setString(2, conditionValue);

			int i=pstmt.executeUpdate();
			System.out.println(i+"row(s) updated successfully");
			options();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void delete() {
		String SQL = "Delete from `employees` where `id` = ? ";
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL);
			
			System.out.println("Enter id:");
			int id = scan.nextInt();
			
			pstmt.setInt(1, id);
			int i = pstmt.executeUpdate();
			System.out.println(i + " row(s) deleted successfully.");
			options();
			
			pstmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void display() {
		try {
			Program2.display(connection, statement, res);
			
			options();

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}	
	
}
