package advent.colorcubes;

import java.util.List;

public class CubeGame {
    private final List<CubeGroup> rounds;
    private final CubeGroup bagContents;

    public CubeGame(final List<CubeGroup> rounds, final CubeGroup bagContents) {
        this.rounds = rounds;
        this.bagContents = bagContents;
    }

    public boolean isGamePossible() {
        return bagContents.canContainAllTheseGroups(rounds);
    }

    public int calculateMinPowerForGame() {
        int result = 1;
        for (final Cube cube : Cube.values()) {
            final int minPowerForCube = calculateMinimumCubesFor(cube);
            result *= minPowerForCube;
        }

        return result;
    }

    public int calculateMinimumCubesFor(final Cube cube) {
        return getMaximumCubesInAllRoundsFor(cube);
    }

    private int getMaximumCubesInAllRoundsFor(final Cube cube) {
        int max = 0;
        for (final CubeGroup round : rounds) {
            final int amount = round.howManyCubesOfColor(cube);
            if (amount > max) {
                max = amount;
            }
        }

        return max;
    }

    @Override
    public String toString() {
        return "CubeGame{" +
                "rounds=" + rounds +
                ", bagContents=" + bagContents +
                '}';
    }
}
