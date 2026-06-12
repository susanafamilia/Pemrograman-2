<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>Data Mobil</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>
<body>
<div class="container mt-4">
<h2>Data Mobil</h2>

<form action="MobilServlet" method="post" class="row g-3 mb-4">
  <div class="col-md-2">
    <label>No Polisi</label>
    <input type="text" name="no_polisi" class="form-control" required>
  </div>
  <div class="col-md-2">
    <label>Merk</label>
    <input type="text" name="merk" class="form-control" required>
  </div>
  <div class="col-md-2">
    <label>Tipe</label>
    <input type="text" name="tipe" class="form-control">
  </div>
  <div class="col-md-1">
    <label>Tahun</label>
    <input type="number" name="tahun" class="form-control">
  </div>
  <div class="col-md-2">
    <label>Warna</label>
    <input type="text" name="warna" class="form-control">
  </div>
  <div class="col-md-2">
    <label>Harga Sewa</label>
    <input type="number" step="0.01" name="harga_sewa" class="form-control" required>
  </div>
  <div class="col-md-1 d-flex align-items-end">
    <button type="submit" class="btn btn-primary w-100">Simpan</button>
  </div>
</form>

<table class="table table-bordered table-striped">
  <thead>
    <tr>
      <th>No</th>
      <th>No Polisi</th>
      <th>Merk</th>
      <th>Tipe</th>
      <th>Tahun</th>
      <th>Warna</th>
      <th>Harga Sewa</th>
      <th>Status</th>
      <th>Aksi</th>
    </tr>
  </thead>
  <tbody>
    <%
        List<Map<String, Object>> listMobil = (List<Map<String, Object>>) request.getAttribute("listMobil");
        int no = 1;
        if (listMobil != null) {
            for (Map<String, Object> m : listMobil) {
    %>
    <tr>
      <td><%= no++ %></td>
      <td><%= m.get("no_polisi") %></td>
      <td><%= m.get("merk") %></td>
      <td><%= m.get("tipe") %></td>
      <td><%= m.get("tahun") %></td>
      <td><%= m.get("warna") %></td>
      <td><%= m.get("harga_sewa") %></td>
      <td>
        <% if ("Tersedia".equals(m.get("status_mobil"))) { %>
          <span class="badge bg-success">Tersedia</span>
        <% } else { %>
          <span class="badge bg-warning text-dark"><%= m.get("status_mobil") %></span>
        <% } %>
      </td>
      <td>
        <a href="MobilServlet?action=hapus&id=<%= m.get("id_mobil") %>"
           class="btn btn-sm btn-danger"
           onclick="return confirm('Hapus data mobil ini?')">Hapus</a>
      </td>
    </tr>
    <%
            }
        }
    %>
  </tbody>
</table>

<a href="index.jsp" class="btn btn-secondary">Kembali</a>
</div>
</body>
</html>
