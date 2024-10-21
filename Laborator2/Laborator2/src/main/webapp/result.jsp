<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collections, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shuffled Lines</title>
</head>
<body>
<h2>Shuffled File Lines</h2>
<%
    List<String> fileLines = (List<String>) session.getAttribute("fileLines");
    if (fileLines != null && !fileLines.isEmpty()) {
        Collections.shuffle(fileLines);  // Shuffle the lines
        for (String line : fileLines) {
            out.println("<p>" + line + "</p>");
        }
    } else {
        out.println("<p>No file content available.</p>");
    }
%>
</body>
</html>
