package advent.colorcubes;

public enum Cube {
    RED,
    GREEN,
    BLUE;

    public static Cube getFromDescription(final String description) {
        if (description.equalsIgnoreCase("red")) {
            return RED;
        } else if (description.equalsIgnoreCase("blue")) {
            return BLUE;
        } else if (description.equalsIgnoreCase("green")) {
            return GREEN;
        }

        return null;
    }
}
