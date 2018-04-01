package gameOfLife.controller;

import gameOfLife.model.chronometer.Chronometer;
import gameOfLife.model.world.World;
import gameOfLife.model.world.WorldImpl;

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
