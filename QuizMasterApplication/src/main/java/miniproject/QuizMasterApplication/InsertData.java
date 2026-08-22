package miniproject.QuizMasterApplication;

import java.sql.*;
import java.util.Scanner;

public class InsertData {

	public StudentRegistration studentRegistration;

	public void insertStudentData(StudentRegistration studentRegistration) {
		this.studentRegistration=studentRegistration;
		ExistingUserCheck existingUserCheck = new ExistingUserCheck();
		LastStudentId lastStudentId = new LastStudentId();

		Scanner scan = new Scanner(System.in);
		System.out.println("1. Student Registration");

		System.out.println("enter student id: ");
		int id = scan.nextInt();

		if (id==existingUserCheck.dbId) {
			System.out.println("User id " + id + " is already present");
			System.out.println();
			System.out.println("The Last user id is --> ");
			lastStudentId.getLastStudentId();

		}else {
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

		StudentRegistration sr = new StudentRegistration(id, firstname, lastname, username, password, city, email, mobilenumber);

		InsertData insert = new InsertData();
		insert.insertStudentData(sr);
}
		try {
			DBConnection db = new DBConnection();
			Connection con = db.getConnectionDetails();
			PreparedStatement ps = con.prepareStatement("insert into student(id,first_name,last_name,username,Password,city,email,mobile) values(?,?,?,?,?,?,?,?)");


			ps.setInt(1, studentRegistration.getId());
			ps.setString(2, studentRegistration.getFirst_name());
			ps.setString(3, studentRegistration.getLast_name());
			ps.setString(4, studentRegistration.getUsername());
			ps.setString(5, studentRegistration.getPassword());
			ps.setString(6, studentRegistration.getCity());
			ps.setString(7, studentRegistration.getEmail());
			ps.setLong(8, studentRegistration.getMobile());

			ps.execute();

			System.out.println("record added successfully");
			ps.close();
			con.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
