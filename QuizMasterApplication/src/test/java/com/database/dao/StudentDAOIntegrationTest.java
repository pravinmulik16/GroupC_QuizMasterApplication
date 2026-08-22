package com.database.dao;

import com.database.config.DatabaseConfig;
import com.database.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDAOIntegrationTest {

    private final StudentDAO studentDAO = new StudentDAO();
    private final String TEST_USER = "junit_test_user";

    @BeforeEach
    @AfterEach
    void cleanUpTestData() throws SQLException {
        // Wipe away the mock profile entry automatically before and after each test run
        String cleanQuery = "DELETE FROM student WHERE username = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(cleanQuery)) {
            stmt.setString(1, TEST_USER);
            stmt.executeUpdate();
        }
    }

    @Test
    void testRegistrationAndLoginFlow() throws SQLException {
        Student mockStudent = new Student(
                "JUnit", "User", TEST_USER, "pass123",
                "BLR", "junit@test.com", "9999999999"
        );

        // 1. Verify username duplicate lookup states 'false' initially
        assertFalse(studentDAO.isUsernameDuplicate(TEST_USER));

        // 2. Perform live structural registration commit
        assertTrue(studentDAO.registerStudent(mockStudent));

        // 3. Confirm duplicate protection framework blocks a second attempt now
        assertTrue(studentDAO.isUsernameDuplicate(TEST_USER));

        // 4. Test database credential validation authentication routing logic
        int validatedId = studentDAO.loginStudent(TEST_USER, "pass123");
        assertNotEquals(-1, validatedId, "Login authentication mapping tracking parameters failed.");

        // 5. Verify incorrect login handling
        assertEquals(-1, studentDAO.loginStudent(TEST_USER, "wrong_password"));
    }
}
