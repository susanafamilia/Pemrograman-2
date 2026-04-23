package Pertemuan5;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SimpanData {
    public static void main(String[] args) {
        try {
            Connection conn = KoneksiDB.getKoneksi();

            String sql = "INSERT INTO datamhs VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, "12345");
            pst.setString(2, "Budi");
            pst.setInt(3, 3);
            pst.setString(4, "A");

            pst.executeUpdate();

            System.out.println("Data berhasil disimpan!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}