package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.Contributie;
import javax.swing.*;
import java.awt.*;

public class ContributieFormDialog extends JDialog {

    private final JTextField authorIdField;
    private final JTextField bookIdField;
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

        JLabel authorLabel = new JLabel("ID Autor:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(authorLabel, cs);

        authorIdField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(authorIdField, cs);

        JLabel bookLabel = new JLabel("ID Carte:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(bookLabel, cs);

        bookIdField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(bookIdField, cs);

        JLabel rolLabel = new JLabel("Rol Contribuție:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(rolLabel, cs);

        rolField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(rolField, cs);

        if (contributie != null) {
            authorIdField.setText(String.valueOf(contributie.getAutorID()));
            bookIdField.setText(String.valueOf(contributie.getCarteID()));
            rolField.setText(contributie.getRolContributie());
        }

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (authorIdField.getText().trim().isEmpty() ||
                    bookIdField.getText().trim().isEmpty() ||
                    rolField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(ContributieFormDialog.this,
                        "Toate câmpurile trebuie completate!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int authorId;
            int bookId;
            try {
                authorId = Integer.parseInt(authorIdField.getText().trim());
                bookId = Integer.parseInt(bookIdField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ContributieFormDialog.this,
                        "ID-urile trebuie să fie numere!",
                        "Eroare",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            succeeded = true;
            if (ContributieFormDialog.this.contributie == null) {
                ContributieFormDialog.this.contributie = new Contributie();
            }
            ContributieFormDialog.this.contributie.setAutorID(authorId);
            ContributieFormDialog.this.contributie.setCarteID(bookId);
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
