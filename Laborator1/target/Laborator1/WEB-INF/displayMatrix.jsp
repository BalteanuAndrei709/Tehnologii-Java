<%--
  Created by IntelliJ IDEA.
  User: andre
  Date: 01-Oct-24
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adjacency Matrix</title>
</head>
<body>
<h1>Adjacency Matrix for <%= request.getAttribute("numVertices") %> Vertices</h1>
<table border='1'>
    <%
        int[][] adjacencyMatrix = (int[][]) request.getAttribute("adjacencyMatrix");

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            out.println("<tr>");
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                out.println("<td>" + adjacencyMatrix[i][j] + "</td>");
            }
            out.println("</tr>");
        }
    %>
</table>
<br>
<a href="html/homework.html">Generate another graph</a> <!-- Link back to the form -->
</body>
</html>