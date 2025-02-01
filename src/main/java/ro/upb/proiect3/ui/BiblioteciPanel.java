package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.BiblioteciDAO;
import ro.upb.proiect3.model.Biblioteca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BiblioteciPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public BiblioteciPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "Biblioteca ID", "Denumire", "Adresa"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                if (columnIndex == 1) return Integer.class;
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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

        refreshBtn.addActionListener(e -> refreshTable());
        addBtn.addActionListener(e -> showAddForm());
        editBtn.addActionListener(e -> showEditForm());
        deleteBtn.addActionListener(e -> deleteSelected());

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Biblioteca> biblioteci = BiblioteciDAO.findAll();
        for (Biblioteca b : biblioteci) {
            Object[] rowData = {false, b.getBibliotecaID(), b.getDenumire(), b.getAdresa()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddForm() {
        Frame parent = JOptionPane.getFrameForComponent(this);
        BibliotecaFormDialog formDialog = new BibliotecaFormDialog(parent);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            BiblioteciDAO.insert(formDialog.getBiblioteca());
            refreshTable();
        }
    }

    private void showEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează o bibliotecă pentru editare!");
            return;
        }
        int bibliotecaID = (int) tableModel.getValueAt(row, 1);
        Biblioteca bib = BiblioteciDAO.findById(bibliotecaID);
        if (bib == null) {
            JOptionPane.showMessageDialog(this, "Biblioteca nu a fost găsită în DB!");
            return;
        }
        Frame parent = JOptionPane.getFrameForComponent(this);
        BibliotecaFormDialog formDialog = new BibliotecaFormDialog(parent, bib);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            BiblioteciDAO.update(formDialog.getBiblioteca());
            refreshTable();
        }
    }

    private void deleteSelected() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                int bibliotecaID = (int) tableModel.getValueAt(i, 1);
                BiblioteciDAO.delete(bibliotecaID);
                tableModel.removeRow(i);
            }
        }
    }
}
