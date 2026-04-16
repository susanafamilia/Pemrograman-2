package view;

import model.DatabaseAction;
import java.sql.ResultSet;
import javax.swing.*;

public class FormUtama extends JFrame {

    JTextField txtNim = new JTextField();
    JTextField txtNama = new JTextField();
    JTextField txtNil1 = new JTextField();
    JTextField txtNil2 = new JTextField();
    JTextField txtRata = new JTextField();

    JButton btnCari = new JButton("Cari");
    JButton btnUpdate = new JButton("Update");
    JButton btnHapus = new JButton("Hapus");

    public FormUtama() {
        setTitle("Aplikasi Nilai Mahasiswa");
        setSize(400, 300);
        setLayout(null);

        add(new JLabel("NIM")).setBounds(20, 20, 100, 20);
        add(txtNim).setBounds(120, 20, 200, 20);

        add(new JLabel("Nama")).setBounds(20, 50, 100, 20);
        add(txtNama).setBounds(120, 50, 200, 20);

        add(new JLabel("Nilai 1")).setBounds(20, 80, 100, 20);
        add(txtNil1).setBounds(120, 80, 200, 20);

        add(new JLabel("Nilai 2")).setBounds(20, 110, 100, 20);
        add(txtNil2).setBounds(120, 110, 200, 20);

        add(new JLabel("Rata")).setBounds(20, 140, 100, 20);
        add(txtRata).setBounds(120, 140, 200, 20);

        btnCari.setBounds(20, 180, 90, 30);
        btnUpdate.setBounds(120, 180, 90, 30);
        btnHapus.setBounds(220, 180, 90, 30);

        add(btnCari);
        add(btnUpdate);
        add(btnHapus);

        // EVENT
        btnCari.addActionListener(e -> cari());
        btnUpdate.addActionListener(e -> update());
        btnHapus.addActionListener(e -> hapus());
    }

    private void cari() {
        try {
            ResultSet rs = DatabaseAction.cariData(txtNama.getText());
            if (rs.next()) {
                txtNim.setText(rs.getString("nim"));
                txtNil1.setText(rs.getString("nil1"));
                txtNil2.setText(rs.getString("nil2"));
                txtRata.setText(rs.getString("rata"));
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void update() {
        DatabaseAction.updateData(
                txtNim.getText(),
                txtNil1.getText(),
                txtNil2.getText(),
                txtRata.getText()
        );
        JOptionPane.showMessageDialog(this, "Update berhasil");
    }

    private void hapus() {
        DatabaseAction.hapusData(txtNim.getText());
        JOptionPane.showMessageDialog(this, "Hapus berhasil");
    }
}