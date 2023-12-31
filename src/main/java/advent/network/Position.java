package advent.network;

import java.util.Objects;

public class Position {

    private final String position;

    public Position(String position) {
        this.position = position;
    }

    public boolean isParallelStart() {
        return position.charAt(position.length() - 1) == 'A';
    }

    public boolean isParallelEnd() {
        return position.charAt(position.length() - 1) == 'Z';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position1 = (Position) o;
        return Objects.equals(position, position1.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    @Override
    public String toString() {
        return "Position{" +
                "position='" + position + '\'' +
                '}';
    }
}
