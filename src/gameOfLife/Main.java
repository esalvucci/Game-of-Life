package gameOfLife;

import gameOfLife.world.AbstractWorld;
import gameOfLife.world.WorldImpl;

public class Main {

    private static final int TIMES = 100;

    public static void main(String... args) {
        AbstractWorld world = new WorldImpl();

        System.out.println("World initialized");
        System.out.println();

        for (int i = 0; i < TIMES; i++) {
            world.setNextState();
            world.printMatrix();

            System.out.println();
        }

    }
}
