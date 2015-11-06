package frontend;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import mechanics.GMResources;
import mechanics.GameMechanicsImpl;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import utils.ResourceFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * Created by dmitri on 06.11.15.
 */
public class GameWebSocketTest {

    GameWebSocket gameWebSocket;
    GameWebSocket gameWebSocket2;
    WebSocketService webSocketService;
    GMResources gameResources;
    GameMechanics gameMechanics;

    String myName = "myName";

    private FakeRemoteEndPoint fakeRemoteEndPoint1 = new FakeRemoteEndPoint();
    private FakeRemoteEndPoint fakeRemoteEndPoint2 = new FakeRemoteEndPoint();

    @Before
    public void setUp() throws Exception {

        webSocketService = new WebSocketServiceImpl();
        gameResources = (GMResources) ResourceFactory.getInstance().getResourceObject("data/game_data.xml");
        gameResources.getColors().remove(0);
        gameResources.getColors().remove(0);
        gameMechanics = new GameMechanicsImpl(webSocketService, gameResources);

        gameWebSocket = new GameWebSocket(myName, gameMechanics, webSocketService);
        Session testSession1 = mock(Session.class);
        Session testSession2 = mock(Session.class);
        when(testSession1.getRemote()).thenReturn(fakeRemoteEndPoint1);
        when(testSession2.getRemote()).thenReturn(fakeRemoteEndPoint2);
        gameWebSocket.onConnect(testSession1);

        gameWebSocket2 = new GameWebSocket("enemyName", gameMechanics, webSocketService);
        gameWebSocket2.onConnect(testSession2);
    }

    @Test
    public void testGetMyName() throws Exception {
        assertEquals(this.myName, gameWebSocket.getMyName());
    }

    @Test
    public void testStartGame() throws Exception {
        String testJson1 = "{\"enemyName\":\"enemyName\",\"status\":\"start\"}";
        assertEquals(testJson1, fakeRemoteEndPoint1.getData());
        String testJson2 = "{\"enemyName\":\"myName\",\"status\":\"start\"}";
        assertEquals(testJson2, fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testGameOver() throws Exception {
        GameUser user1 = new GameUser("myName");
        GameUser user2 = new GameUser("enemyName");

        webSocketService.notifyGameOver(user1, "enemyName");
        webSocketService.notifyGameOver(user2, "enemyName");

        assertEquals("{\"win\":0,\"status\":\"finish\"}", fakeRemoteEndPoint1.getData());
        assertEquals("{\"win\":1,\"status\":\"finish\"}", fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testSendTime() throws Exception {
        GameUser user1 = new GameUser("myName");
        GameUser user2 = new GameUser("enemyName");

        webSocketService.notifyTime(user1, 1000);
        webSocketService.notifyTime(user2, 1000);

        assertEquals("{\"time\":\"1000\"}", fakeRemoteEndPoint1.getData());
        assertEquals("{\"time\":\"1000\"}", fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testOnMessage() throws Exception {
        String data = "0_0";

        gameWebSocket.onMessage(data);
        assertEquals("myName", ((GameMechanicsImpl) gameMechanics).getSquares()[0][0]);

        gameWebSocket2.onMessage(data);
        assertEquals("enemyName", ((GameMechanicsImpl) gameMechanics).getSquares()[0][0]);

    }

    @Test
    public void testOnConnect() throws Exception {
        Session testSession1 = mock(Session.class);
        gameWebSocket.onConnect(testSession1);

        Session testSession2 = mock(Session.class);
        gameWebSocket.onConnect(testSession2);
    }

    @Test
    public void testSetMyScore() throws Exception {
        GameUser user1 = new GameUser("myName");
        GameUser user2 = new GameUser("enemyName");

        webSocketService.notifyMyNewScore(user1, 0, 0);
        webSocketService.notifyMyNewScore(user2, 0, 1);

        assertEquals("{\"score\":" + String.valueOf(user1.getMyScore()) + ',' +
                "\"square\":\"" + String.valueOf(0) + '_' + String.valueOf(0) + "\"," +
                "\"color\":" + String.valueOf(user1.getMyColor()) + ',' +
                "\"name\":\"me\"," +
                "\"status\":\"increment\"" + '}', fakeRemoteEndPoint1.getData());

        assertEquals("{\"score\":" + String.valueOf(user2.getMyScore()) + ',' +
                "\"square\":\"" + String.valueOf(0) + '_' + String.valueOf(1) + "\"," +
                "\"color\":" + String.valueOf(user2.getMyColor()) + ',' +
                "\"name\":\"me\"," +
                "\"status\":\"increment\"" + '}', fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testSetEnemyScore() throws Exception {
        GameUser user1 = new GameUser("myName");
        GameUser user2 = new GameUser("enemyName");

        webSocketService.notifyEnemyNewScore(user1, 0, 0);
        webSocketService.notifyEnemyNewScore(user2, 0, 1);

        assertEquals("{\"score\":" + String.valueOf(user1.getMyScore()) + ',' +
                "\"square\":\"" + String.valueOf(0) + '_' + String.valueOf(0) + "\"," +
                "\"color\":" + String.valueOf(user1.getMyColor()) + ',' +
                "\"name\":\"enemy\"," +
                "\"status\":\"increment\"" + '}', fakeRemoteEndPoint1.getData());

        assertEquals("{\"score\":" + String.valueOf(user2.getMyScore()) + ',' +
                "\"square\":\"" + String.valueOf(0) + '_' + String.valueOf(1) + "\"," +
                "\"color\":" + String.valueOf(user2.getMyColor()) + ',' +
                "\"name\":\"enemy\"," +
                "\"status\":\"increment\"" + '}', fakeRemoteEndPoint2.getData());
    }

    @Test
    public void testGetSession() throws Exception {
        assertNotNull(gameWebSocket.getSession());
    }

    @Test
    public void testSetSession() throws Exception {
        Session testSession = mock(Session.class);
        gameWebSocket.setSession(testSession);
        assertEquals(testSession, gameWebSocket.getSession());
    }

    @Test
    public void testOnClose() throws Exception {
        assertEquals(true, true);
    }
}