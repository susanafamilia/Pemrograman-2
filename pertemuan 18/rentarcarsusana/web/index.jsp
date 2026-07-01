<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Rent Car Susana</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="index.jsp">Rent Car Susana</a>
    <div class="navbar-collapse">
      <ul class="navbar-nav">
        <li class="nav-item"><a class="nav-link" href="CustomerServlet">Data Customer</a></li>
        <li class="nav-item"><a class="nav-link" href="MobilServlet">Data Mobil</a></li>
        <li class="nav-item"><a class="nav-link" href="PenyewaanServlet">Penyewaan</a></li>
        <li class="nav-item"><a class="nav-link" href="PengembalianServlet">Pengembalian</a></li>
        <li class="nav-item"><a class="nav-link" href="laporan.jsp">Laporan</a></li>
      </ul>
    </div>
  </div>
</nav>

<div class="container mt-5">
  <div class="p-5 bg-light rounded-3 text-center">
    <h1>Selamat Datang di Sistem Rental Mobil Susana</h1>
    <p class="lead">Kelola data customer, mobil, penyewaan, dan pengembalian dengan mudah.</p>
  </div>

  <div class="row mt-4 g-3">
    <div class="col-md-3">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Data Customer</h5>
          <a href="CustomerServlet" class="btn btn-primary">Kelola</a>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Data Mobil</h5>
          <a href="MobilServlet" class="btn btn-primary">Kelola</a>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Penyewaan</h5>
          <a href="PenyewaanServlet" class="btn btn-primary">Kelola</a>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card text-center">
        <div class="card-body">
          <h5 class="card-title">Pengembalian</h5>
          <a href="PengembalianServlet" class="btn btn-primary">Kelola</a>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
