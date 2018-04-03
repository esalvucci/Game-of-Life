package gameOfLife.model.matrix;

import gameOfLife.model.Direction;

import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MatrixImpl implements Matrix {

    private boolean[][] matrix;
    private MatrixImpl(boolean[][] matrix) {
        this.matrix = matrix;
    }
    private Map<Point, Boolean> map = new ConcurrentHashMap<>();
    private Set<Point> trueValues = this.map.keySet();

    @Override
    public int getNumberAliveNeighboursOf(int i, int j) {
        int count = 0;

        for (Direction d : Direction.values()) {
            if (this.matrix[(this.getSize() + i + d.getX()) % this.getSize()][(this.getSize() + j + d.getY()) % this.getSize()]) {
                count++;
            }
        }

        return count;
    }

    @Override
    public boolean get(int i, int j) {
        return this.matrix[i][j];
    }

    @Override
    public boolean[][] get() {
        return this.matrix;
    }

    @Override
    public int getSize() {
        return this.matrix.length;
    }

    @Override
    public void updateValueIn(int i, int j, boolean newValue) {
        if (this.get(i, j) != newValue) {
            this.matrix[i][j] = newValue;
            if (newValue) {
                this.map.put(new Point(i, j), true);
            }
        }
    }

    @Override
    public Collection<Point> getAliveCells() {
        return this.trueValues;
    }

    @Override
    public void clearAliveCells() {
        this.trueValues.clear();
    }

    public static class Builder {

        private static final double FIFTY_PERCENT = 0.5;
        private int size;
        private boolean[][] matrix;

        public Builder() {

        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setMatrix() {

            if (this.size == 0) {
                throw new IllegalStateException();
            } else {
                this.matrix = new boolean[this.size][this.size];

                for (int i = 0; i <= this.size - 1; i++) {
                    for (int j = 0; j <= this.size - 1; j++) {
                        this.matrix[i][j] = this.getRandomBoolean();
                    }
                }
            }
            
            return this;
        }

        public Builder setMatrix(boolean[][] matrix) {
            this.matrix = matrix;
            return this;
        }

        public MatrixImpl build() {
            if (this.matrix != null) {
                return new MatrixImpl(this.matrix);
            } else {
                throw new IllegalStateException();
            }
        }

        private boolean getRandomBoolean() {
            // return new Random().nextBoolean();
            return Math.random() < FIFTY_PERCENT;
        }

    }

}
