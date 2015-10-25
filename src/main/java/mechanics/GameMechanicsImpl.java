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

    private static int size = 10;

    private String squares[][] = new String[size][size];

    private static final int STEP_TIME = 100;

    private static final int gameTime = 15 * 1000;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();


    private WebSocketService webSocketService;

    public GameMechanicsImpl(WebSocketService webSocketService) {
        for (int i= 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = "";
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



    private void setStateOfSquare(String userName, int row, int column) {
        GameSession myGameSession = nameToGame.get(userName);

        squares[row][column] = userName.equals(myGameSession.getSelf(userName).getMyName())
                ? myGameSession.getSelf(userName).getMyName()
                : myGameSession.getEnemy(userName).getMyName();

    }

    private String getStateOfSquare(int row, int column) {
        return squares[row][column];
    }

    private int getIncrementScore(String userName, int row, int column) {

        String enemyName = nameToGame.get(userName).getEnemy(userName).getMyName();

        int incrementor = 0;

        if (squares[row][column].equals("")) {
            incrementor = 1;
        } else if (squares[row][column].equals(enemyName)) {
            incrementor = 2;
        }

        return incrementor;
    }

    @Override
    public void tapSquare(String userName, int row, int column) {
        GameSession myGameSession = nameToGame.get(userName);
        GameUser myUser = myGameSession.getSelf(userName);
        GameUser enemyUser = myGameSession.getEnemy(userName);

        myUser.incrementMyScore(getIncrementScore(userName, row, column));
        enemyUser.incrementEnemyScore(getIncrementScore(userName, row, column));

        setStateOfSquare(userName, row, column);

        webSocketService.notifyMyNewScore(myUser, row, column);
        webSocketService.notifyEnemyNewScore(enemyUser, row, column);
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
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));

    }
}
