package servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        List<String> fileLines = new ArrayList<>();

        Part filePart = request.getPart("file");
        if (filePart != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileLines.add(line);
                }
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("fileLines", fileLines);

        String userCaptcha = request.getParameter("captcha");
        String captcha = (String) session.getAttribute("captcha");

        if (userCaptcha != null && userCaptcha.equals(captcha)) {
            response.sendRedirect("result.jsp");
        } else {
            session.setAttribute("errorMessage", "Invalid CAPTCHA. Please try again.");
            response.sendRedirect("input.jsp");
        }
    }
}
