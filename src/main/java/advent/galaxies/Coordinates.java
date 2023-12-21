package advent.galaxies;

import java.util.Objects;

public class Coordinates {

    private final long x;
    private final long y;

    private Coordinates(final long x, final long y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates of(final long x, final long y) {
        return new Coordinates(x, y);
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
