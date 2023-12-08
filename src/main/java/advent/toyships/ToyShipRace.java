package advent.toyships;

import com.google.common.collect.Lists;

import java.util.List;

public class ToyShipRace {

    private final long raceDuration;
    private final List<RaceStrategy> strategies;

    public ToyShipRace(final long raceDuration) {
        this.raceDuration = raceDuration;
        this.strategies = calculatePressTimeToDistances();
    }

    private List<RaceStrategy> calculatePressTimeToDistances() {
        final List<RaceStrategy> newStrategies = Lists.newArrayList();
        for (long buttonPressTime = 0; buttonPressTime <= raceDuration; buttonPressTime++) {
            final long distanceMoved = buttonPressTime * (raceDuration - buttonPressTime);
            newStrategies.add(new RaceStrategy(buttonPressTime, distanceMoved));
        }

        return newStrategies;
    }

    public List<RaceStrategy> getStrategies() {
        return strategies;
    }

    public List<RaceStrategy> getWinningStrategiesWithHigherScoreThan(final long highScore) {
        return strategies.stream().filter(strategy -> strategy.getDistanceMoved() > highScore).toList();
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
        return "ToyRace{" +
                "raceDuration=" + raceDuration +
                ", strategies=" + strategies +
                '}';
    }
}
