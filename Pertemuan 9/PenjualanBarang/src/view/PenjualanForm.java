package view;

import dao.PenjualanDAO;
import model.Penjualan;
import model.DetailPenjualan;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class PenjualanForm extends JFrame {

    private JTextField txtNoFaktur, txtTanggal, txtIdCustomer;
    private JTextField txtKodeBarang, txtHarga, txtQty;
    private JLabel lblTotalBayar;

    private JButton btnTambahItem, btnHapusItem, btnSimpanTransaksi;
    private JTable tblKeranjang;
    private DefaultTableModel modelKeranjang;

    private PenjualanDAO dao = new PenjualanDAO();
    private double grandTotal = 0;

    public PenjualanForm() {
        initComponents();
        initEvent();
        resetFormLengkap();

        setTitle("Transaksi Kasir Penjualan");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelNota = new JPanel(new GridLayout(3, 2, 5, 5));
        panelNota.setBorder(BorderFactory.createTitledBorder("Informasi Nota (Master)"));
        txtNoFaktur = new JTextField();
        txtTanggal = new JTextField();
        txtIdCustomer = new JTextField();

        panelNota.add(new JLabel("No. Faktur"));   panelNota.add(txtNoFaktur);
        panelNota.add(new JLabel("Tanggal (YYYY-MM-DD)")); panelNota.add(txtTanggal);
        panelNota.add(new JLabel("ID Customer"));  panelNota.add(txtIdCustomer);

        JPanel panelInputItem = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInputItem.setBorder(BorderFactory.createTitledBorder("Item Barang (Detail)"));
        txtKodeBarang = new JTextField();
        txtHarga = new JTextField();
        txtQty = new JTextField();

        panelInputItem.add(new JLabel("Kode Barang")); panelInputItem.add(txtKodeBarang);
        panelInputItem.add(new JLabel("Harga Jual"));  panelInputItem.add(txtHarga);
        panelInputItem.add(new JLabel("Qty (Jumlah)")); panelInputItem.add(txtQty);

        btnTambahItem = new JButton("Masukkan Keranjang");
        btnHapusItem = new JButton("Hapus Item");
        JPanel panelAksiItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAksiItem.add(btnTambahItem);
        panelAksiItem.add(btnHapusItem);
        panelInputItem.add(new JLabel("")); panelInputItem.add(panelAksiItem);

        JPanel panelAtasGabungan = new JPanel(new GridLayout(1, 2, 10, 10));
        panelAtasGabungan.add(panelNota);
        panelAtasGabungan.add(panelInputItem);

        modelKeranjang = new DefaultTableModel();
        modelKeranjang.addColumn("Kode Barang");
        modelKeranjang.addColumn("Harga");
        modelKeranjang.addColumn("Qty");
        modelKeranjang.addColumn("Subtotal");

        tblKeranjang = new JTable(modelKeranjang);
        JScrollPane scrollPane = new JScrollPane(tblKeranjang);

        JPanel panelBawah = new JPanel(new BorderLayout());
        lblTotalBayar = new JLabel("TOTAL : Rp 0");
        lblTotalBayar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        lblTotalBayar.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalBayar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        btnSimpanTransaksi = new JButton("Simpan Transaksi Ke Database");
        btnSimpanTransaksi.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        JPanel panelAksiSimpan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAksiSimpan.add(btnSimpanTransaksi);

        panelBawah.add(lblTotalBayar, BorderLayout.NORTH);
        panelBawah.add(panelAksiSimpan, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panelAtasGabungan, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER); // FIXED: Tabel diletakkan di tengah agar mengembang dinamis
        add(panelBawah, BorderLayout.SOUTH);
    }

    private void hitungUlangGrandTotal() {
        grandTotal = 0;
        for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
            grandTotal += Double.parseDouble(modelKeranjang.getValueAt(i, 3).toString());
        }
        lblTotalBayar.setText("TOTAL : Rp " + grandTotal);
    }

    private void resetFormLengkap() {
        txtNoFaktur.setText("");
        txtTanggal.setText(java.time.LocalDate.now().toString());
        txtIdCustomer.setText("");
        txtKodeBarang.setText("");
        txtHarga.setText("");
        txtQty.setText("");
        modelKeranjang.setRowCount(0);
        hitungUlangGrandTotal();
        txtNoFaktur.requestFocus();
    }

    private void initEvent() {
        btnTambahItem.addActionListener(e -> {
            try {
                String kode = txtKodeBarang.getText();
                double harga = Double.parseDouble(txtHarga.getText());
                int qty = Integer.parseInt(txtQty.getText());
                double subtotal = harga * qty;

                Object[] row = { kode, harga, qty, subtotal };
                modelKeranjang.addRow(row);
                hitungUlangGrandTotal();

                txtKodeBarang.setText("");
                txtHarga.setText("");
                txtQty.setText("");
                txtKodeBarang.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan item! Cek kembali format angka.");
            }
        });

        btnHapusItem.addActionListener(e -> {
            int selectedRow = tblKeranjang.getSelectedRow();
            if (selectedRow >= 0) {
                modelKeranjang.removeRow(selectedRow);
                hitungUlangGrandTotal();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris item keranjang yang ingin dihapus.");
            }
        });

        btnSimpanTransaksi.addActionListener(e -> {
            if (modelKeranjang.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Keranjang belanja kosong!");
                return;
            }

            Penjualan p = new Penjualan();
            p.setNoFaktur(txtNoFaktur.getText());
            p.setTanggal(txtTanggal.getText());
            p.setIdCustomer(txtIdCustomer.getText());
            p.setTotal(grandTotal);

            for (int i = 0; i < modelKeranjang.getRowCount(); i++) {
                DetailPenjualan dp = new DetailPenjualan();
                dp.setNoFaktur(txtNoFaktur.getText());
                dp.setKodeBarang(modelKeranjang.getValueAt(i, 0).toString());
                dp.setHarga(Double.parseDouble(modelKeranjang.getValueAt(i, 1).toString()));
                dp.setQty(Integer.parseInt(modelKeranjang.getValueAt(i, 2).toString()));
                dp.setSubtotal(Double.parseDouble(modelKeranjang.getValueAt(i, 3).toString()));
                p.tambahDetail(dp);
            }

            if (dao.simpanTransaksi(p)) {
                JOptionPane.showMessageDialog(this, "Transaksi Berhasil Ditambahkan!");
                resetFormLengkap();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memproses transaksi. Cek keselarasan ID Customer / Kode Barang!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}