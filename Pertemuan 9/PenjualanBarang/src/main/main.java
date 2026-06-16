package main;

import view.LoginForm;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Memulai program langsung dari Form Login
            new LoginForm().setVisible(true);
        });
    }
}