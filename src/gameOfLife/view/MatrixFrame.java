package gameOfLife.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Simple view of a Mandelbrot Set Image
 * 
 * @author aricci
 *
 */
public class MatrixFrame extends JFrame implements MouseMotionListener {

	private static final int SIZE = 500;
//	private MandelbrotSetImage set;
	private Grid canvas;
	private JScrollPane scrollPane;

	public MatrixFrame() {
		super("Mandelbrot Viewer");
		setSize(SIZE, SIZE);
		this.setResizable(false);

		this.canvas = new Grid();
		this.canvas.fillCell(0, 0);
		this.canvas.fillCell(0, 10);
		this.canvas.fillCell(10, 0);
		this.canvas.fillCell(10, 10);

		this.canvas.setPreferredSize(new Dimension(SIZE + 400, SIZE + 50));
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
