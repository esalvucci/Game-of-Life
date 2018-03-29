package gameOfLife.world;

import gameOfLife.Direction;
import gameOfLife.State;

import java.util.Arrays;

public abstract class AbstractWorld implements World {

    private static final int SIZE = 5;
    private static final String SPACE = " ";
    private State[][] previousWorld = new State[SIZE][SIZE];
    private State[][] currentWorld = new State[SIZE][SIZE];

    public AbstractWorld() {
        this.initialize();
    }

    public AbstractWorld(State[][] matrix) {
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
                    this.currentWorld[i][j] = State.DEAD;
                } else if (this.isSurviving(i, j)) {
                    this.currentWorld[i][j] = State.LIVE;
                }
            }
        }
    }

    @Override
    public State[][] getPreviousState() {
        return this.previousWorld;
    }

    @Override
    public State[][] getCurrentState() {
        return this.currentWorld;
    }

    @Override
    public int getNumberAliveNeighboursOf(int i, int j) {
        int count = 0;

        for (Direction d : Direction.values()) {
            if (this.previousWorld[(SIZE + i + d.getX()) % SIZE][(SIZE + j + d.getY()) % SIZE].equals(State.LIVE)) {
                count++;
            }
        }

        return count;
    }

    public void printMatrix() {
        for (int i = 0; i < this.previousWorld.length; i++) {
            for (int j = 0; j < this.previousWorld[i].length; j++) {
                System.out.print(this.previousWorld[i][j] + SPACE);
            }
            System.out.println();
        }
    }

    abstract boolean isSurviving(int i, int j);

    abstract boolean isDying(int i, int j);

    private void initialize() {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.currentWorld[i][j] = State.getRandomly();
            }
        }

    }
}
