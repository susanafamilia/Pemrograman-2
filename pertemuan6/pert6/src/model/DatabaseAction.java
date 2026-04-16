package model;

import koneksi.Koneksi;
import java.sql.*;

public class DatabaseAction {

    // SEARCH
    public static ResultSet cariData(String nama) {
        try {
            Connection conn = Koneksi.getKoneksi();
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM datanilai WHERE nama LIKE '%" + nama + "%'";
            return st.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    // UPDATE
    public static void updateData(String nim, String n1, String n2, String rata) {
        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "UPDATE datanilai SET nil1=?, nil2=?, rata=? WHERE nim=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, n1);
            ps.setString(2, n2);
            ps.setString(3, rata);
            ps.setString(4, nim);

            ps.executeUpdate();
            System.out.println("Update berhasil");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // DELETE
    public static void hapusData(String nim) {
        try {
            Connection conn = Koneksi.getKoneksi();
            String sql = "DELETE FROM datanilai WHERE nim=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nim);
            ps.executeUpdate();

            System.out.println("Hapus berhasil");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}