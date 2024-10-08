package servlets.homework;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;
//http://localhost:8080/Laborator1/html/homework.html
@WebServlet("/GraphServlet")
public class GraphServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Log information about the requst
        logRequestInfo(request);
        // Get the User-Agent header to determine if the request is from a browser
        String userAgent = request.getHeader("User-Agent");

        // Get the parameters from the request
        int numVertices = Integer.parseInt(request.getParameter("numVertices"));
        int numEdges = Integer.parseInt(request.getParameter("numEdges"));

        // Generate the adjacency matrix
        int[][] adjacencyMatrix = generateRandomGraph(numVertices, numEdges);

        if (userAgent != null && (userAgent.contains("Mozilla") || userAgent.contains("Chrome") || userAgent.contains("Safari"))) {
            // Set the adjacency matrix as a request attribute for JSP display
            request.setAttribute("adjacencyMatrix", adjacencyMatrix);
            request.setAttribute("numVertices", numVertices);

            // Forward the request to the JSP file
            request.getRequestDispatcher("/WEB-INF/displayMatrix.jsp").forward(request, response);
        } else {
            // For desktop applications, set the response content type to plain text
            response.setContentType("text/plain");

            // Build the adjacency matrix as a plain text string
            StringBuilder matrixString = new StringBuilder();
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    matrixString.append(adjacencyMatrix[i][j]).append(" ");
                }
                matrixString.append("\n");
            }

            // Send the adjacency matrix as a plain text response
            response.getWriter().write(matrixString.toString());
        }
    }

    private void logRequestInfo(HttpServletRequest request) {
        // Get HTTP method
        String method = request.getMethod();
        // Get client IP address
        String ipAddress = request.getRemoteAddr();
        // Get user-agent
        String userAgent = request.getHeader("User-Agent");
        // Get accepted languages
        String acceptLang = request.getHeader("Accept-Language");
        // Get request parameters
        StringBuilder params = new StringBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            params.append(paramName).append("=").append(paramValue).append("&");
        }

        // Log the information
        System.out.println("Request Method: " + method);
        System.out.println("Client IP: " + ipAddress);
        System.out.println("User-Agent: " + userAgent);
        System.out.println("Accept-Language: " + acceptLang);
        System.out.println("Parameters: " + (params.length() > 0 ? params.substring(0, params.length() - 1) : "None"));
    }

    private int[][] generateRandomGraph(int numVertices, int numEdges) {
        int[][] matrix = new int[numVertices][numVertices];
        Random random = new Random();
        int edgesAdded = 0;

        while (edgesAdded < numEdges) {
            int src = random.nextInt(numVertices);
            int dest = random.nextInt(numVertices);

            if (src != dest && matrix[src][dest] == 0) {
                matrix[src][dest] = 1; // Directed graph (1 for edge from src to dest)
                edgesAdded++;
            }
        }
        return matrix;
    }
}
