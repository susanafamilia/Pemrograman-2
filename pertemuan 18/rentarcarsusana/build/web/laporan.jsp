<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="config.Koneksi"%>
<!DOCTYPE html>
<html>
<head>
<title>Laporan</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>
<body>
<div class="container mt-4">
<h2>Laporan Penyewaan &amp; Pengembalian</h2>

<table class="table table-bordered table-striped">
  <thead>
    <tr>
      <th>No</th>
      <th>Customer</th>
      <th>Mobil</th>
      <th>No Polisi</th>
      <th>Tanggal Sewa</th>
      <th>Lama Sewa</th>
      <th>Total Bayar</th>
      <th>Tanggal Kembali</th>
      <th>Denda</th>
      <th>Status</th>
    </tr>
  </thead>
  <tbody>
    <%
        int no = 1;
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT p.id_sewa, c.nama_customer, m.merk, m.tipe, m.no_polisi, "
                    + "p.tanggal_sewa, p.lama_sewa, p.total_bayar, "
                    + "pk.tanggal_kembali, pk.denda "
                    + "FROM penyewaan p "
                    + "JOIN customer c ON p.id_customer = c.id_customer "
                    + "JOIN mobil m ON p.id_mobil = m.id_mobil "
                    + "LEFT JOIN pengembalian pk ON p.id_sewa = pk.id_sewa "
                    + "ORDER BY p.id_sewa DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                java.sql.Date tglKembali = rs.getDate("tanggal_kembali");
                String status = (tglKembali == null) ? "Disewa" : "Selesai";
    %>
    <tr>
      <td><%= no++ %></td>
      <td><%= rs.getString("nama_customer") %></td>
      <td><%= rs.getString("merk") %> <%= rs.getString("tipe") %></td>
      <td><%= rs.getString("no_polisi") %></td>
      <td><%= rs.getDate("tanggal_sewa") %></td>
      <td><%= rs.getInt("lama_sewa") %> hari</td>
      <td>Rp <%= rs.getDouble("total_bayar") %></td>
      <td><%= (tglKembali != null) ? tglKembali.toString() : "-" %></td>
      <td>Rp <%= rs.getDouble("denda") %></td>
      <td>
        <% if ("Disewa".equals(status)) { %>
          <span class="badge bg-warning text-dark">Disewa</span>
        <% } else { %>
          <span class="badge bg-success">Selesai</span>
        <% } %>
      </td>
    </tr>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
    %>
    <tr><td colspan="10">Gagal memuat data: <%= e.getMessage() %></td></tr>
    <%
        }
    %>
  </tbody>
</table>

<a href="index.jsp" class="btn btn-secondary">Kembali</a>
</div>
</body>
</html>
