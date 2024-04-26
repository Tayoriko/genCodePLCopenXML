package sql;

import org.junit.jupiter.api.Test;
import system.GDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class sqlRequestTest {

    @Test
    void connect() throws SQLException {
        SQLRequest sqlRequest = new SQLRequest();
        Connection connection;
        connection = sqlRequest.connect(GDB.dataBase);
        String sql = "SELECT * FROM varListSys";

        try {

            Statement stmt  = connection.createStatement();
            System.out.println("1");
            ResultSet rs    = stmt.executeQuery(sql);
            System.out.println("2");
            // loop through the result set
            while (rs.next()) {
                System.out.println(
                        rs.getString("name") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        connection.close();
    }

}