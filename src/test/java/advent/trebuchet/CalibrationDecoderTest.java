package advent.trebuchet;

import advent.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Calibration Test")
public class CalibrationDecoderTest {

    @DisplayName("Test Calibration Decoder Values")
    @Nested
    class TestCalibrationDecoderValues {

        @DisplayName("when calibration line is empty - return zero")
        @Test
        void whenCalibrationLineIsEmpty_returnZero() {
            final String line = "";
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isZero();
        }

        @DisplayName("when calibration has no digits - return zero")
        @Test
        void whenCalibrationLineHasNoDigits_returnZero() {
            final String line = "abcda";
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isZero();
        }

        @DisplayName("when calibration has just one digit - return digit twice")
        @Test
        void whenCalibrationLineHasJustOneDigit_returnDigitTwice() {
            final String line = "1";
            final int expectedValue = 11;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has two digits - return first-last combination")
        @Test
        void whenCalibrationLineHasTwoDigits_returnFirstLastCombination() {
            final String line = "12";
            final int expectedValue = 12;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigits_returnFirstLastCombination() {
            final String line = "12345";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and letters before - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigitsAndLettersBefore_returnFirstLastCombination() {
            final String line = "abc12345";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and letters after - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigitsAndLettersAfter_returnFirstLastCombination() {
            final String line = "12345abc";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and letters before and after - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigitsAndLettersBeforeAndAfter_returnFirstLastCombination() {
            final String line = "xyz12345abc";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and letters between - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigitsAndLettersBetween_returnFirstLastCombination() {
            final String line = "1a2b3c4d5";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and letters everywhere - return first-last combination")
        @Test
        void whenCalibrationLineSeveralDigitsAndLettersEverywhere_returnFirstLastCombination() {
            final String line = "xy1a2b3c4d5zt";
            final int expectedValue = 15;
            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int value = calibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("solve trebuchet problem")
        @Test
        void solveTrebuchetProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/trebuchet/input"));

            final CalibrationDecoder calibrationDecoder = new CalibrationDecoder();
            final int result = lines.stream().map(line -> calibrationDecoder.decode(line)).mapToInt(Integer::valueOf).sum();

            System.out.println(result);
        }

    }

}
