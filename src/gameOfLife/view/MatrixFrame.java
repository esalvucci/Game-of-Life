package gameOfLife.view;

import gameOfLife.model.world.World;
import gameOfLife.model.world.WorldImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MatrixFrame extends JFrame implements MouseMotionListener {

	private static final int SIZE = 500;
	private static final int MATRIX_SIZE = 5000;
//	private MandelbrotSetImage set;
	private Grid canvas;
	private JScrollPane scrollPane;

	public MatrixFrame() {
		super("Game of Life");
		setSize(SIZE, SIZE);
		this.setResizable(false);

		this.canvas = new Grid(MATRIX_SIZE);

		this.canvas.setPreferredSize(new Dimension(SIZE + this.canvas.getMatrixSideSize(), SIZE + this.canvas.getMatrixSideSize()));
//		this.set = set;
	    scrollPane = new JScrollPane(canvas);

	    JPanel info = new JPanel();
	    info.add(new JButton("Start"));
	    info.add(new JButton("Stop"));

	    setLayout(new BorderLayout());
	    add(scrollPane, BorderLayout.CENTER);
		add(info,BorderLayout.NORTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	/**
	 * When the mouse is moved, the position on the complex plane is updated  
	 */
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {}

}
