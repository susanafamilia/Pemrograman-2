package dao;

import config.Koneksi;
import model.Barang;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    
    public boolean simpan(Barang barang) {
        String sql = "INSERT INTO barang VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, barang.getKodeBarang());
            ps.setString(2, barang.getNamaBarang());
            ps.setString(3, barang.getKategori());
            ps.setDouble(4, barang.getHargaBeli());
            ps.setDouble(5, barang.getHargaJual());
            ps.setInt(6, barang.getStok());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit(); // <-- Memastikan data dikunci ke database
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Barang> getAll() {
        List<Barang> list = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Statement st = Koneksi.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Barang b = new Barang();
                b.setKodeBarang(rs.getString("kode_barang"));
                b.setNamaBarang(rs.getString("nama_barang"));
                b.setKategori(rs.getString("kategori"));
                b.setHargaBeli(rs.getDouble("harga_beli"));
                b.setHargaJual(rs.getDouble("harga_jual"));
                b.setStok(rs.getInt("stok"));
                list.add(b);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public boolean hapus(String kodeBarang) {
        String sql = "DELETE FROM barang WHERE kode_barang=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, kodeBarang);
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit(); // <-- Memastikan perubahan dikunci
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Barang barang) {
        String sql = "UPDATE barang SET "
                + "nama_barang=?,"
                + "kategori=?,"
                + "harga_beli=?,"
                + "harga_jual=?,"
                + "stok=? "
                + "WHERE kode_barang=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, barang.getNamaBarang());
            ps.setString(2, barang.getKategori());
            ps.setDouble(3, barang.getHargaBeli());
            ps.setDouble(4, barang.getHargaJual());
            ps.setInt(5, barang.getStok());
            ps.setString(6, barang.getKodeBarang());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit(); // <-- Memastikan perubahan dikunci
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}