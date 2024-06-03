/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.captcha_webapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.captcha_webapp.models.User;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giogen
 */
@WebServlet(name = "GenerateReportServlet", urlPatterns = {"/report"})
public class GenerateReportServlet extends HttpServlet {

    private final int ARBITRARY_SIZE = 1024;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String path = request.getServletContext().getRealPath("/");

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
        String filename = df.format(date) + ".pdf";

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path + filename));
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Chunk names = new Chunk("Genna Cervantes, Mar Vincent De Guzman, Carylle Keona Ilano, Julian Ramirez", font);

            document.add(names);

            // Path path = Paths.get(ClassLoader.getSystemResource("java.jpg").toURI());
            PdfPTable table = new PdfPTable(4);
            
            // header names
            String headerNames[] = {"Username", "Password", "Usertype", "Department"};

            for (int i = 0; i < 4; i++) {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerNames[i]));
                table.addCell(header);
            }

            // data in cells
            HttpSession userSession = request.getSession(false);
            List<User> userList = (List<User>) userSession.getAttribute("userList");
            
            for (int i = 0; i < userList.size() ; i++) {
                table.addCell(userList.get(i).getUsername());
                table.addCell(new String(userList.get(i).getEncryptedPassword(), StandardCharsets.UTF_8));
                table.addCell(userList.get(i).getUsertype());
                table.addCell(userList.get(i).getDepartment());
            }

            document.add(table);
            document.close();

            PdfReader pdfReader = new PdfReader(path + filename);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(String.format("%sencrypted_%s", path, filename)));
            pdfStamper.setEncryption("userpass".getBytes(), "password".getBytes(), 0, PdfWriter.ENCRYPTION_AES_256);
            pdfStamper.close();

            response.setContentType("application/pdf");
            ServletOutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(String.format("%sencrypted_%s", path, filename));

            byte[] buffer = new byte[ARBITRARY_SIZE];
            int numBytesRead;

            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
            out.flush();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
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
