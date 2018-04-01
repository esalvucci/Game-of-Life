package gameOfLife.world;

import gameOfLife.matrix.Matrix;
import gameOfLife.matrix.MatrixImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {

    private WorldImpl world;
    private Matrix sampleWorld;
    private static final int OVER_POPULATION_LIMIT = 4;
    private static final int LONELINESS_LIMIT = 1;
    private static final int SURVIVING_MIN_NEIGHBOURS = 2;
    private static final int SURVIVING_MAX_NEIGHBOURS = 3;

    @Before
    public void init() {
        boolean[][] matrix = new boolean[][]{
                {false, false, false, true , false},
                {false, false, false, false, false},
                {true , true , true , false, false},
                {true , true , true , false, false},
                {true , false, false, false, false}
        };

        this.sampleWorld = new MatrixImpl.Builder()
                                .setSize(matrix.length)
                                .setMatrix(matrix)
                                .build();

        this.world = new WorldImpl(this.sampleWorld);
//        this.world.evolve();

    }

    @Test
    public void getPreviousStateSize() {
        Assert.assertEquals(this.world.getPreviousState().getSize(),  this.sampleWorld.getSize());
    }

    @Test
    public void turnNextState() {
        //this.world.evolve();

//        Assert.assertNotEquals(this.world.getPreviousState(), this.world.getCurrentState());
    }

    @Test
    public void getNeighboursNumber() {
        Assert.assertEquals(3, this.world.getPreviousState().getNumberAliveNeighboursOf(1, 1));
    }

    @Test
    public void isDyingBecauseOfLoneliness() {
        Assert.assertTrue(this.isDying(0, 3));
    }

    @Test
    public void isDyingBecauseOfOverPopulation() {
        Assert.assertTrue(this.isDying(3, 1));
    }

    @Test
    public void isSurviving() {
        Assert.assertTrue(this.isLiving(2, 2));
    }

    @Test
    public void becomesLive() {
        Assert.assertTrue(this.isLiving(1, 1));
    }

    private boolean isLiving(int i, int j) {
        return this.isSurviving(i, j) || this.becomesLive(i, j);
    }

    private boolean isDying(int i, int j) {
        return this.isDyingBecauseOfOverPopulation(i, j)
                || this.isDyingBecauseOfLoneliness(i, j);
    }

    private boolean isDyingBecauseOfOverPopulation(int i, int j) {
        return this.world.getPreviousState().getNumberAliveNeighboursOf(i, j) >= OVER_POPULATION_LIMIT
                && this.world.getPreviousState().get(i, j);
    }

    private boolean isDyingBecauseOfLoneliness(final int i, final int j) {
        return this.world.getPreviousState().getNumberAliveNeighboursOf(i, j) <= LONELINESS_LIMIT
                && this.world.getPreviousState().get(i,j);
    }

    private boolean isSurviving(final int i, final int j) {
        return (this.world.getPreviousState().getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS
                || this.world.getPreviousState().getNumberAliveNeighboursOf(i, j) == SURVIVING_MIN_NEIGHBOURS)
                && this.world.getPreviousState().get(i,j);
    }

    private boolean becomesLive(int i, int j) {
        return !this.world.getPreviousState().get(i, j)
                && this.world.getPreviousState().getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS;
    }
}