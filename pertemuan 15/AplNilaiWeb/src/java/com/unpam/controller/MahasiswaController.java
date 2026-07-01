package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MahasiswaController", urlPatterns = {"/MahasiswaController"})
public class MahasiswaController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        MainForm mainForm = new MainForm();
        String konten = "<h2>Form Input Data Mahasiswa</h2>";
        String proses = request.getParameter("proses");

        if (proses != null) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(request.getParameter("nim"));
            mahasiswa.setNama(request.getParameter("nama"));
            mahasiswa.setPassword(request.getParameter("password"));

            if (mahasiswa.simpan()) {
                konten += "<br><p style='color:green;'>Data mahasiswa berhasil disimpan!</p>";
            } else {
                konten += "<br><p style='color:red;'>Gagal menyimpan: " + mahasiswa.getPesan() + "</p>";
            }
        }

        konten += "<form method='post' action='MahasiswaController'>";
        konten += "NIM : <br>";
        konten += "<input type='text' name='nim' required><br><br>";
        
        konten += "Nama Mahasiswa : <br>";
        konten += "<input type='text' name='nama' required><br><br>";
        
        konten += "Password : <br>";
        konten += "<input type='password' name='password' required><br><br>";
        
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