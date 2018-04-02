package gameOfLife.view;

import java.awt.*;
import javax.swing.*;

public class MainView {

    public static void main(String[] a) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame window = new MatrixFrame();
                window.setSize(500, 500);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                window.setVisible(true);

            }
        });
    }
}