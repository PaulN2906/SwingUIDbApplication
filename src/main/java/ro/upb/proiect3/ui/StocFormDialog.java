package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.BiblioteciDAO;
import ro.upb.proiect3.dao.CartiDAO;
import ro.upb.proiect3.model.Biblioteca;
import ro.upb.proiect3.model.Carte;
import ro.upb.proiect3.model.Stoc;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StocFormDialog extends JDialog {

    private final JComboBox<Carte> bookCombo;
    private final JComboBox<Biblioteca> libraryCombo;
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

        JLabel bookLabel = new JLabel("Carte:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(bookLabel, cs);

        bookCombo = new JComboBox<>();
        List<Carte> carti = CartiDAO.findAll();
        for (Carte c : carti) {
            bookCombo.addItem(c);
        }
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(bookCombo, cs);

        JLabel libraryLabel = new JLabel("Bibliotecă:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(libraryLabel, cs);

        libraryCombo = new JComboBox<>();
        List<Biblioteca> biblioteci = BiblioteciDAO.findAll();
        for (Biblioteca b : biblioteci) {
            libraryCombo.addItem(b);
        }
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(libraryCombo, cs);

        JLabel nrLabel = new JLabel("Nr. Exemplare:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(nrLabel, cs);

        nrField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(nrField, cs);

        if (stoc != null) {
            for (int i = 0; i < bookCombo.getItemCount(); i++) {
                if (bookCombo.getItemAt(i).getCarteID() == stoc.getCarteID()) {
                    bookCombo.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < libraryCombo.getItemCount(); i++) {
                if (libraryCombo.getItemAt(i).getBibliotecaID() == stoc.getBibliotecaID()) {
                    libraryCombo.setSelectedIndex(i);
                    break;
                }
            }
            nrField.setText(String.valueOf(stoc.getNrExemplare()));
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (nrField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(StocFormDialog.this,
                        "Câmpul pentru numărul de exemplare trebuie completat!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int nr;
            try {
                nr = Integer.parseInt(nrField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(StocFormDialog.this,
                        "Nr. Exemplare trebuie să fie un număr!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (StocFormDialog.this.stoc == null) {
                StocFormDialog.this.stoc = new Stoc();
            }
            Carte selectedBook = (Carte) bookCombo.getSelectedItem();
            Biblioteca selectedLibrary = (Biblioteca) libraryCombo.getSelectedItem();
            assert selectedBook != null;
            StocFormDialog.this.stoc.setCarteID(selectedBook.getCarteID());
            assert selectedLibrary != null;
            StocFormDialog.this.stoc.setBibliotecaID(selectedLibrary.getBibliotecaID());
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
