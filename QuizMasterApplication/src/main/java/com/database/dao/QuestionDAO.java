package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Question;
import com.database.model.Score;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDAO {

    /**
     * Fetches all available questions from the database, shuffles them randomly,
     * and returns exactly 10 questions for the quiz session.
     * Fulfills User Story 2.1 and User Story 7.1.
     */
    public List<Question> getRandomQuizQuestions() throws SQLException {
        List<Question> questionList = new ArrayList<>();
        String query = "SELECT id, question_text, option1, option2, option3, option4, correct_option FROM question";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setOption1(rs.getString("option1"));
                q.setOption2(rs.getString("option2"));
                q.setOption3(rs.getString("option3"));
                q.setOption4(rs.getString("option4"));
                q.setCorrectOption(rs.getInt("correct_option"));
                questionList.add(q);
            }
        }

        // Shuffle the list to randomize order per User Story 7.1
        Collections.shuffle(questionList);

        // Safely return exactly 10 items (or fewer if database has less than 10)
        return questionList.subList(0, Math.min(questionList.size(), 10));
    }

    /**
     * Saves or replaces a student's final quiz score and calculation grade.
     * Fulfills User Story 2.2.
     */
    public boolean saveOrUpdateScore(Score score) throws SQLException {
        // Use standard "INSERT ... ON DUPLICATE KEY UPDATE" to handle updates seamlessly
        String query = "INSERT INTO score (student_id, total_score, grade) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE total_score = VALUES(total_score), grade = VALUES(grade)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, score.getStudentId());
            stmt.setInt(2, score.getTotalScore());
            stmt.setString(3, score.getGrade());
            return stmt.executeUpdate() > 0;
        }
    }
}
