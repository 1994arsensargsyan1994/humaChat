<%@ page import="java.io.IOException" %><%--
  Created by IntelliJ IDEA.
  User: arsen
  Date: 16.04.20
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%!
    public void printError(HttpServletRequest request,HttpServletResponse response,String  paramName) throws IOException {
        if (request.getAttribute(paramName)!= null){
             response.getWriter().write("<span style=\"color: red\">");
            response.getWriter().write((String) request.getAttribute(paramName));
            response.getWriter().write("</span>");
        }
    }
%>

<%!
    String getParamValue(HttpServletRequest request,String name){
        String value = request.getParameter(name);
        return  value == null ? "" : value;
    }
%>

<form action="/register" method="post">

    <label for="name">Name</label>
    <br>
    <% if (request.getAttribute("errorName") != null){%>
    <span style="color: red">
        <%=request.getAttribute("errorName")%></span>
    <%}%>
    <br>
    <input id="name" name="name" type="text" value="<%= getParamValue(request,"name")%>"/>
  <br>
    <label for="surname">Surname</label>
    <br>
    <% if (request.getAttribute("errorSurname") != null){%>
    <span style="color: red">
        <%=request.getAttribute("errorSurname")%></span>
    <%}%>
    <br>
    <input id="surname" name="surname" type="text" value="<%= getParamValue(request,"surname")%>"/>
    <br>

    <label for="email">Email</label>
    <br>
    <% if (request.getAttribute("errorEmail") != null){%>
    <span style="color: red">
        <%=request.getAttribute("errorEmail")%></span>
    <%}%>
    <br>
    <input id="email" name="email" type="text"value="<%= getParamValue(request,"email")%>"/>
    <br>

    <label for="password">Password</label>
    <br>
    <% if (request.getAttribute("errorPassword") != null){%>
    <span style="color: red">
        <%=request.getAttribute("errorPassword")%></span>
    <%}%>
    <br>
    <input id="password" name="password" type="password"/>
    <br>

    <label for="confirmPassword">confirmPassword</label>
    <br>
    <% if (request.getAttribute("errorConfirmPassword") != null){%>
    <span style="color: red">
        <%=request.getAttribute("errorConfirmPassword")%></span>
    <%}%>
    <br>
    <input id="confirmPassword" name="confirmPassword" type="password"/>
    <br>

    <input type="submit" name="Register">
    <a href="/login">login</a>

</form>
</body>
</html>
