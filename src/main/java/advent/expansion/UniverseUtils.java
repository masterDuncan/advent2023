package advent.expansion;

import advent.continuum.Position;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class UniverseUtils {

    private UniverseUtils() {
    }

    public static List<Integer> calculateDistances(final Map<Integer, Integer> galaxyMap, final int lineSize) {
        final List<Integer> result = Lists.newArrayList();
        final List<Pair<Integer, Integer>> galaxyIdPairs = getGalaxyPairs(galaxyMap);

        for (final Pair<Integer, Integer> pair : galaxyIdPairs) {
            final int indexA = galaxyMap.get(pair.getKey());
            final int indexB = galaxyMap.get(pair.getValue());
            result.add(UniverseUtils.calculateDistance(indexA, indexB, lineSize));
        }

        return result;
    }

    public static List<Pair<Integer, Integer>> getGalaxyPairs(final List<Integer> galaxyIds) {
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

    public static List<Pair<Integer, Integer>> getGalaxyPairs(final Map<Integer, Integer> galaxyMap) {
        final List<Pair<Integer, Integer>> pairs = Lists.newArrayList();

        final List<Integer> galaxyIds = galaxyMap.keySet().stream().toList();
        for (int i = 0; i <= galaxyIds.size() - 1; i++) {
            final int galaxyId = galaxyIds.get(i);
            for (int j = i + 1; j <= galaxyIds.size() - 1; j++) {
                final int otherGalaxyId = galaxyIds.get(j);
                pairs.add(Pair.of(galaxyId, otherGalaxyId));
            }
        }

        return pairs;
    }

    public static int calculateDistanceFromCoordinates(final Position positionA, final Position positionB) {
        if (positionA.equals(positionB)) {
            return 0;
        }

        double ac = Math.abs(positionB.getRow() - positionA.getRow());
        double cb = Math.abs(positionB.getColumn() - positionA.getColumn());

        return (int) Math.hypot(ac, cb);
    }

    public static int calculateDistance(final int indexA, final int indexB, final int lineSize) {
        if (indexA == indexB) {
            return 0;
        }

        final int lineA = determineLineForIndexEfficiently(indexA, lineSize);
        final int lineB = determineLineForIndexEfficiently(indexB, lineSize);

        if (lineA == lineB) {
            return Math.abs(indexA - indexB);
        }

        int verticalSteps = Math.abs(lineA - lineB);
        if (lineB > lineA) {
            int transposedIndexB = indexB - (lineSize * verticalSteps);
            return verticalSteps + Math.abs(indexA - transposedIndexB);
        }

        int transposedIndexA = indexA - (lineSize * verticalSteps);
        return verticalSteps + Math.abs(indexB - transposedIndexA);
    }

    public static int determineLineForIndexEfficiently(final int index, int lineSize) {
        final int mapToFirstLine = index % lineSize;
        final int firstPosition = index - mapToFirstLine;

        return (firstPosition / lineSize) + 1;
    }

    public static int determineLineForIndex(final int index, int lineSize) {
        int line = 1;
        while (!indexBelongsToLine(index, line, lineSize)) {
            line++;
        }

        return line;
    }

    private static boolean indexBelongsToLine(final int index, final int line, final int lineSize) {
        final int init = lineSize * (line - 1);
        final int end = (lineSize * line) - 1;
        return index >= init && index <= end;
    }

    public static List<Integer > getEmptyHorizontalLineIndexes(final String flatMap, final int lineSize) {
        return getEmptyLineIndexes(getHorizontalMap(flatMap, lineSize));
    }

    public static List<Integer > getEmptyVerticalLineIndexes(final String flatMap, final int lineSize) {
        return getEmptyLineIndexes(getVerticalMap(flatMap, lineSize));
    }

    public static List<String> getHorizontalMap(final String flatMap, final int lineSize) {
        final List<String> result = Lists.newArrayList();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <= flatMap.length() - 1; i++) {
            final char symbol = flatMap.charAt(i);
            stringBuilder.append(symbol);
            if ((i + 1) % lineSize == 0) {
                result.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }

        return result;
    }

    public static List<String> getVerticalMap(final String flatMap, final int lineSize) {
        final List<String> result = Lists.newArrayList();
        StringBuilder stringBuilder = new StringBuilder();

        final int vlines = flatMap.length() / lineSize;

        for (int i = 0; i <= lineSize - 1; i++) {

            int vindex = i;
            for (int j = 1; j <= vlines; j += 1) {
                final char symbol = flatMap.charAt(vindex);
                vindex += lineSize;
                stringBuilder.append(symbol);
            }

            result.add(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }

        return result;
    }

    public static List<Integer> getEmptyLineIndexes(final List<String> map) {
        final List<Integer> result = Lists.newArrayList();
        int index = 0;
        for (final String line : map) {
            if (lineHasNoGalaxies(line)) {
                result.add(index);
            }
            index++;
        }

        return result;
    }

    private static boolean lineHasNoGalaxies(final String line) {
        return !line.contains("#");
    }
}
