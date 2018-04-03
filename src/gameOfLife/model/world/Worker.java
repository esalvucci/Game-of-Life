package gameOfLife.model.world;

import gameOfLife.model.matrix.Matrix;

import java.util.concurrent.Semaphore;

public class Worker extends Thread {

    private static final int OVER_POPULATION_LIMIT = 4;
    private static final int LONELINESS_LIMIT = 1;
    private static final int SURVIVING_MIN_NEIGHBOURS = 2;
    private static final int SURVIVING_MAX_NEIGHBOURS = 3;

    private int startingRow;
    private int rowsNumber;
    private Matrix currentWorld;
    private Matrix previousWorld;
    private Semaphore semaphore;
    private Semaphore mutex;
    private boolean running = true;

    private Worker(int startingRow, int rowsNumber, final Matrix previousWorld, Matrix currentWorld, Semaphore semaphore, Semaphore mutex) {
        super();
        this.startingRow = startingRow;
        this.rowsNumber = rowsNumber;
        this.previousWorld = previousWorld;
        this.currentWorld = currentWorld;
        this.semaphore = semaphore;
        this.mutex = mutex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        final int to = this.startingRow + this.rowsNumber;

        while(this.isRunning()) {

            try {
                this.mutex.acquire();

                for (int i = this.startingRow; i < to; i++) {
                    for (int j = 0; j < this.currentWorld.getSize(); j++) {

                        if (this.isDying(i, j)) {
                            this.currentWorld.updateValueIn(i, j, false);
                        } else if (this.isLiving(i, j)) {
                            this.currentWorld.updateValueIn(i, j, true);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                this.semaphore.release();
            }
        }
    }

    /**
     * Stops the evolution of the matrix changing the running flag.
     */
    public void stopEvolution() {
        this.changeRunningStatus();
    }

    private void changeRunningStatus() {
        this.running = !this.isRunning();
    }

    private boolean isRunning() {
        return this.running;
    }

    private boolean isLiving(int i, int j) {
        return this.isSurviving(i, j) || this.becomesLive(i, j);
    }

    private boolean isDying(int i, int j) {
        return this.isDyingBecauseOfOverPopulation(i, j)
                || this.isDyingBecauseOfLoneliness(i, j);
    }

    private boolean isDyingBecauseOfOverPopulation(int i, int j) {
        return this.previousWorld.getNumberAliveNeighboursOf(i, j) >= OVER_POPULATION_LIMIT
                && this.previousWorld.get(i, j);
    }

    private boolean isDyingBecauseOfLoneliness(final int i, final int j) {
        return this.previousWorld.getNumberAliveNeighboursOf(i, j) <= LONELINESS_LIMIT
                && this.previousWorld.get(i,j);
    }

    private boolean isSurviving(final int i, final int j) {
        return (this.previousWorld.getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS
                || this.previousWorld.getNumberAliveNeighboursOf(i, j) == SURVIVING_MIN_NEIGHBOURS)
                && this.previousWorld.get(i,j);
    }

    private boolean becomesLive(int i, int j) {
        return !this.previousWorld.get(i, j)
                && this.previousWorld.getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS;
    }

    public static class Builder {

        private int startingRow;
        private int rowsNumber;
        private Matrix previousState;
        private Matrix currentState;
        private Semaphore semaphore;
        private Semaphore mutex;

        public Builder() {

        }

        public Builder setStartingRow(int startingRow) {
            this.startingRow = startingRow;
            return this;
        }

        public Builder setRowsNumber(int rowsNumber) {
            this.rowsNumber = rowsNumber;
            return this;
        }

        public Builder setPreviousState(Matrix previousState) {
            this.previousState = previousState;
            return this;
        }

        public Builder setCurrentState(Matrix currentState) {
            this.currentState = currentState;
            return this;
        }

        public Builder setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
            return this;
        }

        public Builder setMutex(Semaphore mutex) {
            this.mutex = mutex;
            return this;
        }

        public Worker build() {
            if (this.startingRow != 0 || this.rowsNumber != 0 || this.previousState != null
                    || this.currentState != null || this.semaphore != null || this.mutex != null) {
                return new Worker(this.startingRow, this.rowsNumber,
                        this.previousState, this.currentState, this.semaphore,  this.mutex);
            } else {
                throw new IllegalStateException();
            }
        }
    }

}
