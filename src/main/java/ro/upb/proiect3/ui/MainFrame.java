package ro.upb.proiect3.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("SwingUIDbApplication");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Folosim un tabbed pane, unde un tab e "Autori"
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Autori", new AutoriPanel());

        // Poți adăuga tab și pentru Carti, Biblioteci, etc.

        add(tabbedPane, BorderLayout.CENTER);
    }
}
