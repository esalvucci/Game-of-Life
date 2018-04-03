package gameOfLife.controller;

import gameOfLife.model.matrix.Matrix;
import gameOfLife.view.MatrixFrame;

/**
 * The controller of the application (between the model and the view), according to MVC Design Pattern.
 */
public interface Controller {

    /**
     * Init the workers which will perform the evaluate which cells of the matrix will be alive or dead.
     */
    void createWorkers();

    /**
     * Establish the end of an evolution.
     */
    void endMatrixUpdate();

    /**
     * @return The previous state of the world (the matrix which will be printed during the new one is being calculated.
     */
    Matrix getPreviousState();

    /**
     * Start a single evolution of the world.
     */
    void startEvolution();

    /**
     * Stop a single evolution of the world.
     */
    void stopEvolution();

    /**
     * Sets the MatrixFrame (extending JFrame) in which will be drawn the matrix.
     * @param frame which will be used to draw the matrix.
     */
    void setFrame(MatrixFrame frame);

}
