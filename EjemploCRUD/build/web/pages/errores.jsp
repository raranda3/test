<%--
    Document   : errores
    Created on : 03-dic-2014, 10:11:34
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         <link href="css/datatable.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h1>Pagina pra mostrar errores</h1>
        <div id="prueba" style="color: red">
        <%
            if (exception != null) {
                out.println(exception.getMessage());

            }
        %>
        </div>
    </body>
</html>
