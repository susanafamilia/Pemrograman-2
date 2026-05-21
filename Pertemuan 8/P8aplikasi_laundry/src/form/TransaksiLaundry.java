package form;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.Koneksi;

public class TransaksiLaundry extends javax.swing.JFrame {

    public TransaksiLaundry() {
        initComponents();
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblNama = new javax.swing.JLabel();
        lblAlamat = new javax.swing.JLabel();
        lblNoHp = new javax.swing.JLabel();
        lblBerat = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();

        txtNama = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        txtNoHp = new javax.swing.JTextField();
        txtBerat = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();

        btnHitung = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Transaksi Laundry");

        lblNama.setText("Nama Pelanggan");

        lblAlamat.setText("Alamat");

        lblNoHp.setText("No HP");

        lblBerat.setText("Berat (Kg)");

        lblHarga.setText("Harga per Kg");

        lblTotal.setText("Total");

        btnHitung.setText("HITUNG");

        btnSimpan.setText("SIMPAN");

        btnHitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungActionPerformed(evt);
            }
        });

        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30,30,30)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNama)
                    .addComponent(lblAlamat)
                    .addComponent(lblNoHp)
                    .addComponent(lblBerat)
                    .addComponent(lblHarga)
                    .addComponent(lblTotal))

                .addGap(20,20,20)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,false)
                    .addComponent(txtNama)
                    .addComponent(txtAlamat)
                    .addComponent(txtNoHp)
                    .addComponent(txtBerat)
                    .addComponent(txtHarga)
                    .addComponent(txtTotal)
                    .addComponent(btnHitung,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            200,
                            Short.MAX_VALUE)
                    .addComponent(btnSimpan,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            200,
                            Short.MAX_VALUE))

                .addContainerGap(30, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)

            .addGroup(layout.createSequentialGroup()

                .addGap(30,30,30)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNama)
                    .addComponent(txtNama,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15,15,15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlamat)
                    .addComponent(txtAlamat,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15,15,15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHp)
                    .addComponent(txtNoHp,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15,15,15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBerat)
                    .addComponent(txtBerat,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15,15,15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHarga)
                    .addComponent(txtHarga,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(15,15,15)

                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(txtTotal,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            30,
                            javax.swing.GroupLayout.PREFERRED_SIZE))

                .addGap(20,20,20)

                .addComponent(btnHitung,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        35,
                        javax.swing.GroupLayout.PREFERRED_SIZE)

                .addGap(10,10,10)

                .addComponent(btnSimpan,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        35,
                        javax.swing.GroupLayout.PREFERRED_SIZE)

                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }

    private void btnHitungActionPerformed(java.awt.event.ActionEvent evt) {

        try {

            if(txtBerat.getText().trim().isEmpty()
                    || txtHarga.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Berat dan Harga harus diisi!");

                return;
            }

            double berat =
                    Double.parseDouble(txtBerat.getText());

            double harga =
                    Double.parseDouble(txtHarga.getText());

            double total = berat * harga;

            txtTotal.setText(String.valueOf(total));

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this,
                    "Input harus berupa angka!");

        }
    }

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {

        try {

            Connection conn = Koneksi.getKoneksi();

            String sqlPelanggan =
                    "INSERT INTO pelanggan(nama, alamat, no_hp) VALUES(?,?,?)";

            PreparedStatement psPelanggan =
                    conn.prepareStatement(sqlPelanggan,
                            Statement.RETURN_GENERATED_KEYS);

            psPelanggan.setString(1, txtNama.getText());
            psPelanggan.setString(2, txtAlamat.getText());
            psPelanggan.setString(3, txtNoHp.getText());

            psPelanggan.executeUpdate();

            ResultSet rs = psPelanggan.getGeneratedKeys();

            int idPelanggan = 0;

            if(rs.next()) {
                idPelanggan = rs.getInt(1);
            }

            String sqlTransaksi =
                    "INSERT INTO transaksi(id_pelanggan, berat, total) VALUES(?,?,?)";

            PreparedStatement psTransaksi =
                    conn.prepareStatement(sqlTransaksi);

            psTransaksi.setInt(1, idPelanggan);

            psTransaksi.setDouble(2,
                    Double.parseDouble(txtBerat.getText()));

            psTransaksi.setDouble(3,
                    Double.parseDouble(txtTotal.getText()));

            psTransaksi.executeUpdate();

            JOptionPane.showMessageDialog(this,
                    "Data Berhasil Disimpan");

            kosong();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this,
                    e.getMessage());

        }
    }

    private void kosong() {

        txtNama.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
        txtBerat.setText("");
        txtHarga.setText("");
        txtTotal.setText("");

    }

    private javax.swing.JButton btnHitung;
    private javax.swing.JButton btnSimpan;

    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblAlamat;
    private javax.swing.JLabel lblNoHp;
    private javax.swing.JLabel lblBerat;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblTotal;

    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtNoHp;
    private javax.swing.JTextField txtBerat;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtTotal;

}