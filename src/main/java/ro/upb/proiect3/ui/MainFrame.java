package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(User user) {
        super("SwingUIDbApplication");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setTitle("SwingUIDbApplication - Conectat ca: " + user.getUsername() + " (" + user.getRole() + ")");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Autori", new AutoriPanel(user));
        tabbedPane.add("Cărți", new CartiPanel(user));
        tabbedPane.add("Biblioteci", new BiblioteciPanel(user));
        tabbedPane.add("Contribuții", new ContributiiPanel(user));
        tabbedPane.add("Stocuri", new StocuriPanel(user));

        add(tabbedPane, BorderLayout.CENTER);
    }
}
