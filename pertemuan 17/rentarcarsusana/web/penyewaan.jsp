<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<!DOCTYPE html>
<html>
<head>
<title>Data Penyewaan</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>
<body>
<div class="container mt-4">
<h2>Data Penyewaan</h2>

<form action="PenyewaanServlet" method="post" class="row g-3 mb-4">
  <div class="col-md-3">
    <label>Customer</label>
    <select name="id_customer" class="form-control" required>
      <option value="">-- Pilih Customer --</option>
      <%
          List<Map<String, Object>> listCustomer = (List<Map<String, Object>>) request.getAttribute("listCustomer");
          if (listCustomer != null) {
              for (Map<String, Object> c : listCustomer) {
      %>
      <option value="<%= c.get("id_customer") %>"><%= c.get("nama_customer") %></option>
      <%
              }
          }
      %>
    </select>
  </div>
  <div class="col-md-3">
    <label>Mobil (Tersedia)</label>
    <select name="id_mobil" class="form-control" required>
      <option value="">-- Pilih Mobil --</option>
      <%
          List<Map<String, Object>> listMobil = (List<Map<String, Object>>) request.getAttribute("listMobil");
          if (listMobil != null) {
              for (Map<String, Object> m : listMobil) {
      %>
      <option value="<%= m.get("id_mobil") %>">
        <%= m.get("merk") %> <%= m.get("tipe") %> - <%= m.get("no_polisi") %>
        (Rp <%= m.get("harga_sewa") %>/hari)
      </option>
      <%
              }
          }
      %>
    </select>
  </div>
  <div class="col-md-2">
    <label>Tanggal Sewa</label>
    <input type="date" name="tanggal_sewa" class="form-control" required>
  </div>
  <div class="col-md-2">
    <label>Lama Sewa (hari)</label>
    <input type="number" name="lama_sewa" min="1" class="form-control" required>
  </div>
  <div class="col-md-2 d-flex align-items-end">
    <button type="submit" class="btn btn-primary w-100">Simpan</button>
  </div>
</form>

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
    </tr>
  </thead>
  <tbody>
    <%
        List<Map<String, Object>> listSewa = (List<Map<String, Object>>) request.getAttribute("listSewa");
        int no = 1;
        if (listSewa != null) {
            for (Map<String, Object> s : listSewa) {
    %>
    <tr>
      <td><%= no++ %></td>
      <td><%= s.get("nama_customer") %></td>
      <td><%= s.get("merk") %> <%= s.get("tipe") %></td>
      <td><%= s.get("no_polisi") %></td>
      <td><%= s.get("tanggal_sewa") %></td>
      <td><%= s.get("lama_sewa") %> hari</td>
      <td>Rp <%= s.get("total_bayar") %></td>
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
