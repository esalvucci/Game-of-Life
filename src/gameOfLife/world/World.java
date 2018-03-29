package gameOfLife.world;

import gameOfLife.State;

public interface World {

//    void printMatrix();
    void setNextState();
    State[][] getPreviousState();
    State[][] getCurrentState();

    int getNumberAliveNeighboursOf(int i, int j);
}
