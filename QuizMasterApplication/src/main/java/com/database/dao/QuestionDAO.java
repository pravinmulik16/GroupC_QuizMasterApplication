package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Question;
import com.database.model.Score;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionDAO {


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


        Collections.shuffle(questionList);


        return questionList.subList(0, Math.min(questionList.size(), 10));
    }


    public boolean saveOrUpdateScore(Score score) throws SQLException {

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
