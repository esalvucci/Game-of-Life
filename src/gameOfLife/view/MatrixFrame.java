package gameOfLife.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MatrixFrame extends JFrame implements MouseMotionListener {

	private static final int SIZE = 500;
	private static final int MATRIX_SIZE = 5000;
	private static final String STOP = "Stop";
	private static final String START = "Start";
	private static final String ALIVE_CELLS = " alive cells";
//	private MandelbrotSetImage set;
	private Grid canvas;
	private JScrollPane scrollPane;

	public MatrixFrame() {
		super("Game of Life");
        this.setSize(SIZE, SIZE);
		this.setResizable(false);

		this.canvas = new Grid(MATRIX_SIZE);

		this.canvas.setPreferredSize(new Dimension(SIZE + this.canvas.getMatrixSideSize(), SIZE + this.canvas.getMatrixSideSize()));
//		this.set = set;
        this.scrollPane = new JScrollPane(canvas);
	    this.setLayout(new BorderLayout());
	    this.add(scrollPane, BorderLayout.CENTER);
        this.setInfoPanel();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	private void setInfoPanel() {
        JPanel info = new JPanel();
        JButton start = new JButton(START);
        JButton stop = new JButton(STOP);
        JLabel aliveCells = new JLabel();
        aliveCells.setText(this.canvas.getAliveCellsNumber() + ALIVE_CELLS);

        info.add(start);
        info.add(stop);
        info.add(aliveCells);
        this.add(info, BorderLayout.NORTH);
    }

	/**
	 * When the mouse is moved, the position on the complex plane is updated  
	 */
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {}

}
