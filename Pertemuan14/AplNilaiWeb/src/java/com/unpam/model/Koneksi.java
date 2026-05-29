package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static final String driver =
            "com.mysql.cj.jdbc.Driver";

    private static final String database =
            "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa"
            + "?useSSL=false&serverTimezone=UTC";

    private static final String user = "root";
    private static final String password = "";

    private Connection connection;
    private String pesanKesalahan;

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public Connection getConnection() {

        connection = null;
        pesanKesalahan = "";

        try {

            Class.forName(driver);

            connection = DriverManager.getConnection(
                    database,
                    user,
                    password
            );

        } catch (ClassNotFoundException ex) {

            pesanKesalahan =
                    "Driver MySQL tidak ditemukan\n" + ex;

        } catch (SQLException ex) {

            pesanKesalahan =
                    "Koneksi database gagal\n" + ex;
        }

        return connection;
    }
}