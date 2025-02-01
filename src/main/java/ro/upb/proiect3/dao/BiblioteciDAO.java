package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BiblioteciDAO {

    private static final Logger LOGGER = Logger.getLogger(BiblioteciDAO.class.getName());

    public static void insert(Biblioteca bib) {
        String sql = "INSERT INTO biblioteci (Denumire, Adresa) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, bib.getDenumire());
                ps.setString(2, bib.getAdresa());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting library", e);
        }
    }

    public static List<Biblioteca> findAll() {
        List<Biblioteca> lista = new ArrayList<>();
        String sql = "SELECT * FROM biblioteci";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Biblioteca bib = new Biblioteca();
                    bib.setBibliotecaID(rs.getInt("BibliotecaID"));
                    bib.setDenumire(rs.getString("Denumire"));
                    bib.setAdresa(rs.getString("Adresa"));
                    lista.add(bib);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all libraries", e);
        }
        return lista;
    }

    public static void update(Biblioteca bib) {
        String sql = "UPDATE biblioteci SET Denumire=?, Adresa=? WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, bib.getDenumire());
                ps.setString(2, bib.getAdresa());
                ps.setInt(3, bib.getBibliotecaID());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating library", e);
        }
    }

    public static void delete(int bibliotecaID) {
        String sql = "DELETE FROM biblioteci WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, bibliotecaID);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting library", e);
        }
    }

    public static Biblioteca findById(int bibliotecaID) {
        String sql = "SELECT * FROM biblioteci WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, bibliotecaID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Biblioteca bib = new Biblioteca();
                        bib.setBibliotecaID(rs.getInt("BibliotecaID"));
                        bib.setDenumire(rs.getString("Denumire"));
                        bib.setAdresa(rs.getString("Adresa"));
                        return bib;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding library by ID", e);
        }
        return null;
    }
}
