package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.Biblioteca;

import javax.swing.*;
import java.awt.*;

public class BibliotecaFormDialog extends JDialog {
    private final JTextField denumireField;
    private final JTextField adresaField;
    private boolean succeeded;
    private Biblioteca biblioteca;

    public BibliotecaFormDialog(Frame parent) {
        this(parent, null);
    }

    public BibliotecaFormDialog(Frame parent, Biblioteca biblioteca) {
        super(parent, (biblioteca == null ? "Adaugă Bibliotecă" : "Editează Bibliotecă"), true);
        this.biblioteca = biblioteca;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5);

        JLabel denumireLabel = new JLabel("Denumire:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(denumireLabel, cs);

        denumireField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(denumireField, cs);

        JLabel adresaLabel = new JLabel("Adresa:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(adresaLabel, cs);

        adresaField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(adresaField, cs);

        if (biblioteca != null) {
            denumireField.setText(biblioteca.getDenumire());
            adresaField.setText(biblioteca.getAdresa());
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (denumireField.getText().trim().isEmpty() ||
                    adresaField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(BibliotecaFormDialog.this,
                        "Toate câmpurile trebuie completate!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (BibliotecaFormDialog.this.biblioteca == null) {
                BibliotecaFormDialog.this.biblioteca = new Biblioteca(0,
                        denumireField.getText().trim(),
                        adresaField.getText().trim());
            } else {
                BibliotecaFormDialog.this.biblioteca.setDenumire(denumireField.getText().trim());
                BibliotecaFormDialog.this.biblioteca.setAdresa(adresaField.getText().trim());
            }
            dispose();
        });

        cancelButton.addActionListener(e -> {
            succeeded = false;
            dispose();
        });

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }
}
