<%-- 
    Document   : index
    Created on : Apr 13, 2024, 11:41:41 AM
    Author     : giogen
--%>

<%@page import="com.captcha.botdetect.web.servlet.Captcha"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="login" method="POST">
            <input type="text" id="username" name="username" placeholder="username" />
            <input type="password" id="password" name="password" placeholder="password" />
            <%
                Captcha captcha  = Captcha.load(request, "exampleCaptcha");
                captcha.setUserInputID("captchaCode");
                String captchaHtml = captcha.getHtml();
                out.write(captchaHtml);
            %>
            <input id="captchaCode" type="text" name="captchaCode">
            <button type="submit">Log In</button>
        </form>
    </body>
</html>
