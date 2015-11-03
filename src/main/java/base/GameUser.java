package base;


/**
 * Created by dmitri on 23.10.15.
 */
public class GameUser {


    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;
    private String myColor;
    private String enemyColor;

    public GameUser(String myName) {
        this.myName = myName;
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

    public void setMyColor(String color) {
        this.myColor = color;
    }

    public void incrementMyScore(int incrementor) {
        this.myScore += incrementor;
    }

    public void incrementEnemyScore(int incrementor) {
        this.enemyScore+= incrementor;
    }
}
