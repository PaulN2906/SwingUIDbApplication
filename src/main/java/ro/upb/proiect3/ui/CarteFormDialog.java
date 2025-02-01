package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.Carte;

import javax.swing.*;
import java.awt.*;

public class CarteFormDialog extends JDialog {
    private final JTextField denumireField;
    private final JTextField anField;
    private final JTextField edituraField;
    private boolean succeeded;
    private Carte carte;

    public CarteFormDialog(Frame parent) {
        this(parent, null);
    }

    public CarteFormDialog(Frame parent, Carte carte) {
        super(parent, (carte == null ? "Adaugă Carte" : "Editează Carte"), true);
        this.carte = carte;
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

        JLabel anLabel = new JLabel("An apariție:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(anLabel, cs);

        anField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(anField, cs);

        JLabel edituraLabel = new JLabel("Editură:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(edituraLabel, cs);

        edituraField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(edituraField, cs);

        if (carte != null) {
            denumireField.setText(carte.getDenumire());
            anField.setText(String.valueOf(carte.getAnAparitie()));
            edituraField.setText(carte.getEditura());
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (denumireField.getText().trim().isEmpty() ||
                    anField.getText().trim().isEmpty() ||
                    edituraField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(CarteFormDialog.this,
                        "Toate câmpurile trebuie completate!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int an;
            try {
                an = Integer.parseInt(anField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(CarteFormDialog.this,
                        "Anul apariției trebuie să fie un număr!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (CarteFormDialog.this.carte == null) {
                CarteFormDialog.this.carte = new Carte(0,
                        denumireField.getText().trim(),
                        an,
                        edituraField.getText().trim());
            } else {
                CarteFormDialog.this.carte.setDenumire(denumireField.getText().trim());
                CarteFormDialog.this.carte.setAnAparitie(an);
                CarteFormDialog.this.carte.setEditura(edituraField.getText().trim());
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

    public Carte getCarte() {
        return carte;
    }
}
