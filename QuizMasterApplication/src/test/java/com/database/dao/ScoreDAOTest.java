package com.database.dao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreDAOTest {

    private final ScoreDAO scoreDAO = new ScoreDAO();

    @Test
    void testGradeCalculationBoundaries() {
        // Test Boundary for Grade A (Score >= 8)
        assertEquals("A", scoreDAO.calculateGrade(8));
        assertEquals("A", scoreDAO.calculateGrade(10));

        // Test Boundary for Grade B (5 <= Score < 8)
        assertEquals("B", scoreDAO.calculateGrade(5));
        assertEquals("B", scoreDAO.calculateGrade(7));

        // Test Boundary for Grade C (Score < 5)
        assertEquals("C", scoreDAO.calculateGrade(4));
        assertEquals("C", scoreDAO.calculateGrade(0));
    }
}
