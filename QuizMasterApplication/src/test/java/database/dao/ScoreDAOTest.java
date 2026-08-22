package database.dao;

import com.database.dao.ScoreDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreDAOTest {

    private final ScoreDAO scoreDAO = new ScoreDAO();

    @Test
    void testGradeCalculationBoundaries() {

        assertEquals("A", scoreDAO.calculateGrade(8));
        assertEquals("A", scoreDAO.calculateGrade(10));


        assertEquals("B", scoreDAO.calculateGrade(5));
        assertEquals("B", scoreDAO.calculateGrade(7));


        assertEquals("C", scoreDAO.calculateGrade(4));
        assertEquals("C", scoreDAO.calculateGrade(0));
    }
}
