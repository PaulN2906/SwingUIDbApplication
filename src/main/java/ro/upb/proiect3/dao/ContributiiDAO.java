package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Contributie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContributiiDAO {

    private static final Logger LOGGER = Logger.getLogger(ContributiiDAO.class.getName());

    public static void insert(Contributie c) {
        String sql = "INSERT INTO contributii (AutorID, CarteID, RolContributie) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, c.getAutorID());
                ps.setInt(2, c.getCarteID());
                ps.setString(3, c.getRolContributie());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting contribution", e);
        }
    }

    public static List<Contributie> findAll() {
        List<Contributie> list = new ArrayList<>();
        String sql = "SELECT * FROM contributii";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Contributie c = new Contributie();
                    c.setContributieID(rs.getInt("ContributieID"));
                    c.setAutorID(rs.getInt("AutorID"));
                    c.setCarteID(rs.getInt("CarteID"));
                    c.setRolContributie(rs.getString("RolContributie"));
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all contributions", e);
        }
        return list;
    }

    public static void update(Contributie c) {
        String sql = "UPDATE contributii SET AutorID=?, CarteID=?, RolContributie=? WHERE ContributieID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, c.getAutorID());
                ps.setInt(2, c.getCarteID());
                ps.setString(3, c.getRolContributie());
                ps.setInt(4, c.getContributieID());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating contribution", e);
        }
    }

    public static void delete(int contributieID) {
        String sql = "DELETE FROM contributii WHERE ContributieID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, contributieID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting contribution", e);
        }
    }

    public static Contributie findById(int contributieID) {
        String sql = "SELECT * FROM contributii WHERE ContributieID = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, contributieID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Contributie c = new Contributie();
                        c.setContributieID(rs.getInt("ContributieID"));
                        c.setAutorID(rs.getInt("AutorID"));
                        c.setCarteID(rs.getInt("CarteID"));
                        c.setRolContributie(rs.getString("RolContributie"));
                        return c;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding contribution by ID", e);
        }
        return null;
    }

}
