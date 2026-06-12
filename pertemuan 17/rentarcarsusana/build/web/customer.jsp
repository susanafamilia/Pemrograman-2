<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>Data Customer</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
rel="stylesheet">

</head>

<body>

<div class="container mt-4">

<h2>Data Customer</h2>

<form action="CustomerServlet" method="post">

<div class="mb-3">
<label>NIK</label>
<input type="text" name="nik" class="form-control">
</div>

<div class="mb-3">
<label>Nama Customer</label>
<input type="text" name="nama" class="form-control">
</div>

<div class="mb-3">
<label>Alamat</label>
<textarea name="alamat" class="form-control"></textarea>
</div>

<div class="mb-3">
<label>Telepon</label>
<input type="text" name="telepon" class="form-control">
</div>

<button type="submit" class="btn btn-primary">
Simpan
</button>

<a href="index.jsp" class="btn btn-secondary">
Kembali
</a>

</form>

</div>

</body>
</html>