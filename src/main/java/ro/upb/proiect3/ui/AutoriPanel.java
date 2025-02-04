package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.AutoriDAO;
import ro.upb.proiect3.model.Autor;
import ro.upb.proiect3.model.User;
import ro.upb.proiect3.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AutoriPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public AutoriPanel(User loggedUser) {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "ID Autor", "Nume Autor", "Prenume Autor", "Tara Origine"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                } else if (columnIndex == 1) {
                    return Integer.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        table = new JTable(tableModel);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setGridColor(Color.GRAY);

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
        deleteBtn.addActionListener(e -> deleteSelectedAutori());
        if (!loggedUser.getRole().equals(Role.ADMIN)) {
            deleteBtn.setEnabled(false);
        }
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Autor> autori = AutoriDAO.findAll();
        for (Autor a : autori) {
            Object[] rowData = {false, a.getAutorID(), a.getNumeAutor(), a.getPrenumeAutor(), a.getTaraOrigine()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddForm() {
        Frame parent = JOptionPane.getFrameForComponent(this);
        AutorFormDialog formDialog = new AutorFormDialog(parent);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            AutoriDAO.insert(formDialog.getAutor());
            refreshTable();
        }
    }

    private void showEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează un autor pentru editare!");
            return;
        }
        int autorID = (int) tableModel.getValueAt(row, 1);
        Autor autor = AutoriDAO.findById(autorID);
        if (autor == null) {
            JOptionPane.showMessageDialog(this, "Autorul selectat nu a fost găsit în DB!");
            return;
        }
        Frame parent = JOptionPane.getFrameForComponent(this);
        AutorFormDialog formDialog = new AutorFormDialog(parent, autor);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            AutoriDAO.update(formDialog.getAutor());
            refreshTable();
        }
    }

    private void deleteSelectedAutori() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean isSelected = (Boolean) tableModel.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                int autorID = (int) tableModel.getValueAt(i, 1);
                AutoriDAO.delete(autorID);
                tableModel.removeRow(i);
            }
        }
    }
}
