package Pertemuan5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class FormMahasiswa extends JFrame {

    JTable table;
    DefaultTableModel model;

    public FormMahasiswa() {
        setTitle("Data Mahasiswa");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header tabel
        String[] kolom = {"NIM", "Nama", "Semester", "Kelas"};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

        tampilData();
    }

    // Method untuk ambil data dari database
    private void tampilData() {
        model.setRowCount(0); // reset tabel

        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM datamhs");

            while (rs.next()) {
                Object[] row = {
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getInt("semester"),
                    rs.getString("kelas")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FormMahasiswa().setVisible(true);
    }
}