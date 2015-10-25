package frontend;

import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitri on 23.10.15.
 */
public class WebSocketServiceImpl implements WebSocketService {

    private Map<String, GameWebSocket> userSockets = new HashMap<>();

    @Override
    public void addUser(GameWebSocket user) {
        System.out.println("___ADD USER TO USERSOCKETS____");
        userSockets.put(user.getMyName(), user);
    }

    @Override
    public void notifyMyNewScore(GameUser user, int row, int column) {
        userSockets.get(user.getMyName()).setMyScore(user, row, column);
    }

    @Override
    public void notifyEnemyNewScore(GameUser user, int row, int column) {
        userSockets.get(user.getMyName()).setEnemyScore(user, row, column);
    }

    @Override
    public void notifyStartGame(GameUser user) {
        GameWebSocket gameWebSocket = userSockets.get(user.getMyName());
        System.out.println("USER");
        System.out.println(user.getMyName());
        System.out.println(gameWebSocket.toString());
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
    }

}
