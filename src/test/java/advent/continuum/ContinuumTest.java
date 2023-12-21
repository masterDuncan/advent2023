package advent.continuum;

import advent.expansion.UniverseUtils;
import advent.utils.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Almost there, but it had elements from 'expansion', so it didn't work")
@DisplayName("Continuum Test")
public class ContinuumTest {

    @DisplayName("Continuum Expansion")
    @Nested
    class ContinuumExpansion {

        @DisplayName("when no increments - galaxy does not move")
        @Test
        void whenNoIncrements_galaxyDoesNotMove() {
            final String line1 = "#...";
            final String line2 = "....";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(1);

            assertThat(result.get(1)).isEqualTo(0);
        }

        @DisplayName("when one horizontal increment before galaxy - galaxy moves to the right")
        @Test
        void whenOneHorizontalIncrementBeforeGalaxy_galaxyMovesToTheLeft() {
            final String line1 = ".#..";
            final String line2 = "....";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(1);

            assertThat(result.get(1)).isEqualTo(2);
        }

        @DisplayName("when one horizontal increment of factor 2 before galaxy - galaxy moves to the right")
        @Test
        void whenOneHorizontalIncrementOfFactor2BeforeGalaxy_galaxyMovesToTheLeft() {
            final String line1 = ".#..";
            final String line2 = "....";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(2);

            assertThat(result.get(1)).isEqualTo(3);
        }

        @DisplayName("when one vertical increment before galaxy - galaxy moves down")
        @Test
        void whenOneVerticalIncrementBeforeGalaxy_galaxyMovesDown() {
            final String line1 = "....";
            final String line2 = "#...";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(1);

            assertThat(result.get(1)).isEqualTo(8);
        }

        @DisplayName("when one vertical increment of factor 2 before galaxy - galaxy moves down")
        @Test
        void whenOneVerticalIncrementOfFactor2BeforeGalaxy_galaxyMovesDown() {
            final String line1 = "....";
            final String line2 = "#...";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(2);

            assertThat(result.get(1)).isEqualTo(12);
        }

        @DisplayName("when one vertical increment and one horizontal increment before galaxy - galaxy moves diagonally")
        @Test
        void whenOneVerticalIncrementAndOneHorizontalIncrementBeforeGalaxy_galaxyMovesDiagonally() {
            final String line1 = "....";
            final String line2 = ".#..";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(1);

            assertThat(result.get(1)).isEqualTo(10);
        }

        @DisplayName("when one vertical increment and one horizontal increment of factor 2 before galaxy - galaxy moves diagonally")
        @Test
        void whenOneVerticalIncrementAndOneHorizontalIncrementOfFactor2BeforeGalaxy_galaxyMovesDiagonally() {
            final String line1 = "....";
            final String line2 = ".#..";
            final String line3 = "....";
            final List<String> lines = List.of(line1, line2, line3);

            final Continuum continuum = new Continuum(lines);
            final Map<Integer, Integer> result = continuum.getExpandedIdToIndexMap(2);

            assertThat(result.get(1)).isEqualTo(15);
        }

        @DisplayName("solve expanded galaxy distance problem using just indexes")
        @Test
        void solveExpandedGalaxyDistanceProblemUsingJustIndexes() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/continuum/abridgedinput"));
            final Continuum continuum = new Continuum(lines);

            System.out.println(continuum.represent(continuum.getIdToIndexMap(), lines.get(0).length()));

            final Map<Integer, Integer> expandedMap = continuum.getExpandedIdToIndexMap(3);

            final List<String> verticalMap = continuum.getVerticalMap();
            final List<Integer> emptyVerticalLineIndexes = UniverseUtils.getEmptyLineIndexes(verticalMap);
            final int verticalExpansion = emptyVerticalLineIndexes.size();
            final int newLineSize = lines.get(0).length() + verticalExpansion;

            System.out.println();
            System.out.println(continuum.represent(expandedMap, newLineSize));

            long allDistances = 0;
            final List<Pair<Integer, Integer>> galaxyIdPairs = UniverseUtils.getGalaxyPairs(expandedMap);
            for (final Pair<Integer, Integer> pair : galaxyIdPairs) {
                final int indexA = expandedMap.get(pair.getKey());
                final int indexB = expandedMap.get(pair.getValue());
                allDistances += UniverseUtils.calculateDistance(indexA, indexB, newLineSize);
            }

            System.out.println(allDistances);
        }

        @DisplayName("solve expanded galaxy distance problem using just indexes 2")
        @Test
        void solveExpandedGalaxyDistanceProblemUsingJustIndexes2() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/continuum/abridgedinput"));
            final Continuum2D continuum = new Continuum2D(lines);

            final Map<Integer, Position> expandedMap = continuum.getExpandedIdToIndexMap(2);

            long allDistances = 0;
            final List<Pair<Integer, Integer>> galaxyIdPairs = UniverseUtils.getGalaxyPairs(expandedMap.keySet().stream().toList());
            for (final Pair<Integer, Integer> pair : galaxyIdPairs) {
                final Position positionA = expandedMap.get(pair.getKey());
                final Position positionB = expandedMap.get(pair.getValue());
                allDistances += UniverseUtils.calculateDistanceFromCoordinates(positionA, positionB);
            }

            System.out.println(allDistances);
        }
    }

}
