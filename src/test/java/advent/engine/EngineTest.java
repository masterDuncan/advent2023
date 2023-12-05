package advent.engine;

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

@DisplayName("Engine Test")
public class EngineTest {


    @DisplayName("Locate Part Numbers")
    @Nested
    class LocatePartNumbers {

        @DisplayName("when schematic is empty - return no part numbers")
        @Test
        void whenSchematicIsEmpty_returnNoPartNumbers() {
            final List<String> lines = Collections.emptyList();

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isEmpty();
        }

        @DisplayName("when schematic has no part numbers - return no part numbers")
        @Test
        void whenSchematicHasNoPartNumbers_returnNoPartNumbers() {
            final String schematic = "..........";
            final List<String> lines = List.of(schematic);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isEmpty();
        }

        @DisplayName("when schematic has no valid part numbers - return no part numbers")
        @Test
        void whenSchematicHasNoValidPartNumbers_returnNoPartNumbers() {
            final String line = "....331...";
            final List<String> lines = List.of(line);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isEmpty();
        }

        @DisplayName("when schematic has valid part number on the left of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTheLeftOfASymbol_returnPartNumber() {
            final String line = "....331#..";
            final List<String> lines = List.of(line);
            final int partNumber = 331;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 7))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 7))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on the right of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTheRightOfASymbol_returnPartNumber() {
            final String line = "....#266..";
            final List<String> lines = List.of(line);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 4))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 4))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on top far left of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTopFarLeftOfASymbol_returnPartNumber() {
            final String line1 = ".266......";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on top left of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTopLeftOfASymbol_returnPartNumber() {
            final String line1 = "..266.....";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on top of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTopOfASymbol_returnPartNumber() {
            final String line1 = "...266....";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on top right of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTopRightOfASymbol_returnPartNumber() {
            final String line1 = "....266...";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).contains(partNumber);
        }

        @DisplayName("when schematic has valid part number on top far right of a symbol - return part number")
        @Test
        void whenSchematicHasValidPartNumberOnTopFarRightOfASymbol_returnPartNumber() {
            final String line1 = ".....266..";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);
            final int partNumber = 266;

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 14))).contains(partNumber);
        }

        @DisplayName("when symbol is too close to left start - return part number on the upper line")
        @Test
        void whenSymbolIsTooCloseToLeftStart_returnPartNumberOnTheUpperLine() {
            final String line1 = ".......266";
            final String line2 = "..........";
            final String line3 = "#.........";
            final List<String> lines = List.of(line1, line2, line3);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 20))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 20))).isEmpty();
        }

        @DisplayName("when symbol is too close to right end - return part number on the upper line")
        @Test
        void whenSymbolIsTooCloseToRightEnd_returnPartNumberOnTheUpperLine() {
            final String line1 = "..........";
            final String line2 = "..........";
            final String line3 = "266......#";
            final List<String> lines = List.of(line1, line2, line3);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 29))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 29))).isEmpty();
        }

        @DisplayName("when symbol is too close to left start - return part number on the bottom line")
        @Test
        void whenSymbolIsTooCloseToLeftStart_returnPartNumberOnTheBottomLine() {
            final String line1 = "..........";
            final String line2 = "#......266";
            final String line3 = "..........";
            final List<String> lines = List.of(line1, line2, line3);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 10))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 10))).isEmpty();
        }

        @DisplayName("when symbol is too close to right start - return part number on the bottom line")
        @Test
        void whenSymbolIsTooCloseToRightStart_returnPartNumberOnTheBottomLine() {
            final String line1 = "..........";
            final String line2 = ".........#";
            final String line3 = "..........";
            final String line4 = "266.......";
            final List<String> lines = List.of(line1, line2, line3, line4);

            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolsToPartNumbers = partNumberExtractor.extractPartNumbersForSymbols();

            assertThat(symbolsToPartNumbers).isNotEmpty();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 19))).isNotNull();
            assertThat(symbolsToPartNumbers.get(new Symbol('#', 19))).isEmpty();
        }
    }

    @DisplayName("Get Numbers from Segment")
    @Nested
    class GetNumbersFromSegment {

        @DisplayName("when segment has no numbers - return nothing")
        @Test
        void whenSegmentHasNoNumbers_returnNothing() {
            final String schematic = "......";
            final String segment = "...";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 0, 2);

            assertThat(numbers).isEmpty();
        }

        @DisplayName("when segment has full number - return nothing")
        @Test
        void whenSegmentHasFullNumber_returnNumber() {
            final String schematic = "123...";
            final String segment = "123";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 0, 2);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment has partial left number - return whole number")
        @Test
        void whenSegmentHasPartialLeftNumber_returnNumber() {
            final String schematic = ".123..";
            final String segment = ".12";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 0, 2);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment has very partial left number - return whole number")
        @Test
        void whenSegmentHasVeryPartialLeftNumber_returnNumber() {
            final String schematic = "..123.";
            final String segment = "..1";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 0, 2);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment has partial right number - return whole number")
        @Test
        void whenSegmentHasPartialRightNumber_returnNumber() {
            final String schematic = ".123..";
            final String segment = "23.";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 2, 4);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment has very partial right number - return whole number")
        @Test
        void whenSegmentHasVeryPartialRightNumber_returnNumber() {
            final String schematic = ".123..";
            final String segment = "3..";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 3, 5);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment has two numbers - return both numbers")
        @Test
        void whenSegmentHasTwoNumbers_returnBothNumbers() {
            final String schematic = ".123.456...";
            final String segment = "3.4";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 3, 5);

            assertThat(numbers).hasSize(2);
            assertThat(numbers).contains(123);
            assertThat(numbers).contains(456);
        }

        @DisplayName("when segment has one digit number in the middle - return number")
        @Test
        void whenSegmentHasOneDigitNumberInTheMiddle_returnNumber() {
            final String schematic = "...3.456...";
            final String segment = ".3.";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 2, 4);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(3);
        }

        @DisplayName("when segment 2 has partial left number - return whole number")
        @Test
        void whenSegment2HasPartialLeftNumber_returnNumber() {
            final String schematic = ".123..";
            final String segment = "3.";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 3, 4);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }

        @DisplayName("when segment 2 has partial right number - return whole number")
        @Test
        void whenSegment2HasPartialRightNumber_returnNumber() {
            final String schematic = ".123..";
            final String segment = ".1";
            final List<Integer> numbers = Utils.getNumbersFromSegment(schematic, segment, 0, 1);

            assertThat(numbers).hasSize(1);
            assertThat(numbers).contains(123);
        }
    }

    @DisplayName("Check Part Numbers")
    @Nested
    class CheckPartNumbers {

        @DisplayName("when schematic is empty - part number is not valid")
        @Test
        void whenSchematicIsEmpty_partNumberIsNotValid() {
            final List<String> schematic = Collections.emptyList();
            final int partNumber = 0;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isFalse();
        }

        @DisplayName("when not adjacent to symbol - part number is not valid")
        @Test
        void whenNotAdjacentToSymbol_partNumberIsNotValid() {
            final List<String> lines = Lists.newArrayList("...255..#....");
            final int partNumber = 255;
            final int indexOnSchematic = 3;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isFalse();
        }

        @DisplayName("when not adjacent to symbol on the beginning of the line - part number is not valid")
        @Test
        void whenNotAdjacentToSymbolOnTheBeginningOfTheLine_partNumberIsNotValid() {
            final List<String> lines = Lists.newArrayList("255.....#....");
            final int partNumber = 255;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isFalse();
        }

        @DisplayName("when not adjacent to symbol on the end of the line - part number is not valid")
        @Test
        void whenNotAdjacentToSymbolOnTheEndOfTheLine_partNumberIsNotValid() {
            final List<String> lines = Lists.newArrayList("........#.255");
            final int partNumber = 255;
            final int indexOnSchematic = 11;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isFalse();
        }

        @DisplayName("when adjacent to symbol on the left - part number is valid")
        @Test
        void whenAdjacentToSymbolOnTheLeft_partNumberIsValid() {
            final List<String> lines = Lists.newArrayList("...#255......");
            final int partNumber = 255;
            final int indexOnSchematic = 4;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on the right - part number is valid")
        @Test
        void whenAdjacentToSymbolOnTheRight_partNumberIsValid() {
            final List<String> lines = Lists.newArrayList("...255#......");
            final int partNumber = 255;
            final int indexOnSchematic = 3;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on top left diagonal - part number is valid")
        @Test
        void whenAdjacentToSymbolOnTopLeftDiagonal_partNumberIsValid() {
            final String line1 = "....#.....";
            final String line2 = ".....255..";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 15;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on top - part number is valid")
        @Test
        void whenAdjacentToSymbolOnTop_partNumberIsValid() {
            final String line1 = "......#...";
            final String line2 = ".....255..";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 15;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on top right diagonal - part number is valid")
        @Test
        void whenAdjacentToSymbolOnTopRightDiagonal_partNumberIsValid() {
            final String line1 = "........#.";
            final String line2 = ".....255..";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 15;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on the bottom left diagonal - part number is valid")
        @Test
        void whenAdjacentToSymbolOnBottomLeftDiagonal_partNumberIsValid() {
            final String line1 = ".....255..";
            final String line2 = "....#.....";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 5;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on the bottom - part number is valid")
        @Test
        void whenAdjacentToSymbolOnBottom_partNumberIsValid() {
            final String line1 = ".....255..";
            final String line2 = "......#...";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 6;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("when adjacent to symbol on the bottom right diagonal- part number is valid")
        @Test
        void whenAdjacentToSymbolOnBottomRightDiagonal_partNumberIsValid() {
            final String line1 = ".....255..";
            final String line2 = "........#.";
            final List<String> lines = List.of(line1, line2);

            final int partNumber = 255;
            final int indexOnSchematic = 6;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);
            final boolean valid = partNumberChecker.isValidPartNumber(partNumber, indexOnSchematic);

            assertThat(valid).isTrue();
        }

        @DisplayName("Solve schematic problem")
        @Test
        void solveSchematicProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/engine/input"));
            final List<Pair<Integer, Integer>> partNumbersAndIndexes = Utils.extractPossiblePartNumbers(lines);
            final PartNumberChecker partNumberChecker = new PartNumberChecker(lines);

            final List<Integer> validPartNumbers = Lists.newArrayList();
            for (final Pair<Integer, Integer> partNumberAndIndex : partNumbersAndIndexes) {
                final int partNumber = partNumberAndIndex.getKey();
                final int partIndex = partNumberAndIndex.getValue();
                final boolean isValid = partNumberChecker.isValidPartNumber(partNumber, partIndex);
                if (isValid) {
                    validPartNumbers.add(partNumber);
                }

                System.out.println("Part number " + partNumber + " in position " + partIndex + (isValid ? " was valid" : " was invalid"));
            }

            final int result = validPartNumbers.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Result: " + result);
        }

        @DisplayName("Solve gear ratio problem")
        @Test
        void solveGearRatioProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/engine/input"));
            final PartNumberExtractor partNumberExtractor = new PartNumberExtractor(lines);
            final Map<Symbol, List<Integer>> symbolMap = partNumberExtractor.extractPartNumbersForSymbols();

            final int result = symbolMap.keySet().stream().filter(symbol -> symbol.getSymbol() == '*')
                    .map(symbolMap::get).filter(partNumbers -> partNumbers.size() == 2)
                    .map(partNumbers -> partNumbers.get(0) * partNumbers.get(1)).mapToInt(Integer::intValue)
                    .sum();

            System.out.println(result);
        }
    }

    @DisplayName("Extract numbers from schema")
    @Nested
    class ExtractNumbersFromSchema {

        @DisplayName("when extracting part numbers from schema - there are no numbers - do nothing")
        @Test
        void whenExtractingPartNumbersFromSchema_thereAreNoNumbers_doNothing() {
            final List<String> lines = List.of(".....");
            final List<Pair<Integer, Integer>> partNumbers = Utils.extractPossiblePartNumbers(lines);

            assertThat(partNumbers).isEmpty();
        }

        @DisplayName("when extracting part numbers from schema - is the last number - extract number and index")
        @Test
        void whenExtractingPartNumbersFromSchema_isTheLastNumber_extractNumberAndIndex() {
            final List<String> lines = List.of(".....1");
            final List<Pair<Integer, Integer>> partNumbers = Utils.extractPossiblePartNumbers(lines);

            assertThat(partNumbers).isNotEmpty();
            assertThat(partNumbers.get(0).getKey()).isEqualTo(1);
            assertThat(partNumbers.get(0).getValue()).isEqualTo(5);
        }

        @DisplayName("when extracting part numbers from schema - is the first number with one digit - extract number and index")
        @Test
        void whenExtractingPartNumbersFromSchema_isTheFirstNumberWithOneDigit_extractNumberAndIndex() {
            final List<String> lines = List.of("1......");
            final List<Pair<Integer, Integer>> partNumbers = Utils.extractPossiblePartNumbers(lines);

            assertThat(partNumbers).isNotEmpty();
            assertThat(partNumbers.get(0).getKey()).isEqualTo(1);
            assertThat(partNumbers.get(0).getValue()).isEqualTo(0);
        }

        @DisplayName("when extracting part numbers from schema - is the first number with several digits - extract number and index")
        @Test
        void whenExtractingPartNumbersFromSchema_isTheFirstNumberWithSeveralDigits_extractNumberAndIndex() {
            final List<String> lines = List.of("123......");
            final List<Pair<Integer, Integer>> partNumbers = Utils.extractPossiblePartNumbers(lines);

            assertThat(partNumbers).isNotEmpty();
            assertThat(partNumbers.get(0).getKey()).isEqualTo(123);
            assertThat(partNumbers.get(0).getValue()).isEqualTo(0);
        }

        @DisplayName("when extracting part numbers from schema - extract number and index")
        @Test
        void whenExtractingPartNumbersFromSchema_extractNumberAndIndex() {
            final List<String> lines = List.of("....123......");
            final List<Pair<Integer, Integer>> partNumbers = Utils.extractPossiblePartNumbers(lines);

            assertThat(partNumbers).isNotEmpty();
            assertThat(partNumbers.get(0).getKey()).isEqualTo(123);
            assertThat(partNumbers.get(0).getValue()).isEqualTo(4);
        }
    }

    @DisplayName("Get Adjacent Symbols for Part Number")
    @Nested
    class AdjacentSymbols {

        @DisplayName("when schematic is empty - return no symbols")
        @Test
        void whenSchematicIsEmpty_returnNoSymbols() {
            final List<String> schematic = Collections.emptyList();
            final int partNumber = 0;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isEmpty();
        }

        @DisplayName("when schematic has no part numbers - return no symbols")
        @Test
        void whenSchematicHasNoPartNumbers_returnNoSymbols() {
            final List<String> schematic = List.of(".........");
            final int partNumber = 0;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isEmpty();
        }

        @DisplayName("when schematic has no symbols - return no symbols")
        @Test
        void whenSchematicHasSymbols_returnNoSymbols() {
            final List<String> schematic = List.of("..334...12....");
            final int partNumber = 0;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isEmpty();
        }

        @DisplayName("when schematic has no valid part numbers - return no symbols")
        @Test
        void whenSchematicHasNoValidPartNumbers_returnNoSymbols() {
            final List<String> schematic = List.of("..334.!..12..*..");
            final int partNumber = 0;
            final int indexOnSchematic = 0;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isEmpty();
        }

        @DisplayName("when schematic has a valid part number with symbol on right - return adjacent symbol")
        @Test
        void whenSchematicHasAValidPartNumberWithSymbolOnRight_returnAdjacentSymbol() {
            final List<String> schematic = List.of("..1224?....");
            final int partNumber = 1224;
            final int indexOnSchematic = 2;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('?');
        }

        @DisplayName("when schematic has a valid part number with symbol on left - return adjacent symbol")
        @Test
        void whenSchematicHasAValidPartNumberWithSymbolOnLeft_returnAdjacentSymbol() {
            final List<String> schematic = List.of("..+1224....");
            final int partNumber = 1224;
            final int indexOnSchematic = 3;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('+');
        }

        @DisplayName("when schematic has a valid part number with a symbol on top - return adjacent symbol")
        @Test
        void whenSchematicHasAValidPartNumberWithASymbolOnTop_returnAdjacentSymbol() {
            final String line1 = ".....+...";
            final String line2 = "...167...";
            final List<String> schematic = List.of(line1, line2);
            final int partNumber = 167;
            final int indexOnSchematic = 12;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('+');
        }

        @DisplayName("when schematic has a valid part number with several symbols on top - return adjacent symbols")
        @Test
        void whenSchematicHasAValidPartNumberWithSeveralSymbolsOnTop_returnAdjacentSymbols() {
            final String line1 = "..~..+...";
            final String line2 = "...167...";
            final List<String> schematic = List.of(line1, line2);
            final int partNumber = 167;
            final int indexOnSchematic = 12;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('~');
            assertThat(symbols.get(1)).isEqualTo('+');
        }

        @DisplayName("when schematic has a valid part number with a symbol on bottom - return adjacent symbol")
        @Test
        void whenSchematicHasAValidPartNumberWithASymbolOnBottom_returnAdjacentSymbol() {
            final String line1 = "....23...";
            final String line2 = ".....=...";
            final List<String> schematic = List.of(line1, line2);
            final int partNumber = 23;
            final int indexOnSchematic = 4;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('=');
        }

        @DisplayName("when schematic has a valid part number with several symbols on bottom - return adjacent symbols")
        @Test
        void whenSchematicHasAValidPartNumberWithSeveralSymbolsOnBottom_returnAdjacentSymbols() {
            final String line1 = "....23...";
            final String line2 = "...!.=...";
            final List<String> schematic = List.of(line1, line2);
            final int partNumber = 23;
            final int indexOnSchematic = 4;

            final PartNumberChecker partNumberChecker = new PartNumberChecker(schematic);
            final List<Character> symbols = partNumberChecker.getAdjacentSymbolsForPartNumber(partNumber, indexOnSchematic);

            assertThat(symbols).isNotEmpty();
            assertThat(symbols.get(0)).isEqualTo('!');
            assertThat(symbols.get(1)).isEqualTo('=');
        }

    }
}
