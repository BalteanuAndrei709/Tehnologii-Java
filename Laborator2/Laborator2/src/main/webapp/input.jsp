<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
</head>
<body>
<h2>Upload a Text File</h2>
<form action="FileUploadServlet" method="post" enctype="multipart/form-data">
    <input type="file" name="file" accept=".txt" required>
    <br><br>

    <img src="captcha" alt="CAPTCHA Image" />
    <br>
    <input type="text" name="captcha" placeholder="Enter CAPTCHA" required>
    <br><br>

    <input type="submit" value="Upload">
</form>

<%-- Display error message if it exists --%>
<% String errorMessage = (String) session.getAttribute("errorMessage");
    if (errorMessage != null) { %>
<div style="color: red;"><%= errorMessage %></div>
<% session.removeAttribute("errorMessage"); } %>
</body>
</html>
