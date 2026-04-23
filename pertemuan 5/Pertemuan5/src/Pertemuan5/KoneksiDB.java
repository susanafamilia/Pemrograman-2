package Pertemuan5;

import java.sql.Connection;
import java.sql.DriverManager;

public class KoneksiDB {
    public static Connection getKoneksi() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/MHS",
                "root",
                ""
            );
        } catch (Exception e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
        }
        return conn;
    }
}