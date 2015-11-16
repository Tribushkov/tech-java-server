package frontend;

import base.GameMechanics;
import base.WebSocketService;
import main.AccountService;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

/**
 * Created by dmitri on 23.10.15.
 */
public class GameWebSocketCreator implements WebSocketCreator {
    private AccountService accountService;
    private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocketCreator(AccountService accountService,
                                GameMechanics gameMechanics,
                                WebSocketService webSocketService) {
        this.accountService = accountService;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        String sessionId = req.getHttpServletRequest().getSession().getId();
        String name = "";
        if (accountService.getSession(sessionId) != null) {
            name = accountService.getSession(sessionId).getEmail();
        }
        return new GameWebSocket(name, gameMechanics, webSocketService);
    }
}
