package gameOfLife.model.matrix;

import java.util.Collection;

public interface Matrix {

    int getNumberAliveNeighboursOf(int i, int j);
    boolean get(int i, int j);
    boolean[][] get();
    int getSize();
    void updateValueIn(int i, int j, boolean newValue);
}
