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
        gameWebSocket.startGame(user);
    }

    @Override
    public void notifyGameOver(GameUser user, boolean win) {
        userSockets.get(user.getMyName()).gameOver(user, win);
        userSockets.remove(user.getMyName());
    }

    @Override
    public void notifyTime(GameUser user, long time) {
        userSockets.get(user.getMyName()).sendTime(user, time);
    }
}
