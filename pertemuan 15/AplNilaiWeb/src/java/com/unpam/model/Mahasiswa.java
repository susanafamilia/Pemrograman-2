package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;

public class Mahasiswa {

    private String nim, nama, kelas, password;
    private int semester;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

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

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            boolean simpan = false;
            String SQLStatemen = "";
            PreparedStatement preparedStatement = null;
            ResultSet rset = null;

            try {
                simpan = true;
                SQLStatemen = "insert into tbmahasiswa(nim, nama, semester, kelas, password) values (?,?,?,?,?)";

                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, nama);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, kelas);
                preparedStatement.setString(5, password);

                jumlahSimpan = preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                    if (rset != null) rset.close();
                    connection.close();
                } catch (SQLException ex) {
                    // ignore
                }
            }

            if (simpan) {
                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data mahasiswa";
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
