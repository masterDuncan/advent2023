package advent.trebuchet;

import advent.trebuchet.DigitData;
import advent.trebuchet.ImprovedCalibrationDecoder;
import advent.utils.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Improved Calibration Decoder Test")
public class ImprovedCalibrationDecoderTest {

    @DisplayName("Test Improved Calibration Decoder Values")
    @Nested
    class TestImprovedCalibrationDecoderValues {

        @DisplayName("when calibration line is empty - return zero")
        @Test
        void whenCalibrationLineIsEmpty_returnZero() {
            final String line = "";
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isZero();
        }

        @DisplayName("when calibration has no digits - return zero")
        @Test
        void whenCalibrationLineHasNoDigits_returnZero() {
            final String line = "abcda";
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isZero();
        }

        @DisplayName("when calibration has just one digit - return digit twice")
        @Test
        void whenCalibrationLineHasJustOneDigit_returnDigitTwice() {
            final String line = "1";
            final int expectedValue = 11;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has two digits - return combination first-last")
        @Test
        void whenCalibrationLineHasTwoDigits_returnCombinationFirstLast() {
            final String line = "21";
            final int expectedValue = 21;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has just one string digit - return digit twice")
        @Test
        void whenCalibrationLineHasJustOneStringDigit_returnDigitTwice() {
            final String line = "one";
            final int expectedValue = 11;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has two string digits - return combination first-last")
        @Test
        void whenCalibrationLineHasTwoStringDigit_returnCombinationFirstLast() {
            final String line = "onenine";
            final int expectedValue = 19;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has one digit and one string digit - return combination first-last")
        @Test
        void whenCalibrationLineHasOneDigitAndOneStringDigit_returnCombinationFirstLast() {
            final String line = "4seven";
            final int expectedValue = 47;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has one string digit and one digit - return combination first-last")
        @Test
        void whenCalibrationLineHasOneStringDigitAndOneDigit_returnCombinationFirstLast() {
            final String line = "eight6";
            final int expectedValue = 86;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several string digits - return combination first-last")
        @Test
        void whenCalibrationLineHasSeveralStringDigits_returnCombinationFirstLast() {
            final String line = "eightfourseven";
            final int expectedValue = 87;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits and string digits - return combination first-last")
        @Test
        void whenCalibrationLineHasSeveralDigitsAndStringDigits_returnCombinationFirstLast() {
            final String line = "1eight5four66seven3";
            final int expectedValue = 13;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("when calibration has several digits, string digits and noise - return combination first-last")
        @Test
        void whenCalibrationLineHasSeveralDigitsStringDigitsAndNoise_returnCombinationFirstLast() {
            final String line = "onceeeer1eight5fourtarolo6tadda6seven3sievvvna";
            final int expectedValue = 13;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("real failing case 1 - return combination first-last")
        @Test
        void realFailingCase1_returnCombinationFirstLast() {
            final String line = "gckhqpb6twoqnjxqplthree2fourkspnsnzxlz1";
            final int expectedValue = 61;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("real failing case 2 - return combination first-last")
        @Test
        void realFailingCase2_returnCombinationFirstLast() {
            final String line = "frkh2nineqmqxrvdsevenfive";
            final int expectedValue = 25;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("real failing case 3 - return combination first-last")
        @Test
        void realFailingCase3_returnCombinationFirstLast() {
            final String line = "onetwones";
            final int expectedValue = 11;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("real failing case 4 - return combination first-last")
        @Test
        void realFailingCase4_returnCombinationFirstLast() {
            final String line = "mbkfgktwolbvsptgsixseven1oneightzvm";
            final int expectedValue = 28;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("real failing case 5 - return combination first-last")
        @Test
        void realFailingCase5_returnCombinationFirstLast() {
            final String line = "zmeightwohkgs6 ";
            final int expectedValue = 86;
            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final int value = improvedCalibrationDecoder.decode(line);

            assertThat(value).isEqualTo(expectedValue);
        }

        @DisplayName("solve improved trebuchet problem")
        @Test
        void solveImprovedTrebuchetProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/trebuchet/input"));

            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            for (final String line : lines) {
                final int value = improvedCalibrationDecoder.decode(line);
                System.out.println(line + " : " + value);
            }

            final int result = lines.stream().map(improvedCalibrationDecoder::decode).mapToInt(Integer::valueOf).sum();

            System.out.println(result);
        }
    }

    @DisplayName("Value Extractor")
    @Nested
    class ValueExtractor {

        @DisplayName("when line is empty - return zero")
        @Test
        void whenLineIsEmpty_returnZero() {
            final String line = "";
            final int position = 0;

            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final DigitData digitData = improvedCalibrationDecoder.extractDigitFromPosition(line, position);

            assertThat(digitData.getValue()).isZero();
        }

        @DisplayName("when position is negative - return zero")
        @Test
        void whenPositionIsNegative_returnZero() {
            final String line = "";
            final int position = -1;

            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final DigitData digitData = improvedCalibrationDecoder.extractDigitFromPosition(line, position);

            assertThat(digitData.getValue()).isZero();
        }

        @DisplayName("when position is out of the line - return zero")
        @Test
        void whenPositionIsOutOfTheLine_returnZero() {
            final String line = "abc";
            final int position = 3;

            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final DigitData digitData = improvedCalibrationDecoder.extractDigitFromPosition(line, position);

            assertThat(digitData.getValue()).isZero();
        }

        @DisplayName("when position in line is a digit - return digit")
        @Test
        void whenPositionInLineIsADigit_returnZero() {
            final String line = "3";
            final int position = 0;
            final int expectedValue = 3;

            final ImprovedCalibrationDecoder improvedCalibrationDecoder = new ImprovedCalibrationDecoder();
            final DigitData digitData = improvedCalibrationDecoder.extractDigitFromPosition(line, position);

            assertThat(digitData.getValue()).isEqualTo(expectedValue);
        }

    }

}
