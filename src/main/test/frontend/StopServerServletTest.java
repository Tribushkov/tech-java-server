package frontend;

import org.eclipse.jetty.server.Server;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 01.11.15.
 */
public class StopServerServletTest {

    MockHttpServletRequest req = new MockHttpServletRequest();
    MockHttpServletResponse resp = new MockHttpServletResponse();

    @Test
    public void testDoGet() throws ServletException, IOException {
        Server server = new Server();

        req.setParameter("shutdown", "100");
        StopServerServlet stopServerServlet = new StopServerServlet(server);
        stopServerServlet.doGet(req, resp);
        assertFalse(server.isRunning());
    }

}