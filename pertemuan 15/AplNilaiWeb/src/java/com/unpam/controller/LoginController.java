package com.unpam.controller;

import com.unpam.view.MainForm;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        
        String konten = "";
        String proses = request.getParameter("proses");

        if (proses == null) {
            // Tampilan Form Login Awal
            konten = "<h2>Login</h2>"
                    + "<form method='post' action='LoginController'>"
                    + "NIM / Username:<br>"
                    + "<input type='text' name='nim' required><br><br>"
                    + "Password:<br>"
                    + "<input type='password' name='password' required><br><br>"
                    + "<input type='hidden' name='proses' value='1'>"
                    + "<input type='submit' value='Login'>"
                    + "</form>";
        } else {
            // Proses Validasi Login ke Database
            String nim = request.getParameter("nim");
            String password = request.getParameter("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Sesuaikan port 3306/3307 dan nama DB Anda di sini jika berbeda
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dbaplikasipenilaianmahasiswa", "root", "");
                
                String sql = "SELECT * FROM tbmahasiswa WHERE nim=? AND password=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, nim);
                pstmt.setString(2, password);
                ResultSet rset = pstmt.executeQuery();

                if (rset.next()) {
                    // Jika Login Sukses
                    session.setAttribute("userName", rset.getString("nama"));
                    konten = "<br><h1>Selamat Datang</h1>"
                            + "<h2>" + rset.getString("nama") + "</h2>";
                } else {
                    // Jika Login Gagal
                    konten = "<p style='color:red;'>NIM atau Password salah!</p>"
                            + "<br><a href='LoginController'>Kembali Login</a>";
                }
                
                rset.close();
                pstmt.close();
                conn.close();
            } catch (Exception ex) {
                konten = "<p style='color:red;'>Error Database: " + ex.getMessage() + "</p>"
                        + "<br><a href='LoginController'>Kembali Login</a>";
            }
        }

        // MEMANGGIL MAINFORM DENGAN URUTAN PARAMETER YANG BENAR: request, response, konten
        MainForm mainForm = new MainForm();
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