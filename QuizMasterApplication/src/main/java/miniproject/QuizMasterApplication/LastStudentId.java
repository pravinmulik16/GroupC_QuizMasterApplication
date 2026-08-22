package miniproject.QuizMasterApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LastStudentId {
    int lastDbId;
    public void getLastStudentId() {
        try {
        DBConnection dbConnection = new DBConnection();
        Connection con = dbConnection.getConnectionDetails();
        Statement stmt = con.createStatement();
        String sql = "select MAX(id) as lastDbId from student";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            lastDbId = rs.getInt(1);
            System.out.println(lastDbId);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
