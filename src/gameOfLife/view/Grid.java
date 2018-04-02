package gameOfLife.view;

import gameOfLife.model.matrix.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Grid extends JPanel {

    private static final int TEN = 10;
    private Matrix matrix;
    private Set<Point> fillCells;

    public Grid(Matrix matrix) {
        fillCells = new HashSet<>();
        this.matrix = matrix;
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if (matrix.get(i, j)) {
                    fillCells.add(new Point(i, j));
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Point fillCell : fillCells) {
            int cellX = TEN + (fillCell.x * TEN);
            int cellY = TEN + (fillCell.y * TEN);
            g.setColor(Color.BLUE);
            g.fillRect(cellX, cellY, TEN, TEN);
        }
        g.setColor(Color.BLACK);
        g.drawRect(TEN, TEN, this.getMatrixSideSize(), this.getMatrixSideSize());

        for (int i = TEN; i <= this.getMatrixSideSize(); i += TEN) {
            g.drawLine(i, TEN, i, this.getMatrixSideSize() + TEN);
        }

        for (int i = TEN; i <= this.getMatrixSideSize(); i += TEN) {
            g.drawLine(TEN, i, this.getMatrixSideSize() + TEN, i);
        }
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y));
        repaint();
    }

    public int getMatrixSideSize() {
        return this.matrix.getSize() * TEN + TEN;
    }
}