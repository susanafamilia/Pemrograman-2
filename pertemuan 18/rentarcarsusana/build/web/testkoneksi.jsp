<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="config.Koneksi"%>

<%
Connection conn = Koneksi.getConnection();

if(conn != null){
    out.println("KONEKSI BERHASIL");
}else{
    out.println("KONEKSI GAGAL");
}
%>