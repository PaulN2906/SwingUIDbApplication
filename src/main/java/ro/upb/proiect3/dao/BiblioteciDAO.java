package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BiblioteciDAO {

    public static void insert(Biblioteca bib) {
        String sql = "INSERT INTO biblioteci (Denumire, Adresa) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bib.getDenumire());
            ps.setString(2, bib.getAdresa());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Biblioteca> findAll() {
        List<Biblioteca> lista = new ArrayList<>();
        String sql = "SELECT * FROM biblioteci";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Biblioteca bib = new Biblioteca();
                bib.setBibliotecaID(rs.getInt("BibliotecaID"));
                bib.setDenumire(rs.getString("Denumire"));
                bib.setAdresa(rs.getString("Adresa"));
                lista.add(bib);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void update(Biblioteca bib) {
        String sql = "UPDATE biblioteci SET Denumire=?, Adresa=? WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bib.getDenumire());
            ps.setString(2, bib.getAdresa());
            ps.setInt(3, bib.getBibliotecaID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int bibliotecaID) {
        String sql = "DELETE FROM biblioteci WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bibliotecaID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Biblioteca findById(int bibliotecaID) {
        String sql = "SELECT * FROM biblioteci WHERE BibliotecaID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
