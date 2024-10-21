package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/input.jsp")
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        System.out.println("Request received from: " + remoteAddr + " (" + remoteHost + ")");

        chain.doFilter(request, response);
    }
}
