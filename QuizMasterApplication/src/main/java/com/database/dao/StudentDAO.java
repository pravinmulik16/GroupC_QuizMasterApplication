package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {


    public boolean isUsernameDuplicate(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM student WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public boolean registerStudent(Student student) throws SQLException {
        String query = "INSERT INTO student (first_name, last_name, username, password, city, email, mobile) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getFirstName());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getUsername());
            stmt.setString(4, student.getPassword());
            stmt.setString(5, student.getCity());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getMobile());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Verifies credentials and returns the student's unique internal database ID.
     * Fulfills User Story 1.2 (Student Login).
     */
    public int loginStudent(String username, String password) throws SQLException {
        String query = "SELECT id FROM student WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Successful authentication
                }
            }
        }
        return -1; // Login failed
    }

    /**
     * Fetches and displays a student's score directly from the score table.
     * Fulfills User Story 2.3 (View My Score).
     */
    public void viewStudentScore(String username, String password) throws SQLException {
        int studentId = loginStudent(username, password);
        if (studentId == -1) {
            System.out.println("Error: Invalid credentials. Cannot fetch score.");
            return;
        }

        String query = "SELECT total_score, grade FROM score WHERE student_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n=== Your Quiz Result ===");
                    System.out.println("Your Score: " + rs.getInt("total_score") + " / 10");
                    System.out.println("Your Grade: " + rs.getString("grade"));
                } else {
                    System.out.println("\nYou have not attempted the quiz yet.");
                }
            }
        }
    }
}
