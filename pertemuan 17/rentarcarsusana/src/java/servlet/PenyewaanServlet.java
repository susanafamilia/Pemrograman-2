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

@WebServlet(name = "PenyewaanServlet", urlPatterns = {"/PenyewaanServlet"})
public class PenyewaanServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Connection conn = Koneksi.getConnection();

            // Data penyewaan (join customer & mobil)
            String sql = "SELECT p.id_sewa, p.tanggal_sewa, p.lama_sewa, p.total_bayar, "
                    + "c.nama_customer, m.merk, m.tipe, m.no_polisi "
                    + "FROM penyewaan p "
                    + "JOIN customer c ON p.id_customer = c.id_customer "
                    + "JOIN mobil m ON p.id_mobil = m.id_mobil "
                    + "ORDER BY p.id_sewa DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Map<String, Object>> listSewa = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_sewa", rs.getInt("id_sewa"));
                row.put("tanggal_sewa", rs.getDate("tanggal_sewa"));
                row.put("lama_sewa", rs.getInt("lama_sewa"));
                row.put("total_bayar", rs.getDouble("total_bayar"));
                row.put("nama_customer", rs.getString("nama_customer"));
                row.put("merk", rs.getString("merk"));
                row.put("tipe", rs.getString("tipe"));
                row.put("no_polisi", rs.getString("no_polisi"));
                listSewa.add(row);
            }

            // Data customer untuk dropdown
            String sqlCustomer = "SELECT id_customer, nama_customer FROM customer ORDER BY nama_customer";
            PreparedStatement psCust = conn.prepareStatement(sqlCustomer);
            ResultSet rsCust = psCust.executeQuery();
            List<Map<String, Object>> listCustomer = new ArrayList<>();
            while (rsCust.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_customer", rsCust.getInt("id_customer"));
                row.put("nama_customer", rsCust.getString("nama_customer"));
                listCustomer.add(row);
            }

            // Data mobil yang tersedia untuk dropdown
            String sqlMobil = "SELECT id_mobil, no_polisi, merk, tipe, harga_sewa "
                    + "FROM mobil WHERE status_mobil = 'Tersedia' ORDER BY merk";
            PreparedStatement psMobil = conn.prepareStatement(sqlMobil);
            ResultSet rsMobil = psMobil.executeQuery();
            List<Map<String, Object>> listMobil = new ArrayList<>();
            while (rsMobil.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id_mobil", rsMobil.getInt("id_mobil"));
                row.put("no_polisi", rsMobil.getString("no_polisi"));
                row.put("merk", rsMobil.getString("merk"));
                row.put("tipe", rsMobil.getString("tipe"));
                row.put("harga_sewa", rsMobil.getDouble("harga_sewa"));
                listMobil.add(row);
            }

            request.setAttribute("listSewa", listSewa);
            request.setAttribute("listCustomer", listCustomer);
            request.setAttribute("listMobil", listMobil);

            request.getRequestDispatcher("penyewaan.jsp").forward(request, response);
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

            int idCustomer = Integer.parseInt(request.getParameter("id_customer"));
            int idMobil = Integer.parseInt(request.getParameter("id_mobil"));
            String tanggalSewa = request.getParameter("tanggal_sewa");
            int lamaSewa = Integer.parseInt(request.getParameter("lama_sewa"));

            // Ambil harga sewa mobil untuk hitung total bayar otomatis
            String sqlHarga = "SELECT harga_sewa FROM mobil WHERE id_mobil = ?";
            PreparedStatement psHarga = conn.prepareStatement(sqlHarga);
            psHarga.setInt(1, idMobil);
            ResultSet rsHarga = psHarga.executeQuery();
            double hargaSewa = 0;
            if (rsHarga.next()) {
                hargaSewa = rsHarga.getDouble("harga_sewa");
            }
            double totalBayar = hargaSewa * lamaSewa;

            // Insert data penyewaan
            String sql = "INSERT INTO penyewaan "
                    + "(id_customer, id_mobil, tanggal_sewa, lama_sewa, total_bayar) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idCustomer);
            ps.setInt(2, idMobil);
            ps.setDate(3, java.sql.Date.valueOf(tanggalSewa));
            ps.setInt(4, lamaSewa);
            ps.setDouble(5, totalBayar);
            ps.executeUpdate();

            // Update status mobil menjadi "Disewa"
            String sqlUpdate = "UPDATE mobil SET status_mobil = 'Disewa' WHERE id_mobil = ?";
            PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.setInt(1, idMobil);
            psUpdate.executeUpdate();

            response.sendRedirect("PenyewaanServlet");
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