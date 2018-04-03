package gameOfLife;

import gameOfLife.controller.Controller;
import gameOfLife.controller.ControllerImpl;
import gameOfLife.view.MatrixFrame;

import javax.swing.*;

public class MainView {

    private static final int SIZE = 5000;

    public static void main(String[] a) {

        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {

                                           Controller controller = new ControllerImpl(SIZE);
                                           controller.createWorkers();
                                           MatrixFrame window = new MatrixFrame.Builder()
                                                                .setController(controller)
                                                                .build();
                                           controller.setFrame(window);
                                           window.setSize(500, 500);
                                           window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                           window.setVisible(true);
                                       }
                                   });
    }
}