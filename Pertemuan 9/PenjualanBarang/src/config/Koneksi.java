package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    private static Connection conn;

    public static Connection getConnection() {
        try {

            if (conn == null || conn.isClosed()) {

                String url = "jdbc:mysql://localhost:3306/penjualan_barang";
                String user = "root";
                String password = "";

                conn = DriverManager.getConnection(
                        url,
                        user,
                        password
                );

                System.out.println("Koneksi Berhasil");
            }

        } catch (Exception e) {

            System.out.println("Koneksi Gagal");
            System.out.println(e.getMessage());

        }

        return conn;
    }
}