package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.BiblioteciDAO;
import ro.upb.proiect3.dao.CartiDAO;
import ro.upb.proiect3.dao.StocuriDAO;
import ro.upb.proiect3.model.Carte;
import ro.upb.proiect3.model.Stoc;
import ro.upb.proiect3.model.Biblioteca;
import ro.upb.proiect3.model.User;
import ro.upb.proiect3.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StocuriPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public StocuriPanel(User loggedUser) {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "StocID", "Carte", "Bibliotecă", "Nr. Exemplare"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                if (columnIndex == 1 || columnIndex == 4) return Integer.class;
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
        deleteBtn.addActionListener(e -> deleteSelected());

        if (!loggedUser.getRole().equals(Role.ADMIN)) {
            deleteBtn.setEnabled(false);
        }

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Stoc> list = StocuriDAO.findAll();
        for (Stoc s : list) {
            Carte carte = CartiDAO.findById(s.getCarteID());
            String carteName = carte != null ? carte.getDenumire() : "N/A";
            Biblioteca bib = BiblioteciDAO.findById(s.getBibliotecaID());
            String bibName = bib != null ? bib.getDenumire() : "N/A";

            Object[] rowData = {false, s.getStocID(), carteName, bibName, s.getNrExemplare()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddForm() {
        Frame parent = JOptionPane.getFrameForComponent(this);
        StocFormDialog formDialog = new StocFormDialog(parent);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            StocuriDAO.insert(formDialog.getStoc());
            refreshTable();
        }
    }

    private void showEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează un stoc pentru editare!");
            return;
        }
        int stocID = (int) tableModel.getValueAt(row, 1);
        Stoc s = StocuriDAO.findById(stocID);
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Stocul nu a fost găsit în DB!");
            return;
        }
        Frame parent = JOptionPane.getFrameForComponent(this);
        StocFormDialog formDialog = new StocFormDialog(parent, s);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            StocuriDAO.update(formDialog.getStoc());
            refreshTable();
        }
    }

    private void deleteSelected() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                int stocID = (int) tableModel.getValueAt(i, 1);
                StocuriDAO.delete(stocID);
                tableModel.removeRow(i);
            }
        }
    }
}
