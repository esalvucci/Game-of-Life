package gameOfLife.controller;

import gameOfLife.model.matrix.Matrix;
import gameOfLife.view.MatrixFrame;

public interface Controller {

    void createWorkers();
    void endMatrixUpdate();
    Matrix getPreviousState();
    Matrix getCurrentState();
    void startEvolution();
    void stopEvolution();
    int getSize();
    void setFrame(MatrixFrame frame);

}
