package gameOfLife.view;

import gameOfLife.controller.Controller;
import gameOfLife.controller.ControllerImpl;

import javax.swing.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MatrixPanel extends JPanel {

    private static final int TEN = 10;
    private Controller controller;
    private long counter;

    public MatrixPanel(Controller controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        counter = 0;

        for (int i = 0; i < this.controller.getPreviousState().getSize(); i++) {
            for (int j = 0; j < this.controller.getPreviousState().getSize(); j++) {
                if (this.controller.getPreviousState().get(i, j)) {
                    counter++;
                    int cellX = TEN + (i * TEN);
                    int cellY = TEN + (j * TEN);
                    graphics.setColor(Color.BLUE);
                    graphics.fillRect(cellX, cellY, TEN, TEN);
                }
            }
        }

        graphics.setColor(Color.BLACK);
        graphics.drawRect(TEN, TEN, this.getMatrixSideSize(), this.getMatrixSideSize());

        for (int i = TEN; i <= this.getMatrixSideSize(); i += TEN) {
            graphics.drawLine(i, TEN, i, this.getMatrixSideSize() + TEN);
        }

        for (int i = TEN; i <= this.getMatrixSideSize(); i += TEN) {
            graphics.drawLine(TEN, i, this.getMatrixSideSize() + TEN, i);
        }

    }

    public long getCounter() {
        return this.counter;
    }

    public int getMatrixSideSize() {
        return this.controller.getPreviousState().getSize() * TEN;
    }
}