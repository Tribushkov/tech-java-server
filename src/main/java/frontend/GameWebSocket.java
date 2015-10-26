package frontend;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;

/**
 * Created by dmitri on 23.10.15.
 */

@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    @NotNull private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user) {
        try {
            System.out.println("GAME STARTING");
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            System.out.println(jsonStart.toJSONString());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, boolean win) {
        try {

            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            jsonStart.put("win", win);
            session.getRemote().sendString(jsonStart.toJSONString());

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void sendTime(GameUser user, long time) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("time", String.valueOf(time));
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(@NotNull String data) {
        String[] splitted = data.split("_");
        System.out.println("INCOMING MESSAGE");
        System.out.println(data);
        gameMechanics.tapSquare(myName, Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]));
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("___CONNECTION OPENED___");
        setSession(session);
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    public void setMyScore(GameUser user, int row, int column) {
        System.out.println(user.getMyName() + ": " + String.valueOf(user.getMyScore()));
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", "me");
        jsonStart.put("score", user.getMyScore());
        jsonStart.put("square", String.valueOf(row) + '_' + String.valueOf(column));
        jsonStart.put("color", user.getMyColor());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void setEnemyScore(GameUser user, int row, int column) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", "enemy");
        jsonStart.put("score", user.getEnemyScore());
        jsonStart.put("square", String.valueOf(row) + '_' + String.valueOf(column));
        jsonStart.put("color", user.getEnemyColor());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("CONNECTION CLOSED");
    }
}
