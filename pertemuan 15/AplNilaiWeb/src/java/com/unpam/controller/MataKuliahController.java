package com.unpam.controller;

import com.unpam.model.MataKuliah;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MataKuliahController", urlPatterns = {"/MataKuliahController"})
public class MataKuliahController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        MainForm mainForm = new MainForm();
        String konten = "<h2>Form Form Mata Kuliah</h2>";
        String proses = request.getParameter("proses");

        if (proses != null) {
            MataKuliah mataKuliah = new MataKuliah();
            mataKuliah.setKodeMataKuliah(request.getParameter("kode"));
            mataKuliah.setNamaMataKuliah(request.getParameter("nama"));

            try {
                mataKuliah.setJumlahSks(Integer.parseInt(request.getParameter("sks")));
                if (mataKuliah.simpan()) {
                    konten += "<br><p style='color:green;'>Data mata kuliah berhasil disimpan</p>";
                } else {
                    konten += "<br><p style='color:red;'>Gagal menyimpan: " + mataKuliah.getPesan() + "</p>";
                }
            } catch (NumberFormatException e) {
                konten += "<br><p style='color:red;'>Format SKS harus berupa angka!</p>";
            }
        }

        konten += "<form method='post' action='MataKuliahController'>";
        konten += "Kode Mata Kuliah : <br>";
        konten += "<input type='text' name='kode' required><br><br>";
        
        konten += "Nama Mata Kuliah : <br>";
        konten += "<input type='text' name='nama' required><br><br>";
        
        konten += "Jumlah SKS : <br>";
        konten += "<input type='number' name='sks' required><br><br>";
        
        konten += "<input type='hidden' name='proses' value='1'>";
        konten += "<input type='submit' value='Simpan'>";
        konten += "</form>";
        
        // Urutan parameter: request, response, konten
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