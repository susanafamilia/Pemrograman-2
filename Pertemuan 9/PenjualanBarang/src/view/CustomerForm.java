package view;

import dao.CustomerDAO;
import model.Customer;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {

    private JTextField txtIdCustomer;
    private JTextField txtNama;
    private JTextField txtTelepon;
    private JTextField txtAlamat;

    private JButton btnSimpan;
    private JButton btnUbah;
    private JButton btnHapus;
    private JButton btnReset;

    private JTable tblCustomer;
    private DefaultTableModel model;

    private CustomerDAO dao = new CustomerDAO();

    public CustomerForm() {
        initComponents();
        initEvent();
        loadData();

        setTitle("Data Customer (Pelanggan)");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        txtIdCustomer = new JTextField();
        txtNama = new JTextField();
        txtTelepon = new JTextField();
        txtAlamat = new JTextField();

        panelForm.add(new JLabel("ID Customer"));
        panelForm.add(txtIdCustomer);

        panelForm.add(new JLabel("Nama Lengkap"));
        panelForm.add(txtNama);

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
        model.addColumn("ID Customer");
        model.addColumn("Nama");
        model.addColumn("Telepon");
        model.addColumn("Alamat");

        tblCustomer = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tblCustomer);

        // Penerapan BorderLayout yang benar (Fix Bug Layout)
        setLayout(new java.awt.BorderLayout());
        add(panelForm, java.awt.BorderLayout.NORTH);
        add(scrollPane, java.awt.BorderLayout.CENTER); // Tabel mengambil porsi utama di tengah
        add(panelButton, java.awt.BorderLayout.SOUTH); // Tombol ditaruh di bawah
    }

    private void loadData() {
        model.setRowCount(0);
        List<Customer> list = dao.getAll();

        for (Customer c : list) {
            Object[] row = {
                c.getIdCustomer(),
                c.getNama(),
                c.getTelepon(),
                c.getAlamat()
            };
            model.addRow(row);
        }
    }

    private void resetForm() {
        txtIdCustomer.setText("");
        txtNama.setText("");
        txtTelepon.setText("");
        txtAlamat.setText("");
        
        txtIdCustomer.setEditable(true); // Membuka kembali kunci input ID
        txtIdCustomer.requestFocus();
    }

    private void initEvent() {
        // SIMPAN
        btnSimpan.addActionListener(e -> {
            Customer c = new Customer();
            c.setIdCustomer(txtIdCustomer.getText());
            c.setNama(txtNama.getText());
            c.setTelepon(txtTelepon.getText());
            c.setAlamat(txtAlamat.getText());

            if (dao.simpan(c)) {
                JOptionPane.showMessageDialog(this, "Data customer berhasil disimpan");
                loadData();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data customer gagal disimpan");
            }
        });

        // RESET
        btnReset.addActionListener(e -> {
            resetForm();
        });

        // KLIK TABEL
        tblCustomer.getSelectionModel().addListSelectionListener(e -> {
            int row = tblCustomer.getSelectedRow();
            if (row >= 0) {
                txtIdCustomer.setText(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtTelepon.setText(model.getValueAt(row, 2).toString());
                txtAlamat.setText(model.getValueAt(row, 3).toString());
                
                txtIdCustomer.setEditable(false); // ID Utama dikunci agar tidak keliru saat ubah/hapus
            }
        });

        // UBAH
        btnUbah.addActionListener(e -> {
            Customer c = new Customer();
            c.setIdCustomer(txtIdCustomer.getText());
            c.setNama(txtNama.getText());
            c.setTelepon(txtTelepon.getText());
            c.setAlamat(txtAlamat.getText());

            if (dao.update(c)) {
                JOptionPane.showMessageDialog(this, "Data customer berhasil diubah");
                loadData();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data customer gagal diubah");
            }
        });

        // HAPUS
        btnHapus.addActionListener(e -> {
            String idCustomer = txtIdCustomer.getText();
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data customer ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

            if (konfirmasi == JOptionPane.YES_OPTION) {
                if (dao.hapus(idCustomer)) {
                    JOptionPane.showMessageDialog(this, "Data customer berhasil dihapus");
                    loadData();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Data customer gagal dihapus");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerForm().setVisible(true);
        });
    }

    @Override
    public void setVisible(boolean b) {
    super.setVisible(b); // <-- Mengembalikan fungsi pembuka jendela asli Java
}
}