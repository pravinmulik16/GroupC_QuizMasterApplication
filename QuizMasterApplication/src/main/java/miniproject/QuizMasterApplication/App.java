package miniproject.QuizMasterApplication;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
  public static void main(String[] args) throws SQLException {

    StudentLogin studentLogin = new StudentLogin();
    studentLogin.getStudentLogin();
  }
}
