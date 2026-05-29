```java
package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Mahasiswa {

    private String nim;
    private String nama;
    private String kelas;
    private String password;

    private int semester;

    private String pesan;

    private Object[][] list;

    private final Koneksi koneksi =
            new Koneksi();

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getPesan() {
        return pesan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    public boolean simpan() {

        boolean adaKesalahan = false;

        Connection connection;

        if ((connection =
                koneksi.getConnection()) != null) {

            String SQLStatemen = "";

            try {

                SQLStatemen =
                        "INSERT INTO tbmahasiswa "
                        + "(nim,nama,semester,kelas,password) "
                        + "VALUES (?,?,?,?,?)";

                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                SQLStatemen
                        );

                preparedStatement.setString(
                        1,
                        nim
                );

                preparedStatement.setString(
                        2,
                        nama
                );

                preparedStatement.setInt(
                        3,
                        semester
                );

                preparedStatement.setString(
                        4,
                        kelas
                );

                preparedStatement.setString(
                        5,
                        password
                );

                int jumlahSimpan =
                        preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {

                    adaKesalahan = true;

                    pesan =
                            "Gagal menyimpan data mahasiswa";
                }

                preparedStatement.close();
                connection.close();

            } catch (SQLException ex) {

                adaKesalahan = true;

                pesan =
                        "Tidak dapat menyimpan data\n"
                        + ex;
            }

        } else {

            adaKesalahan = true;

            pesan =
                    "Tidak dapat koneksi ke database\n"
                    + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
