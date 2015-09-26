package frontend;

import org.eclipse.jetty.server.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dmitri on 26.09.15.
 */
public class StopServerServlet extends HttpServlet {

    private Server server;


    public StopServerServlet(Server server) {
        this.server = server;
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        long shutDownTime = Long.parseLong(request.getParameter("shutdown"));

        if (shutDownTime != 0) {
            assert server != null;
            server.setStopTimeout(shutDownTime);
            try {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            server.stop();
                        } catch (Exception ex) {
                            System.out.println("Failed to stop Jetty");
                        }
                    }
                }.start();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
