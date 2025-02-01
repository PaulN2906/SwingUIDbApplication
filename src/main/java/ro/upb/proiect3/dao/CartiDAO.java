package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Carte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartiDAO {

    private static final Logger LOGGER = Logger.getLogger(CartiDAO.class.getName());

    public static void insert(Carte carte) {
        String sql = "INSERT INTO carti (Denumire, AnAparitie, Editura) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, carte.getDenumire());
                ps.setInt(2, carte.getAnAparitie());
                ps.setString(3, carte.getEditura());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting book", e);
        }
    }

    public static List<Carte> findAll() {
        List<Carte> lista = new ArrayList<>();
        String sql = "SELECT * FROM carti";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Carte carte = new Carte();
                    carte.setCarteID(rs.getInt("CarteID"));
                    carte.setDenumire(rs.getString("Denumire"));
                    carte.setAnAparitie(rs.getInt("AnAparitie"));
                    carte.setEditura(rs.getString("Editura"));
                    lista.add(carte);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all books", e);
        }
        return lista;
    }

    public static void update(Carte carte) {
        String sql = "UPDATE carti SET Denumire=?, AnAparitie=?, Editura=? WHERE CarteID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, carte.getDenumire());
                ps.setInt(2, carte.getAnAparitie());
                ps.setString(3, carte.getEditura());
                ps.setInt(4, carte.getCarteID());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating book", e);
        }
    }

    public static void delete(int carteID) {
        String sql = "DELETE FROM carti WHERE CarteID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, carteID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting book", e);
        }
    }

    public static Carte findById(int carteID) {
        String sql = "SELECT * FROM carti WHERE CarteID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, carteID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Carte carte = new Carte();
                        carte.setCarteID(rs.getInt("CarteID"));
                        carte.setDenumire(rs.getString("Denumire"));
                        carte.setAnAparitie(rs.getInt("AnAparitie"));
                        carte.setEditura(rs.getString("Editura"));
                        return carte;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding book by ID", e);
        }
        return null;
    }
}
