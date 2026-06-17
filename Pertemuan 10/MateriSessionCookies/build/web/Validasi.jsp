<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userId = request.getParameter("userId");
    String password = request.getParameter("password");
    Cookie cookie;

    // Validasi User ID dan Password [cite: 151]
    if ((userId != null) && (userId.equalsIgnoreCase("ADMIN")) 
            && (password != null) && (password.equalsIgnoreCase("ADMIN"))) {

        java.text.SimpleDateFormat waktu = new java.text.SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        java.util.Date waktuLogin = new java.util.Date();

        // Membuat Session 
        session.setAttribute("userLogin", "Administrator");
        session.setAttribute("waktuLogin", waktu.format(waktuLogin));
        session.setMaxInactiveInterval(20);

        // Membuat Cookie Nama 
        cookie = new Cookie("nama", "Administrator");
        cookie.setMaxAge(15);
        response.addCookie(cookie);

        // Membuat Cookie Waktu Login 
        cookie = new Cookie("waktuLogin", waktu.format(waktuLogin));
        cookie.setMaxAge(20);
        response.addCookie(cookie);

    } else {
        // Jika gagal login (Menggunakan underscore agar aman dari error Tomcat) 
        cookie = new Cookie("keterangan", "User_ID_atau_password_salah");
        cookie.setMaxAge(15);
        response.addCookie(cookie);
    }

    // Mengalihkan kembali ke index.jsp setelah data diproses 
    response.sendRedirect("index.jsp");
%>