package ro.upb.proiect3;

import ro.upb.proiect3.db.DatabaseConnection;
import ro.upb.proiect3.ui.MainFrame;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Starting SwingUIDbApplication...");

        // Poți testa conexiunea la DB
        if (DatabaseConnection.getConnection() != null) {
            System.out.println("Conexiune reușită la proiect3_db!");
        } else {
            System.out.println("Eroare la conexiune!");
        }

        SwingUtilities.invokeLater(() -> {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            });

    }
}
