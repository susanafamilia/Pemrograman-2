package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {

    public static Connection getConnection() {

        Connection conn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/rentcar_susana",
                    "root",
                    ""
            );

            System.out.println("Koneksi Berhasil");

        } catch (Exception e) {

            System.out.println("ERROR KONEKSI");
            System.out.println("Pesan Error = " + e.getMessage());

            e.printStackTrace();

        }

        return conn;
    }
}