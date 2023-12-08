package advent.toyships;

public class SimpleToyShipRace {

    private final long raceDuration;

    public SimpleToyShipRace(final long raceDuration) {
        this.raceDuration = raceDuration;
    }

    public long getWinningStrategyCountWithHigherScoreThan(final long highScore) {
        long count = 0;
        for (long buttonPressTime = 0; buttonPressTime <= raceDuration; buttonPressTime++) {
            final long distanceMoved = buttonPressTime * (raceDuration - buttonPressTime);
            if (distanceMoved > highScore) {
                count++;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        return "SimpleToyRace{" +
                "raceDuration=" + raceDuration +
                '}';
    }
}
