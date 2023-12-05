package advent.trebuchet;

public class DigitData {

    private final int value;
    private final int increment;

    public DigitData(int value, int increment) {
        this.value = value;
        this.increment = increment;
    }

    public int getValue() {
        return value;
    }

    public int getIncrement() {
        return increment;
    }

    @Override
    public String toString() {
        return "DigitData{" +
                "value=" + value +
                ", increment=" + increment +
                '}';
    }
}
