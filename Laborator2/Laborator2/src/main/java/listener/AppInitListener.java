package listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String prelude = sce.getServletContext().getInitParameter("prelude");
        String coda = sce.getServletContext().getInitParameter("coda");

        sce.getServletContext().setAttribute("prelude", prelude);
        sce.getServletContext().setAttribute("coda", coda);

        System.out.println("Prelude: " + prelude);
        System.out.println("Coda: " + coda);
    }
}
