package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.UserDAO;
import ro.upb.proiect3.model.Role;
import ro.upb.proiect3.model.User;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;
    private final JComboBox<Role> roleCombo;

    public RegistrationFrame() {
        setTitle("Înregistrare Utilizator");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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

        JLabel passwordLabel = new JLabel("Parolă:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        JLabel confirmLabel = new JLabel("Confirmă parolă:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(confirmLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(confirmPasswordField, gbc);

        JLabel roleLabel = new JLabel("Rol:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(roleLabel, gbc);

        roleCombo = new JComboBox<>(Role.values());
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(roleCombo, gbc);

        JButton registerButton = new JButton("Înregistrează-te");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(registerButton, gbc);

        JLabel statusLabel = new JLabel("");
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(statusLabel, gbc);

        add(panel);

        registerButton.addActionListener(e -> doRegister());
    }

    private void doRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completează toate câmpurile!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Parolele nu se potrivesc!");
            return;
        }

        String hashedPassword = UserDAO.hashPassword(password);
        Role selectedRole = (Role) roleCombo.getSelectedItem();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPasswordHash(hashedPassword);
        newUser.setRole(selectedRole);

        if (UserDAO.registerUser(newUser)) {
            JOptionPane.showMessageDialog(this, "Înregistrare reușită! Te poți loga acum.");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Eroare la înregistrare! Încearcă din nou.");
        }
    }
}
