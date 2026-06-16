package view;

import config.Koneksi;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;

    public LoginForm() {
        initComponents();
        initEvent();

        setTitle("Login Sistem Penjualan");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        panelForm.add(new JLabel("Username :"));
        panelForm.add(txtUsername);
        panelForm.add(new JLabel("Password :"));
        panelForm.add(txtPassword);

        btnLogin = new JButton("Login");
        btnCancel = new JButton("Cancel");

        JPanel panelButton = new JPanel();
        panelButton.add(btnLogin);
        panelButton.add(btnCancel);

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
    }

    private void initEvent() {
        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Selamat Datang, " + rs.getString("nama_lengkap"));
                        new DashboardForm().setVisible(true);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Akun tidak ditemukan!", "Gagal Login", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal terhubung ke database!");
            }
        });

        btnCancel.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}