package sql;

import enumerations.eVarType;
import genSystemFunctions.SysPulse;
import uniqueItems.OneVar;

import enumerations.eVarAllocate;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SQLRequest {

    public Connection connect(String db) {
        Connection conn = null;
        try {
            // Укажите путь к вашей базе данных SQLite
            String url = "jdbc:sqlite:" + db;
            // Устанавливаем соединение
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public OneVar OneVarDao(String name, Connection conn) {
        String tableName = "varListSys";
        OneVar oneVar = new OneVar();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName +
                    " WHERE name = '" + name + "';");
            oneVar.setName(resultSet.getString("userName"));
            oneVar.setType(resultSet.getString("type"));
            oneVar.setComment(resultSet.getString("comment"));
            if (resultSet.getString("addrEnable").equals("1")) {
                eVarAllocate alloc = eVarAllocate.findByValue(resultSet.getString("allocate"));
                StringBuilder sb = new StringBuilder();
                if (oneVar.getType().equals(eVarType.BOOL)) {
                    sb.append(resultSet.getString("word")).append(".").append(resultSet.getString("bit"));
                } else {
                    sb.append(resultSet.getString("word"));
                }
                oneVar.setAddress(alloc, sb.toString());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return oneVar;
    }

    public Map<String, SysPulse> SysPulseClockDao(String name, Connection conn) {
        Map<String, SysPulse> map = new LinkedHashMap<>();
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + "cfg" + name);
            while (resultSet.next()) {
                SysPulse pulse = new SysPulse();
                pulse.setName(name.toLowerCase());
                if (resultSet.getInt("enable") == 1) {
                    pulse.setFullTime(
                            resultSet.getInt("day"),
                            resultSet.getInt("hour"),
                            resultSet.getInt("minute"),
                            resultSet.getInt("second"),
                            resultSet.getInt("ms"));
                    map.put(pulse.getFullTime(), pulse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return map;
    }




}