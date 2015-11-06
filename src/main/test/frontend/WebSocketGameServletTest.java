package frontend;

import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import mechanics.GMResources;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.api.extensions.ExtensionFactory;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.channels.AcceptPendingException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 03.11.15.
 */
public class WebSocketGameServletTest {

    private WebSocketGameServlet webSocketGameServlet;

    @Before
    public void setUp() throws Exception {
        AccountService accountService = new AccountService();
        WebSocketService webSocketService = new WebSocketServiceImpl();

        ArrayList<String> colors = new ArrayList<>();
        colors.add("#D32F2F");
        colors.add("#FF4081");
        int gameTime = 100;
        int gameStamp = 100;
        int fieldSize = 6;
        GMResources gameResources = new GMResources(colors, gameTime, gameStamp, fieldSize);
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, gameResources);

        webSocketGameServlet = new WebSocketGameServlet(accountService, gameMechanics, webSocketService);
    }

    @Test
    public void testConfigure() throws Exception {
//        webSocketGameServlet.
    }
}