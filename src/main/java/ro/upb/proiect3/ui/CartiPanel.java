package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.CartiDAO;
import ro.upb.proiect3.model.Carte;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CartiPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public CartiPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "Carte ID", "Denumire", "An Aparitie", "Editura"};
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
        List<Carte> carti = CartiDAO.findAll();
        for (Carte c : carti) {
            Object[] rowData = {false, c.getCarteID(), c.getDenumire(), c.getAnAparitie(), c.getEditura()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddForm() {
        Frame parent = JOptionPane.getFrameForComponent(this);
        CarteFormDialog formDialog = new CarteFormDialog(parent);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            CartiDAO.insert(formDialog.getCarte());
            refreshTable();
        }
    }

    private void showEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează o carte pentru editare!");
            return;
        }
        int carteID = (int) table.getValueAt(row, 1);
        Carte carte = CartiDAO.findById(carteID);
        if (carte == null) {
            JOptionPane.showMessageDialog(this, "Cartea nu a fost găsită în DB!");
            return;
        }
        Frame parent = JOptionPane.getFrameForComponent(this);
        CarteFormDialog formDialog = new CarteFormDialog(parent, carte);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            CartiDAO.update(formDialog.getCarte());
            refreshTable();
        }
    }

    private void deleteSelected() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                int carteID = (int) tableModel.getValueAt(i, 1);
                CartiDAO.delete(carteID);
                tableModel.removeRow(i);
            }
        }
    }
}
