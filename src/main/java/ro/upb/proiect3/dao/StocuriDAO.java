package ro.upb.proiect3.dao;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.model.Stoc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StocuriDAO {

    public static void insert(Stoc stoc) {
        String sql = "INSERT INTO stocuri (CarteID, BibliotecaID, NrExemplare) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stoc.getCarteID());
            ps.setInt(2, stoc.getBibliotecaID());
            ps.setInt(3, stoc.getNrExemplare());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Stoc> findAll() {
        List<Stoc> list = new ArrayList<>();
        String sql = "SELECT * FROM stocuri";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Stoc stoc = new Stoc();
                stoc.setStocID(rs.getInt("StocID"));
                stoc.setCarteID(rs.getInt("CarteID"));
                stoc.setBibliotecaID(rs.getInt("BibliotecaID"));
                stoc.setNrExemplare(rs.getInt("NrExemplare"));
                list.add(stoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void update(Stoc stoc) {
        String sql = "UPDATE stocuri SET CarteID=?, BibliotecaID=?, NrExemplare=? WHERE StocID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stoc.getCarteID());
            ps.setInt(2, stoc.getBibliotecaID());
            ps.setInt(3, stoc.getNrExemplare());
            ps.setInt(4, stoc.getStocID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int stocID) {
        String sql = "DELETE FROM stocuri WHERE StocID=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, stocID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
