package ro.upb.proiect3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // baza de date MySQL locală:
    private static final String URL = "jdbc:mysql://localhost:3306/proiect3_db";
    private static final String USER = "pr3_user";
    private static final String PASSWORD = "654321";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
