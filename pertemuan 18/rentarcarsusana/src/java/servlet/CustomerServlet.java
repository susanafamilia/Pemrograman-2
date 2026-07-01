package servlet;

import config.Koneksi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/CustomerServlet"})
public class CustomerServlet extends HttpServlet {

    // TAMBAHKAN INI: Menangani jika servlet diakses langsung lewat URL browser
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Alihkan langsung ke halaman form customer agar tidak error 405
        response.sendRedirect("customer.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ... isi kode doPost kamu yang lama tetap di sini ...
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "INSERT INTO customer (nik,nama_customer,alamat,telepon) VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("nik"));
            ps.setString(2, request.getParameter("nama"));
            ps.setString(3, request.getParameter("alamat"));
            ps.setString(4, request.getParameter("telepon"));
            ps.executeUpdate();
            response.sendRedirect("customer.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}