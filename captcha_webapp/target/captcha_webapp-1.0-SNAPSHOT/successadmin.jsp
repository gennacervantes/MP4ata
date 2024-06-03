<%-- 
    Document   : successadmin
    Created on : May 30, 2024, 3:59:47 PM
    Author     : giogen
--%>

<%@page import="com.mycompany.captcha_webapp.utils.DatabaseManager"%>
<%@page import="com.mycompany.captcha_webapp.models.User"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
        <h1>Hello Admin</h1>
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
                <td><%= user.getEncryptedPassword()%></td>
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
        <h2>Add User Form</h2>
        <form action="add">
            <input type="text" placeholder="username" name="username" id="username" required/>
            <input type="password" placeholder="password" name="password" id="password" required/>
            <select name="usertype" id="usertype" required>
                <option value="member" >Member</option>
                <option value="admin">Admin</option>
            </select>
            <input type="text" placeholder="department" name="department" id="department" required />
            <button type="submit">Add User</button>
        </form>
        <h2>Delete User Form</h2>
        <form action="delete">
            <input type="text" placeholder="username" name="username" id="username" required/>
            <button type="submit">Delete User</button>
        </form>
        <form action="logout">
            <button type="submit">Logout</button>
        </form>
        <form action="report">
            <button type="submit">Report</button>
        </form>

    </body>
</html>
