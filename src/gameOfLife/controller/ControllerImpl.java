package gameOfLife.controller;

import gameOfLife.model.chronometer.Chronometer;
import gameOfLife.model.matrix.Matrix;
import gameOfLife.model.matrix.MatrixImpl;
import gameOfLife.model.world.Worker;
import gameOfLife.view.MatrixFrame;

import javax.print.attribute.standard.Chromaticity;
import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * {@inheritDoc}
 */
public class ControllerImpl extends SwingWorker<Void, Void> implements Controller {

    private static final int ADDICTIONAL_THREADS = 1;
    private final Worker[] workers = new Worker[this.getThreadsNumber()];
    private List<Semaphore> semaphores = new ArrayList<>();
    private List<Semaphore> mutexes = new ArrayList<>();
    private Matrix previousWorld;
    private Matrix currentWorld;
    private boolean running = true;
    private MatrixFrame frame;
    private Chronometer chronometer = new Chronometer();

    /**
     * Constructor which get as parameter the size of the matrix that is going to initialize.
     * @param size of the matrix.
     */
    public ControllerImpl(int size) {
        this.currentWorld = new MatrixImpl.Builder()
                                .setSize(size)
                                .setMatrix()
                                .build();
        this.previousWorld = new MatrixImpl.Builder()
                                .setSize(size)
                                .setEmptyMatrix()
                                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createWorkers() {

        int startingRow = 0;
        int rowsNumber = this.currentWorld.getSize() / this.workers.length;

            for (int i = 0; i < this.workers.length; i++) {
                Semaphore semaphore = new Semaphore(0, true);
                Semaphore mutex = new Semaphore(1, true);

                this.workers[i] = new Worker.Builder()
                                        .setStartingRow(startingRow)
                                        .setRowsNumber(rowsNumber)
                                        .setPreviousState(this.previousWorld)
                                        .setCurrentState(this.currentWorld)
                                        .setSemaphore(semaphore)
                                        .setMutex(mutex)
                                        .build();
                this.semaphores.add(semaphore);
                this.mutexes.add(mutex);
                startingRow = startingRow + rowsNumber;
            }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matrix getPreviousState() {
        return this.previousWorld;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Void doInBackground() {

        while(this.isRunning()) {
            try {
                this.chronometer.start();

                for (Semaphore semaphore : this.semaphores) {
                    semaphore.acquire();
                }
                SwingUtilities.invokeAndWait(() -> this.process(new ArrayList<>()));
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void process(List<Void> chunks) {
        this.frame.updateView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endMatrixUpdate() {
        for (Semaphore semaphore : this.mutexes) {
            semaphore.release();
        }

        for (int i = 0; i < this.currentWorld.getSize(); i++) {
            for (int j = 0; j < this.currentWorld.getSize(); j++) {
                boolean newValue = this.currentWorld.get(i, j);
                this.previousWorld.updateValueIn(i, j, newValue);
            }
        }

        this.chronometer.stop();
/*
        System.out.println(this.previousWorld.getSize() + " | " + this.getThreadsNumber()
                + " | " + (this.getThreadsNumber() - 1) + " | " + this.chronometer.getTime() + "ms");
*/

        System.out.println(this.chronometer.getTime());


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startEvolution() {

        for(Worker worker : workers){
            worker.start();
        }
        this.execute();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopEvolution() {
        for(Worker worker : workers) {
            worker.stopEvolution();
        }
        this.changeRunningState();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFrame(MatrixFrame frame) {
        this.frame = frame;
    }

    private void changeRunningState() {
        this.running = !this.isRunning();
    }

    private boolean isRunning() {
        return this.running;
    }

    private int getThreadsNumber() {
//        return 1;
        return Runtime.getRuntime().availableProcessors() + ADDICTIONAL_THREADS;
    }

}
