package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.AutoriDAO;
import ro.upb.proiect3.model.Autor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoriPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public AutoriPanel() {
        setLayout(new BorderLayout());

        // Numele coloanelor (prima e "Select" pentru checkbox)
        String[] columnNames = {"Select", "ID Autor", "Nume Autor", "Prenume Autor", "Tara Origine"};

        // Cream modelul de tabel cu 0 rânduri, 5 coloane
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Indică tipul de date pentru fiecare coloană
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    // Prima coloană (index 0) e Boolean => checkbox
                    return Boolean.class;
                } else if (columnIndex == 1) {
                    // A doua coloană e ID (int)
                    return Integer.class;
                }
                // Restul sunt String
                return String.class;
            }

            // Controlăm ce coloane sunt editabile (doar col 0)
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // doar checkbox
            }
        };

        // Creăm JTable pe baza modelului
        table = new JTable(tableModel);

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel pentru butoane
        JPanel buttonPanel = new JPanel();
        JButton refreshBtn = new JButton("Refresh");
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(refreshBtn);
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Evenimente butoane
        refreshBtn.addActionListener(e -> refreshTable());
        addBtn.addActionListener(e -> addAutor());
        editBtn.addActionListener(e -> editAutor());
        deleteBtn.addActionListener(e -> deleteSelectedAutori());

        // La crearea panelului, încărcăm datele
        refreshTable();
    }

    /** Metodă pentru a reîncărca tabela cu date din DB */
    private void refreshTable() {
        // Ștergem toate rândurile curente
        tableModel.setRowCount(0);
        // Luăm lista de autori din DB
        List<Autor> autori = AutoriDAO.findAll();
        // Adăugăm rânduri în model
        for (Autor a : autori) {
            Object[] rowData = {
                    false,                // prima coloană: checkbox debifat inițial
                    a.getAutorID(),
                    a.getNumeAutor(),
                    a.getPrenumeAutor(),
                    a.getTaraOrigine()
            };
            tableModel.addRow(rowData);
        }
    }

    /** Inserare autor nou printr-un dialog simplu */
    private void addAutor() {
        String nume = JOptionPane.showInputDialog(this, "Nume Autor:");
        String prenume = JOptionPane.showInputDialog(this, "Prenume Autor:");
        String tara = JOptionPane.showInputDialog(this, "Tara Origine:");

        if (nume != null && prenume != null && tara != null) {
            Autor autor = new Autor(0, nume, prenume, tara);
            AutoriDAO.insert(autor);
            refreshTable();
        }
    }

    /** Editarea datelor unui autor selectat din tabel */
    private void editAutor() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează un autor pentru editare!");
            return;
        }
        // Coloana 1 (AutorID)
        int autorID = (int) table.getValueAt(row, 1);

        // Coloanele 2,3,4
        String numeCurent = (String) table.getValueAt(row, 2);
        String prenumeCurent = (String) table.getValueAt(row, 3);
        String taraCurenta = (String) table.getValueAt(row, 4);

        String numeNou = JOptionPane.showInputDialog(this, "Nume Autor:", numeCurent);
        String prenumeNou = JOptionPane.showInputDialog(this, "Prenume Autor:", prenumeCurent);
        String taraNoua = JOptionPane.showInputDialog(this, "Tara Origine:", taraCurenta);

        if (numeNou != null && prenumeNou != null && taraNoua != null) {
            Autor autor = new Autor(autorID, numeNou, prenumeNou, taraNoua);
            AutoriDAO.update(autor);
            refreshTable();
        }
    }

    /** Ștergerea autorilor pentru care checkbox-ul e bifat */
    private void deleteSelectedAutori() {
        // Parcurgem rândurile în ordine inversă (evităm probleme cu indexurile)
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                // Aflăm autorID de pe coloana 1
                int autorID = (int) tableModel.getValueAt(i, 1);

                // Ștergere din DB
                AutoriDAO.delete(autorID);

                // Ștergere din model
                tableModel.removeRow(i);
            }
        }
    }
}
