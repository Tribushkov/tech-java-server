package base;

/**
 * Created by dmitri on 23.10.15.
 */
public interface GameMechanics {
    void addUser(String user);

    void tapSquare(String userName, int row, int column, int stateSquare);

    void run();
}
