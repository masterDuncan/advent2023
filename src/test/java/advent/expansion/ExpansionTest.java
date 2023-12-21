package advent.expansion;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Was a nice idea, but it didn't work")
@DisplayName("Expansion Test")
public class ExpansionTest {

    @DisplayName("Cartograph universe")
    @Nested
    class CartographUniverse {

        @DisplayName("when universe has no size - no galaxies")
        @Test
        void whenUniverseHasNoSize_noGalaxies() {
            final List<String> lines = Collections.emptyList();
            final Universe universe = new Universe(lines);
            assertThat(universe.getGalaxyMap()).isEmpty();
            ;
        }

        @DisplayName("when universe is empty - no galaxies")
        @Test
        void whenUniverseIsEmpty_noGalaxies() {
            final String line = ".........";
            final List<String> lines = List.of(line);
            final Universe universe = new Universe(lines);
            assertThat(universe.getGalaxyMap()).isEmpty();
            ;
        }

        @DisplayName("when universe not is empty - cartograph")
        @Test
        void whenUniverseIsNotEmpty_cartograph() {
            final String line = "....#....";
            final List<String> lines = List.of(line);
            final Universe universe = new Universe(lines);

            assertThat(universe.getGalaxyMap()).hasSize(1);
            assertThat(universe.getGalaxyMap()).containsKey(1);
            assertThat(universe.getGalaxyMap()).containsValue(4);
        }
    }

    @DisplayName("Calculate maps")
    @Nested
    class CalculateMaps {

        @DisplayName("when there are several lines - horizontal map has those lines")
        @Test
        void whenThereAreSeveralLines_horizontalMapHasThoseLines() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final List<String> horizontalMap = universe.getHorizontalMap();
            assertThat(horizontalMap).isEqualTo(lines);
        }

        @DisplayName("when there are several lines - vertical map has vertical lines")
        @Test
        void whenThereAreSeveralLines_verticalMapHasVerticalLines() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final String vLine1 = "..#";
            final String vLine2 = "...";
            final String vLine3 = "...";
            final String vLine4 = "#..";
            final String vLine5 = "...";
            final String vLine6 = "...";
            final String vLine7 = "...";
            final String vLine8 = ".#.";
            final String vLine9 = "...";
            final String vLine10 = "...";
            final List<String> vLines = List.of(vLine1, vLine2, vLine3, vLine4, vLine5, vLine6, vLine7, vLine8, vLine9, vLine10);

            final List<String> verticalMap = universe.getVerticalMap();
            assertThat(verticalMap).isEqualTo(vLines);
        }

    }

    @DisplayName("Expand universe")
    @Nested
    class ExpandUniverse {

        @DisplayName("when expanding universe - create expanded universe")
        @Test
        void whenExpandingUniverse_createExpandedUniverse() {
            final String line1 = "..#...";
            final String line2 = "......";
            final String line3 = "....#.";
            final String line4 = "#.....";
            final List<String> lines = List.of(line1, line2, line3, line4);
            final Universe universe = new Universe(lines);

            final Universe expandedUniverse = universe.createExpanded();
            final List<String> horizontalMap = expandedUniverse.getHorizontalMap();

            assertThat(horizontalMap.get(0)).isEqualTo("...#.....");
            assertThat(horizontalMap.get(1)).isEqualTo(".........");
            assertThat(horizontalMap.get(2)).isEqualTo(".........");
            assertThat(horizontalMap.get(3)).isEqualTo("......#..");
            assertThat(horizontalMap.get(4)).isEqualTo("#........");
        }

        @DisplayName("when expanding universe vertically - create expanded universe")
        @Test
        void whenExpandingUniverseVertically_createExpandedUniverse() {
            final String line1 = "..#...";
            final String line2 = "......";
            final String line3 = "....#.";
            final String line4 = "......";
            final String line5 = "#.....";
            final List<String> lines = List.of(line1, line2, line3, line4, line5);
            final Universe universe = new Universe(lines);

            final Universe expandedUniverse = universe.createExpanded();

            final List<String> horizontalMap = expandedUniverse.getHorizontalMap();
            for (final String line : horizontalMap) {
                System.out.println(line);
            }

            assertThat(horizontalMap.get(0)).isEqualTo("...#.....");
            assertThat(horizontalMap.get(1)).isEqualTo(".........");
            assertThat(horizontalMap.get(2)).isEqualTo(".........");
            assertThat(horizontalMap.get(3)).isEqualTo("......#..");
            assertThat(horizontalMap.get(4)).isEqualTo(".........");
            assertThat(horizontalMap.get(5)).isEqualTo(".........");
            assertThat(horizontalMap.get(6)).isEqualTo("#........");
        }

    }

    @DisplayName("calculate distances")
    @Nested
    class CalculateDistances {

        @DisplayName("when galaxies in same place - distance is zero")
        @Test
        void whenGalaxiesInSamePlace_distanceIsZero() {
            final String line1 = "..#......s";
            final List<String> lines = List.of(line1);
            final Universe universe = new Universe(lines);

            final int distance = UniverseUtils.calculateDistance(2, 2, 9);
            assertThat(distance).isZero();
        }

        @DisplayName("when galaxies in same line - calculate distance")
        @Test
        void whenGalaxiesInSameLine_calculateDistance() {
            final String line1 = "..#...#..";
            final List<String> lines = List.of(line1);
            final Universe universe = new Universe(lines);

            final int distance = UniverseUtils.calculateDistance(2, 6, 9);
            assertThat(distance).isEqualTo(4);
        }

        @DisplayName("when galaxies in same vertical - calculate distance")
        @Test
        void whenGalaxiesInSameVertical_calculateDistance() {
            final String line1 = "..#.......";
            final String line2 = "..........";
            final String line3 = "..........";
            final String line4 = "..#.......";
            final List<String> lines = List.of(line1, line2, line3, line4);
            final Universe universe = new Universe(lines);

            final int distance = UniverseUtils.calculateDistance(2, 32, 10);
            assertThat(distance).isEqualTo(3);
        }

        @DisplayName("when galaxies in same diagonal - calculate distance")
        @Test
        void whenGalaxiesInSameDiagonal_calculateDistance() {
            final String line1 = "..#.......";
            final String line2 = "..........";
            final String line3 = "..........";
            final String line4 = ".....#....";
            final List<String> lines = List.of(line1, line2, line3, line4);
            final Universe universe = new Universe(lines);

            final int distance = UniverseUtils.calculateDistance(2, 35, 10);
            assertThat(distance).isEqualTo(6);
        }

        @DisplayName("when galaxies not in diagonal - calculate distance")
        @Test
        void whenGalaxiesNotInDiagonal_calculateDistance() {
            final String line1 = "..#.......";
            final String line2 = "..........";
            final String line3 = "..........";
            final String line4 = "........#.";
            final List<String> lines = List.of(line1, line2, line3, line4);
            final Universe universe = new Universe(lines);

            final int distance = UniverseUtils.calculateDistance(2, 38, 10);
            assertThat(distance).isEqualTo(9);
        }

        @DisplayName("when we have several galaxies - calculate distances")
        @Test
        void whenWeHaveSeveralGalaxies_calculateDistances() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final List<Integer> distances = universe.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            assertThat(sum).isEqualTo(5 + 5 + 8);
        }

        @DisplayName("when we have several galaxies - calculate example distances")
        @Test
        void whenWeHaveSeveralGalaxies_calculateExampleDistances() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final String line4 = "..........";
            final String line5 = "......#...";
            final String line6 = ".#........";
            final String line7 = ".........#";
            final String line8 = "..........";
            final String line9 = ".......#..";
            final String line10 = "#...#.....";

            final List<String> lines = List.of(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10);
            final Universe universe = new Universe(lines);
            final Universe expandedUniverse = universe.createExpanded();

            final List<String> horizontalMap = expandedUniverse.getHorizontalMap();
            for (final String line : horizontalMap) {
                System.out.println(line);
            }

            final List<Integer> distances = expandedUniverse.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            assertThat(sum).isEqualTo(374);
        }

        @DisplayName("when we have several galaxies - calculate expanded example distances")
        @Test
        void whenWeHaveSeveralGalaxies_calculateExpandedExampleDistances() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final String line4 = "..........";
            final String line5 = "......#...";
            final String line6 = ".#........";
            final String line7 = ".........#";
            final String line8 = "..........";
            final String line9 = ".......#..";
            final String line10 = "#...#.....";

            final List<String> lines = List.of(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10);
            final Universe universe = new Universe(lines);
            final Universe expandedUniverse = universe.createExpanded(10);

            final List<Integer> distances = expandedUniverse.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            assertThat(sum).isEqualTo(1030);
        }

        @DisplayName("when we have several galaxies - calculate very expanded example distances")
        @Test
        void whenWeHaveSeveralGalaxies_calculateVeryExpandedExampleDistances() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final String line4 = "..........";
            final String line5 = "......#...";
            final String line6 = ".#........";
            final String line7 = ".........#";
            final String line8 = "..........";
            final String line9 = ".......#..";
            final String line10 = "#...#.....";

            final List<String> lines = List.of(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10);
            final Universe universe = new Universe(lines);
            final Universe expandedUniverse = universe.createExpanded(100);

            final List<Integer> distances = expandedUniverse.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            assertThat(sum).isEqualTo(8410);

            final List<String> verticalMap = universe.getVerticalMap();
            final List<Integer> emptyVerticalLineIndexes = UniverseUtils.getEmptyLineIndexes(verticalMap);
            final int verticalExpansion = emptyVerticalLineIndexes.size() * 100;
            final int newLineSize = lines.get(0).length() + verticalExpansion;

            final Map<Integer, Integer> expandedMap = universe.getExpandedGalaxyMap(100);

            final List<Integer> distances2 = Lists.newArrayList();
            final List<Pair<Integer, Integer>> galaxyIdPairs = UniverseUtils.getGalaxyPairs(expandedMap);
            for (final Pair<Integer, Integer> pair : galaxyIdPairs) {
                final int indexA = expandedMap.get(pair.getKey());
                final int indexB = expandedMap.get(pair.getValue());
                distances2.add(UniverseUtils.calculateDistance(indexA, indexB, newLineSize));
            }

            final long sum2 = distances2.stream().mapToInt(Integer::valueOf).sum();

            assertThat(sum2).isEqualTo(8410);
        }

        @DisplayName("solve expanded galaxy distance problem")
        @Test
        void solveExpandedGalaxyDistanceProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/expansion/input"));
            final Universe universe = new Universe(lines);
            final Universe expandedUniverse = universe.createExpanded();

            final List<Integer> distances = expandedUniverse.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            System.out.println(sum);
        }

        @DisplayName("solve expanded galaxy distance problem using just indexes")
        @Test
        void solveExpandedGalaxyDistanceProblemUsingJustIndexes() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/expansion/input"));
            final Universe universe = new Universe(lines);
            final Map<Integer, Integer> expandedMap = universe.getExpandedGalaxyMap(1);

            final List<String> verticalMap = universe.getVerticalMap();
            final List<Integer> emptyVerticalLineIndexes = UniverseUtils.getEmptyLineIndexes(verticalMap);
            final int verticalExpansion = emptyVerticalLineIndexes.size();
            final int newLineSize = lines.get(0).length() + verticalExpansion;

            final List<Integer> distances = Lists.newArrayList();
            final List<Pair<Integer, Integer>> galaxyIdPairs = UniverseUtils.getGalaxyPairs(expandedMap);
            for (final Pair<Integer, Integer> pair : galaxyIdPairs) {
                final int indexA = expandedMap.get(pair.getKey());
                final int indexB = expandedMap.get(pair.getValue());
                distances.add(UniverseUtils.calculateDistance(indexA, indexB, newLineSize));
            }

            final long sum = distances.stream().mapToInt(Integer::valueOf).sum();

            System.out.println(sum);
        }

        @Disabled("Will blow up the heap")
        @DisplayName("solve greatly expanded galaxy distance problem")
        @Test
        void solveGreatlyExpandedGalaxyDistanceProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/expansion/input"));
            final Universe universe = new Universe(lines);
            final Universe expandedUniverse = universe.createExpanded(1_000_000);

            final List<Integer> distances = expandedUniverse.calculateDistances();
            final int sum = distances.stream().mapToInt(Integer::valueOf).sum();

            System.out.println(sum);
        }
    }

    @DisplayName("Calculate pairs")
    @Nested
    class CalculatePairs {

        @DisplayName("when we have several galaxies - calculate pairs")
        @Test
        void whenWeHaveSeveralGalaxies_calculatePairs() {
            final String line1 = "...#......";
            final String line2 = ".......#..";
            final String line3 = "#.........";
            final String line4 = ".....#....";
            final List<String> lines = List.of(line1, line2, line3, line4);
            final Universe universe = new Universe(lines);

            final List<Pair<Integer, Integer>> pairs = universe.getGalaxyPairs();
            System.out.println(pairs);
        }

    }

    @DisplayName("Expand galaxy map")
    @Nested
    class ExpandGalaxyMap {

        @DisplayName("when expanding space to the right of galaxy - index does not move")
        @Test
        void whenExpandingSpaceToTheRightOfGalaxy_indexDoesNotMove() {
            final String line1 = "#.";
            final String line2 = "..";
            final List<String> lines = List.of(line1, line2);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // #..
            // ...
            // ...
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(0);
        }

        @DisplayName("when expanding space to the left of galaxy - index increments")
        @Test
        void whenExpandingSpaceToTheLeftOfGalaxy_indexIncrements() {
            final String line1 = ".#";
            final String line2 = "..";
            final List<String> lines = List.of(line1, line2);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // ..#
            // ...
            // ...
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(2);
        }

        @DisplayName("when expanding space twice to the left of galaxy - index increments")
        @Test
        void whenExpandingSpaceTwiceToTheLeftOfGalaxy_indexIncrements() {
            final String line1 = "..#";
            final String line2 = "...";
            final String line3 = "...";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // ....#
            // .....
            // .....
            // .....
            // .....
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(4);
        }

        @DisplayName("when expanding space up the galaxy - index increments to next line")
        @Test
        void whenExpandingSpaceUpTheGalaxy_indexIncrementsToTheNextLine() {
            final String line1 = "...";
            final String line2 = "#..";
            final String line3 = "...";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // .....
            // .....
            // #....
            // .....
            // .....
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(10);
        }

        @DisplayName("when expanding space up a galaxy at the end - index increments to next lines")
        @Test
        void whenExpandingSpaceUpAGalaxyAtTheEnd_indexIncrementsToTheNextLines() {
            final String line1 = "...";
            final String line2 = "...";
            final String line3 = "#..";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // .....
            // .....
            // .....
            // .....
            // #....
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(20);
        }

        @DisplayName("when expanding space to the left of galaxy - second line - index increments")
        @Test
        void whenExpandingSpaceToTheLeftOfGalaxy_secondLine_indexIncrements() {
            final String line1 = "..";
            final String line2 = ".#";
            final List<String> lines = List.of(line1, line2);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // ...
            // ...
            // ..#
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(8);
        }

        @DisplayName("when expanding space to the left of galaxy - second line - index increments 2")
        @Test
        void whenExpandingSpaceToTheLeftOfGalaxy_secondLine_indexIncrements_2() {
            final String line1 = "...";
            final String line2 = "...";
            final String line3 = "..#";
            final List<String> lines = List.of(line1, line2, line3);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> galaxyMap = universe.getExpandedGalaxyMap(1);

            // .....
            // .....
            // .....
            // .....
            // ....#
            assertThat(galaxyMap).hasSize(1);
            assertThat(galaxyMap.get(1)).isEqualTo(24);
        }

        @DisplayName("when expanding universe vertically - expand the indexes")
        @Test
        void whenExpandingUniverseVertically_createExpandedUniverse() {
            final String line1 = "..#...";
            final String line2 = "......";
            final String line3 = "....#.";
            final String line4 = "......";
            final String line5 = "#.....";
            final List<String> lines = List.of(line1, line2, line3, line4, line5);
            final Universe universe = new Universe(lines);

            final Map<Integer, Integer> expandedMap = universe.getExpandedGalaxyMap(1);
            assertThat(expandedMap.get(1)).isEqualTo(3);
            assertThat(expandedMap.get(2)).isEqualTo(33);
            assertThat(expandedMap.get(3)).isEqualTo(54);
        }

    }

}
