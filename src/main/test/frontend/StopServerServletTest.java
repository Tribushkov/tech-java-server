package frontend;

import org.eclipse.jetty.server.Server;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 01.11.15.
 */
public class StopServerServletTest {

    public static final long time = 100L;
    @NotNull
    private Server server;


    @Test
    public void testDoGet() throws ServletException, IOException {
        long shutDownTime = time;
        server = new Server();
        if (shutDownTime != 0) {
            server.setStopTimeout(shutDownTime);
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assertFalse(server.isRunning());
    }

}