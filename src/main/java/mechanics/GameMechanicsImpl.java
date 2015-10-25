package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.TimeHelper;


/**
 * Created by dmitri on 23.10.15.
 */
public class GameMechanicsImpl implements GameMechanics {

    private String anticipant;

    private static int size = 5;

    private int squares[][] = new int[size][size];

    private static final int STEP_TIME = 100;

    private static final int gameTime = 15 * 1000;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();


    private WebSocketService webSocketService;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        for (int i= 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = 0;
            }
        }

        this.webSocketService = webSocketService;

    }

    @Override
    public void addUser(String user) {
        System.out.println("_________ADD USER_________");
        if (anticipant != null) {
            System.out.println("_________S_________");
            startGame(user);
            anticipant = null;
        } else {
            anticipant = user;
        }
    }

    @Override
    public void tapSquare(String userName, int row, int column, int stateOfSquare) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        GameUser enemyUser = myGameSession.getEnemy(userName);

        myUser.incrementMyScore(stateOfSquare);
        enemyUser.incrementEnemyScore(stateOfSquare);

        webSocketService.notifyMyNewScore(myUser);
        webSocketService.notifyEnemyNewScore(enemyUser);
    }

    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(STEP_TIME);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), firstWin);
                webSocketService.notifyGameOver(session.getSecond(), !firstWin);
            }
        }
    }

    private void startGame(String first) {

        System.out.println("_________START GAME_________");

        String second = anticipant;
        GameSession gameSession = new GameSession(first, second);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));

    }
}
