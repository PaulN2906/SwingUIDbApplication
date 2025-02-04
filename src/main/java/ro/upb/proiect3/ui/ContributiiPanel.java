package ro.upb.proiect3.ui;

import ro.upb.proiect3.dao.AutoriDAO;
import ro.upb.proiect3.dao.CartiDAO;
import ro.upb.proiect3.dao.ContributiiDAO;
import ro.upb.proiect3.model.Autor;
import ro.upb.proiect3.model.Carte;
import ro.upb.proiect3.model.Contributie;
import ro.upb.proiect3.model.User;
import ro.upb.proiect3.model.Role;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ContributiiPanel extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;

    public ContributiiPanel(User loggedUser) {
        setLayout(new BorderLayout());

        String[] columnNames = {"Select", "ID", "Autor", "Carte", "Rol Contribuție"};
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
        List<Contributie> list = ContributiiDAO.findAll();
        for (Contributie c : list) {
            Autor autor = AutoriDAO.findById(c.getAutorID());
            String autorName = autor != null ? autor.getNumeAutor() + " " + autor.getPrenumeAutor() : "N/A";
            Carte carte = CartiDAO.findById(c.getCarteID());
            String carteName = carte != null ? carte.getDenumire() : "N/A";

            Object[] rowData = {false, c.getContributieID(), autorName, carteName, c.getRolContributie()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddForm() {
        Frame parent = JOptionPane.getFrameForComponent(this);
        ContributieFormDialog formDialog = new ContributieFormDialog(parent);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            ContributiiDAO.insert(formDialog.getContributie());
            refreshTable();
        }
    }

    private void showEditForm() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selectează o contribuție pentru editare!");
            return;
        }
        int contribID = (int) tableModel.getValueAt(row, 1);
        Contributie c = ContributiiDAO.findById(contribID);
        Frame parent = JOptionPane.getFrameForComponent(this);
        ContributieFormDialog formDialog = new ContributieFormDialog(parent, c);
        formDialog.setVisible(true);
        if (formDialog.isSucceeded()) {
            ContributiiDAO.update(formDialog.getContributie());
            refreshTable();
        }
    }

    private void deleteSelected() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                int contribID = (int) tableModel.getValueAt(i, 1);
                ContributiiDAO.delete(contribID);
                tableModel.removeRow(i);
            }
        }
    }
}
