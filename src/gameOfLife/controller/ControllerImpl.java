package gameOfLife.controller;

import gameOfLife.model.chronometer.Chronometer;
import gameOfLife.model.matrix.Matrix;
import gameOfLife.model.matrix.MatrixImpl;
import gameOfLife.model.Worker;
import gameOfLife.view.MatrixFrame;

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
    private List<Semaphore> consumer = new ArrayList<>();
    private List<Semaphore> producer = new ArrayList<>();
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
        for (int i = 0; i < this.currentWorld.getSize(); i++) {
            for (int j = 0; j < this.currentWorld.getSize(); j++) {
                boolean newValue = this.currentWorld.get(i, j);
                this.previousWorld.updateValueIn(i, j, newValue);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createWorkers() {

        int startingRow = 0;
        int rowsNumber = this.currentWorld.getSize() / this.workers.length;

            for (int i = 0; i < this.workers.length-1; i++) {
                Semaphore consumer = new Semaphore(0, true);
                Semaphore producer = new Semaphore(1, true);
                this.workers[i] = new Worker.Builder()
                                        .setStartingRow(startingRow)
                                        .setRowsNumber(rowsNumber)
                                        .setPreviousState(this.previousWorld)
                                        .setCurrentState(this.currentWorld)
                                        .setConsumer(consumer)
                                        .setProducer(producer)
                                        .build();
                this.consumer.add(consumer);
                this.producer.add(producer);
                startingRow = startingRow + rowsNumber;
            }

        Semaphore lastConsumer = new Semaphore(0, true);
        Semaphore lastProducer = new Semaphore(1, true);

        this.workers[this.workers.length - 1] = new Worker.Builder()
                .setStartingRow(startingRow)
                .setRowsNumber(this.previousWorld.getSize()-startingRow)
                .setPreviousState(this.previousWorld)
                .setCurrentState(this.currentWorld)
                .setConsumer(lastConsumer)
                .setProducer(lastProducer)
                .build();
        this.consumer.add(lastConsumer);
        this.producer.add(lastProducer);
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

                for (Semaphore consumer : this.consumer) {
                    consumer.acquire();
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
        for (int i = 0; i < this.currentWorld.getSize(); i++) {
            for (int j = 0; j < this.currentWorld.getSize(); j++) {
                boolean newValue = this.currentWorld.get(i, j);
                this.previousWorld.updateValueIn(i, j, newValue);
            }
        }

        for (Semaphore producer : this.producer) {
            producer.release();
        }

        this.chronometer.stop();
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
        return Runtime.getRuntime().availableProcessors() + ADDICTIONAL_THREADS;
    }

}
