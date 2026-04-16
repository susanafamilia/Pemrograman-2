package p2pertemuan3;

import javax.swing.*;
import java.awt.event.*;

public class KalkulatorSederhana extends JFrame {

    JLabel lblAngka1 = new JLabel("Angka 1");
    JLabel lblAngka2 = new JLabel("Angka 2");
    JLabel lblHasil = new JLabel("Hasil");

    JTextField txtAngka1 = new JTextField();
    JTextField txtAngka2 = new JTextField();
    JTextField txtHasil = new JTextField();

    JButton btnTambah = new JButton("Tambah");
    JButton btnHapus = new JButton("Hapus");
    JButton btnExit = new JButton("Exit");

    public KalkulatorSederhana() {
        setTitle("Kalkulator Sederhana");
        setSize(400, 250);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Label
        lblAngka1.setBounds(20, 20, 100, 20);
        lblAngka2.setBounds(20, 60, 100, 20);
        lblHasil.setBounds(20, 100, 100, 20);

        // TextField
        txtAngka1.setBounds(120, 20, 200, 20);
        txtAngka2.setBounds(120, 60, 200, 20);
        txtHasil.setBounds(120, 100, 200, 20);
        txtHasil.setEditable(false);

        // Button
        btnTambah.setBounds(20, 150, 100, 30);
        btnHapus.setBounds(140, 150, 100, 30);
        btnExit.setBounds(260, 150, 80, 30);

        // Tambah ke frame
        add(lblAngka1);
        add(lblAngka2);
        add(lblHasil);

        add(txtAngka1);
        add(txtAngka2);
        add(txtHasil);

        add(btnTambah);
        add(btnHapus);
        add(btnExit);

        // EVENT TAMBAH
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int a = Integer.parseInt(txtAngka1.getText());
                    int b = Integer.parseInt(txtAngka2.getText());
                    int hasil = a + b;

                    txtHasil.setText(String.valueOf(hasil));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Input harus angka!");
                }
            }
        });

        // EVENT HAPUS
        btnHapus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtAngka1.setText("");
                txtAngka2.setText("");
                txtHasil.setText("");
            }
        });

        // EVENT EXIT
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new KalkulatorSederhana().setVisible(true);
    }
}