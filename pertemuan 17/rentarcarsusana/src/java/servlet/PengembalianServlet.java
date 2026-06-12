package servlet;

import config.Koneksi;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PengembalianServlet", urlPatterns = {"/PengembalianServlet"})
public class PengembalianServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = Koneksi.getConnection();

            // Ambil data pengembalian beserta info penyewaan, customer, dan mobil
            String sql = "SELECT pk.id_kembali, pk.id_sewa, pk.tanggal_kembali, pk.denda, "
                    + "c.nama_customer, m.merk, m.tipe, m.no_polisi "
                    + "FROM pengembalian pk "
                    + "JOIN penyewaan p ON pk.id_sewa = p.id_sewa "
                    + "JOIN customer c ON p.id_customer = c.id_customer "
                    + "JOIN mobil m ON p.id_mobil = m.id_mobil "
                    + "ORDER BY pk.id_kembali DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            request.setAttribute("listPengembalian", rs);

            // Ambil data penyewaan yang masih aktif (belum dikembalikan) untuk dropdown
            String sqlSewa = "SELECT p.id_sewa, c.nama_customer, m.merk, m.tipe, m.no_polisi "
                    + "FROM penyewaan p "
                    + "JOIN customer c ON p.id_customer = c.id_customer "
                    + "JOIN mobil m ON p.id_mobil = m.id_mobil "
                    + "WHERE p.id_sewa NOT IN (SELECT id_sewa FROM pengembalian) "
                    + "ORDER BY p.id_sewa DESC";
            PreparedStatement psSewa = conn.prepareStatement(sqlSewa);
            ResultSet rsSewa = psSewa.executeQuery();

            // Tampung hasil ke list sederhana sebelum forward (karena ResultSet bisa
            // tertutup saat connection di-reuse)
            java.util.List<java.util.Map<String, Object>> listSewa = new java.util.ArrayList<>();
            while (rsSewa.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("id_sewa", rsSewa.getInt("id_sewa"));
                row.put("nama_customer", rsSewa.getString("nama_customer"));
                row.put("merk", rsSewa.getString("merk"));
                row.put("tipe", rsSewa.getString("tipe"));
                row.put("no_polisi", rsSewa.getString("no_polisi"));
                listSewa.add(row);
            }

            java.util.List<java.util.Map<String, Object>> listKembali = new java.util.ArrayList<>();
            while (rs.next()) {
                java.util.Map<String, Object> row = new java.util.HashMap<>();
                row.put("id_kembali", rs.getInt("id_kembali"));
                row.put("id_sewa", rs.getInt("id_sewa"));
                row.put("tanggal_kembali", rs.getDate("tanggal_kembali"));
                row.put("denda", rs.getDouble("denda"));
                row.put("nama_customer", rs.getString("nama_customer"));
                row.put("merk", rs.getString("merk"));
                row.put("tipe", rs.getString("tipe"));
                row.put("no_polisi", rs.getString("no_polisi"));
                listKembali.add(row);
            }

            request.setAttribute("listPengembalian", listKembali);
            request.setAttribute("listSewa", listSewa);

            request.getRequestDispatcher("pengembalian.jsp").forward(request, response);
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

            int idSewa = Integer.parseInt(request.getParameter("id_sewa"));
            String tanggalKembali = request.getParameter("tanggal_kembali");
            double denda = 0;
            String dendaParam = request.getParameter("denda");
            if (dendaParam != null && !dendaParam.trim().isEmpty()) {
                denda = Double.parseDouble(dendaParam);
            }

            // Insert data pengembalian
            String sql = "INSERT INTO pengembalian (id_sewa, tanggal_kembali, denda) "
                    + "VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idSewa);
            ps.setDate(2, java.sql.Date.valueOf(tanggalKembali));
            ps.setDouble(3, denda);
            ps.executeUpdate();

            // Update status mobil menjadi "Tersedia" lagi setelah dikembalikan
            String sqlMobil = "UPDATE mobil m "
                    + "JOIN penyewaan p ON m.id_mobil = p.id_mobil "
                    + "SET m.status_mobil = 'Tersedia' "
                    + "WHERE p.id_sewa = ?";
            PreparedStatement psMobil = conn.prepareStatement(sqlMobil);
            psMobil.setInt(1, idSewa);
            psMobil.executeUpdate();

            response.sendRedirect("PengembalianServlet");
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