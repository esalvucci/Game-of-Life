package gameOfLife.model.matrix;

import java.awt.*;
import java.util.Collection;

public interface Matrix {

    /**
     * Compute the number of neighbours of a cell.
     * @param i number of the cell's row.
     * @param j number of the cell's column.
     * @return the number of neighbours of a cell.
     */
    int getNumberAliveNeighboursOf(int i, int j);

    /**
     * Getter for the element of the matrix in position i, j.
     * @param i the row position in the matrix.
     * @param j the column position in the matrix.
     * @return the element of the matrix in position i, j.
     */
    boolean get(int i, int j);

    /**
     * Getter for the matrix itself.
     * @return the matrix.
     */
    boolean[][] get();

    /**
     * Getter for the size of the matrix.
     * @return the size of the matrix.
     */
    int getSize();

    /**
     * Updates the value in position i, j whith a new value.
     * @param i the row position of the cell to update.
     * @param j the column position of the cell to update.
     * @param newValue which will be set in position i, j
     */
    void updateValueIn(int i, int j, boolean newValue);

    /**
     * Getter for all the alive cells represented as a Collection of points. Each point has a x and a y which are the
     * coordinates of the cell in the matrix.
     * @return the alive cells in the matrix.
     */
    Collection<Point> getAliveCells();

    /**
     * Clears the Collection storing the alive cells to free heap memory.
     */
    void clearAliveCells();
}
