package sql;

import java.sql.*;

public class SQLRequest {

    public Connection connect(String db) {
        Connection conn = null;
        try {
            // Укажите путь к вашей базе данных SQLite
            String url = "jdbc:sqlite:"+db;
            // Устанавливаем соединение
            conn = DriverManager.getConnection(url);
            System.out.println("Подключение к SQLite успешно установлено.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void test(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM varListSys");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
