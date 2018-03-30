package gameOfLife.world;

public interface World {

    void setNextState();
    int getNumberAliveNeighboursOf(int i, int j);
    boolean[][] getPreviousState();
    boolean[][] getCurrentState();

}
