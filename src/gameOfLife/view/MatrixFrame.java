package gameOfLife.view;

import gameOfLife.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MatrixFrame extends JFrame implements MouseMotionListener {

	private static final int SIZE = 500;
	private static final String STOP = "Stop";
	private static final String START = "Start";
	private static final String ALIVE_CELLS = " alive cells";
	private static final String TITLE = "Game of Life";
	private MatrixPanel canvas;
	private JScrollPane scrollPane;
	private Controller controller;
	private final JPanel info = new JPanel();
    private final JButton start = new JButton(START);
    private final JButton stop = new JButton(STOP);
    private final JLabel aliveCells = new JLabel();

    private MatrixFrame(Controller controller) {
		super(TITLE);
        this.setSize(SIZE, SIZE);
		this.controller = controller;
		this.canvas = new MatrixPanel(controller);
		this.canvas.setPreferredSize(
		        new Dimension(SIZE + this.canvas.getMatrixSideSize(), SIZE + this.canvas.getMatrixSideSize()));
        this.scrollPane = new JScrollPane(canvas);
	    this.setLayout(new BorderLayout());
	    this.add(scrollPane, BorderLayout.CENTER);
        this.setInfoPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

    @Override
    public void mouseMoved(MouseEvent e) {

	}

    public void updateView() {
        scrollPane.repaint();

        this.aliveCells.setText(this.canvas.getCounter() + ALIVE_CELLS);
		this.getController().endMatrixUpdate();
	}

	private Controller getController() {
	    return this.controller;
    }

	private void setInfoPanel() {

		this.start.addActionListener(e -> {
            start.setEnabled(false);
            stop.setEnabled(true);
            getController().startEvolution();

        });

		this.stop.addActionListener(e -> {
            stop.setEnabled(false);
            start.setEnabled(false);
            getController().stopEvolution();
        });

		info.add(this.start);
		info.add(this.stop);
		info.add(this.aliveCells);
		this.add(info, BorderLayout.NORTH);
	}

	public static class Builder {

		private Controller controller;

		public Builder() {

		}

		public Builder setController(Controller controller) {
			this.controller = controller;
			return this;
		}

		public MatrixFrame build() {
			if (this.controller != null) {
				return new MatrixFrame(this.controller);
			} else {
				throw new IllegalStateException();
			}
		}
	}

}
