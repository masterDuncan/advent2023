package advent.colorcubes;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Cube Game Test")
public class CubeGameTest {

    @DisplayName("Validate Cube Game")
    @Nested
    class ValidateCubeGame {

        @DisplayName("when cube game has no rounds and bag is empty - game is valid")
        @Test
        void whenCubeGameHasNoRoundsAndBagIsEmpty_gameIsValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            final CubeGame cubeGame = new CubeGame(Collections.emptyList(), cubeBagContents);

            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isTrue();
        }

        @DisplayName("when cube game has no rounds and bag is not empty - game is valid")
        @Test
        void whenCubeGameHasNoRoundsAndBagIsNotEmpty_gameIsValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(Cube.RED, 1);

            final CubeGame cubeGame = new CubeGame(Collections.emptyList(), cubeBagContents);

            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isTrue();
        }

        @DisplayName("when cube game has one invalid round - red cube is not present - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_redCubeIsNotPresent_gameIsValid() {
            whenCubeGameHasOneInvalidRound_CubeIsNotPresent_gameIsValid(Cube.RED);
        }

        @DisplayName("when cube game has one invalid round - blue cube is not present - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_blueCubeIsNotPresent_gameIsValid() {
            whenCubeGameHasOneInvalidRound_CubeIsNotPresent_gameIsValid(Cube.BLUE);
        }


        @DisplayName("when cube game has one invalid round - green cube is not present - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_greenCubeIsNotPresent_gameIsValid() {
            whenCubeGameHasOneInvalidRound_CubeIsNotPresent_gameIsValid(Cube.GREEN);
        }


        void whenCubeGameHasOneInvalidRound_CubeIsNotPresent_gameIsValid(final Cube cube) {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup = new CubeGroup();
            cubeGroup.setCubesOfColor(cube, 1);

            final CubeGame cubeGame = new CubeGame(List.of(cubeGroup), cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isFalse();
        }

        @DisplayName("when cube game has one invalid round - not enough red cubes - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_notEnoughRedCubes_gameIsValid() {
            whenCubeGameHasOneInvalidRound_notEnoughCubes_gameIsValid(Cube.RED);
        }

        @DisplayName("when cube game has one invalid round - not enough blue cubes - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_notEnoughBlueCubes_gameIsValid() {
            whenCubeGameHasOneInvalidRound_notEnoughCubes_gameIsValid(Cube.BLUE);
        }

        @DisplayName("when cube game has one invalid round - not enough red cubes - game is invalid")
        @Test
        void whenCubeGameHasOneInvalidRound_notEnoughGreenCubes_gameIsValid() {
            whenCubeGameHasOneInvalidRound_notEnoughCubes_gameIsValid(Cube.GREEN);
        }

        void whenCubeGameHasOneInvalidRound_notEnoughCubes_gameIsValid(final Cube cube) {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(cube, 1);

            final CubeGroup cubeGroup = new CubeGroup();
            cubeGroup.setCubesOfColor(cube, 2);

            final CubeGame cubeGame = new CubeGame(List.of(cubeGroup), cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isFalse();
        }

        @DisplayName("when cube game has one valid round - red cube is present - game is valid")
        @Test
        void whenCubeGameHasOneValidRound_redCubeIsPresent_gameIsValid() {
            whenCubeGameHasOneValidRound_CubeIsPresent_gameIsValid(Cube.RED);
        }

        @DisplayName("when cube game has one valid round - blue cube is present - game is valid")
        @Test
        void whenCubeGameHasOneValidRound_blueCubeIsPresent_gameIsValid() {
            whenCubeGameHasOneValidRound_CubeIsPresent_gameIsValid(Cube.BLUE);
        }

        @DisplayName("when cube game has one valid round - green cube is present - game is valid")
        @Test
        void whenCubeGameHasOneValidRound_greenCubeIsPresent_gameIsValid() {
            whenCubeGameHasOneValidRound_CubeIsPresent_gameIsValid(Cube.GREEN);
        }

        void whenCubeGameHasOneValidRound_CubeIsPresent_gameIsValid(final Cube cube) {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(cube, 1);

            final CubeGroup cubeGroup = new CubeGroup();
            cubeGroup.setCubesOfColor(cube, 1);

            final CubeGame cubeGame = new CubeGame(List.of(cubeGroup), cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isTrue();
        }

        @DisplayName("when cube game has one invalid round with several cubes - game is not valid")
        @Test
        void whenCubeGameHasOneInvalidRoundWithSeveralCubes_gameIsNotValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(Cube.RED, 12);
            cubeBagContents.setCubesOfColor(Cube.GREEN, 13);
            cubeBagContents.setCubesOfColor(Cube.BLUE, 14);

            final CubeGroup cubeGroup = new CubeGroup();
            cubeGroup.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup.setCubesOfColor(Cube.BLUE, 6);
            cubeGroup.setCubesOfColor(Cube.RED, 20);

            final CubeGame cubeGame = new CubeGame(List.of(cubeGroup), cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isFalse();
        }

        @DisplayName("when cube game has one valid round with several cubes - game is valid")
        @Test
        void whenCubeGameHasOneValidRoundWithSeveralCubes_gameIsNotValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(Cube.RED, 12);
            cubeBagContents.setCubesOfColor(Cube.GREEN, 13);
            cubeBagContents.setCubesOfColor(Cube.BLUE, 14);

            final CubeGroup cubeGroup = new CubeGroup();
            cubeGroup.setCubesOfColor(Cube.BLUE, 1);
            cubeGroup.setCubesOfColor(Cube.GREEN, 2);

            final CubeGame cubeGame = new CubeGame(List.of(cubeGroup), cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isTrue();
        }

        @DisplayName("when cube game has several rounds with several cubes but one is invalid - game is not valid")
        @Test
        void whenCubeGameHasSeveralRoundsWithSeveralCubesButOneIsInvalid_gameIsNotValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(Cube.RED, 12);
            cubeBagContents.setCubesOfColor(Cube.GREEN, 13);
            cubeBagContents.setCubesOfColor(Cube.BLUE, 14);

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup1.setCubesOfColor(Cube.BLUE, 6);
            cubeGroup1.setCubesOfColor(Cube.RED, 20);

            final CubeGroup cubeGroup2 = new CubeGroup();
            cubeGroup2.setCubesOfColor(Cube.BLUE, 5);
            cubeGroup2.setCubesOfColor(Cube.RED, 4);
            cubeGroup2.setCubesOfColor(Cube.GREEN, 13);

            final CubeGroup cubeGroup3 = new CubeGroup();
            cubeGroup3.setCubesOfColor(Cube.GREEN, 5);
            cubeGroup3.setCubesOfColor(Cube.RED, 1);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1, cubeGroup2, cubeGroup3);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isFalse();
        }

        @DisplayName("when cube game has several rounds with several cubes and all are valid - game is valid")
        @Test
        void whenCubeGameHasSeveralRoundsWithSeveralCubesAndAllAreValid_gameIsNotValid() {
            final CubeGroup cubeBagContents = new CubeGroup();
            cubeBagContents.setCubesOfColor(Cube.RED, 12);
            cubeBagContents.setCubesOfColor(Cube.GREEN, 13);
            cubeBagContents.setCubesOfColor(Cube.BLUE, 14);

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.BLUE, 3);
            cubeGroup1.setCubesOfColor(Cube.RED, 4);

            final CubeGroup cubeGroup2 = new CubeGroup();
            cubeGroup2.setCubesOfColor(Cube.RED, 1);
            cubeGroup2.setCubesOfColor(Cube.GREEN, 2);
            cubeGroup2.setCubesOfColor(Cube.BLUE, 6);

            final CubeGroup cubeGroup3 = new CubeGroup();
            cubeGroup3.setCubesOfColor(Cube.RED, 2);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1, cubeGroup2, cubeGroup3);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final boolean validGame = cubeGame.isGamePossible();

            assertThat(validGame).isTrue();
        }

        @DisplayName("Solve cube problem")
        @Test
        void solveCubeProblem() {
            final List<Integer> validGameIds = Lists.newArrayList();
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/colorcubes/input"));
            for (final String line : lines) {
                final int gameId = extractGameIdFrom(line);
                final CubeGame cubeGame = extractCubeGameFrom(line);
                final boolean valid = cubeGame.isGamePossible();
                if (valid) {
                    validGameIds.add(gameId);
                }

                System.out.println(line + " " + (valid ? " valid" : " invalid"));
            }

            final int result = validGameIds.stream().mapToInt(Integer::intValue).sum();
            System.out.println(result);
        }

    }

    @DisplayName("Calculate Minimal Cube Powers")
    @Nested
    class CalculateMinimalCubePowers {

        @DisplayName("when calculating minimal red cubes for game with no red cubes and one round, return zero")
        @Test
        void whenCalculatingMinimalRedCubesForGameWithNoRedCubesAndOneRound_returnZero() {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup1.setCubesOfColor(Cube.BLUE, 6);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final int result = cubeGame.calculateMinimumCubesFor(Cube.RED);

            assertThat(result).isZero();
        }

        @DisplayName("when calculating minimal red cubes for game with no red cubes and several rounds, return zero")
        @Test
        void whenCalculatingMinimalRedCubesForGameWithNoRedCubesAndSeveralRounds_returnZero() {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup1.setCubesOfColor(Cube.BLUE, 6);

            final CubeGroup cubeGroup2 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.GREEN, 18);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1, cubeGroup2);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final int result = cubeGame.calculateMinimumCubesFor(Cube.RED);

            assertThat(result).isZero();
        }

        @DisplayName("when calculating minimal red cubes for game with red cubes and one round, return max cubes in round")
        @Test
        void whenCalculatingMinimalRedCubesForGameWithRedCubesAndOneRound_returnMaxCubesInRound() {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.RED, 14);
            cubeGroup1.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup1.setCubesOfColor(Cube.BLUE, 6);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final int result = cubeGame.calculateMinimumCubesFor(Cube.RED);

            assertThat(result).isEqualTo(14);
        }

        @DisplayName("when calculating minimal red cubes for game with red cubes and several rounds, return max cubes in all rounds")
        @Test
        void whenCalculatingMinimalRedCubesForGameWithRedCubesAndSeveralRound_returnMaxCubesInAllRound() {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.RED, 14);
            cubeGroup1.setCubesOfColor(Cube.GREEN, 8);
            cubeGroup1.setCubesOfColor(Cube.BLUE, 6);

            final CubeGroup cubeGroup2 = new CubeGroup();
            cubeGroup2.setCubesOfColor(Cube.GREEN, 18);
            cubeGroup2.setCubesOfColor(Cube.RED, 10);
            cubeGroup2.setCubesOfColor(Cube.BLUE, 3);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1, cubeGroup2);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final int result = cubeGame.calculateMinimumCubesFor(Cube.RED);

            assertThat(result).isEqualTo(14);
        }

        @DisplayName("when calculating minimal cube power - calculate power")
        @Test
        void whenCalculatingMinimalCubePower_calculatePower() {
            final CubeGroup cubeBagContents = new CubeGroup();

            final CubeGroup cubeGroup1 = new CubeGroup();
            cubeGroup1.setCubesOfColor(Cube.BLUE, 3);
            cubeGroup1.setCubesOfColor(Cube.RED, 4);

            final CubeGroup cubeGroup2 = new CubeGroup();
            cubeGroup2.setCubesOfColor(Cube.RED, 1);
            cubeGroup2.setCubesOfColor(Cube.GREEN, 2);
            cubeGroup2.setCubesOfColor(Cube.BLUE, 6);

            final CubeGroup cubeGroup3 = new CubeGroup();
            cubeGroup2.setCubesOfColor(Cube.GREEN, 2);

            final List<CubeGroup> cubeGroups = List.of(cubeGroup1, cubeGroup2, cubeGroup3);

            final CubeGame cubeGame = new CubeGame(cubeGroups, cubeBagContents);
            final int minCubePower = cubeGame.calculateMinPowerForGame();

            assertThat(minCubePower).isEqualTo(48);
        }

        @DisplayName("Solve cube power problem")
        @Test
        void solveCubePowerProblem() {
            final List<Integer> gamePowers = Lists.newArrayList();
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/colorcubes/input"));
            for (final String line : lines) {
                final CubeGame cubeGame = extractCubeGameFrom(line);
                gamePowers.add(cubeGame.calculateMinPowerForGame());
            }

            final int result = gamePowers.stream().mapToInt(Integer::intValue).sum();
            System.out.println(result);
        }

    }

    private int extractGameIdFrom(final String line) {
        final String[] parts = line.split(":");
        return Integer.parseInt(parts[0].split(" ")[1]);
    }

    private CubeGame extractCubeGameFrom(final String line) {
        final String[] parts = line.split(":");
        final String[] rounds = parts[1].split(";");
        final List<CubeGroup> cubeGameRounds = extractRounds(rounds);
        return new CubeGame(cubeGameRounds, getCubeBagContents());
    }

    private List<CubeGroup> extractRounds(final String[] rounds) {
        final List<CubeGroup> cubeGroups = Lists.newArrayList();
        for (final String round : rounds) {
            final String[] cubeGroup = round.split(",");
            final CubeGroup cubeGameGroup = extractCubeGroup(cubeGroup);
            cubeGroups.add(cubeGameGroup);
        }

        return cubeGroups;
    }

    private CubeGroup extractCubeGroup(final String[] cubeGroups) {
        final CubeGroup cubeGameGroup = new CubeGroup();
        for (final String cubeGroup : cubeGroups) {
            final String[] parts = cubeGroup.trim().split(" ");
            final int amount = Integer.parseInt(parts[0]);
            final String description = parts[1];
            cubeGameGroup.setCubesOfColor(Cube.getFromDescription(description), amount);
        }

        return cubeGameGroup;
    }

    private CubeGroup getCubeBagContents() {
        final CubeGroup cubeGroup = new CubeGroup();
        cubeGroup.setCubesOfColor(Cube.RED, 12);
        cubeGroup.setCubesOfColor(Cube.GREEN, 13);
        cubeGroup.setCubesOfColor(Cube.BLUE, 14);

        return cubeGroup;
    }
}
