package view;

import dao.BarangDAO;
import model.Barang;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BarangForm extends JFrame {

    private JTextField txtKode;
    private JTextField txtNama;
    private JTextField txtKategori;
    private JTextField txtHargaBeli;
    private JTextField txtHargaJual;
    private JTextField txtStok;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnReset;

    private JTable tblBarang;
    private DefaultTableModel model;

    private BarangDAO dao = new BarangDAO();

    public BarangForm() {

    initComponents();
    initEvent();
    loadData();

    setTitle("Data Barang");
    setSize(900, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
    private void initComponents() {

        JPanel panelForm = new JPanel();

        panelForm.setLayout(
            new java.awt.GridLayout(6, 2, 5, 5)
        );

        txtKode = new JTextField();
        txtNama = new JTextField();
        txtKategori = new JTextField();
        txtHargaBeli = new JTextField();
        txtHargaJual = new JTextField();
        txtStok = new JTextField();

        panelForm.add(new JLabel("Kode Barang"));
        panelForm.add(txtKode);

        panelForm.add(new JLabel("Nama Barang"));
        panelForm.add(txtNama);

        panelForm.add(new JLabel("Kategori"));
        panelForm.add(txtKategori);

        panelForm.add(new JLabel("Harga Beli"));
        panelForm.add(txtHargaBeli);

        panelForm.add(new JLabel("Harga Jual"));
        panelForm.add(txtHargaJual);

        panelForm.add(new JLabel("Stok"));
        panelForm.add(txtStok);

        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        JPanel panelButton = new JPanel();

        panelButton.add(btnSimpan);
        panelButton.add(btnUbah);
        panelButton.add(btnHapus);
        panelButton.add(btnReset);

        model = new DefaultTableModel();

        model.addColumn("Kode");
        model.addColumn("Nama");
        model.addColumn("Kategori");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Stok");

        tblBarang = new JTable(model);

        JScrollPane scrollPane =
            new JScrollPane(tblBarang);

        setLayout(new java.awt.BorderLayout());

        add(panelForm,
            java.awt.BorderLayout.NORTH);

        add(panelButton,
            java.awt.BorderLayout.CENTER);

        add(scrollPane,
            java.awt.BorderLayout.SOUTH);
    }
        private void loadData() {

        model.setRowCount(0);

        List<Barang> list =
                dao.getAll();

        for (Barang b : list) {

            Object[] row = {

                b.getKodeBarang(),
                b.getNamaBarang(),
                b.getKategori(),
                b.getHargaBeli(),
                b.getHargaJual(),
                b.getStok()

            };

            model.addRow(row);
        }
    }

    private void resetForm() {

        txtKode.setText("");
        txtNama.setText("");
        txtKategori.setText("");
        txtHargaBeli.setText("");
        txtHargaJual.setText("");
        txtStok.setText("");

        txtKode.requestFocus();
    }
        private void initEvent() {

    // SIMPAN
    btnSimpan.addActionListener(e -> {

        Barang barang = new Barang();

        barang.setKodeBarang(txtKode.getText());
        barang.setNamaBarang(txtNama.getText());
        barang.setKategori(txtKategori.getText());
        barang.setHargaBeli(
                Double.parseDouble(txtHargaBeli.getText()));
        barang.setHargaJual(
                Double.parseDouble(txtHargaJual.getText()));
        barang.setStok(
                Integer.parseInt(txtStok.getText()));

        if (dao.simpan(barang)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil disimpan");

            loadData();
            resetForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Data gagal disimpan");
        }

    });

    // RESET
    btnReset.addActionListener(e -> {

        resetForm();

    });

    // KLIK TABEL
    tblBarang.getSelectionModel().addListSelectionListener(e -> {

        int row = tblBarang.getSelectedRow();

        if (row >= 0) {

            txtKode.setText(
                    model.getValueAt(row, 0).toString());

            txtNama.setText(
                    model.getValueAt(row, 1).toString());

            txtKategori.setText(
                    model.getValueAt(row, 2).toString());

            txtHargaBeli.setText(
                    model.getValueAt(row, 3).toString());

            txtHargaJual.setText(
                    model.getValueAt(row, 4).toString());

            txtStok.setText(
                    model.getValueAt(row, 5).toString());

        }

    });

    // UBAH
    btnUbah.addActionListener(e -> {

        Barang barang = new Barang();

        barang.setKodeBarang(txtKode.getText());
        barang.setNamaBarang(txtNama.getText());
        barang.setKategori(txtKategori.getText());
        barang.setHargaBeli(
                Double.parseDouble(txtHargaBeli.getText()));
        barang.setHargaJual(
                Double.parseDouble(txtHargaJual.getText()));
        barang.setStok(
                Integer.parseInt(txtStok.getText()));

        if (dao.update(barang)) {
            System.out.println("Kode : " + barang.getKodeBarang());
            System.out.println("Nama : " + barang.getNamaBarang());
            
            JOptionPane.showMessageDialog(
                    this,
                    "Data berhasil diubah");

            loadData();
            resetForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Data gagal diubah");
        }

    });

    // HAPUS
    btnHapus.addActionListener(e -> {

        String kode = txtKode.getText();

        int konfirmasi =
                JOptionPane.showConfirmDialog(
                        this,
                        "Hapus data ini?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {

            if (dao.hapus(kode)) {
                System.out.println("Kode hapus : " + kode);
                JOptionPane.showMessageDialog(
                        this,
                        "Data berhasil dihapus");

                loadData();
                resetForm();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Data gagal dihapus");
            }
        }

    });
    }
        public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new BarangForm()
                    .setVisible(true);

        });
    }
}