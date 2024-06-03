/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.captcha_webapp;

import com.captcha.botdetect.web.servlet.Captcha;
import com.mycompany.captcha_webapp.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycompany.captcha_webapp.utils.DatabaseManager;
import com.mycompany.captcha_webapp.utils.PreparedStatementBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giogen
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private DatabaseManager dbManager;


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // first check if captcha is correct, otherwise redirect to error page
        String captchaCode = request.getParameter("captchaCode");

        Captcha captcha = Captcha.load(request, "exampleCaptcha");
        if (!captcha.validate(captchaCode)) {
            response.sendRedirect("error.jsp");
        }

        // check credentials
        String username = request.getParameter("username");
        String password = request.getParameter("password");

//            DatabaseManager dbManager = new DatabaseManager("GennaCervantes", "Password123.", "mysql", new PreparedStatementBuilder());
        System.out.println(dbManager);
        User user = null;
        try {
            user = dbManager.getUser(username);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (user == null) {
            response.sendRedirect("nouserfound.jsp"); // no user found error
            return;
        }

        if (!user.getPassword().equals(password)) {
            response.sendRedirect("wrongpassword.jsp"); // wrong password error
            return;
        }

        // success
        HttpSession session = request.getSession();
        List<User> allUsers = new ArrayList<>();

        try {
            allUsers = dbManager.getUsers();
        } catch (Exception e) {
            System.out.println(e);
        }

        session.setAttribute("userList", allUsers);
        session.setAttribute("dbManager", dbManager);

        if (user.getUsertype().equals("member")) {
            response.sendRedirect("success.jsp");
            return;
        }
        response.sendRedirect("successadmin.jsp");
        return;

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String dbDriver = config.getInitParameter("DBDriver");
        String dbUsername = config.getInitParameter("DBUsername");
        String dbPassword = config.getInitParameter("DBPassword");

        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException error) {
        }

        try {
            dbManager = new DatabaseManager(dbUsername, dbPassword, "mysql", new PreparedStatementBuilder());
        } catch (Exception e) {
            System.out.println("error with db");
            System.out.println(e);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
