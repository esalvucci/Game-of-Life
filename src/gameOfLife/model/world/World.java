package gameOfLife.model.world;

import gameOfLife.model.matrix.Matrix;

public interface World {

    void evolve();
    Matrix getPreviousState();
    Matrix getCurrentState();

}
