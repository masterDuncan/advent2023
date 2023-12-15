package advent.oasis;

import advent.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Oasis Device Test")
public class OasisDeviceTest {

    @DisplayName("Calculate Predictions")
    @Nested
    class CalculatePredictions {

        @DisplayName("when sensor input has one value - prediction is one")
        @Test
        void whenSensorInputHasOneValue_predictionIsZero() {
            final int[] sensorHistory = new int[]{1};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isOne();
        }

        @DisplayName("when sensor input has two identical values - predict using difference")
        @Test
        void whenSensorInputHasTwoIdenticalValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{1, 1};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(1);
        }

        @DisplayName("when sensor input has two increasing values - predict using difference")
        @Test
        void whenSensorInputHasTwoIncreasingValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{1, 3};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(5);
        }

        @DisplayName("when sensor input has two decreasing values - predict using difference")
        @Test
        void whenSensorInputHasTwoDecreasingValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{5, 3};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(1);
        }

        @DisplayName("when sensor input has three identical values - predict using difference")
        @Test
        void whenSensorInputHasThreeIdenticalValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{1, 1, 1};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(1);
        }

        @DisplayName("when sensor input has three increasing values - predict using difference")
        @Test
        void whenSensorInputHasThreeIncreasingValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{1, 3, 5};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(7);
        }

        @DisplayName("when sensor input has three decreasing values - predict using difference")
        @Test
        void whenSensorInputHasThreeDecreasingValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{7, 5, 3};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(1);
        }

        @DisplayName("when sensor input has second-level values - predict using difference")
        @Test
        void whenSensorInputHasSecondLevelValues_predictUsingDifference() {
            final int[] sensorHistory = new int[]{1, 3, 6, 10, 15, 21};
            final int nextValue = OasisDevice.predictNextValue(sensorHistory);

            assertThat(nextValue).isEqualTo(28);
        }

        @DisplayName("when sensor input has second-level values - predict backwards using difference")
        @Test
        void whenSensorInputHasSecondLevelValues_predictBackwardsUsingDifference() {
            final int[] sensorHistory = new int[]{10, 13, 16, 21, 30, 45};
            final int previousValue = OasisDevice.predictPreviousValue(sensorHistory);

            assertThat(previousValue).isEqualTo(5);
        }

        @DisplayName("when sensor input has second-level values - predict backwards using difference")
        @Test
        void whenSensorInputHasSecondLevelValues_predictBackwardsUsingDifference_2() {
            final int[] sensorHistory = new int[]{27, 49, 92, 176};
            final int previousValue = OasisDevice.predictPreviousValue(sensorHistory);

            assertThat(previousValue).isEqualTo(6);
        }

        @DisplayName("when sensor input has second-level values - predict backwards using difference")
        @Test
        void whenSensorInputHasSecondLevelValues_predictBackwardsUsingDifference_3() {
            final int[] sensorHistory = new int[]{1, 3, 6, 10, 15, 21};
            final int previousValue = OasisDevice.predictPreviousValue(sensorHistory);

            assertThat(previousValue).isEqualTo(0);
        }

        @DisplayName("Solve OASIS problem")
        @Test
        void solveOasisProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/oasis/input"));

            int result = 0;
            for (final String line : lines) {
                result += OasisDevice.predictNextValue(Arrays.stream(line.split(" ")).map(Integer::parseInt)
                        .mapToInt(Integer::intValue).toArray());
            }

            System.out.println(result);
        }

        @DisplayName("Solve OASIS problem backwards")
        @Test
        void solveOasisProblemBackwards() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/oasis/input"));

            int result = 0;
            for (final String line : lines) {
                result += OasisDevice.predictPreviousValue(Arrays.stream(line.split(" ")).map(Integer::parseInt)
                        .mapToInt(Integer::intValue).toArray());
            }

            System.out.println(result);
        }
    }

}
