package base;

import java.util.Random;

/**
 * Created by dmitri on 23.10.15.
 */
public class GameUser {

    private String[] colors = { "#D32F2F", "#D32F2F", "#FFA000", "#FF4081",
            "#512DA8", "#E040FB", "#388E3C", "#8BC34A", "#455A64" };


    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;
    private String myColor;
    private String enemyColor;

    public GameUser(String myName) {
        this.myName = myName;

        Random rand = new Random();
        int randomNum = rand.nextInt(8);
        this.myColor = colors[randomNum];

    }

    public int getMyScore() {
        return this.myScore;
    }

    public String getMyName() {
        return this.myName;
    }

    public int getEnemyScore() {
        return this.enemyScore;
    }

    public String getEnemyName() {
        return this.enemyName;
    }

    public String getMyColor() {
        return this.myColor;
    }

    public String getEnemyColor() {
        return this.enemyColor;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void setEnemyColor(String color) {
        this.enemyColor = color;
    }

    public void incrementMyScore(int incrementor) {
        this.myScore += incrementor;
    }

    public void incrementEnemyScore(int incrementor) {
        this.enemyScore+= incrementor;
    }
}
