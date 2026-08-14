package miniproject.QuizMasterApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertData {

	public void insertStudentData(StudentRegistration studentRegistration) {
		try {
		DBConnection db = new DBConnection();
		Connection con =db.getConnectionDetails();
		PreparedStatement ps= con.prepareStatement("insert into student(id,first_name,last_name,username,Password,city,email,mobile) values(?,?,?,?,?,?,?,?)");

		
			ps.setInt(1, studentRegistration.getId());
			ps.setString(2, studentRegistration.getFirst_name());
			ps.setString(3, studentRegistration.getLast_name());
			ps.setString(4, studentRegistration.getUsername());
			ps.setString(5, studentRegistration.getPassword());
			ps.setString(6, studentRegistration.getCity());
			ps.setString(7, studentRegistration.getEmail());
			ps.setInt(8, studentRegistration.getMobile());
			
			ps.execute();
			
			System.out.println("record added successfully");
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("1. Student Registration");
		
		System.out.println("enter student id: ");
		int id=scan.nextInt();
		
		System.out.println("Enter First Name: ");
		String firstname=scan.next();
		
		System.out.println("Enter Last Name: ");
		String lastname=scan.next();
		
		System.out.println("Enter Username: ");
		String username=scan.next();
		
		System.out.println("Enter password: ");
		String password=scan.next();
		
		System.out.println("Enter city: ");
		String city=scan.next();
		
		System.out.println("Enter email: ");
		String email=scan.next();
		
		System.out.println("Enter mobile number: ");
		int mobilenumber=scan.nextInt();
		
		StudentRegistration sr =new StudentRegistration(id,firstname, lastname, username, password, city, email, mobilenumber);
		
		InsertData insert = new InsertData();
		insert.insertStudentData(sr);
	}
}
