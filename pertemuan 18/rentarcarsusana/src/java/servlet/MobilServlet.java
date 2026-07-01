package servlet;

import config.Koneksi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MobilServlet", urlPatterns = {"/MobilServlet"})
public class MobilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // Hapus data mobil
        if ("hapus".equals(action)) {
            try {
                Connection conn = Koneksi.getConnection();
                String sql = "DELETE FROM mobil WHERE id_mobil = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(request.getParameter("id")));
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("MobilServlet");
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM mobil ORDER BY id_mobil DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> listMobil = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_mobil", rs.getInt("id_mobil"));
                row.put("no_polisi", rs.getString("no_polisi"));
                row.put("merk", rs.getString("merk"));
                row.put("tipe", rs.getString("tipe"));
                row.put("tahun", rs.getInt("tahun"));
                row.put("warna", rs.getString("warna"));
                row.put("harga_sewa", rs.getDouble("harga_sewa"));
                row.put("status_mobil", rs.getString("status_mobil"));
                listMobil.add(row);
            }

            request.setAttribute("listMobil", listMobil);
            request.getRequestDispatcher("mobil.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new ServletException(e);
            } catch (ServletException ex) {
                throw ex;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "INSERT INTO mobil "
                    + "(no_polisi, merk, tipe, tahun, warna, harga_sewa, status_mobil) "
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, request.getParameter("no_polisi"));
            ps.setString(2, request.getParameter("merk"));
            ps.setString(3, request.getParameter("tipe"));
            ps.setInt(4, Integer.parseInt(request.getParameter("tahun")));
            ps.setString(5, request.getParameter("warna"));
            ps.setDouble(6, Double.parseDouble(request.getParameter("harga_sewa")));
            ps.setString(7, "Tersedia");
            ps.executeUpdate();
            response.sendRedirect("MobilServlet");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                throw new ServletException(e);
            } catch (ServletException ex) {
                throw ex;
            }
        }
    }
}