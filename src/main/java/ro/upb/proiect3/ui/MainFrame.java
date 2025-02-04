package ro.upb.proiect3.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("SwingUIDbApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Autori", new AutoriPanel());
        tabbedPane.add("Cărți", new CartiPanel());
        tabbedPane.add("Biblioteci", new BiblioteciPanel());
        tabbedPane.add("Contribuții", new ContributiiPanel());
        tabbedPane.add("Stocuri", new StocuriPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
