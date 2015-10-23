package base;

/**
 * Created by dmitri on 23.10.15.
 */
public interface GameMechanics {
    public void addUser(String user);

    public void tapSquare(String userName, int row, int column, int stateSquare);

    public void run();
}
