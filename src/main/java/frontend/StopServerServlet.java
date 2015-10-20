package frontend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.jetbrains.annotations.NotNull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StopServerServlet  extends HttpServlet {

    @NotNull
    private final Server server;


    public StopServerServlet(@NotNull Server server) {
        this.server = server;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long shutDownTime = Long.parseLong(req.getParameter("shutdown"));

        if (shutDownTime != 0) {
            server.setStopTimeout(shutDownTime);
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
