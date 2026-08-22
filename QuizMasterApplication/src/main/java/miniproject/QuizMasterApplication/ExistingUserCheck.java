package miniproject.QuizMasterApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExistingUserCheck {

        int dbId;
        String dbFirstname;
        String dbLastname;
        String dbUsername;
        String dbPassword;
        String dbCity;
        String dbEmail;
        long dbMobilenumber;
    {
        try {
        DBConnection db = new DBConnection();
        Connection con = db.getConnectionDetails();
        Statement stmt = con.createStatement();
            String sql = "select * from student";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {


                dbId = rs.getInt(1);
                dbFirstname = rs.getString(2);
                dbLastname = rs.getString(3);
                dbUsername = rs.getString(4);
                dbPassword = rs.getString(5);
                dbCity = rs.getString(6);
                dbEmail = rs.getString(7);
                dbMobilenumber = rs.getLong(8);
            }

            con.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
