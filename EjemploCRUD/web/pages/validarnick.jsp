<%--
    Document   : validarnick
    Created on : 03-dic-2014, 8:52:25
    Author     : admin
--%>

<%@page import="modelo.daos.ProfesorDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Valdiar Nick</title>
    </head>
    <body>

        <%
            if (request.getParameter("nickname") != null) {
                ProfesorDAO pdao = new ProfesorDAO();
                out.print(pdao.validarExistenciaNick(request.getParameter("nickname").trim()));
            }else{
                out.print("ok");
            }
        %>


    </body>
</html>
