package miniproject.QuizMasterApplication;

import java.sql.*;
import java.util.Scanner;

public class StudentLogin {

    public StudentRegistration studentRegistration;

    public StudentQuiz studentQuiz;

    public void getStudentLogin() throws SQLException{

//        DBConnection db = new DBConnection();
//        Connection con = db.getConnectionDetails();
//        Statement stmt = con.createStatement();
//
//        String sql = "select * from student";
//        ResultSet rs = stmt.executeQuery(sql);
//
//        while(rs.next()) {
//            int dbId = rs.getInt(1);
//            String dbFirstname = rs.getString(2);
//            String dbLastname = rs.getString(3);
//            String dbUsername = rs.getString(4);
//            String dbPassword = rs.getString(5);
//            String dbCity = rs.getString(6);
//            String dbEmail = rs.getString(7);
//            long dbMobilenumber = rs.getLong(8);


        ExistingUserCheck existingUserCheck = new ExistingUserCheck();
        System.out.println("Student Login");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Username: ");
        String u_name=scanner.next();

        if (u_name.equals(existingUserCheck.dbUsername)) {
            System.out.println("Username " + existingUserCheck.dbUsername + " is already registered -> Please Proceed with Login");
            System.out.println("Enter Password: ");
            String pwd=scanner.next();
            if (pwd.equals(existingUserCheck.dbPassword)) {
                System.out.println("Login Successful");
                System.out.println("You can start with the Quiz");
                studentQuiz.getQuiz();
            }else {
                System.out.println("Please enter correct password");
            }

        } else if (!u_name.equals(existingUserCheck.dbUsername)) {
            System.out.println("Username is not registered -> Please Proceed with Registration");
            InsertData insertData = new InsertData();
            insertData.insertStudentData(studentRegistration);
        }
    }
}
