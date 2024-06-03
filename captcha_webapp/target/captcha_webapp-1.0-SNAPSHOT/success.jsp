<%-- 
    Document   : success
    Created on : Apr 13, 2024, 12:34:27 PM
    Author     : giogen
--%>

<%@page import="com.mycompany.captcha_webapp.utils.DatabaseManager"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.captcha_webapp.models.User" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
        <h2>Users List:</h2>
        <table>
            <tr>
                <th>Username</th>
                <th>Password</th>
                <th>Usertype</th>
                <th>Department</th>
            </tr>
            <%
                // Retrieve the user list from the session attribute
                HttpSession userSession = request.getSession(false);
                if (userSession != null) {
                    DatabaseManager dbManager = (DatabaseManager) userSession.getAttribute("dbManager");
                    List<User> userList = dbManager.getUsers();

                    // Check if the user list is not null
                    System.out.println(userList);
                    if (userList != null && !userList.isEmpty()) {
                        for (User user : userList) {

            %> 
            <tr>
                <td><%= user.getUsername()%></td>
                <td><%= user.getEncryptedPassword() %></td>
                <td><%= user.getUsertype()%></td>
                <td><%= user.getDepartment()%></td>
            </tr>
            <%           }
                    } else {
                        response.sendRedirect("error.jsp");
                        return;
                    }
                } else {
                    response.sendRedirect("error.jsp");
                    return;
                }
            %>
        </table>
        <form action="logout">
            <button type="submit">Logout</button>
        </form>
        <form action="report">
            <button type="submit">Report</button>
        </form>

    </body>
</html>

