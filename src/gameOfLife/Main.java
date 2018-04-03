package gameOfLife;

import gameOfLife.controller.Controller;
import gameOfLife.controller.ControllerImpl;
import gameOfLife.model.chronometer.Chronometer;

public class Main {

    public static void main(String... args) {
        Controller controller = new ControllerImpl(10);

        ((ControllerImpl) controller).printMatrix();

        for (int i = 0; i < 4 ; i++) {
            Chronometer chronometer = new Chronometer();
            chronometer.start();
//            controller.evolve();
            chronometer.stop();

            System.out.println();

            ((ControllerImpl) controller).printMatrix();

            System.out.println("Matrix evolved in " + chronometer.getTime() + "ms");
        }

    }
}
