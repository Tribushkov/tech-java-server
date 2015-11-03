package mechanics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 03.11.15.
 */
public class GMResourcesTest {

    GMResources gameResources;
    ArrayList<String> colors;
    int gameTime = 100;
    int timeStamp = 100;
    int fieldSize = 6;

    @Before
    public void setUp() throws Exception {
        colors = new ArrayList<>();
        colors.add("#D32F2F");
        colors.add("#FF4081");

        gameResources = new GMResources(colors, gameTime, timeStamp, fieldSize);
    }

    @Test
    public void testGetColors() throws Exception {
        ArrayList<String> expectedColors = new ArrayList<>();
        expectedColors.add("#D32F2F");
        expectedColors.add("#FF4081");
        assertEquals(expectedColors, gameResources.getColors());
    }

    @Test
    public void testGetGameTime() throws Exception {
        assertEquals(gameTime, gameResources.getGameTime());
    }

    @Test
    public void testGetTimeStamp() throws Exception {
        assertEquals(timeStamp, gameResources.getTimeStamp());
    }

    @Test
    public void testGetFieldSize() throws Exception {
        assertEquals(fieldSize, gameResources.getFieldSize());
    }
}