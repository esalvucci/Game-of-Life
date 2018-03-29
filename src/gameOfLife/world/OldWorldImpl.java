package gameOfLife.world;

import gameOfLife.Direction;
import gameOfLife.State;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OldWorldImpl implements World {

    private State[][] previousWorld;
    private State[][] currentWorld;
    private static final int ZERO = 0;
    private static final int SIZE = 10;
    private static final String SPACE = " ";
    private List<State[][]> matrixes = new LinkedList<>();

    public OldWorldImpl() {
        this.initialize();
    }

    @Override
    public void setNextState() {
        this.previousWorld = Arrays.copyOf(this.currentWorld, SIZE);

        for (int i = 0; i < this.currentWorld.length; i++) {
            for (int j = 0; j < this.currentWorld.length; j++) {
                if (this.isDying(i, j)) {
                    this.currentWorld[i][j] = State.DEAD;
                }

                if (this.isSurviving(i, j)) {
                    this.currentWorld[i][j] = State.LIVE;
                }

            }
        }
    }

    @Override
    public State[][] getCurrentState() {
        return this.currentWorld;
    }

    @Override
    public State[][] getPreviousState() {
        return this.previousWorld;
    }

    public void printMatrix() {
        for (int i = 0; i < this.currentWorld.length; i++) {
            for (int j = 0; j < this.currentWorld[i].length; j++) {
                System.out.print(this.currentWorld[i][j] + SPACE);
            }
            System.out.println();
        }
        this.matrixes.add(this.currentWorld);
    }

    private int getSize() {
        return SIZE;
    }

    private void initialize() {
        this.currentWorld = new State[this.getSize()][this.getSize()];

        for (int i = 0; i < this.currentWorld.length; i++) {
            for (int j = 0; j < this.currentWorld.length; j++) {
                this.currentWorld[i][j] = State.getRandomly();
            }
        }
    }

    @Override
    public int getNumberAliveNeighboursOf(int i, int j) {
        int count = ZERO;
        for (Direction direction : Direction.values()) {
            if (this.previousWorld[(i + direction.getX() + SIZE) % SIZE][(j + direction.getY() + SIZE) % SIZE].isAlive()) {
                count++;
            }
        }
        return count;
    }

    private boolean isDyingBecauseOfLoneliness(final int i, final int j) {
        return this.getNumberAliveNeighboursOf(i, j) <= 1 && this.previousWorld[i][j].isAlive();
    }

    private boolean isDyingBecauseOfOverPopulation(final int i, final int j) {
        return this.getNumberAliveNeighboursOf(i, j) >= 4 && this.previousWorld[i][j].isAlive();
    }

    private boolean isSurviving(final int i, final int j) {
        return  ((this.getNumberAliveNeighboursOf(i, j) == 3
                || this.getNumberAliveNeighboursOf(i, j) == 2)
                && this.previousWorld[i][j].isAlive()) ||
                (!this.previousWorld[i][j].isAlive() && this.getNumberAliveNeighboursOf(i, j) == 3);
    }

    private boolean isDying(final int i, final int j) {
        return  this.isDyingBecauseOfOverPopulation(i, j) || this.isDyingBecauseOfLoneliness(i, j);
    }

}