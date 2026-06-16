package view;

import dao.SupplierDAO;
import model.Supplier;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SupplierForm extends JFrame {

    private JTextField txtIdSupplier;
    private JTextField txtNamaToko;
    private JTextField txtTelepon;
    private JTextField txtAlamat;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnReset;

    private JTable tblSupplier;
    private DefaultTableModel model;

    private SupplierDAO dao = new SupplierDAO();

    public SupplierForm() {
        initComponents();
        initEvent();
        loadData();

        setTitle("Data Supplier (Pemasok)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        txtIdSupplier = new JTextField();
        txtNamaToko = new JTextField();
        txtTelepon = new JTextField();
        txtAlamat = new JTextField();

        panelForm.add(new JLabel("ID Supplier"));
        panelForm.add(txtIdSupplier);

        panelForm.add(new JLabel("Nama Toko / Perusahaan"));
        panelForm.add(txtNamaToko);

        panelForm.add(new JLabel("No. Telepon"));
        panelForm.add(txtTelepon);

        panelForm.add(new JLabel("Alamat"));
        panelForm.add(txtAlamat);

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
        model.addColumn("ID Supplier");
        model.addColumn("Nama Toko");
        model.addColumn("Telepon");
        model.addColumn("Alamat");

        tblSupplier = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblSupplier);

        // Penerapan BorderLayout yang benar (Fix Bug Layout)
        setLayout(new java.awt.BorderLayout());
        add(panelForm, java.awt.BorderLayout.NORTH);
        add(scrollPane, java.awt.BorderLayout.CENTER); // Tabel mengambil porsi utama di tengah
        add(panelButton, java.awt.BorderLayout.SOUTH); // Tombol ditaruh di bawah
    }

    private void loadData() {
        model.setRowCount(0);
        List<Supplier> list = dao.getAll();

        for (Supplier s : list) {
            Object[] row = {
                s.getIdSupplier(),
                s.getNamaToko(),
                s.getTelepon(),
                s.getAlamat()
            };
            model.addRow(row);
        }
    }

    private void resetForm() {
        txtIdSupplier.setText("");
        txtNamaToko.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        
        txtIdSupplier.setEditable(true); // Membuka kembali kunci input ID
        txtIdSupplier.requestFocus();
    }

    private void initEvent() {
        // SIMPAN
        btnSimpan.addActionListener(e -> {
            Supplier s = new Supplier();
            s.setIdSupplier(txtIdSupplier.getText());
            s.setNamaToko(txtNamaToko.getText());
            s.setTelepon(txtTelepon.getText());
            s.setAlamat(txtAlamat.getText());

            if (dao.simpan(s)) {
                JOptionPane.showMessageDialog(this, "Data supplier berhasil disimpan");
                loadData();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data supplier gagal disimpan");
            }
        });

        // RESET
        btnReset.addActionListener(e -> {
            resetForm();
        });

        // KLIK TABEL
        tblSupplier.getSelectionModel().addListSelectionListener(e -> {
            int row = tblSupplier.getSelectedRow();
            if (row >= 0) {
                txtIdSupplier.setText(model.getValueAt(row, 0).toString());
                txtNamaToko.setText(model.getValueAt(row, 1).toString());
                txtTelepon.setText(model.getValueAt(row, 2).toString());
                txtAlamat.setText(model.getValueAt(row, 3).toString());
                
                txtIdSupplier.setEditable(false); // ID Utama dikunci agar tidak salah ubah/hapus
            }
        });

        // UBAH
        btnUbah.addActionListener(e -> {
            Supplier s = new Supplier();
            s.setIdSupplier(txtIdSupplier.getText());
            s.setNamaToko(txtNamaToko.getText());
            s.setTelepon(txtTelepon.getText());
            s.setAlamat(txtAlamat.getText());

            if (dao.update(s)) {
                JOptionPane.showMessageDialog(this, "Data supplier berhasil diubah");
                loadData();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data supplier gagal diubah");
            }
        });

        // HAPUS
        btnHapus.addActionListener(e -> {
            String idSupplier = txtIdSupplier.getText();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data supplier ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                if (dao.hapus(idSupplier)) {
                    JOptionPane.showMessageDialog(this, "Data supplier berhasil dihapus");
                    loadData();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Data supplier gagal dihapus");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SupplierForm().setVisible(true);
        });
    }

     @Override
    public void setVisible(boolean b) {
    super.setVisible(b); // <-- Mengembalikan fungsi pembuka jendela asli Java
}
}