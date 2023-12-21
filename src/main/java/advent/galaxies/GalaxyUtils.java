package advent.galaxies;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class GalaxyUtils {

    private GalaxyUtils() {
    }

    public static List<Coordinates> getCoordinatesFrom(final List<String> input) {
        final List<Coordinates> coordinates = Lists.newArrayList();

        long y = 0;
        for (final String line : input) {
            coordinates.addAll(getCoordinatesFromLine(line, y));

            y++;
        }

        return coordinates;
    }

    private static List<Coordinates> getCoordinatesFromLine(final String line, final long y) {
        final List<Coordinates> coordinates = Lists.newArrayList();

        for (int x = 0; x <= line.length() - 1; x++) {
            if (line.charAt(x) == '#') {
                coordinates.add(Coordinates.of(x, y));
            }
        }

        return coordinates;
    }

    public static long getHExpandableUnits(final Coordinates coordinate, final List<String> input) {
        if (input.isEmpty()) {
            return 0L;
        }

        final long y = coordinate.getY();
        if (y > input.size() - 1) {
            return 0L;
        }

        return LongStream.range(0, coordinate.getX()).boxed().filter(l -> xIndexExpandable(l, input)).count();
    }

    private static boolean xIndexExpandable(final long x, final List<String> input) {
        if (input.isEmpty() || x > input.get(0).length() - 1) {
            return false;
        }

        return input.stream().allMatch(line -> line.charAt((int) x) != '#');
    }

    public static long getVExpandableUnits(final Coordinates coordinate, final List<String> input) {
        if (input.isEmpty()) {
            return 0L;
        }

        final long y = coordinate.getY();
        if (y > input.size() - 1) {
            return 0L;
        }

        return LongStream.range(0, coordinate.getY()).boxed().filter(l -> yIndexExpandable(l, input)).count();
    }

    private static boolean yIndexExpandable(final long y, final List<String> input) {
        return !input.get((int) y).contains("#");
    }

    public static Coordinates expand(final Coordinates coordinate, final int toAdd, final long hExpandableUnits, final long vExpandableUnits) {
        return Coordinates.of(coordinate.getX() + addExpansions(toAdd - 1, hExpandableUnits),
                coordinate.getY() + addExpansions(toAdd - 1, vExpandableUnits));
    }

    static long addExpansions(final int toAdd, final long expandedUnits) {
        return (toAdd + 1) * expandedUnits;
    }

    public static long distance(final Coordinates coordinate1, final Coordinates coordinate2) {
        return
                Math.max(
                        Math.abs(coordinate2.getX() - coordinate1.getX()) + (coordinate2.getY() - coordinate1.getY()),
                        Math.abs(coordinate1.getX() - coordinate2.getX()) + (coordinate1.getY() - coordinate2.getY()));
    }

    public static List<Pair<Integer, Integer>> getGalaxyDistancePairs(final Integer numberOfGalaxies) {
        final List<Integer> galaxyIds = IntStream.rangeClosed(1, numberOfGalaxies).boxed().toList();
        final List<Pair<Integer, Integer>> pairs = Lists.newArrayList();

        for (int i = 0; i <= galaxyIds.size() - 1; i++) {
            final int galaxyId = galaxyIds.get(i);
            for (int j = i + 1; j <= galaxyIds.size() - 1; j++) {
                final int otherGalaxyId = galaxyIds.get(j);
                pairs.add(Pair.of(galaxyId, otherGalaxyId));
            }
        }

        return pairs;
    }
}
