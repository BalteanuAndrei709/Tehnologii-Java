<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01-Oct-24
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>String to List</title>
</head>
<body>
<h1>Characters in Ordered List</h1>
<%
    // Get the input string from the request attribute
    String inputString = (String) request.getAttribute("inputString");

    // Check if the input string is not null
    if (inputString != null && !inputString.isEmpty()) {
%>
<ol>
    <%
        // Iterate over each character in the string
        for (int i = 0; i < inputString.length(); i++) {
            char character = inputString.charAt(i);
    %>
    <li><%= character %></li>
    <%
        }
    %>
</ol>
<%
} else {
%>
<p>No input string was provided.</p>
<%
    }
%>
</body>
</html>
