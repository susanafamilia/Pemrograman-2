package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Nilai {
    private String nim;
    private String kodeMataKuliah;
    private double nilaiAbsen;
    private double nilaiTugas;
    private double nilaiUts;
    private double nilaiUas;
    private String pesan;

    // Getter dan Setter
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getKodeMataKuliah() { return kodeMataKuliah; }
    public void setKodeMataKuliah(String kodeMataKuliah) { this.kodeMataKuliah = kodeMataKuliah; }

    public double getNilaiAbsen() { return nilaiAbsen; }
    public void setNilaiAbsen(double nilaiAbsen) { this.nilaiAbsen = nilaiAbsen; }

    public double getNilaiTugas() { return nilaiTugas; }
    public void setNilaiTugas(double nilaiTugas) { this.nilaiTugas = nilaiTugas; }

    public double getNilaiUts() { return nilaiUts; }
    public void setNilaiUts(double nilaiUts) { this.nilaiUts = nilaiUts; }

    public double getNilaiUas() { return nilaiUas; }
    public void setNilaiUas(double nilaiUas) { this.nilaiUas = nilaiUas; }

    public String getPesan() { return pesan; }

    // Logika Hitung Nilai Akhir & Grade
    public double getNilaiAkhir() {
        return (nilaiAbsen * 0.1) + (nilaiTugas * 0.2) + (nilaiUts * 0.3) + (nilaiUas * 0.4);
    }

    public String getGrade() {
        double na = getNilaiAkhir();
        if (na >= 85) return "A";
        else if (na >= 70) return "B";
        else if (na >= 55) return "C";
        else if (na >= 40) return "D";
        else return "E";
    }

    // Method Simpan ke Database
    public boolean simpan() {
        boolean berkas = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa", "root", "");
            
            String sql = "INSERT INTO tbnilai (nim, kode_mk, absen, tugas, uts, uas, nilai_akhir, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nim);
            pstmt.setString(2, kodeMataKuliah);
            pstmt.setDouble(3, nilaiAbsen);
            pstmt.setDouble(4, nilaiTugas);
            pstmt.setDouble(5, nilaiUts);
            pstmt.setDouble(6, nilaiUas);
            pstmt.setDouble(7, getNilaiAkhir());
            pstmt.setString(8, getGrade());

            if (pstmt.executeUpdate() > 0) {
                berkas = true;
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            pesan = e.getMessage();
        }
        return berkas;
    }
}