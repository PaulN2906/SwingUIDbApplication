package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.Stoc;
import javax.swing.*;
import java.awt.*;

public class StocFormDialog extends JDialog {

    private final JTextField bookIdField;
    private final JTextField libraryIdField;
    private final JTextField nrField;
    private boolean succeeded;
    private Stoc stoc;

    public StocFormDialog(Frame parent) {
        this(parent, null);
    }

    public StocFormDialog(Frame parent, Stoc stoc) {
        super(parent, (stoc == null ? "Adaugă Stoc" : "Editează Stoc"), true);
        this.stoc = stoc;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5);

        JLabel bookLabel = new JLabel("ID Carte:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(bookLabel, cs);

        bookIdField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(bookIdField, cs);

        JLabel libraryLabel = new JLabel("ID Bibliotecă:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(libraryLabel, cs);

        libraryIdField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(libraryIdField, cs);

        JLabel nrLabel = new JLabel("Nr. Exemplare:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(nrLabel, cs);

        nrField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(nrField, cs);

        if (stoc != null) {
            bookIdField.setText(String.valueOf(stoc.getCarteID()));
            libraryIdField.setText(String.valueOf(stoc.getBibliotecaID()));
            nrField.setText(String.valueOf(stoc.getNrExemplare()));
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (bookIdField.getText().trim().isEmpty() ||
                    libraryIdField.getText().trim().isEmpty() ||
                    nrField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(StocFormDialog.this,
                        "Toate câmpurile trebuie completate!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int bookId;
            int libraryId;
            int nr;
            try {
                bookId = Integer.parseInt(bookIdField.getText().trim());
                libraryId = Integer.parseInt(libraryIdField.getText().trim());
                nr = Integer.parseInt(nrField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(StocFormDialog.this,
                        "ID-urile și numărul de exemplare trebuie să fie numere!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (StocFormDialog.this.stoc == null) {
                StocFormDialog.this.stoc = new Stoc();
            }
            StocFormDialog.this.stoc.setCarteID(bookId);
            StocFormDialog.this.stoc.setBibliotecaID(libraryId);
            StocFormDialog.this.stoc.setNrExemplare(nr);
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

    public Stoc getStoc() {
        return stoc;
    }
}
