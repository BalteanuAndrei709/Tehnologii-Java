package filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class DecoratorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        CharResponseWrapper responseWrapper = new CharResponseWrapper(httpResponse);

        chain.doFilter(request, responseWrapper);

        ServletContext context = httpRequest.getServletContext();
        String prelude = (String) context.getAttribute("prelude");
        String coda = (String) context.getAttribute("coda");

        PrintWriter out = httpResponse.getWriter();
        out.write(prelude);
        out.write(responseWrapper.getContent());
        out.write(coda);
        out.flush();
    }

    private static class CharResponseWrapper extends HttpServletResponseWrapper {
        private StringWriter writer = new StringWriter();

        public CharResponseWrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() {
            return new PrintWriter(writer);
        }

        public String getContent() {
            return writer.toString();
        }
    }
}
