package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import frontend.FakeRemoteEndPoint;
import frontend.GameWebSocket;
import frontend.WebSocketServiceImpl;
import org.eclipse.jetty.websocket.api.Session;
import org.junit.Before;
import org.junit.Test;
import utils.GameMechanicsRunnable;
import utils.ResourceFactory;

import java.net.URISyntaxException;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by dmitri on 02.11.15.
 */

public class GameMechanicsImplTest {

    GameMechanics gameMechanics;
    GameWebSocket gameWebSocket;
    GameWebSocket gameWebSocket2;
    private FakeRemoteEndPoint fakeRemoteEndPoint1 = new FakeRemoteEndPoint();
    private FakeRemoteEndPoint fakeRemoteEndPoint2 = new FakeRemoteEndPoint();

    @Before
    public void preparing() throws URISyntaxException {

        WebSocketService webSocketService = new WebSocketServiceImpl();

        GMResources gameResources = (GMResources) ResourceFactory.getInstance().getResourceObject("data/game_data.xml");
        gameResources.getColors().remove(0);
        gameResources.getColors().remove(0);

        gameMechanics = new GameMechanicsImpl(webSocketService, gameResources);

        gameWebSocket = new GameWebSocket("myName", gameMechanics, webSocketService);

        gameWebSocket2 = new GameWebSocket("enemyName", gameMechanics, webSocketService);
    }

    @Test
    public void testAddUser() throws Exception {
        GameMechanicsRunnable runnable = new GameMechanicsRunnable(gameMechanics);
        Thread t = new Thread(runnable);
        t.start();

        Session testSession1 = mock(Session.class);
        Session testSession2 = mock(Session.class);
        when(testSession1.getRemote()).thenReturn(fakeRemoteEndPoint1);
        when(testSession2.getRemote()).thenReturn(fakeRemoteEndPoint2);
        gameWebSocket.onConnect(testSession1);
        gameWebSocket2.onConnect(testSession2);
    }

    @Test
    public void testTapSquare() throws Exception {
        GameMechanicsRunnable runnable = new GameMechanicsRunnable(gameMechanics);
        Thread t = new Thread(runnable);
        t.start();

        Session testSession1 = mock(Session.class);
        Session testSession2 = mock(Session.class);
        when(testSession1.getRemote()).thenReturn(fakeRemoteEndPoint1);
        when(testSession2.getRemote()).thenReturn(fakeRemoteEndPoint2);
        gameWebSocket.onConnect(testSession1);
        gameWebSocket2.onConnect(testSession2);

        gameWebSocket.onMessage("1_1");
        assertEquals(1, ((GameMechanicsImpl) gameMechanics).getGameSession(gameWebSocket2.getMyName()).getSecond().getMyScore());

        gameWebSocket2.onMessage("1_1");
        assertEquals(2, ((GameMechanicsImpl) gameMechanics).getGameSession(gameWebSocket.getMyName()).getFirst().getMyScore());
    }

    @Test
    public void testRun() throws Exception {
        GameMechanicsRunnable runnable = new GameMechanicsRunnable(gameMechanics);
        Thread t = new Thread(runnable);
        t.start();
    }
}