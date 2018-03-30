package gameOfLife.world;

import gameOfLife.State;

public class WorldImpl extends AbstractWorld {

    private static final int OVER_POPULATION_LIMIT = 4;
    private static final int LONELINESS_LIMIT = 1;
    private static final int SURVIVING_MIN_NEIGHBOURS = 2;
    private static final int SURVIVING_MAX_NEIGHBOURS = 3;

    public WorldImpl() {
        super();
    }

    public WorldImpl(boolean[][] matrix) {
        super(matrix);
    }

    @Override
    boolean isLiving(int i, int j) {
        return this.isSurviving(i, j) || this.becomesLive(i, j);
    }

    @Override
    boolean isDying(int i, int j) {
        return this.isDyingBecauseOfOverPopulation(i, j)
                || this.isDyingBecauseOfLoneliness(i, j);
    }

    private boolean isDyingBecauseOfOverPopulation(int i, int j) {
        return this.getNumberAliveNeighboursOf(i, j) >= OVER_POPULATION_LIMIT
                && this.getPreviousState()[i][j];
    }

    private boolean isDyingBecauseOfLoneliness(final int i, final int j) {
        return this.getNumberAliveNeighboursOf(i, j) <= LONELINESS_LIMIT
                && this.getPreviousState()[i][j];
    }

    private boolean isSurviving(final int i, final int j) {
        return (this.getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS
                || this.getNumberAliveNeighboursOf(i, j) == SURVIVING_MIN_NEIGHBOURS)
                && this.getPreviousState()[i][j];
    }

    private boolean becomesLive(int i, int j) {
        return !this.getPreviousState()[i][j]
                && this.getNumberAliveNeighboursOf(i, j) == SURVIVING_MAX_NEIGHBOURS;
    }
}
