package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.UserDAO;
import ro.upb.proiect3.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Înregistrare");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        JLabel statusLabel = new JLabel();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(statusLabel, gbc);

        add(panel);

        loginButton.addActionListener(e -> doLogin());
        registerButton.addActionListener(e -> openRegistration());
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completează toate câmpurile!");
            return;
        }

        User user = UserDAO.validateUser(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login reușit! Bine ai venit, " + user.getUsername());
            MainFrame mainFrame = new MainFrame(user);
            mainFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username sau parolă incorectă!");
        }
    }

    private void openRegistration() {
        RegistrationFrame regFrame = new RegistrationFrame();
        regFrame.setVisible(true);
    }
}
