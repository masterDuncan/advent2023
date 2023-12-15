package advent.pipes;

import advent.utils.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pipe test")
public class PipesTest {

    @DisplayName("Assert connectivity")
    @Nested
    class AssertConnectivity {

        @DisplayName("is vertical is connected to vertical by north - is true")
        @Test
        void isVerticalIsConnectedToVerticalByNorth_isTrue() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.NORTH, Pipe.fromRepresentation("|"));

            assertThat(connected).isTrue();
        }

        @DisplayName("is vertical is connected to vertical by east - is false")
        @Test
        void isVerticalIsConnectedToVerticalByEast_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.EAST, Pipe.fromRepresentation("|"));

            assertThat(connected).isFalse();
        }

        @DisplayName("vertical is connected to vertical by south - is true")
        @Test
        void isVerticalIsConnectedToVerticalBySouth_isTrue() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.SOUTH, Pipe.fromRepresentation("|"));

            assertThat(connected).isTrue();
        }

        @DisplayName("is vertical is connected to vertical by west - is false")
        @Test
        void isVerticalIsConnectedToVerticalByWest_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.WEST, Pipe.fromRepresentation("|"));

            assertThat(connected).isFalse();
        }

        @DisplayName("is vertical is connected to horizontal by north - is false")
        @Test
        void isVerticalIsConnectedToHorizontalByNorth_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.NORTH, Pipe.fromRepresentation("-"));

            assertThat(connected).isFalse();
        }

        @DisplayName("is vertical is connected to horizontal by east - is false")
        @Test
        void isVerticalIsConnectedToHorizontalByEast_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.EAST, Pipe.fromRepresentation("-"));

            assertThat(connected).isFalse();
        }

        @DisplayName("vertical is connected to horizontal by south - is false")
        @Test
        void isVerticalIsConnectedToHorizontalBySouth_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.SOUTH, Pipe.fromRepresentation("-"));

            assertThat(connected).isFalse();
        }

        @DisplayName("is vertical is connected to horizontal by west - is false")
        @Test
        void isVerticalIsConnectedToHorizontalByWest_isFalse() {
            final Pipe pipe = Pipe.fromRepresentation("|");
            final boolean connected = pipe.isConnectedFrom(Connection.WEST, Pipe.fromRepresentation("-"));

            assertThat(connected).isFalse();
        }

        @DisplayName("follow pipe")
        @Test
        void followPipe() {
            final String line1 = ".....";
            final String line2 = ".S-7.";
            final String line3 = ".|.|.";
            final String line4 = ".L-J.";
            final String line5 = ".....";

            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4, line5));
            final int loopSteps = pipeMap.calculateLoopSteps();

            assertThat(loopSteps).isEqualTo(8);
        }

        @DisplayName("follow complex pipe")
        @Test
        void followComplexPipe() {
            final String line1 = "..F7.";
            final String line2 = ".FJ|.";
            final String line3 = "SJ.L7";
            final String line4 = "|F--J";
            final String line5 = "LJ...";

            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4, line5));
            final int loopSteps = pipeMap.calculateLoopSteps();

            assertThat(loopSteps).isEqualTo(16);
        }

        @DisplayName("solve pipe loop problem")
        @Test
        void solvePipeLoopProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/pipes/input"));
            final PipeMap pipeMap = new PipeMap(lines);
            final int loopSteps = pipeMap.calculateLoopSteps();

            assertThat(loopSteps % 2).isZero();
            System.out.println(loopSteps);
        }
    }

    @DisplayName("Determine line from index")
    @Nested
    class DetermineLineFromIndex {

        @DisplayName("when there is one line - first index - determine first line")
        @Test
        void whenThereIsOneLine_firstIndex_determineFirstLine() {
            final String line1 = "S.........";
            final PipeMap pipeMap = new PipeMap(List.of(line1));

            final int line = pipeMap.determineLineForIndex(0);
            assertThat(line).isOne();
        }

        @DisplayName("when there is one line - determine first line")
        @Test
        void whenThereIsOneLine_determineFirstLine() {
            final String line1 = "...S......";
            final PipeMap pipeMap = new PipeMap(List.of(line1));

            final int line = pipeMap.determineLineForIndex(3);
            assertThat(line).isOne();
        }

        @DisplayName("when there is one line - last line - determine first line")
        @Test
        void whenThereIsOneLine_lastLine_determineFirstLine() {
            final String line1 = ".........S";
            final PipeMap pipeMap = new PipeMap(List.of(line1));

            final int line = pipeMap.determineLineForIndex(9);
            assertThat(line).isOne();
        }

        @DisplayName("when there are two lines - index in first line - determine first line")
        @Test
        void whenThereAreTwoLines_indexInFirstLine_determineFirstLine() {
            final String line1 = "...S......";
            final String line2 = "..........";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final int line = pipeMap.determineLineForIndex(3);
            assertThat(line).isOne();
        }

        @DisplayName("when there are two lines - last index in first line - determine first line")
        @Test
        void whenThereAreTwoLines_lastIndexInFirstLine_determineFirstLine() {
            final String line1 = ".........S";
            final String line2 = "..........";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final int line = pipeMap.determineLineForIndex(9);
            assertThat(line).isOne();
        }

        @DisplayName("when there are two lines - first index in second line - determine second line")
        @Test
        void whenThereAreTwoLines_firstIndexInSecondLine_determineSecondLine() {
            final String line1 = "..........";
            final String line2 = "S.........";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final int line = pipeMap.determineLineForIndex(10);
            assertThat(line).isEqualTo(2);

        }

        @DisplayName("when there are two lines - index in second line - determine second line")
        @Test
        void whenThereAreTwoLines_indexInSecondLine_determineSecondLine() {
            final String line1 = "..........";
            final String line2 = "...S......";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final int line = pipeMap.determineLineForIndex(13);
            assertThat(line).isEqualTo(2);
        }

        @DisplayName("when there are two lines - last index in second line - determine second line")
        @Test
        void whenThereAreTwoLines_lastIndexInSecondLine_determineSecondLine() {
            final String line1 = "..........";
            final String line2 = ".........S";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final int line = pipeMap.determineLineForIndex(19);
            assertThat(line).isEqualTo(2);
        }

        @DisplayName("when there are three lines - first index in third line - determine third line")
        @Test
        void whenThereAreTwoLines_firstIndexInThirdLine_determineThirdLine() {
            final String line1 = "..........";
            final String line2 = "..........";
            final String line3 = "S.........";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3));

            final int line = pipeMap.determineLineForIndex(20);
            assertThat(line).isEqualTo(3);
        }

    }

    @Disabled("Wrong solution")
    @DisplayName("Count tiles inside loop")
    @Nested
    class CountTilesInsideLoop {

        @DisplayName("test retrieve pipe indexes")
        @Test
        void testRetrievePipeIndexes() {
            final String line1 = "..........";
            final String line2 = "..S----7..";
            final String line3 = "..L----J..";
            final String line4 = "..........";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4));

            final List<Integer> expectedIndexes = List.of(12, 13, 14, 15, 16, 17, 27, 26, 25, 24, 23, 22);
            final List<Integer> indexes = pipeMap.getLoopStepIndexes();

            System.out.println(indexes);
            assertThat(indexes).isEqualTo(expectedIndexes);
        }

        @DisplayName("when there are no inside tiles - count is zero")
        @Test
        void whenThereAreNoInsideTiles_countIsZero() {
            final String line1 = "S7";
            final String line2 = "LJ";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2));

            final List<Integer> result = pipeMap.getTileIndexesInsideLoop();

            assertThat(result).isEmpty();
        }

        @DisplayName("when there are one inside tile - count is one")
        @Test
        void whenThereAreOneInsideTile_countIsOne() {
            final String line1 = "S-7";
            final String line2 = "|.|";
            final String line3 = "L-J";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3));

            final List<Integer> result = pipeMap.getTileIndexesInsideLoop();

            assertThat(result).hasSize(1);
            assertThat(result).contains(4);
        }

        @DisplayName("when there are one inside tile - there are outside tiles - count is one")
        @Test
        void whenThereAreOneInsideTile_thereAreOutsideTiles_countIsOne() {
            final String line1 = ".....";
            final String line2 = ".S-7.";
            final String line3 = ".|.|.";
            final String line4 = ".L-J.";
            final String line5 = ".....";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4, line5));

            final List<Integer> result = pipeMap.getTileIndexesInsideLoop();

            System.out.println(pipeMap.getPipeMapRepresentation());

            assertThat(result).hasSize(1);
            assertThat(result).contains(12);
        }

        @DisplayName("test big problem")
        @Test
        void testBigProblem() {
            final String line1 = "FF7FSF7F7F7F7F7F---7";
            final String line2 = "L|LJ||||||||||||F--J";
            final String line3 = "FL-7LJLJ||||||LJL-77";
            final String line4 = "F--JF--7||LJLJ7F7FJ-";
            final String line5 = "L---JF-JLJ.||-FJLJJ7";
            final String line6 = "|F|F-JF---7F7-L7L|7|";
            final String line7 = "|FFJF7L7F-JF7|JL---7";
            final String line8 = "7-L-JL7||F7|L7F-7F7|";
            final String line9 = "L.L7LFJ|||||FJL7||LJ";
            final String line10 = "L7JLJL-JLJLJL--JLJ.L";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4, line5, line6, line7, line8, line9, line10));

            final List<Integer> result = pipeMap.getTileIndexesInsideLoop();
            System.out.println(pipeMap.getPipeMapRepresentation());

            assertThat(result).hasSize(10);
        }

        @DisplayName("solve pipe loop enclosure problem")
        @Test
        void solvePipeLoopEnclosureProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/pipes/input"));
            final PipeMap pipeMap = new PipeMap(lines);
            final List<Integer> enclosedTiles = pipeMap.getTileIndexesInsideLoop();

            System.out.println(pipeMap.getPipeMapRepresentation());
            System.out.println(enclosedTiles.size());
        }
    }

    @DisplayName("Concave Convex")
    @Nested
    class ConcaveConvex {

        @DisplayName("when")
        @Test
        void when() {
            final String line1 = ".....";
            final String line2 = ".F-7.";
            final String line3 = ".|.|.";
            final String line4 = ".L-S.";
            final String line5 = ".....";
            final PipeMap pipeMap = new PipeMap(List.of(line1, line2, line3, line4, line5));

//            final List<Tile> tiles = pipeMap.getTilesFromLoopIndexes();
//            System.out.println(tiles);
//
//            System.out.println(tiles.get(1).getOrientationFor(Connection.NORTH));
//            System.out.println(tiles.get(3).getOrientationFor(Connection.WEST));
//            System.out.println(tiles.get(5).getOrientationFor(Connection.SOUTH));
//            System.out.println(tiles.get(7).getOrientationFor(Connection.EAST));
//
//            System.out.println();
//
//            System.out.println(tiles.get(1).getOrientationFor(Connection.SOUTH));
//            System.out.println(tiles.get(3).getOrientationFor(Connection.EAST));
//            System.out.println(tiles.get(5).getOrientationFor(Connection.NORTH));
//            System.out.println(tiles.get(7).getOrientationFor(Connection.WEST));
//
//            System.out.println();
//
//            System.out.println(tiles.get(2).getOrientationFor(Connection.WEST));
//            System.out.println(tiles.get(2).getOrientationFor(Connection.SOUTH));
//
//            System.out.println(tiles.get(4).getOrientationFor(Connection.WEST));
//            System.out.println(tiles.get(4).getOrientationFor(Connection.NORTH));
//
//            System.out.println(tiles.get(6).getOrientationFor(Connection.EAST));
//            System.out.println(tiles.get(6).getOrientationFor(Connection.NORTH));

            final List<Integer> inside = pipeMap.getTileIndexesInsideLoop();
            System.out.println(inside);

        }

    }

}
