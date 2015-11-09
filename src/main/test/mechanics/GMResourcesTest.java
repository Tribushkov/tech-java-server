package mechanics;

import org.junit.Before;
import org.junit.Test;
import utils.ResourceFactory;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dmitri on 03.11.15.
 */
public class GMResourcesTest {

    public static final int GAMETIME = 60;
    GMResources gameResources;
    int gameTime = GAMETIME;
    int timeStamp = 100;
    int fieldSize = 6;

    @Before
    public void setUp() throws Exception {
        gameResources = (GMResources) ResourceFactory.getInstance().getResourceObject("data/game_data.xml");
        gameResources.getColors().remove(0);
        gameResources.getColors().remove(0);
    }

    @Test
    public void testGetColors() throws Exception {
        ArrayList<String> expectedColors = new ArrayList<>();
        expectedColors.add("#D32F2F");
        expectedColors.add("#FF4081");
        expectedColors.add("#FFA000");
        expectedColors.add("#512DA8");
        expectedColors.add("#E040FB");
        expectedColors.add("#388E3C");
        expectedColors.add("#8BC34A");
        expectedColors.add("#455A64");
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