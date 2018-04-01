package gameOfLife.world;

import gameOfLife.matrix.Matrix;
import gameOfLife.matrix.MatrixImpl;

public class WorldImpl implements World {

    private static final int SIZE = 5000;
    private static final String SPACE = " ";
    private static final int ADDICTIONAL_THREADS = 1;
    private Matrix previousWorld;
    private Matrix currentWorld;
    private Worker[] workers = new Worker[this.getThreadsNumber()];
    
    public WorldImpl() {
        this.currentWorld = new MatrixImpl.Builder()
                                .setSize(SIZE)
                                .setMatrix()
                                .build();
        this.previousWorld = this.currentWorld;
    }

    public WorldImpl(Matrix matrix) {
        this.currentWorld = matrix;
        this.previousWorld = this.currentWorld;
    }

    @Override
    public void evolve() {
        this.previousWorld = this.currentWorld;

        int startingRow = 0;
        int rowsNumber = this.currentWorld.getSize() / workers.length;

/*
        List<Semaphore> semaphores = new LinkedList<>();
        Semaphore mutex = new Semaphore(workers.length);
*/

        try {
            for (int i = 0; i <= workers.length - 1; i++) {
//                Semaphore semaphore = new Semaphore(0, true);

                workers[i] = new Worker(startingRow, rowsNumber, this.previousWorld, this.currentWorld);
//                semaphores.add(semaphore);
                startingRow = startingRow + rowsNumber;
            }
/*
            Semaphore lastSemaphore = new Semaphore(0, true);*/
//            workers[workers.length - 1] = new Worker(startingRow, rowsNumber, this.previousWorld, this.currentWorld);
//            semaphores.add(lastSemaphore);

            for (Worker w : workers) {
                w.start();
            }
/*
            for (Semaphore s : semaphores) {
                s.acquire();
            }

            mutex.release();
*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Matrix getPreviousState() {
        return this.previousWorld;
    }

    @Override
    public Matrix getCurrentState() {
        return this.currentWorld;
    }

    public void printMatrix() {
        for (int i = 0; i < this.previousWorld.getSize(); i++) {
            for (int j = 0; j < this.previousWorld.getSize(); j++) {
                System.out.print(this.previousWorld.get(i, j) + SPACE);
            }
            System.out.println();
        }
    }

    private int getThreadsNumber() {
        return 1;
        //        return Runtime.getRuntime().availableProcessors() + ADDICTIONAL_THREADS;
    }

}
