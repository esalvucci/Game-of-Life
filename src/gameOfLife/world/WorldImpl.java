package gameOfLife.world;

import gameOfLife.State;

public class WorldImpl extends AbstractWorld {

    private static final int OVER_POPULATION_LIMIT = 4;
    private static final int LONELINESS_LIMIT = 1;

    public WorldImpl() {
        super();
    }

    public WorldImpl(State[][] matrix) {
        super(matrix);
    }

    @Override
    boolean isSurviving(int i, int j) {
        return true;
    }

    @Override
    boolean isDying(int i, int j) {
        return this.isDyingBecauseOfOverPopulation(i, j)
                || this.isDyingBecauseOfLoneliness(i, j);
    }

    private boolean isDyingBecauseOfOverPopulation(int i, int j) {
        return this.getNumberAliveNeighboursOf(i, j) >= OVER_POPULATION_LIMIT
                && this.getPreviousState()[i][j].isAlive();
    }

    private boolean isDyingBecauseOfLoneliness(final int i, final int j) {
        return this.getNumberAliveNeighboursOf(i, j) <= LONELINESS_LIMIT
                && this.getPreviousState()[i][j].isAlive();
    }
}
