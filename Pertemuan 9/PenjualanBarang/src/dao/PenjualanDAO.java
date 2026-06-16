package dao;

import config.Koneksi;
import model.Penjualan;
import model.DetailPenjualan;
import java.sql.*;

public class PenjualanDAO {

    public boolean simpanTransaksi(Penjualan p) {
        String sqlMaster = "INSERT INTO penjualan (no_faktur, tanggal, id_customer, total) VALUES (?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_penjualan (no_faktur, kode_barang, qty, harga, subtotal) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateStok = "UPDATE barang SET stok = stok - ? WHERE kode_barang = ?";

        Connection conn = null;
        try {
            conn = Koneksi.getConnection();
            conn.setAutoCommit(false); // Mode Transaksi diaktifkan

            // 1. Simpan ke master tabel
            try (PreparedStatement psMaster = conn.prepareStatement(sqlMaster)) {
                psMaster.setString(1, p.getNoFaktur());
                psMaster.setString(2, p.getTanggal());
                psMaster.setString(3, p.getIdCustomer());
                psMaster.setDouble(4, p.getTotal());
                psMaster.executeUpdate();
            }

            // 2. Simpan detail & Kurangi stok barang
            try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                 PreparedStatement psStok = conn.prepareStatement(sqlUpdateStok)) {
                
                for (DetailPenjualan detail : p.getDetailItem()) {
                    psDetail.setString(1, p.getNoFaktur());
                    psDetail.setString(2, detail.getKodeBarang());
                    psDetail.setInt(3, detail.getQty());
                    psDetail.setDouble(4, detail.getHarga());
                    psDetail.setDouble(5, detail.getSubtotal());
                    psDetail.addBatch();

                    psStok.setInt(1, detail.getQty());
                    psStok.setString(2, detail.getKodeBarang());
                    psStok.addBatch();
                }
                
                psDetail.executeBatch();
                psStok.executeBatch();
            }

            conn.commit(); // Eksekusi sukses permanen
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}