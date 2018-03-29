package gameOfLife;

import java.util.Random;

public enum State {
    LIVE,
    DEAD;

    public static State getRandomly() {
        return values()[new Random().nextInt(values().length)];
    }

    public boolean isAlive() {
        return this.equals(LIVE);
    }

    public String toString() {
        return this.name().substring(0, 1);
    }
}