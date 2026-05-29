package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.view.MainForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "MahasiswaController",
        urlPatterns = {"/MahasiswaController"}
)
public class MahasiswaController
        extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        MainForm mainForm =
                new MainForm();

        String konten =
                "<h2>Form Mahasiswa</h2>";

        String proses =
                request.getParameter(
                        "proses"
                );

        if (proses != null) {

            Mahasiswa mahasiswa =
                    new Mahasiswa();

            mahasiswa.setNim(
                    request.getParameter(
                            "nim"
                    )
            );

            mahasiswa.setNama(
                    request.getParameter(
                            "nama"
                    )
            );

            mahasiswa.setSemester(
                    Integer.parseInt(
                            request.getParameter(
                                    "semester"
                            )
                    )
            );

            mahasiswa.setKelas(
                    request.getParameter(
                            "kelas"
                    )
            );

            mahasiswa.setPassword(
                    request.getParameter(
                            "password"
                    )
            );

            if (mahasiswa.simpan()) {

                konten +=
                        "<br>Data mahasiswa "
                        + "berhasil disimpan";

            } else {

                konten +=
                        "<br>"
                        + mahasiswa.getPesan();
            }
        }

        konten +=
                "<form method='post' "
                + "action='MahasiswaController'>";

        konten +=
                "NIM : <br>";

        konten +=
                "<input type='text' "
                + "name='nim'>"
                + "<br><br>";

        konten +=
                "Nama : <br>";

        konten +=
                "<input type='text' "
                + "name='nama'>"
                + "<br><br>";

        konten +=
                "Semester : <br>";

        konten +=
                "<input type='number' "
                + "name='semester'>"
                + "<br><br>";

        konten +=
                "Kelas : <br>";

        konten +=
                "<input type='text' "
                + "name='kelas'>"
                + "<br><br>";

        konten +=
                "Password : <br>";

        konten +=
                "<input type='password' "
                + "name='password'>"
                + "<br><br>";

        konten +=
                "<input type='hidden' "
                + "name='proses' "
                + "value='1'>";

        konten +=
                "<input type='submit' "
                + "value='Simpan'>";

        konten +=
                "</form>";

        mainForm.tampilkan(
                konten,
                request,
                response
        );
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(
                request,
                response
        );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(
                request,
                response
        );
    }
}