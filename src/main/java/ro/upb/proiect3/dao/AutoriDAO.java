package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Autor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AutoriDAO {

    private static final Logger LOGGER = Logger.getLogger(AutoriDAO.class.getName());

    public static void insert(Autor autor) {
        String sql = "INSERT INTO autori (NumeAutor, PrenumeAutor, TaraOrigine) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, autor.getNumeAutor());
                ps.setString(2, autor.getPrenumeAutor());
                ps.setString(3, autor.getTaraOrigine());
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting author", e);
        }
    }

    public static List<Autor> findAll() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT * FROM autori";

        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Autor a = new Autor();
                    a.setAutorID(rs.getInt("AutorID"));
                    a.setNumeAutor(rs.getString("NumeAutor"));
                    a.setPrenumeAutor(rs.getString("PrenumeAutor"));
                    a.setTaraOrigine(rs.getString("TaraOrigine"));

                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding all authors", e);
        }
        return lista;
    }

    public static void update(Autor autor) {
        String sql = "UPDATE autori SET NumeAutor=?, PrenumeAutor=?, TaraOrigine=? WHERE AutorID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, autor.getNumeAutor());
                ps.setString(2, autor.getPrenumeAutor());
                ps.setString(3, autor.getTaraOrigine());
                ps.setInt(4, autor.getAutorID());
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating author", e);
        }
    }

    public static void delete(int autorID) {
        String sql = "DELETE FROM autori WHERE AutorID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, autorID);
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting author", e);
        }
    }

    public static Autor findById(int autorID) {
        String sql = "SELECT * FROM autori WHERE AutorID=?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, autorID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Autor a = new Autor();
                        a.setAutorID(rs.getInt("AutorID"));
                        a.setNumeAutor(rs.getString("NumeAutor"));
                        a.setPrenumeAutor(rs.getString("PrenumeAutor"));
                        a.setTaraOrigine(rs.getString("TaraOrigine"));
                        return a;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding author by ID", e);
        }
        return null;
    }
}
