<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>Data Pengembalian</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>
<body>
<div class="container mt-4">
<h2>Data Pengembalian</h2>

<form action="PengembalianServlet" method="post" class="row g-3 mb-4">
  <div class="col-md-4">
    <label>Penyewaan (Belum Dikembalikan)</label>
    <select name="id_sewa" class="form-control" required>
      <option value="">-- Pilih Penyewaan --</option>
      <%
          List<Map<String, Object>> listSewa = (List<Map<String, Object>>) request.getAttribute("listSewa");
          if (listSewa != null) {
              for (Map<String, Object> s : listSewa) {
      %>
      <option value="<%= s.get("id_sewa") %>">
        #<%= s.get("id_sewa") %> - <%= s.get("nama_customer") %>
        (<%= s.get("merk") %> <%= s.get("tipe") %> - <%= s.get("no_polisi") %>)
      </option>
      <%
              }
          }
      %>
    </select>
  </div>
  <div class="col-md-3">
    <label>Tanggal Kembali</label>
    <input type="date" name="tanggal_kembali" class="form-control" required>
  </div>
  <div class="col-md-3">
    <label>Denda (Rp)</label>
    <input type="number" step="0.01" name="denda" value="0" class="form-control">
  </div>
  <div class="col-md-2 d-flex align-items-end">
    <button type="submit" class="btn btn-primary w-100">Simpan</button>
  </div>
</form>

<table class="table table-bordered table-striped">
  <thead>
    <tr>
      <th>No</th>
      <th>ID Sewa</th>
      <th>Customer</th>
      <th>Mobil</th>
      <th>No Polisi</th>
      <th>Tanggal Kembali</th>
      <th>Denda</th>
    </tr>
  </thead>
  <tbody>
    <%
        List<Map<String, Object>> listPengembalian = (List<Map<String, Object>>) request.getAttribute("listPengembalian");
        int no = 1;
        if (listPengembalian != null) {
            for (Map<String, Object> p : listPengembalian) {
    %>
    <tr>
      <td><%= no++ %></td>
      <td><%= p.get("id_sewa") %></td>
      <td><%= p.get("nama_customer") %></td>
      <td><%= p.get("merk") %> <%= p.get("tipe") %></td>
      <td><%= p.get("no_polisi") %></td>
      <td><%= p.get("tanggal_kembali") %></td>
      <td>Rp <%= p.get("denda") %></td>
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
