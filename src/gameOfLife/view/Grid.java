package gameOfLife.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grid extends JPanel {

    private static final int TEN = 10;
    private static final int MATRIX_WIDTH = 10 * TEN + TEN;
    private static final int MATRIX_HEIGHT = 10 * TEN + TEN;
    private List<Point> fillCells;

    public Grid() {
        fillCells = new ArrayList<>(25);
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
        g.drawRect(TEN, TEN, MATRIX_WIDTH, MATRIX_HEIGHT);

        for (int i = TEN; i <= MATRIX_WIDTH; i += TEN) {
            g.drawLine(i, TEN, i, MATRIX_HEIGHT + TEN);
        }

        for (int i = TEN; i <= MATRIX_HEIGHT; i += TEN) {
            g.drawLine(TEN, i, MATRIX_WIDTH + TEN, i);
        }
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y));
        repaint();
    }
}