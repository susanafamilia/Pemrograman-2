package Pertemuan5;

import java.sql.*;

public class TampilData {
    public static void main(String[] args) {
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM datamhs");

            while (rs.next()) {
                System.out.println(
                    rs.getString("nim") + " | " +
                    rs.getString("nama")
                );
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}