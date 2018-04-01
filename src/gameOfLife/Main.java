package gameOfLife;

import gameOfLife.chronometer.Chronometer;
import gameOfLife.world.World;
import gameOfLife.world.WorldImpl;

public class Main {

    public static void main(String... args) {
        World world = new WorldImpl();

//        ((WorldImpl) world).printMatrix();
        while (true) {
            Chronometer chronometer = new Chronometer();
            chronometer.start();
            world.evolve();
            chronometer.stop();

            System.out.println();

    //        ((WorldImpl) world).printMatrix();

            System.out.println("Matrix evolved in " + chronometer.getTime() + "ms");
        }

    }
}
