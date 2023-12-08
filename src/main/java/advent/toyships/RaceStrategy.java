package advent.toyships;

public class RaceStrategy {

    private final long buttonPressTime;
    private final long distanceMoved;

    public RaceStrategy(final long buttonPressTime, final long distanceMoved) {
        this.buttonPressTime = buttonPressTime;
        this.distanceMoved = distanceMoved;
    }

    public long getButtonPressTime() {
        return buttonPressTime;
    }

    public long getDistanceMoved() {
        return distanceMoved;
    }

    @Override
    public String toString() {
        return "RaceStrategy{" +
                "buttonPressTime=" + buttonPressTime +
                ", distanceMoved=" + distanceMoved +
                '}';
    }
}
