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
    private boolean stopped = false;

    public Worker(int startingRow, int rowsNumber, final Matrix previousWorld, Matrix currentWorld, Semaphore semaphore, Semaphore mutex) {
        super();
        this.startingRow = startingRow;
        this.rowsNumber = rowsNumber;
        this.previousWorld = previousWorld;
        this.currentWorld = currentWorld;

        this.semaphore = semaphore;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        final int to = this.startingRow + this.rowsNumber;

        while(!stopped) {

            try {
                this.mutex.acquire();

                log(" acquired " + String.valueOf(System.currentTimeMillis()), Thread.currentThread().getId());

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
                log(" released " + String.valueOf(System.currentTimeMillis()), Thread.currentThread().getId());

                this.semaphore.release();
            }
        }

    }

    public void stopEvolution() {
        this.stopped = true;
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

    private void log(String msg, long i) {
        System.out.println("[WORKER_" + i + "]: " + Thread.currentThread().getName()+ ", " + msg);
    }

}
