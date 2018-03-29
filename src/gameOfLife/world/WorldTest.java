package gameOfLife.world;

import static gameOfLife.State.*;
import gameOfLife.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {

    private AbstractWorld world;
    private State[][] sampleWorld;

    @Before
    public void init() {
        this.sampleWorld = new State[][]{
                {DEAD, DEAD, DEAD, LIVE, DEAD},
                {DEAD, DEAD, DEAD, DEAD, DEAD},
                {LIVE, LIVE, LIVE, DEAD, DEAD},
                {LIVE, LIVE, LIVE, DEAD, DEAD},
                {LIVE, DEAD, DEAD, DEAD, DEAD}
        };

        this.world = new WorldImpl(this.sampleWorld);
    }

    @Test
    public void getPreviousStateSize() {
        Assert.assertEquals(this.world.getPreviousState().length,  this.sampleWorld.length);
    }

    @Test
    public void turnNextState() {
        this.world.setNextState();

        Assert.assertNotEquals(this.world.getPreviousState(), this.world.getCurrentState());
    }

    @Test
    public void getNeighboursNumber() {
        Assert.assertEquals(3, this.world.getNumberAliveNeighboursOf(1, 1));
    }

    @Test
    public void isDyingBecauseOfLoneliness() {
        Assert.assertEquals(true, this.world.isDying(0, 3));
    }

    @Test
    public void isDyingBecauseOfOverPopulation() {
        Assert.assertEquals(true, this.world.isDying(3, 1));
    }

    @Test
    public void isSurviving() {
        Assert.assertEquals(true, this.world.isLiving(2, 2));
    }

    @Test
    public void becomesLive() {
        Assert.assertEquals(true, this.world.isLiving(1, 1));
    }
}