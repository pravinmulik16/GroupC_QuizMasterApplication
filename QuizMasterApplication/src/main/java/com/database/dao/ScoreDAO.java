package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDAO {

    /**
     * Determines the performance grade letter based on the quiz score criteria.
     * Fulfills User Story 2.2 and 5.1.
     */
    public String calculateGrade(int score) {
        if (score >= 8) {
            return "A";
        } else if (score >= 5) {
            return "B";
        } else {
            return "C";
        }
    }

    /**
     * Returns the contextual text feedback message based on the performance grade.
     * Fulfills User Story 5.1.
     */
    public String getFeedbackMessage(String grade) {
        switch (grade) {
            case "A": return "Excellent";
            case "B": return "Good";
            case "C": return "Needs Improvement";
            default: return "Attempted";
        }
    }

    /**
     * Saves a student's final quiz score into the database.
     * If the student re-attempts the quiz, their previous score is overwritten.
     * Fulfills User Story 2.2.
     */
    public boolean saveStudentScore(Score score) throws SQLException {
        String query = "INSERT INTO score (student_id, total_score, grade) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE total_score = VALUES(total_score), grade = VALUES(grade)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, score.getStudentId());
            stmt.setInt(2, score.getTotalScore());
            stmt.setString(3, score.getGrade());
            return stmt.executeUpdate() > 0;
        }
    }
}
