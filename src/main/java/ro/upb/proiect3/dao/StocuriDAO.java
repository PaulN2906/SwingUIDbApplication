package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Stoc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StocuriDAO {

    private static final Logger LOGGER = Logger.getLogger(StocuriDAO.class.getName());

    public static void insert(Stoc stoc) {
        String sql = "INSERT INTO stocuri (CarteID, BibliotecaID, NrExemplare) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, stoc.getCarteID());
                ps.setInt(2, stoc.getBibliotecaID());
                ps.setInt(3, stoc.getNrExemplare());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting stock", e);
        }
    }

    public static List<Stoc> findAll() {
        List<Stoc> list = new ArrayList<>();
        String sql = "SELECT * FROM stocuri";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Stoc stoc = new Stoc();
                    stoc.setStocID(rs.getInt("StocID"));
                    stoc.setCarteID(rs.getInt("CarteID"));
                    stoc.setBibliotecaID(rs.getInt("BibliotecaID"));
                    stoc.setNrExemplare(rs.getInt("NrExemplare"));
                    list.add(stoc);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all stocks", e);
        }
        return list;
    }

    public static void update(Stoc stoc) {
        String sql = "UPDATE stocuri SET CarteID=?, BibliotecaID=?, NrExemplare=? WHERE StocID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, stoc.getCarteID());
                ps.setInt(2, stoc.getBibliotecaID());
                ps.setInt(3, stoc.getNrExemplare());
                ps.setInt(4, stoc.getStocID());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating stock", e);
        }
    }

    public static void delete(int stocID) {
        String sql = "DELETE FROM stocuri WHERE StocID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, stocID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting stock", e);
        }
    }

    public static Stoc findById(int stocID) {
        String sql = "SELECT * FROM stocuri WHERE StocID = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, stocID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Stoc s = new Stoc();
                        s.setStocID(rs.getInt("StocID"));
                        s.setCarteID(rs.getInt("CarteID"));
                        s.setBibliotecaID(rs.getInt("BibliotecaID"));
                        s.setNrExemplare(rs.getInt("NrExemplare"));
                        return s;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding stock by ID", e);
        }
        return null;
    }

}
