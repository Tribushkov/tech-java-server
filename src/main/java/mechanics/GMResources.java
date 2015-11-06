package mechanics;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dmitri on 28.10.15.
 */

public class GMResources implements Serializable {

    private ArrayList<String> colors;
    private int gameTime;
    private int timeStamp;
    private int fieldSize;

    @SuppressWarnings({"unused", "MagicNumber"})
    public GMResources() {
        this.colors = new ArrayList<>();
        colors.add("#FFA000");
        colors.add("#D32F2F");
        this.gameTime = 20;
        this.timeStamp = 100;
        this.fieldSize = 6;
    }

    public GMResources(ArrayList<String> colors, int gameTime, int timeStamp, int fieldSize) {
        this.colors = colors;
        this.gameTime = gameTime;
        this.timeStamp = timeStamp;
        this.fieldSize = fieldSize;
    }

    public ArrayList<String> getColors() {
        return this.colors;
    }

    public int getGameTime() {
        return this.gameTime;
    }

    public int getTimeStamp() {
        return this.timeStamp;
    }

    public int getFieldSize() {
        return this.fieldSize;
    }
}
