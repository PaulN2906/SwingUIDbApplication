package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.Autor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutorFormDialog extends JDialog {

    private JTextField numeField;
    private JTextField prenumeField;
    private JTextField taraField;
    private JButton saveButton;
    private JButton cancelButton;
    private boolean succeeded;
    private Autor autor;

    public AutorFormDialog(Frame parent) {
        this(parent, null);
    }

    public AutorFormDialog(Frame parent, Autor autor) {
        super(parent, (autor == null ? "Adaugă Autor" : "Editează Autor"), true);
        this.autor = autor;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.insets = new Insets(5, 5, 5, 5);

        JLabel numeLabel = new JLabel("Nume Autor:");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(numeLabel, cs);

        numeField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(numeField, cs);

        JLabel prenumeLabel = new JLabel("Prenume Autor:");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(prenumeLabel, cs);

        prenumeField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        panel.add(prenumeField, cs);

        JLabel taraLabel = new JLabel("Țara Origine:");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(taraLabel, cs);

        taraField = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(taraField, cs);

        if (autor != null) {
            numeField.setText(autor.getNumeAutor());
            prenumeField.setText(autor.getPrenumeAutor());
            taraField.setText(autor.getTaraOrigine());
        }

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        JPanel bp = new JPanel();
        bp.add(saveButton);
        bp.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numeField.getText().trim().isEmpty() ||
                        prenumeField.getText().trim().isEmpty() ||
                        taraField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(AutorFormDialog.this,
                            "Toate câmpurile trebuie completate!",
                            "Eroare",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                succeeded = true;
                if (AutorFormDialog.this.autor == null) {
                    AutorFormDialog.this.autor = new Autor(0,
                            numeField.getText().trim(),
                            prenumeField.getText().trim(),
                            taraField.getText().trim());
                } else {
                    AutorFormDialog.this.autor.setNumeAutor(numeField.getText().trim());
                    AutorFormDialog.this.autor.setPrenumeAutor(prenumeField.getText().trim());
                    AutorFormDialog.this.autor.setTaraOrigine(taraField.getText().trim());
                }
                dispose();
            }
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

    public Autor getAutor() {
        return autor;
    }
}
