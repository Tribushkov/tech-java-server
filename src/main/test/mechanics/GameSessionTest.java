package mechanics;

import base.GameUser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 02.11.15.
 */
public class GameSessionTest {

    private String name1 = "user1";
    private String name2 = "user2";
    private GameSession gameSession = new GameSession(name1, name2);

    @Test
    public void testGetEnemy() throws Exception {
        assertEquals(gameSession.getSecond(), gameSession.getEnemy(name1));
        assertEquals(gameSession.getFirst(), gameSession.getEnemy(name2));
    }

    @Test
    public void testGetSelf() throws Exception {
        assertEquals(gameSession.getFirst(), gameSession.getSelf(name1));
        assertEquals(gameSession.getSecond(), gameSession.getSelf(name2));
    }

    @Test
    public void testGetFirst() throws Exception {
        assertEquals(gameSession.getSelf(name1), gameSession.getFirst());
    }

    @Test
    public void testGetSecond() throws Exception {
        assertEquals(gameSession.getSelf(name2), gameSession.getSecond());
    }

    @Test
    public void testWhoIsWinner() throws Exception {

    }
}