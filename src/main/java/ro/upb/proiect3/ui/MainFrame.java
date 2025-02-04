package ro.upb.proiect3.ui;

import ro.upb.proiect3.model.User;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private User loggedUser;

    public MainFrame(User user) {
        super("SwingUIDbApplication");
        this.loggedUser = user;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        setTitle("SwingUIDbApplication - Conectat ca: " + user.getUsername() + " (" + user.getRole() + ")");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Autori", new AutoriPanel());
        tabbedPane.add("Cărți", new CartiPanel());
        tabbedPane.add("Biblioteci", new BiblioteciPanel());
        tabbedPane.add("Contribuții", new ContributiiPanel());
        tabbedPane.add("Stocuri", new StocuriPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
