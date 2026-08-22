package miniproject.QuizMasterApplication;

import java.sql.*;
import java.util.Scanner;

public class InsertData {

	public StudentRegistration studentRegistration;

	public void insertStudentData() {

		ExistingUserCheck existingUserCheck = new ExistingUserCheck();
		LastStudentId lastStudentId = new LastStudentId();
	try {

		Scanner scan = new Scanner(System.in);
		System.out.println("1. Student Registration");

		System.out.println("The Last user id is --> ");
		lastStudentId.getLastStudentId();

		System.out.println("Enter First Name: ");
		String firstname = scan.next();

		System.out.println("Enter Last Name: ");
		String lastname = scan.next();

		System.out.println("Enter Username: ");
		String username = scan.next();

		System.out.println("Enter password: ");
		String password = scan.next();

		System.out.println("Enter city: ");
		String city = scan.next();

		System.out.println("Enter email: ");
		String email = scan.next();

		System.out.println("Enter mobile number: ");
		long mobilenumber = scan.nextLong();

		StudentRegistration studentRegistration = new StudentRegistration(firstname, lastname, username, password, city, email, mobilenumber);

			DBConnection db = new DBConnection();
			Connection con = db.getConnectionDetails();
			PreparedStatement ps = con.prepareStatement("insert into student(first_name,last_name,username,Password,city,email,mobile) values(?,?,?,?,?,?,?)");


			ps.setString(1, studentRegistration.getFirst_name());
			ps.setString(2, studentRegistration.getLast_name());
			ps.setString(3, studentRegistration.getUsername());
			ps.setString(4, studentRegistration.getPassword());
			ps.setString(5, studentRegistration.getCity());
			ps.setString(6, studentRegistration.getEmail());
			ps.setLong(7, studentRegistration.getMobile());

			ps.execute();

			System.out.println("record added successfully");
			ps.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
