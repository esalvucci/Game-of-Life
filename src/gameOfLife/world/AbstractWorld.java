package gameOfLife.world;

import gameOfLife.Direction;

import java.util.Arrays;
import java.util.Random;

public abstract class AbstractWorld implements World {

    private static final int SIZE = 5;
    private static final String SPACE = " ";
    private boolean[][] previousWorld = new boolean[SIZE][SIZE];
    private boolean[][] currentWorld = new  boolean[SIZE][SIZE];

    public AbstractWorld() {
        this.initialize();
    }

    public AbstractWorld(boolean[][] matrix) {
        this.currentWorld = matrix;
        this.previousWorld = Arrays.copyOf(this.currentWorld, SIZE);
    }

    // Template Method
    @Override
    public void setNextState() {
        this.previousWorld = Arrays.copyOf(this.currentWorld, SIZE);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.isDying(i, j)) {
                    this.currentWorld[i][j] = false;
                } else if (this.isLiving(i, j)) {
                    this.currentWorld[i][j] = true;
                }
            }
        }
    }

    @Override
    public boolean[][] getPreviousState() {
        return this.previousWorld;
    }

    @Override
    public boolean[][] getCurrentState() {
        return this.currentWorld;
    }

    @Override
    public int getNumberAliveNeighboursOf(int i, int j) {
        int count = 0;

        for (Direction d : Direction.values()) {
            if (this.previousWorld[(SIZE + i + d.getX()) % SIZE][(SIZE + j + d.getY()) % SIZE]) {
                count++;
            }
        }

        return count;
    }

    public void printMatrix() {
        for (int i = 0; i < this.previousWorld.length; i++) {
            for (int j = 0; j < this.previousWorld.length; j++) {
                System.out.print(this.previousWorld[i][j] + SPACE);
            }
            System.out.println();
        }
    }

    abstract boolean isLiving(int i, int j);

    abstract boolean isDying(int i, int j);

    private void initialize() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.currentWorld[i][j] = new Random().nextBoolean();
            }
        }

    }
}
