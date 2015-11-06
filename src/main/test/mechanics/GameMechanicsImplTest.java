package mechanics;

import base.GameMechanics;
import base.WebSocketService;
import frontend.WebSocketServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class GameMechanicsImplTest {

    private GameMechanics gameMechanics;

    @Before
    public void preparing() {
        WebSocketService webSocketService = new WebSocketServiceImpl();

        ArrayList<String> colors = new ArrayList<>();
        colors.add("#D32F2F");
        colors.add("#FF4081");

        GMResources gameResources = new GMResources(colors, 10000, 100, 6);

        gameMechanics = new GameMechanicsImpl(webSocketService, gameResources);
    }

    @Test
    public void testAddUser() throws Exception {
        gameMechanics.addUser("user1");
//        gameMechanics.addUser("user2");
    }

    @Test
    public void testTapSquare() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }
}