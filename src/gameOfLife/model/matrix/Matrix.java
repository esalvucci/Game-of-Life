package gameOfLife.model.matrix;

import java.awt.*;
import java.util.Collection;

public interface Matrix {

    int getNumberAliveNeighboursOf(int i, int j);
    boolean get(int i, int j);
    boolean[][] get();
    int getSize();
    void updateValueIn(int i, int j, boolean newValue);
    Collection<Point> getAliveCells();
    void clearAliveCells();
}
