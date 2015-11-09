package frontend;

import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import main.UserProfile;
import mechanics.GMResources;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 03.11.15.
 */
public class GameWebSocketCreatorTest {

    private GameWebSocketCreator gameWebSocketCreator;
    private AccountService accountService;
    @Before
    public void setUp() throws Exception {
        accountService = new AccountService();
        WebSocketService webSocketService = new WebSocketServiceImpl();

        ArrayList<String> colors = new ArrayList<>();
        colors.add("#D32F2F");
        colors.add("#FF4081");
        int gameTime = 100;
        int gameStamp = 100;
        int fieldSize = 6;
        GMResources gameResources = new GMResources(colors, gameTime, gameStamp, fieldSize);
        GameMechanics gameMechanics = new GameMechanicsImpl(webSocketService, gameResources);

        gameWebSocketCreator = new GameWebSocketCreator(accountService, gameMechanics, webSocketService);
    }

    @Test
    public void testCreateWebSocket() throws URISyntaxException {

        MockHttpServletRequest req = new MockHttpServletRequest();
        accountService.addSession(req.getSession(true).getId(),new UserProfile("email", "login", "password"));
        ServletUpgradeRequest servletUpgradeRequest = new ServletUpgradeRequest(req);

        MockHttpServletResponse resp = new MockHttpServletResponse();
        ServletUpgradeResponse servletUpgradeResponse = new ServletUpgradeResponse(resp);

        assertNotNull(gameWebSocketCreator.createWebSocket(
                servletUpgradeRequest, servletUpgradeResponse));
    }
}