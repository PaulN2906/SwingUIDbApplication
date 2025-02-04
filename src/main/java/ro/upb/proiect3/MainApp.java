package ro.upb.proiect3;

import com.formdev.flatlaf.FlatDarkLaf;
import ro.upb.proiect3.ui.LoginFrame;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp {
    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch(Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to initialize FlatLaf", ex);
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
