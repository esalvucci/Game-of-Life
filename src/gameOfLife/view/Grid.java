package gameOfLife.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Grid extends JPanel {

    private List<Point> fillCells;

    public Grid() {
        fillCells = new ArrayList<>(25);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Point fillCell : fillCells) {
            int cellX = 10 + (fillCell.x * 10);
            int cellY = 10 + (fillCell.y * 10);
            g.setColor(Color.BLUE);
            g.fillRect(cellX, cellY, 10, 10);
        }
        g.setColor(Color.BLACK);
        g.drawRect(10, 10, 800, 500);

        for (int i = 10; i <= 800; i += 10) {
            g.drawLine(i, 10, i, 510);
        }

        for (int i = 10; i <= 500; i += 10) {
            g.drawLine(10, i, 810, i);
        }
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y));
        repaint();
    }

}