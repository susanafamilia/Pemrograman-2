package com.unpam.controller;

import com.unpam.model.Nilai;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "NilaiController", urlPatterns = {"/NilaiController"})
public class NilaiController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        MainForm mainForm = new MainForm();
        String konten = "<h2>Form Input Nilai Mahasiswa</h2>";
        String proses = request.getParameter("proses");

        if (proses != null) {
            try {
                Nilai nilai = new Nilai();
                nilai.setNim(request.getParameter("nim"));
                nilai.setKodeMataKuliah(request.getParameter("kode_mk"));
                nilai.setNilaiAbsen(Double.parseDouble(request.getParameter("absen")));
                nilai.setNilaiTugas(Double.parseDouble(request.getParameter("tugas")));
                nilai.setNilaiUts(Double.parseDouble(request.getParameter("uts")));
                nilai.setNilaiUas(Double.parseDouble(request.getParameter("uas")));

                if (nilai.simpan()) {
                    konten += "<br><p style='color:green;'>Nilai berhasil disimpan! Nilai Akhir: " 
                            + nilai.getNilaiAkhir() + " (" + nilai.getGrade() + ")</p>";
                } else {
                    konten += "<br><p style='color:red;'>Gagal menyimpan: " + nilai.getPesan() + "</p>";
                }
            } catch (Exception e) {
                konten += "<br><p style='color:red;'>Input nilai harus berupa angka valid!</p>";
            }
        }

        // Form HTML Input Nilai
        konten += "<form method='post' action='NilaiController'>";
        konten += "NIM Mahasiswa : <br><input type='text' name='nim' required><br><br>";
        konten += "Kode Mata Kuliah : <br><input type='text' name='kode_mk' required><br><br>";
        konten += "Nilai Absen : <br><input type='number' step='0.01' name='absen' required><br><br>";
        konten += "Nilai Tugas : <br><input type='number' step='0.01' name='tugas' required><br><br>";
        konten += "Nilai UTS : <br><input type='number' step='0.01' name='uts' required><br><br>";
        konten += "Nilai UAS : <br><input type='number' step='0.01' name='uas' required><br><br>";
        konten += "<input type='hidden' name='proses' value='1'>";
        konten += "<input type='submit' value='Simpan Nilai'>";
        konten += "</form>";

        // Panggil kerangka MainForm
        mainForm.tampilkan(request, response, konten);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}