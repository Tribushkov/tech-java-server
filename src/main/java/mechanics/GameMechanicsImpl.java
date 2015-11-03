package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;

import java.util.*;

import utils.TimeHelper;


/**
 * Created by dmitri on 23.10.15.
 */

public class GameMechanicsImpl implements GameMechanics {

    private String anticipant;

    private int size;

    private int stepTime;

    private long gameTime;

    private ArrayList<String> colors;

    private String[][] squares;

    private Map<String, GameSession> nameToGame = new HashMap<>();

    private Set<GameSession> allSessions = new HashSet<>();

    private WebSocketService webSocketService;


    public GameMechanicsImpl(WebSocketService webSocketService, GMResources gameResources) {
        clearSquares();
        this.size = gameResources.getFieldSize();
        this.stepTime = gameResources.getTimeStamp();
        this.gameTime = gameResources.getGameTime() * 1000;
        this.colors = gameResources.getColors();
        this.webSocketService = webSocketService;
        this.squares = new String[size][size];
    }

    private void clearSquares() {
        for (int i= 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                squares[i][j] = "";
            }
        }
    }

    @Override
    public void addUser(String user) {
        System.out.println(user);
        if (anticipant != null) {
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


    private int getIncrementScore(String userName, int row, int column) {

        String enemyName = nameToGame.get(userName).getEnemy(userName).getMyName();

        int incrementor = 0;

        if (squares[row][column].isEmpty()) {
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

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            gmStep();
            TimeHelper.sleep(stepTime);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > gameTime) {
                webSocketService.notifyGameOver(session.getFirst(),
                        session.whoIsWinner(session.getFirst(), session.getSecond()));
                webSocketService.notifyGameOver(session.getSecond(),
                        session.whoIsWinner(session.getSecond(), session.getFirst()));
                nameToGame.remove(session.getFirst().getMyName());
                nameToGame.remove(session.getSecond().getMyName());
                allSessions.remove(session);
                anticipant = null;
            } else {
                webSocketService.notifyTime(session.getFirst(), session.getSessionTime());
                webSocketService.notifyTime(session.getSecond(), session.getSessionTime());
            }
        }
    }

    private void startGame(String first) {

        clearSquares();

        String second = anticipant;
        GameSession gameSession = new GameSession(first, second);

        Random random = new Random();
        int colorNum = random.nextInt(6);
        gameSession.getFirst().setMyColor(colors.get(colorNum));
        gameSession.getSecond().setEnemyColor(colors.get(colorNum));
        gameSession.getSecond().setMyColor(colors.get(colorNum + 1));
        gameSession.getFirst().setEnemyColor(colors.get(colorNum + 1));

        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);

        webSocketService.notifyStartGame(gameSession.getSelf(first));
        webSocketService.notifyStartGame(gameSession.getSelf(second));


    }
}
