package advent.network;

public enum Direction {
    L,
    R;

    static Direction fromChar(final char character) {
        if (character == 'L') {
            return L;
        }
        if (character == 'R') {
            return R;
        }

        return null;
    }
}
