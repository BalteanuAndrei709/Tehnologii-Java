package servlets.compulsory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
//http://localhost:8080/Laborator1/StringServlet?inputString=Hello
@WebServlet("/StringServlet")
public class StringServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String inputString = request.getParameter("inputString");
        request.setAttribute("inputString", inputString);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/stringToList.jsp");
        dispatcher.forward(request, response);
    }
}
