package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Question;
import java.sql.*;

public class AdminDAO {

    /**
     * Verifies that the exact question statement text doesn't already exist,
     * then commits new problem structures to database persistence tiers.
     * Fulfills User Story 3.1 and User Story 6.1.
     */
    public boolean addQuestion(Question q) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM question WHERE question_text = ?";
        String insertQuery = "INSERT INTO question (question_text, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection()) {
            // Check for duplication to prevent redundancies
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, q.getQuestionText());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        System.out.println("\n[Error]: Redundant question. System skipped entry allocation.");
                        return false;
                    }
                }
            }
            // Execute safe parametrized insert
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, q.getQuestionText());
                insertStmt.setString(2, q.getOption1());
                insertStmt.setString(3, q.getOption2());
                insertStmt.setString(4, q.getOption3());
                insertStmt.setString(5, q.getOption4());
                insertStmt.setInt(6, q.getCorrectOption());
                return insertStmt.executeUpdate() > 0;
            }
        }
    }

    /**
     * Updates text options and valid target responses based on a target tracking code.
     * Fulfills User Story 6.2.
     */
    public boolean editQuestion(Question q) throws SQLException {
        String query = "UPDATE question SET question_text=?, option1=?, option2=?, option3=?, option4=?, correct_option=? WHERE id=?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, q.getQuestionText());
            stmt.setString(2, q.getOption1());
            stmt.setString(3, q.getOption2());
            stmt.setString(4, q.getOption3());
            stmt.setString(5, q.getOption4());
            stmt.setInt(6, q.getCorrectOption());
            stmt.setInt(7, q.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Removes structural entities from storage pools based on identification indexes.
     * Fulfills User Story 6.3.
     */
    public boolean deleteQuestion(int id) throws SQLException {
        String query = "DELETE FROM question WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Gathers all performance metrics and logs rows in ascending value format.
     * Fulfills User Story 3.2.
     */
    public void viewAllStudentScores() throws SQLException {
        String query = "SELECT s.id, CONCAT(s.first_name, ' ', s.last_name) AS name, sc.total_score, sc.grade " +
                "FROM student s JOIN score sc ON s.id = sc.student_id ORDER BY sc.total_score ASC";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n=== All Student Scores (Sorted in Ascending Order) ===");
            System.out.println("Student ID | Name | Score | Grade");
            boolean absoluteEmpty = true;
            while (rs.next()) {
                absoluteEmpty = false;
                System.out.printf("%d | %s | %d | %s\n",
                        rs.getInt("id"), rs.getString("name"), rs.getInt("total_score"), rs.getString("grade"));
            }
            if (absoluteEmpty) {
                System.out.println("[Notice]: No examination entries found in database registry logs.");
            }
        }
    }

    /**
     * Looks up performance evaluations based on standard student sequence numbers.
     * Fulfills User Story 3.3.
     */
    public void fetchScoreByStudentId(int studentId) throws SQLException {
        String query = "SELECT total_score, grade FROM score WHERE student_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nScore: " + rs.getInt("total_score"));
                    System.out.println("Grade: " + rs.getString("grade"));
                } else {
                    System.out.println("\n[Notice]: No records exist for target Student ID: " + studentId);
                }
            }
        }
    }

    /**
     * Locates maximum grade results and prints associated profiles.
     * Fulfills User Story 5.2.
     */
    public void viewTopScorer() throws SQLException {
        String query = "SELECT CONCAT(s.first_name, ' ', s.last_name) AS name, sc.total_score, sc.grade " +
                "FROM student s JOIN score sc ON s.id = sc.student_id " +
                "WHERE sc.total_score = (SELECT MAX(total_score) FROM score)";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\n=== Top Scorer ===");
            boolean matchFound = false;
            while (rs.next()) {
                matchFound = true;
                System.out.println("Student Name: " + rs.getString("name"));
                System.out.println("Score: " + rs.getInt("total_score") + " / 10");
                System.out.println("Grade: " + rs.getString("grade"));
                System.out.println("-------------------------");
            }
            if (!matchFound) {
                System.out.println("[Notice]: No evaluations evaluated yet.");
            }
        }
    }
}
