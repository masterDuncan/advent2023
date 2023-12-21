package advent.continuum;

import advent.expansion.UniverseUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Continuum2D {

    private final String flatMap;
    private final int lineSize;
    final Map<Integer, Position> galaxyMap = Maps.newHashMap();

    public Continuum2D(final List<String> input) {
        this.flatMap = String.join("", input);
        this.lineSize = input.isEmpty() ? 0 : input.get(0).length();
        cartograph(input);
    }

    private void cartograph(final List<String> input) {
        int galaxyId = 1;
        for (int row = 0; row <= input.size() - 1; row++) {
            final String line = input.get(row);
            for (int column = 0; column <= line.length() - 1; column++) {
                if (line.charAt(column) == '#') {
                    galaxyMap.put(galaxyId, new Position(column, row));
                    galaxyId++;
                }
            }
        }
    }

    private void represent() {
        System.out.println();
    }

    public Map<Integer, Position> getExpandedIdToIndexMap(final int factor) {
        final List<Integer> emptyHorizontals = UniverseUtils.getEmptyHorizontalLineIndexes(flatMap, lineSize);
        final List<Integer> emptyVerticals = UniverseUtils.getEmptyVerticalLineIndexes(flatMap, lineSize);

        System.out.println("emptyHorizontals: " + emptyHorizontals);
        System.out.println("emptyVerticals: " + emptyVerticals);

        final Map<Integer, Position> result = Maps.newHashMap();

        for (final Map.Entry<Integer, Position> entry : galaxyMap.entrySet()) {
            final int galaxyId = entry.getKey();
            final Position galaxyPosition = entry.getValue();

            int baseVerticalIncrements = 0;
            for (int i = 0; i <= emptyVerticals.size() - 1; i++) {
                if (emptyVerticals.get(i) < galaxyPosition.getColumn()) {
                    baseVerticalIncrements += factor;
                }
            }

            int baseHorizontalIncrements = 0;
            for (int i = 0; i <= emptyHorizontals.size() - 1; i++) {
                if (emptyHorizontals.get(i) < galaxyPosition.getRow()) {
                    baseHorizontalIncrements += factor;
                }
            }

            final Position newGalaxyPosition = new Position(galaxyPosition.getColumn() + baseVerticalIncrements,
                    galaxyPosition.getRow() + baseHorizontalIncrements);
            result.put(galaxyId, newGalaxyPosition);
        }

        return result;
    }
}
