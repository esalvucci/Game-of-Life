package gameOfLife.view;

import gameOfLife.model.matrix.Matrix;
import gameOfLife.model.world.World;
import gameOfLife.model.world.WorldImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Grid extends JPanel {

    private static final int TEN = 10;
    private World world;
    private Set<Point> fillCells;

    public Grid(int size) {
        fillCells = new HashSet<>();
        this.world = new WorldImpl(size);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                world.evolve();
                fillCells.addAll(world.getPreviousState().getAliveCells());
                for (Point fillCell : fillCells) {
                    int cellX = TEN + (fillCell.x * TEN);
                    int cellY = TEN + (fillCell.y * TEN);
                    g.setColor(Color.BLUE);
                    g.fillRect(cellX, cellY, TEN, TEN);
                }
            }
        });



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
        return this.world.getPreviousState().getSize() * TEN + TEN;
    }
}