package advent.galaxies;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Galaxy Test")
public class GalaxyTest {

    @DisplayName("Translation")
    @Nested
    class Translation {

        @DisplayName("when input contains no galaxies, return no coordinates")
        @Test
        void translationTest_1() {
            final String line = "..........";
            final List<String> input = List.of(line);

            final List<Coordinates> positions = GalaxyUtils.getCoordinatesFrom(input);

            assertThat(positions).isEmpty();
        }

        @DisplayName("when input contains a galaxy in first line at the beginning, return coordinates")
        @Test
        void translationTest_2() {
            final String line = "#.........";
            final List<String> input = List.of(line);

            final List<Coordinates> positions = GalaxyUtils.getCoordinatesFrom(input);

            assertThat(positions).isNotEmpty();
            assertThat(positions).hasSize(1);
            assertThat(positions.get(0)).isEqualTo(Coordinates.of(0, 0));
        }

        @DisplayName("when input contains a galaxy in first line at the end, return coordinates")
        @Test
        void translationTest_3() {
            final String line = ".........#";
            final List<String> input = List.of(line);

            final List<Coordinates> positions = GalaxyUtils.getCoordinatesFrom(input);

            assertThat(positions).isNotEmpty();
            assertThat(positions).hasSize(1);
            assertThat(positions.get(0)).isEqualTo(Coordinates.of(9, 0));
        }

        @DisplayName("when input contains several galaxies in first line, return coordinates")
        @Test
        void translationTest_4() {
            final String line = "..#...#...";
            final List<String> input = List.of(line);

            final List<Coordinates> positions = GalaxyUtils.getCoordinatesFrom(input);

            assertThat(positions).isNotEmpty();
            assertThat(positions).hasSize(2);
            assertThat(positions.get(0)).isEqualTo(Coordinates.of(2, 0));
            assertThat(positions.get(1)).isEqualTo(Coordinates.of(6, 0));
        }

        @DisplayName("when input contains several galaxies in several lines, return coordinates")
        @Test
        void translationTest_5() {
            final String line1 = "..#...#...";
            final String line2 = "....#....#";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> positions = GalaxyUtils.getCoordinatesFrom(input);

            assertThat(positions).isNotEmpty();
            assertThat(positions).hasSize(4);
            assertThat(positions.get(0)).isEqualTo(Coordinates.of(2, 0));
            assertThat(positions.get(1)).isEqualTo(Coordinates.of(6, 0));
            assertThat(positions.get(2)).isEqualTo(Coordinates.of(4, 1));
            assertThat(positions.get(3)).isEqualTo(Coordinates.of(9, 1));
        }

    }

    @DisplayName("Expansion")
    @Nested
    class Expansion {

        @DisplayName("when galaxy is in first position - no h expandable units")
        @Test
        void expansionTest_1() {
            final String line = "#.........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isZero();
        }

        @DisplayName("when galaxy is in second position and there is one line only - one h expandable unit")
        @Test
        void expansionTest_2() {
            final String line = ".#........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isOne();
        }

        @DisplayName("when galaxy is in second position and previous index is not expandable - no h expandable units")
        @Test
        void expansionTest_3() {
            final String line1 = ".#........";
            final String line2 = "#.........";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isZero();
        }

        @DisplayName("when galaxy is in second position and first position is h expandable - one h expandable unit")
        @Test
        void expansionTest_4() {
            final String line1 = ".#........";
            final String line2 = "..........";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isOne();
        }

        @DisplayName("when galaxy is in third position and first and second positions are h expandable - two h expandable units")
        @Test
        void expansionTest_5() {
            final String line1 = "..#.......";
            final String line2 = "..........";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isEqualTo(2L);
        }

        @DisplayName("when galaxy has no empty lines before - no v expandable units")
        @Test
        void expansionTest_6() {
            final String line = "#.........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            assertThat(hExpandableUnits).isZero();
        }

        @DisplayName("when galaxy has an empty line before - one v expandable units")
        @Test
        void expansionTest_7() {
            final String line1 = "..........";
            final String line2 = "#.........";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            assertThat(vExpandableUnits).isOne();
        }

        @DisplayName("when galaxy has empty lines before - several v expandable units")
        @Test
        void expansionTest_8() {
            final String line1 = "..........";
            final String line2 = "#.........";
            final String line3 = "..........";
            final String line4 = "#.........";
            final List<String> input = List.of(line1, line2, line3, line4);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(1);

            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            assertThat(vExpandableUnits).isEqualTo(2);
        }

        @DisplayName("when galaxy is in first position and expanded adding 1 - no changes")
        @Test
        void expansionTest_9() {
            final String line = "#.........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(coordinate).isEqualTo(expandedCoordinate);
        }

        @DisplayName("when galaxy is in second position and expanded adding 1 - h expand once")
        @Test
        void expansionTest_10() {
            final String line = ".#........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(2);
            assertThat(expandedCoordinate.getY()).isEqualTo(0);
        }

        @DisplayName("when galaxy is in third position and expanded adding 1 - h expand twice")
        @Test
        void expansionTest_11() {
            final String line = "..#........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(4);
            assertThat(expandedCoordinate.getY()).isEqualTo(0);
        }

        @DisplayName("when galaxy is in third position and expanded adding 1 - h expand thrice")
        @Test
        void expansionTest_12() {
            final String line = "...#........";
            final List<String> input = List.of(line);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(6);
            assertThat(expandedCoordinate.getY()).isEqualTo(0);
        }

        @DisplayName("when galaxy is in second line and expanded adding 1 - v expand once")
        @Test
        void expansionTest_13() {
            final String line1 = "..........";
            final String line2 = "#.........";
            final List<String> input = List.of(line1, line2);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(0);
            assertThat(expandedCoordinate.getY()).isEqualTo(2);
        }

        @DisplayName("when galaxy is in third line and expanded adding 1 - v expand twice")
        @Test
        void expansionTest_14() {
            final String line1 = "..........";
            final String line2 = "..........";
            final String line3 = "#.........";
            final List<String> input = List.of(line1, line2, line3);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(0);
            assertThat(expandedCoordinate.getY()).isEqualTo(4);
        }

        @DisplayName("when galaxy is in fourth line and expanded adding 1 - v expand thrice")
        @Test
        void expansionTest_15() {
            final String line1 = "..........";
            final String line2 = "..........";
            final String line3 = "..........";
            final String line4 = "#.........";
            final List<String> input = List.of(line1, line2, line3, line4);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(0);
            assertThat(expandedCoordinate.getY()).isEqualTo(6);
        }

        @DisplayName("combine expansions")
        @Test
        void expansionTest_16() {
            final String line1 = "..........";
            final String line2 = "..........";
            final String line3 = "..........";
            final String line4 = "..#.......";
            final List<String> input = List.of(line1, line2, line3, line4);

            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);
            final Coordinates coordinate = coordinates.get(0);

            final long hExpandableUnits = GalaxyUtils.getHExpandableUnits(coordinate, input);
            final long vExpandableUnits = GalaxyUtils.getVExpandableUnits(coordinate, input);

            final Coordinates expandedCoordinate = GalaxyUtils.expand(coordinate, 1, hExpandableUnits, vExpandableUnits);

            assertThat(expandedCoordinate.getX()).isEqualTo(4);
            assertThat(expandedCoordinate.getY()).isEqualTo(6);
        }

    }

    @DisplayName("Add Expansions")
    @Nested
    class AddExpansions {

        @DisplayName("test add expansions")
        @Test
        void testAddExpansions() {
            assertThat(GalaxyUtils.addExpansions(1, 1)).isEqualTo(2);
            assertThat(GalaxyUtils.addExpansions(1, 2)).isEqualTo(4);
            assertThat(GalaxyUtils.addExpansions(1, 3)).isEqualTo(6);
            assertThat(GalaxyUtils.addExpansions(1, 4)).isEqualTo(8);
            assertThat(GalaxyUtils.addExpansions(1, 5)).isEqualTo(10);

            assertThat(GalaxyUtils.addExpansions(2, 1)).isEqualTo(3);
            assertThat(GalaxyUtils.addExpansions(2, 2)).isEqualTo(6);
            assertThat(GalaxyUtils.addExpansions(2, 3)).isEqualTo(9);
            assertThat(GalaxyUtils.addExpansions(2, 4)).isEqualTo(12);
            assertThat(GalaxyUtils.addExpansions(2, 5)).isEqualTo(15);
        }

    }

    @DisplayName("Measure")
    @Nested
    class Measure {

        @DisplayName("when same coordinates - no distance")
        @Test
        void measureTest_1() {
            final Coordinates coordinate1 = Coordinates.of(0, 0);
            final Coordinates coordinate2 = Coordinates.of(0, 0);

            assertThat(GalaxyUtils.distance(coordinate1, coordinate2)).isZero();
        }

        @DisplayName("when separate by one horizontal - distance is 1")
        @Test
        void measureTest_2() {
            final Coordinates coordinate1 = Coordinates.of(0, 0);
            final Coordinates coordinate2 = Coordinates.of(1, 0);

            assertThat(GalaxyUtils.distance(coordinate1, coordinate2)).isOne();
        }

        @DisplayName("when separate by one vertical - distance is 1")
        @Test
        void measureTest_3() {
            final Coordinates coordinate1 = Coordinates.of(0, 0);
            final Coordinates coordinate2 = Coordinates.of(0, 1);

            assertThat(GalaxyUtils.distance(coordinate1, coordinate2)).isOne();
        }

        @DisplayName("when separate by one vertical and one horizontal - distance is 2")
        @Test
        void measureTest_4() {
            final Coordinates coordinate1 = Coordinates.of(0, 0);
            final Coordinates coordinate2 = Coordinates.of(1, 1);

            assertThat(GalaxyUtils.distance(coordinate1, coordinate2)).isEqualTo(2);
        }

        @DisplayName("test longer example")
        @Test
        void measureTest_5() {
            final Coordinates coordinate1 = Coordinates.of(5, 11);
            final Coordinates coordinate2 = Coordinates.of(1, 6);

            assertThat(GalaxyUtils.distance(coordinate1, coordinate2)).isEqualTo(9);
        }

    }

    @DisplayName("Solve")
    @Nested
    class Solve {

        @DisplayName("solve for abridged input")
        @Test
        void solveForAbridgedInput() {
            final List<String> input = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/galaxies/abridgedinput"));
            final int expansionAdds = 1;

            final long solution = solveFor(input, expansionAdds);
            assertThat(solution).isEqualTo(374L);
        }

        @DisplayName("solve expanded for abridged input")
        @Test
        void solveExpandedForAbridgedInput() {
            final List<String> input = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/galaxies/abridgedinput"));
            final int expansionAdds = 9;

            final long solution = solveFor(input, expansionAdds);
            assertThat(solution).isEqualTo(1030L);
        }

        @DisplayName("solve very expanded for abridged input")
        @Test
        void solveVeryExpandedForAbridgedInput() {
            final List<String> input = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/galaxies/abridgedinput"));
            final int expansionAdds = 99;

            final long solution = solveFor(input, expansionAdds);
            assertThat(solution).isEqualTo(8410L);
        }

        @DisplayName("solve initial problem")
        @Test
        void solveInitialProblem() {
            final List<String> input = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/galaxies/input"));
            final int expansionAdds = 1;

            final long solution = solveFor(input, expansionAdds);
            assertThat(solution).isEqualTo(9274989L);
        }

        @DisplayName("solve ridiculously expanded problem")
        @Test
        void solveRidiculouslyExpandedProblem() {
            final List<String> input = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/galaxies/input"));
            final int expansionAdds = 999_999;

            final long solution = solveFor(input, expansionAdds);
            assertThat(solution).isEqualTo(357134560737L);
        }

        private long solveFor(final List<String> input, final int expansionAdds) {
            final List<Coordinates> coordinates = GalaxyUtils.getCoordinatesFrom(input);

            final List<Galaxy> galaxies = Lists.newArrayList();

            int id = 1;
            for (final Coordinates coordinate : coordinates) {
                galaxies.add(new Galaxy(id, coordinate, input));
                id++;
            }

            final Map<Integer, Galaxy> galaxyMap = galaxies.stream().collect(Collectors.toMap(Galaxy::getGalaxyId, Function.identity()));
            final List<Pair<Integer, Integer>> distancePairs = GalaxyUtils.getGalaxyDistancePairs(galaxies.size());

            long distanceSum = 0L;
            for (final Pair<Integer, Integer> distancePair : distancePairs) {
                final Integer firstGalaxyId = distancePair.getKey();
                final Integer secondGalaxyId = distancePair.getValue();

                final Galaxy firstGalaxy = galaxyMap.get(firstGalaxyId);
                final Galaxy secondGalaxy = galaxyMap.get(secondGalaxyId);

                distanceSum += GalaxyUtils.distance(firstGalaxy.getExpandedCoordinatesAdding(expansionAdds), secondGalaxy.getExpandedCoordinatesAdding(expansionAdds));
            }

            System.out.println(distanceSum);
            return distanceSum;
        }

    }
}
