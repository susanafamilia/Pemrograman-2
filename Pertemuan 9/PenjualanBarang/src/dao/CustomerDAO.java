package dao;

import config.Koneksi;
import model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    public boolean simpan(Customer c) {
        String sql = "INSERT INTO customer (id_customer, nama_customer, alamat, telepon, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getIdCustomer());
            ps.setString(2, c.getNamaCustomer());
            ps.setString(3, c.getAlamat());
            ps.setString(4, c.getTelepon());
            ps.setString(5, c.getEmail());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        try (Statement st = Koneksi.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setIdCustomer(rs.getString("id_customer"));
                c.setNamaCustomer(rs.getString("nama_customer")); // FIX: Sesuaikan kolom DB
                c.setAlamat(rs.getString("alamat"));
                c.setTelepon(rs.getString("telepon"));
                c.setEmail(rs.getString("email"));
                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(Customer c) {
        String sql = "UPDATE customer SET nama_customer=?, alamat=?, telepon=?, email=? WHERE id_customer=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, c.getNamaCustomer());
            ps.setString(2, c.getAlamat());
            ps.setString(3, c.getTelepon());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getIdCustomer());
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapus(String idCustomer) {
        String sql = "DELETE FROM customer WHERE id_customer=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, idCustomer);
            
            int affected = ps.executeUpdate();
            Koneksi.getConnection().commit();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}