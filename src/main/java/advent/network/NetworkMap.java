package advent.network;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class NetworkMap {

    private final Map<Position, Pair<Position, Position>> map = Maps.newHashMap();

    public NetworkMap(final List<String> lines) {
        lines.forEach(this::storePosition);
    }

    private void storePosition(final String line) {
        final String[] parts = line.split("=");
        final String initial = parts[0].trim();
        final String left = parts[1].split(",")[0].substring(2, 5);
        final String right = parts[1].split(",")[1].substring(1, 4);

        map.put(new Position(initial), Pair.of(new Position(left), new Position(right)));
    }

    public Position transit(final Position currentPosition, final Direction direction) {
        if (direction == Direction.L) {
            return map.get(currentPosition).getLeft();
        }

        return map.get(currentPosition).getRight();
    }

    public List<Position> getPositions() {
        return map.keySet().stream().toList();
    }

    @Override
    public String toString() {
        return "NetworkMap{" +
                "map=" + map +
                '}';
    }
}
