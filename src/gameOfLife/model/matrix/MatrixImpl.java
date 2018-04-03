package gameOfLife.model.matrix;

import gameOfLife.model.Direction;

import java.awt.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MatrixImpl implements Matrix {

    private boolean[][] matrix;
    private Map<Point, Boolean> map = new ConcurrentHashMap<>();
    private Set<Point> trueValues = this.map.keySet();

    private MatrixImpl(boolean[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean get(int i, int j) {
        return this.matrix[i][j];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean[][] get() {
        return this.matrix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return this.matrix.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateValueIn(int i, int j, boolean newValue) {
        if (this.get(i, j) != newValue) {
            this.matrix[i][j] = newValue;
            if (newValue) {
                this.map.put(new Point(i, j), true);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Point> getAliveCells() {
        return this.trueValues;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAliveCells() {
        this.trueValues.clear();
    }

    /**
     * Builder of the class according to Builder Design Pattern.
     */
    public static class Builder {

        private static final double FIFTY_PERCENT = 0.5;
        private int size;
        private boolean[][] matrix;

        /**
         * Default constructor of the builder.
         */
        public Builder() {

        }

        /**
         * Setter for the size of the matrix.
         * @param size of the matrix.
         * @return the builder itself.
         */
        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        /**
         * Setter for the matrix. It fill each cell with a random boolean value.
         * @return the builder itself.
         */
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

        /**
         * Method that create a MatrixImpl if the matrix has been setted before in the builder.
         * @return the matrix implementation.
         */
        public MatrixImpl build() {
            if (this.matrix != null) {
                return new MatrixImpl(this.matrix);
            } else {
                throw new IllegalStateException();
            }
        }

        private boolean getRandomBoolean() {
            return Math.random() < FIFTY_PERCENT;
        }

    }

}
