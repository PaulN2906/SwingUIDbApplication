package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.AutoriDAO;
import ro.upb.proiect3.dao.CartiDAO;
import ro.upb.proiect3.model.Autor;
import ro.upb.proiect3.model.Carte;
import ro.upb.proiect3.model.Contributie;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ContributieFormDialog extends JDialog {

    private final JComboBox<Autor> authorCombo;
    private final JComboBox<Carte> bookCombo;
    private final JTextField rolField;
    private boolean succeeded;
    private Contributie contributie;

    public ContributieFormDialog(Frame parent) {
        this(parent, null);
    }

    public ContributieFormDialog(Frame parent, Contributie contributie) {
        super(parent, (contributie == null ? "Adaugă Contribuție" : "Editează Contribuție"), true);
        this.contributie = contributie;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5);

        JLabel authorLabel = new JLabel("Autor:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(authorLabel, cs);

        authorCombo = new JComboBox<>();
        List<Autor> autori = AutoriDAO.findAll();
        for (Autor a : autori) {
            authorCombo.addItem(a);
        }
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(authorCombo, cs);

        JLabel bookLabel = new JLabel("Carte:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(bookLabel, cs);

        bookCombo = new JComboBox<>();
        List<Carte> carti = CartiDAO.findAll();
        for (Carte c : carti) {
            bookCombo.addItem(c);
        }
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(bookCombo, cs);

        JLabel rolLabel = new JLabel("Rol Contribuție:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(rolLabel, cs);

        rolField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(rolField, cs);

        if (contributie != null) {
            for (int i = 0; i < authorCombo.getItemCount(); i++) {
                if (authorCombo.getItemAt(i).getAutorID() == contributie.getAutorID()) {
                    authorCombo.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < bookCombo.getItemCount(); i++) {
                if (bookCombo.getItemAt(i).getCarteID() == contributie.getCarteID()) {
                    bookCombo.setSelectedIndex(i);
                    break;
                }
            }
            rolField.setText(contributie.getRolContributie());
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (rolField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(ContributieFormDialog.this,
                        "Câmpul pentru rol trebuie completat!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (ContributieFormDialog.this.contributie == null) {
                ContributieFormDialog.this.contributie = new Contributie();
            }
            Autor selectedAuthor = (Autor) authorCombo.getSelectedItem();
            Carte selectedBook = (Carte) bookCombo.getSelectedItem();
            ContributieFormDialog.this.contributie.setAutorID(selectedAuthor.getAutorID());
            ContributieFormDialog.this.contributie.setCarteID(selectedBook.getCarteID());
            ContributieFormDialog.this.contributie.setRolContributie(rolField.getText().trim());
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

    public Contributie getContributie() {
        return contributie;
    }
}
