package view;

import javax.swing.*;
import java.awt.*;

public class DashboardForm extends JFrame {

    private JButton btnBarang, btnCustomer, btnSupplier, btnPenjualan, btnLogout;

    public DashboardForm() {
        initComponents();
        initEvent();

        setTitle("Dashboard Utama - Aplikasi Penjualan");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(41, 128, 185));
        JLabel lblTitle = new JLabel("SISTEM INFORMASI MANAJEMEN PENJUALAN (DATABASE TERPADU)");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        panelHeader.add(lblTitle);

        JPanel panelMenu = new JPanel(new GridLayout(2, 2, 20, 20));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        btnBarang = new JButton("Data Barang");
        btnCustomer = new JButton("Data Customer");
        btnSupplier = new JButton("Data Supplier");
        btnPenjualan = new JButton("Transaksi Penjualan (Kasir)");

        Font fontTombol = new Font("Arial", Font.PLAIN, 16);
        btnBarang.setFont(fontTombol);
        btnCustomer.setFont(fontTombol);
        btnSupplier.setFont(fontTombol);
        btnPenjualan.setFont(fontTombol);

        panelMenu.add(btnBarang);
        panelMenu.add(btnCustomer);
        panelMenu.add(btnSupplier);
        panelMenu.add(btnPenjualan);

        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Logout");
        panelFooter.add(btnLogout);

        setLayout(new BorderLayout());
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);
        add(panelFooter, BorderLayout.SOUTH);
    }

    private void initEvent() {
        btnBarang.addActionListener(e -> new BarangForm().setVisible(true));
        btnCustomer.addActionListener(e -> new CustomerForm().setVisible(true));
        btnSupplier.addActionListener(e -> new SupplierForm().setVisible(true));
        btnPenjualan.addActionListener(e -> new PenjualanForm().setVisible(true));
        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Keluar ke halaman log masuk?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                new LoginForm().setVisible(true);
                this.dispose();
            }
        });
    }
}