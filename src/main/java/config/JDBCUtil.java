package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

    private static final String URL = "jdbc:mysql://localhost:3306/quanlikhohang?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (Exception e) {
            e.printStackTrace(); // in lỗi thật ra console
            return null;
        }
    }

    public static void closeConnection(Connection c) {
        try {
            if (c != null)
                c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
