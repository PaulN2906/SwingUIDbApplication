package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.User;
import ro.upb.proiect3.model.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class UserDAO {

    public static boolean registerUser(User user) {
        String sql = "INSERT INTO users (Username, PasswordHash, Role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPasswordHash());
                ps.setString(3, user.getRole().toString());
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User validateUser(String username, String plainPassword) {
        String sql = "SELECT * FROM users WHERE Username = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if(rs.next()) {
                        User user = new User();
                        user.setUserID(rs.getInt("UserID"));
                        user.setUsername(rs.getString("Username"));
                        user.setPasswordHash(rs.getString("PasswordHash"));
                        user.setRole(Role.valueOf(rs.getString("Role")));

                        if(BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
                            return user;
                        }
                    }
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
}
