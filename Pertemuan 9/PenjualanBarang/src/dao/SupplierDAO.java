package dao;

import config.Koneksi;
import model.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public boolean simpan(Supplier s) {
        String sql = "INSERT INTO supplier (id_supplier, nama_supplier, alamat, telepon, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getIdSupplier());
            ps.setString(2, s.getNamaSupplier());
            ps.setString(3, s.getAlamat());
            ps.setString(4, s.getTelepon());
            ps.setString(5, s.getEmail());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Supplier> getAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";
        try (Statement st = Koneksi.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setIdSupplier(rs.getString("id_supplier"));
                s.setNamaSupplier(rs.getString("nama_supplier")); // FIX: Sesuaikan kolom DB
                s.setAlamat(rs.getString("alamat"));
                s.setTelepon(rs.getString("telepon"));
                s.setEmail(rs.getString("email"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Supplier s) {
        String sql = "UPDATE supplier SET nama_supplier=?, alamat=?, telepon=?, email=? WHERE id_supplier=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, s.getNamaSupplier());
            ps.setString(2, s.getAlamat());
            ps.setString(3, s.getTelepon());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getIdSupplier());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapus(String idSupplier) {
        String sql = "DELETE FROM supplier WHERE id_supplier=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, idSupplier);
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}