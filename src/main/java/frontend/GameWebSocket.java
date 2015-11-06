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

import java.io.IOException;

/**
 * Created by dmitri on 23.10.15.
 */

@SuppressWarnings("unchecked")
@WebSocket
public class GameWebSocket {
    private String myName;
    private Session session;
    @NotNull private GameMechanics gameMechanics;
    private WebSocketService webSocketService;

    public GameWebSocket(String myName, @NotNull GameMechanics gameMechanics, WebSocketService webSocketService) {
        this.myName = myName;
        this.gameMechanics = gameMechanics;
        this.webSocketService = webSocketService;
    }

    public String getMyName() {
        return myName;
    }

    public void startGame(GameUser user) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "start");
            jsonStart.put("enemyName", user.getEnemyName());
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    public void gameOver(GameUser user, String win) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("status", "finish");
            if (win.equals(user.getMyName())) {
                jsonStart.put("win", 1);
            } else {
                if (win.equals(user.getEnemyName())) {
                    jsonStart.put("win", 2);
                } else {
                    jsonStart.put("win", 0);
                }
            }
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    public void sendTime(long time) {
        try {
            JSONObject jsonStart = new JSONObject();
            jsonStart.put("time", String.valueOf(time));
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.print(e.toString());
        }
    }

    @OnWebSocketMessage
    public void onMessage(@NotNull String data) {

        String regex = "_";

        String[] splitted = data.split(regex);
        gameMechanics.tapSquare(myName, Integer.valueOf(splitted[0]), Integer.valueOf(splitted[1]));
    }

    @SuppressWarnings("ParameterHidesMemberVariable")
    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        webSocketService.addUser(this);
        gameMechanics.addUser(myName);
    }

    public void setMyScore(GameUser user, int row, int column) {
        JSONObject jsonStart = new JSONObject();
        jsonStart.put("status", "increment");
        jsonStart.put("name", "me");
        jsonStart.put("score", user.getMyScore());
        jsonStart.put("square", String.valueOf(row) + '_' + String.valueOf(column));
        jsonStart.put("color", user.getMyColor());
        try {
            session.getRemote().sendString(jsonStart.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
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

    }
}
