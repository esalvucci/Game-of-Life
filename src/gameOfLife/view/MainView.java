package gameOfLife.view;

import java.awt.*;
import javax.swing.*;

public class MainView {

    public static void main(String[] a) {

        SwingUtilities.invokeLater(new Runnable() {
                                       public void run() {

                                           JFrame window = new MatrixFrame();
                                           window.setSize(500, 500);
                                           window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                                           window.setVisible(true);
                                       }
                                   });
    }
}