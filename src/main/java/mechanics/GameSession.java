package mechanics;

import base.GameMechanics;
import base.GameUser;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmitri on 23.10.15.
 */
public class GameSession {
    private long startTime;

    private GameUser firstPlayer;
    private GameUser secondPlayer;

    private Map<String, GameUser> users = new HashMap<>();

    public GameSession(String user1, String user2) {

        startTime = new Date().getTime();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setEnemyName(user2);

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setEnemyName(user1);

//        gameUser1.setEnemyColor(gameUser2.getMyColor());
//        gameUser2.setEnemyColor(gameUser1.getMyColor());


        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.firstPlayer = gameUser1;
        this.secondPlayer = gameUser2;
    }

    public GameUser getEnemy(String user) {
        String enemyName = users.get(user).getEnemyName();
        return users.get(enemyName);
    }

    public GameUser getSelf(String user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public GameUser getFirst() {
        return firstPlayer;
    }

    public GameUser getSecond() {
        return secondPlayer;
    }

    public String whoIsWinner(GameUser user, GameUser enemy){
        if(user.getMyScore() > enemy.getMyScore()) {
            return user.getMyName();
        } else {
            if(user.getMyScore() < enemy.getMyScore()) {
                return enemy.getMyName();
            } else {
                return "0";
            }
        }
    }

}
