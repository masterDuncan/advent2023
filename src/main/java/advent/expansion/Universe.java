package advent.expansion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class Universe {

    private final String flatMap;
    private final int lineSize;
    private final Map<Integer, Integer> idToIndexMap = Maps.newLinkedHashMap();

    public Universe(final List<String> input) {
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

    public Map<Integer, Integer> getGalaxyMap() {
        return idToIndexMap;
    }

    public List<Integer> calculateDistances() {
        return UniverseUtils.calculateDistances(idToIndexMap, lineSize);
    }

    List<Pair<Integer, Integer>> getGalaxyPairs() {
        return UniverseUtils.getGalaxyPairs(idToIndexMap);
    }

    public final Map<Integer, Integer> getExpandedGalaxyMap(final int factor) {
        final Map<Integer, Integer> result = Maps.newHashMap();

        final List<String> horizontalMap = getHorizontalMap();
        final List<String> verticalMap = getVerticalMap();

        final List<Integer> emptyHorizontalLineIndexes = UniverseUtils.getEmptyLineIndexes(horizontalMap);
        final List<Integer> emptyVerticalLineIndexes = UniverseUtils.getEmptyLineIndexes(verticalMap);
        final int verticalExpansion = emptyVerticalLineIndexes.size() * factor;
        final int newLineSize = lineSize + verticalExpansion;

        for (final Map.Entry<Integer, Integer> entry : idToIndexMap.entrySet()) {
            final int galaxyId = entry.getKey();
            final int galaxyIndex = entry.getValue();

            final int verticalIncrements = countVerticalIncrements(galaxyIndex, emptyVerticalLineIndexes);
            final int toAddFromVertical = verticalIncrements * factor;

            final int horizontalIncrements = countHorizontalIncrements(galaxyIndex, emptyHorizontalLineIndexes);
            final int line = UniverseUtils.determineLineForIndex(galaxyIndex, lineSize) - 1;
            final int toAddFromHorizontal = horizontalIncrements == 0 ? 0 : newLineSize * (line + horizontalIncrements);

            final int mappedToFirstLine = mapToFirstLine(galaxyIndex);
            final int newGalaxyIndex = mappedToFirstLine + toAddFromVertical + toAddFromHorizontal;

            result.put(galaxyId, newGalaxyIndex);
        }

        return result;
    }

    private int countVerticalIncrements(final int galaxyIndex, final List<Integer> emptyVerticalLineIndexes) {
        final int mappedToFirstLine = mapToFirstLine(galaxyIndex);

        int increment = 0;
        for (final int emptyIndex : emptyVerticalLineIndexes) {
            if (mappedToFirstLine > emptyIndex) {
                increment++;
            }
        }

        return increment;
    }

    private int mapToFirstLine(final int galaxyIndex) {
        final int line = UniverseUtils.determineLineForIndex(galaxyIndex, lineSize);
        return galaxyIndex - lineSize * (line - 1);
    }

    private int countHorizontalIncrements(final int galaxyIndex, final List<Integer> emptyHorizontalLineIndexes) {
        final int line = UniverseUtils.determineLineForIndex(galaxyIndex, lineSize) - 1;

        int increment = 0;
        for (final int emptyIndex : emptyHorizontalLineIndexes) {
            if (line > emptyIndex) {
                increment++;
            }
        }

        return increment;
    }

    public Universe createExpanded() {
        return createExpanded(1);
    }

    public Universe createExpanded(final int factor) {
        final List<String> horizontalMap = getHorizontalMap();
        final List<String> verticalMap = getVerticalMap();

        final List<Integer> emptyHorizontalLineIndexes = UniverseUtils.getEmptyHorizontalLineIndexes(flatMap, lineSize);
        final List<Integer> emptyVerticalLineIndexes = UniverseUtils.getEmptyVerticalLineIndexes(flatMap, lineSize);

        final List<String> horizontalInserted = insertHorizontal(factor, horizontalMap, emptyHorizontalLineIndexes);
        final List<String> verticalInserted = insertVertical(factor, horizontalInserted, emptyVerticalLineIndexes);

        return new Universe(verticalInserted);
    }

    private List<String> insertHorizontal(final int factor, final List<String> horizontalMap, final List<Integer> emptyHorizontalLineIndexes) {
        final List<String> result = Lists.newArrayList();

        for (int i = 0; i <= horizontalMap.size() - 1; i++) {
            final String line = horizontalMap.get(i);
            result.add(line);
            if (emptyHorizontalLineIndexes.contains(i)) {
                for (int j = 1; j <= factor - 1; j++) {
                    result.add(line);
                }
            }
        }

        return result;
    }

    private List<String> insertVertical(final int factor, final List<String> horizontalInserted, final List<Integer> emptyVerticalLineIndexes) {
        final List<String> result = Lists.newArrayList();

        for (int i = 0; i <= horizontalInserted.size() - 1; i++) {

            final String line = horizontalInserted.get(i);
            final StringBuilder stringBuilder = new StringBuilder();
            for (int c = 0; c <= line.length() - 1; c++) {
                final char character = line.charAt(c);
                stringBuilder.append(character);
                if (emptyVerticalLineIndexes.contains(c)) {
                    for (int j = 1; j <= factor - 1; j++) {
                        stringBuilder.append(character);
                    }
                }
            }

            result.add(stringBuilder.toString());
        }

        return result;
    }

    List<String> getHorizontalMap() {
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

    List<String> getVerticalMap() {
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

    @Override
    public String toString() {
        return "Universe{" +
                "flatMap='" + flatMap + '\'' +
                ", lineSize=" + lineSize +
                ", idToIndexMap=" + idToIndexMap +
                '}';
    }
}
