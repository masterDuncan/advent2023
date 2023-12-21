package advent.continuum;

import advent.expansion.UniverseUtils;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Continuum {

    private final String flatMap;
    private final int lineSize;
    private final Map<Integer, Integer> idToIndexMap = Maps.newLinkedHashMap();

    public Continuum(final List<String> input) {
        this.flatMap = String.join("", input);
        this.lineSize = input.isEmpty() ? 0 : input.get(0).length();
        cartograph();
    }

    private void cartograph() {
        idToIndexMap.clear();
        int id = 1;
        for (int i = 0; i <= flatMap.length() - 1; i++) {
            final char symbol = flatMap.charAt(i);
            if (symbol == '#') {
                idToIndexMap.put(id, i);
                id++;
            }
        }
    }

    public Map<Integer, Integer> getIdToIndexMap() {
        return idToIndexMap;
    }

    public String represent(final Map<Integer, Integer> galaxyMap, final int lineSize) {
        final StringBuilder stringBuilder = new StringBuilder();
        final int maxIndex = galaxyMap.values().stream().mapToInt(Integer::valueOf).max().orElse(0);
        for (int i = 0; i <= maxIndex; i++) {
            if (i % lineSize == 0) {
                stringBuilder.append("\n");
            }

            final int currentIndex = i;
            final int tile = galaxyMap.values().stream().filter(value -> value == currentIndex).findFirst().orElse(-1);
            if (tile == -1) {
                stringBuilder.append(".");
            } else {
                stringBuilder.append("#");
            }
        }

        return stringBuilder.toString();
    }

    public Map<Integer, Integer> getExpandedIdToIndexMap(final int factor) {
        final List<Integer> emptyHorizontals = UniverseUtils.getEmptyHorizontalLineIndexes(flatMap, lineSize);
        final List<Integer> emptyVerticals = UniverseUtils.getEmptyVerticalLineIndexes(flatMap, lineSize);

        System.out.println("emptyHorizontals: " + emptyHorizontals);
        System.out.println("emptyVerticals: " + emptyVerticals);

        final Map<Integer, Integer> result = Maps.newHashMap();

        for (final Map.Entry<Integer, Integer> entry : idToIndexMap.entrySet()) {
            final int galaxyId = entry.getKey();
            final int galaxyIndex = entry.getValue();

            int baseVerticalIncrements = 0;
            for (int i = 0; i <= emptyVerticals.size() - 1; i++) {
                if (emptyVerticals.get(i) < mapToFirstLine(galaxyIndex)) {
                    baseVerticalIncrements += 1;
                }
            }

            final int lineWeAreIn = UniverseUtils.determineLineForIndexEfficiently(galaxyIndex, lineSize) - 1;

            int baseHorizontalIncrements = 0;
            for (int i = 0; i <= emptyHorizontals.size() - 1; i++) {
                if (emptyHorizontals.get(i) < lineWeAreIn) {
                    baseHorizontalIncrements += 1;
                }
            }

            final int toAddForVertical = baseVerticalIncrements * (factor - 1);
            final int toAddForHorizontal = baseHorizontalIncrements * (lineSize + emptyVerticals.size()) * (factor - 1);
            final int toAddForLineSizeChange = emptyVerticals.size() * (factor - 1) * lineWeAreIn;

            final int newGalaxyIndex = galaxyIndex + toAddForVertical + toAddForHorizontal + toAddForLineSizeChange;
            result.put(galaxyId, newGalaxyIndex);
        }

        return result;
    }

    private int mapToFirstLine(final int galaxyIndex) {
        final int line = UniverseUtils.determineLineForIndexEfficiently(galaxyIndex, lineSize);
        return galaxyIndex - lineSize * (line - 1);
    }

    public List<String> getVerticalMap() {
        return UniverseUtils.getVerticalMap(flatMap, lineSize);
    }
}
