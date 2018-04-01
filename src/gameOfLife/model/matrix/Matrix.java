package gameOfLife.model.matrix;

public interface Matrix {

    int getNumberAliveNeighboursOf(int i, int j);
    boolean get(int i, int j);
    boolean[][] get();
    int getSize();
    void updateValueIn(int i, int j, boolean newValue);
}
