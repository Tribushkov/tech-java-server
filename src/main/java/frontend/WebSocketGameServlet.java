package frontend;

import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */

@WebServlet(name = "WebSocketGameServlet", urlPatterns = {"/game"})
public class WebSocketGameServlet extends WebSocketServlet {
    private static final int IDLE_TIME = 60 * 1000;
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public WebSocketGameServlet(AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @Override
    public void configure(WebSocketServletFactory factory) {
        System.out.println("WebSocketGameServlet.configure()");
        factory.getPolicy().setIdleTimeout(IDLE_TIME);
        factory.setCreator(new GameWebSocketCreator(accountService, gameMechanics, webSocketService));
    }
}
