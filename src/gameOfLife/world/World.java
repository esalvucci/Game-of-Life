package gameOfLife.world;

import gameOfLife.matrix.Matrix;

public interface World {

    void evolve();
    Matrix getPreviousState();
    Matrix getCurrentState();

}
