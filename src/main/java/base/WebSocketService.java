package base;

import frontend.GameWebSocket;

/**
 * Created by dmitri on 23.10.15.
 */
public interface WebSocketService {

    void addUser(GameWebSocket user);

    void notifyMyNewScore(GameUser user, int row, int column);

    void notifyEnemyNewScore(GameUser user, int row, int column);

    void notifyStartGame(GameUser user);

    void notifyGameOver(GameUser user, boolean win);
}
