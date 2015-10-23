package base;

/**
 * Created by dmitri on 23.10.15.
 */
public class GameUser {

    private final String myName;
    private String enemyName;
    private int myScore = 0;
    private int enemyScore = 0;

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

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void incrementMyScore(int stateOfSquare) {
        switch (stateOfSquare) {
            case 0:
                this.myScore++;
                break;
            case 1:
                break;
            case 2:
                this.myScore += 2;
                break;
        }
    }

    public void incrementEnemyScore(int stateOfSquare) {

        switch (stateOfSquare) {
            case 0:
                this.enemyScore++;
                break;
            case 1:
                break;
            case 2:
                this.enemyScore += 2;
                break;
        }
    }
}
