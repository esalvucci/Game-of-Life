package gameOfLife.controller;

import gameOfLife.model.matrix.Matrix;
import gameOfLife.model.matrix.MatrixImpl;
import gameOfLife.model.world.Worker;
import gameOfLife.view.MatrixFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ControllerImpl extends SwingWorker<Void, Void> implements Controller {

    private MatrixFrame frame;
    private static final String SPACE = " ";
    private static final int ADDICTIONAL_THREADS = 1;
    private Matrix previousWorld;
    private Matrix currentWorld;
    private final Worker[] workers = new Worker[this.getThreadsNumber()];
    private List<Semaphore> semaphores = new ArrayList<>();
    private List<Semaphore> mutexes = new ArrayList<>();
    private boolean stopped = false;
    private int size;

    public ControllerImpl(int size) {
        this.currentWorld = new MatrixImpl.Builder()
                                .setSize(size)
                                .setMatrix()
                                .build();
        this.previousWorld = this.currentWorld;
        this.size = size;
    }

    public ControllerImpl(Matrix matrix) {
        this.currentWorld = matrix;
        this.previousWorld = this.currentWorld;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setFrame(MatrixFrame frame) {
        this.frame = frame;
    }

    @Override
    public void createWorkers() {
        this.previousWorld = this.currentWorld;

        int startingRow = 0;
        int rowsNumber = this.currentWorld.getSize() / this.workers.length;

            for (int i = 0; i < this.workers.length - 1; i++) {
                Semaphore semaphore = new Semaphore(0, true);
                Semaphore mutex = new Semaphore(1, true);

                this.workers[i] = new Worker(startingRow, rowsNumber, this.previousWorld, this.currentWorld, semaphore, mutex);
                this.semaphores.add(semaphore);
                this.mutexes.add(mutex);
                startingRow = startingRow + rowsNumber;
            }

            Semaphore lastSemaphore = new Semaphore(0, true);
            Semaphore lastMutex = new Semaphore(1, true);
            this.workers[workers.length - 1] = new Worker(startingRow, rowsNumber, this.previousWorld, this.currentWorld, lastSemaphore, lastMutex);
            this.semaphores.add(lastSemaphore);
            this.mutexes.add(lastMutex);
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

    @Override
    protected Void doInBackground() {

        while(!stopped) {
            try {
                for (Semaphore semaphore : this.semaphores) {
                    semaphore.acquire();
                }

                this.process(new ArrayList<>());
            } catch (InterruptedException e) {
                for (Semaphore mutex : this.mutexes) {
                    mutex.release();
                }

                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void process(List<Void> chunks) {
        this.frame.updateView();
    }

    @Override
    public void endMatrixUpdate() {

        this.getPreviousState().clearAliveCells();

        for (Semaphore semaphore : this.mutexes) {
            semaphore.release();
        }
    }

    @Override
    public void startEvolution() {
        for(Worker worker : workers){
            worker.start();
        }

        this.execute();

    }

    @Override
    public void stopEvolution() {
        for(Worker worker : workers) {
            worker.stopEvolution();
        }

        this.stopped = true;

    }

    private void setRunning() {
        this.stopped = !this.isStopped();
    }

    private boolean isStopped() {
        return this.stopped;
    }

    private int getThreadsNumber() {
//        return 1;
        return Runtime.getRuntime().availableProcessors() + ADDICTIONAL_THREADS;
    }

}
