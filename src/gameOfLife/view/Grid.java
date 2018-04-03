package gameOfLife.view;

import gameOfLife.controller.Controller;
import gameOfLife.controller.ControllerImpl;

import javax.swing.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Grid extends JPanel {

    private static final int TEN = 10;
    private Controller controller;
    private Set<Point> fillCells;

    public Grid(Controller controller) {
        fillCells = new HashSet<>();
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        fillCells.clear();
        fillCells.addAll(controller.getPreviousState().getAliveCells());

        for (Point fillCell : fillCells) {
            int cellX = TEN + (fillCell.x * TEN);
            int cellY = TEN + (fillCell.y * TEN);
            graphics.setColor(Color.BLUE);
            graphics.fillRect(cellX, cellY, TEN, TEN);
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

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y));
        repaint();
    }

    public int getAliveCellsNumber() {
        return this.fillCells.size();
    }

    public int getMatrixSideSize() {
        return this.controller.getPreviousState().getSize() * TEN + TEN;
    }
}