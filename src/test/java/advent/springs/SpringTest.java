package advent.springs;

import advent.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Spring test")
public class SpringTest {

    @DisplayName("Calculate permutations")
    @Nested
    class CalculatePermutations {

        @DisplayName("when record is not degraded - the record is the permutation")
        @Test
        void permutationTest_1() {
            final String record = "..#..#..###";
            final List<String> permutations = RecordUtils.getPermutations(record);

            assertThat(permutations).hasSize(1);
            assertThat(permutations.get(0)).isEqualTo(record);
        }

        @DisplayName("when record has one tile degraded - calculate permutations")
        @Test
        void permutationTest_2() {
            final String record = "..#?.#..###";
            final List<String> permutations = RecordUtils.getPermutations(record);

            assertThat(permutations).hasSize(2);
            assertThat(permutations.get(0)).isEqualTo("..#..#..###");
            assertThat(permutations.get(1)).isEqualTo("..##.#..###");
        }

        @DisplayName("when record has two tiles degraded - calculate permutations")
        @Test
        void permutationTest_3() {
            final String record = "..#??#..###";
            final List<String> permutations = RecordUtils.getPermutations(record);

            assertThat(permutations).hasSize(4);
            assertThat(permutations.get(0)).isEqualTo("..#..#..###");
            assertThat(permutations.get(1)).isEqualTo("..#.##..###");
            assertThat(permutations.get(2)).isEqualTo("..##.#..###");
            assertThat(permutations.get(3)).isEqualTo("..####..###");
        }

    }

    @DisplayName("Calculate valid arrangements")
    @Nested
    class CalculateValidArrangements {

        @DisplayName("test extract defect pattern")
        @Test
        void testExtractDefectPattern() {
            final String record = "..#..#..###";
            final List<Integer> defectPattern = RecordUtils.extractDefectPattern(record);

            assertThat(defectPattern).hasSize(3);
            assertThat(defectPattern.get(0)).isEqualTo(1);
            assertThat(defectPattern.get(1)).isEqualTo(1);
            assertThat(defectPattern.get(2)).isEqualTo(3);
        }

        @DisplayName("test spring condition record with only one valid arrangement")
        @Test
        void testSpringConditionRecord_1() {
            final String line = "???.### 1,1,3";

            final SpringConditionRecord record = new SpringConditionRecord(line);
            final List<String> validArrangements = record.calculateValidArrangements();

            assertThat(validArrangements).hasSize(1);
            assertThat(validArrangements.get(0)).isEqualTo("#.#.###");
        }

        @DisplayName("test spring condition record with several correct arrangements")
        @Test
        void testSpringConditionRecord_2() {
            final String line = ".??..??...?##. 1,1,3";

            final SpringConditionRecord record = new SpringConditionRecord(line);
            final List<String> validArrangements = record.calculateValidArrangements();

            assertThat(validArrangements).hasSize(4);
            assertThat(validArrangements.get(0)).isEqualTo("..#...#...###.");
            assertThat(validArrangements.get(1)).isEqualTo("..#..#....###.");
            assertThat(validArrangements.get(2)).isEqualTo(".#....#...###.");
            assertThat(validArrangements.get(3)).isEqualTo(".#...#....###.");
        }
    }

    @DisplayName("Solve")
    @Nested
    class Solve {

        @DisplayName("solve defect spring arrangements problem")
        @Test
        void solveDefectSpringArrangementsProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/springs/input"));

            int result = 0;
            for (final String line : lines) {
                final SpringConditionRecord record = new SpringConditionRecord(line);
                result += record.calculateValidArrangements().size();
            }

            System.out.println(result);
            assertThat(result).isEqualTo(7599);
        }
    }
}
