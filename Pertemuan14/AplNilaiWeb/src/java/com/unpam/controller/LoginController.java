package com.unpam.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController",
        urlPatterns = {"/LoginController"})
public class LoginController
        extends HttpServlet {

    protected void processRequest(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType(
                "text/html;charset=UTF-8");

        HttpSession session =
                request.getSession();

        String username =
                request.getParameter(
                        "username"
                );

        String password =
                request.getParameter(
                        "password"
                );

        if (username != null
                && password != null) {

            session.setAttribute(
                    "userName",
                    username
            );

            response.sendRedirect(
                    "MahasiswaController"
            );

        } else {

            response.getWriter().println(
                    "<html>"
                    + "<body>"
                    + "<h2>Login</h2>"

                    + "<form method='post'>"

                    + "Username : <br>"
                    + "<input type='text' "
                    + "name='username'>"
                    + "<br><br>"

                    + "Password : <br>"
                    + "<input type='password' "
                    + "name='password'>"
                    + "<br><br>"

                    + "<input type='submit' "
                    + "value='Login'>"

                    + "</form>"

                    + "</body>"
                    + "</html>"
            );
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
}
