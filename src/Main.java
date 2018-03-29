import gameOfLife.world.AbstractWorld;
import gameOfLife.world.WorldImpl;

public class Main {

    public static void main(String... args) {
        AbstractWorld world = new WorldImpl();

        System.out.println("World initialized");
        System.out.println();

        while (true) {
            world.setNextState();
            world.printMatrix();

            System.out.println();
        }

    }
}
