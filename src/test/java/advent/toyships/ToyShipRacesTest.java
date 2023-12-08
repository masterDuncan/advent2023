package advent.toyships;

import advent.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Toy Ship Races Test")
public class ToyShipRacesTest {

    @DisplayName("Calculate Winning Combinations")
    @Nested
    class CalculateWinningCombinations {

        @DisplayName("when total race time is zero - get strategies")
        @Test
        void whenTotalRaceTimeIsZero_getStrategies() {
            final long totalRaceTime = 0L;

            final ToyShipRace toyShipRace = new ToyShipRace(totalRaceTime);
            final List<RaceStrategy> strategies = toyShipRace.getStrategies();

            assertThat(strategies).isNotEmpty();
            assertThat(strategies.get(0).getButtonPressTime()).isEqualTo(0L);
            assertThat(strategies.get(0).getDistanceMoved()).isEqualTo(0L);
        }

        @DisplayName("when total race time is one - get strategies")
        @Test
        void whenTotalRaceTimeIsOne_getStrategies() {
            final long totalRaceTime = 1L;

            final ToyShipRace toyShipRace = new ToyShipRace(totalRaceTime);
            final List<RaceStrategy> strategies = toyShipRace.getStrategies();

            assertThat(strategies).isNotEmpty();
            assertThat(strategies.get(0).getButtonPressTime()).isEqualTo(0L);
            assertThat(strategies.get(0).getDistanceMoved()).isEqualTo(0L);
            assertThat(strategies.get(1).getButtonPressTime()).isEqualTo(1L);
            assertThat(strategies.get(1).getDistanceMoved()).isEqualTo(0L);
        }

        @DisplayName("when total race time is two - get strategies")
        @Test
        void whenTotalRaceTimeIsTwo_getStrategies() {
            final long totalRaceTime = 2L;

            final ToyShipRace toyShipRace = new ToyShipRace(totalRaceTime);
            final List<RaceStrategy> strategies = toyShipRace.getStrategies();

            assertThat(strategies).hasSize(3);
            assertThat(strategies.get(0).getButtonPressTime()).isEqualTo(0L);
            assertThat(strategies.get(0).getDistanceMoved()).isEqualTo(0L);
            assertThat(strategies.get(1).getButtonPressTime()).isEqualTo(1L);
            assertThat(strategies.get(1).getDistanceMoved()).isEqualTo(1L);
            assertThat(strategies.get(2).getButtonPressTime()).isEqualTo(2L);
            assertThat(strategies.get(2).getDistanceMoved()).isEqualTo(0L);
        }

        @DisplayName("test abrigded case 1")
        @Test
        void testAbrigdedCase1() {
            final long totalRaceTime = 7L;
            final long highScore = 9L;

            final ToyShipRace toyShipRace = new ToyShipRace(totalRaceTime);
            final List<RaceStrategy> winningStrategies = toyShipRace.getWinningStrategiesWithHigherScoreThan(highScore);

            assertThat(winningStrategies).hasSize(4);
        }

        @DisplayName("test abrigded case 2")
        @Test
        void testAbridgedCase2() {
            final long totalRaceTime = 15L;
            final long highScore = 40L;

            final ToyShipRace toyShipRace = new ToyShipRace(totalRaceTime);
            final List<RaceStrategy> winningStrategies = toyShipRace.getWinningStrategiesWithHigherScoreThan(highScore);

            assertThat(winningStrategies).hasSize(8);
        }

        @DisplayName("Solve toy ship race problem")
        @Test
        void solveToyShipRaceProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/toyships/input"));

            final List<ToyShipRace> races = extractRaces(lines);
            final List<Long> distances = extractDistances(lines);

            long result = 1;
            for (int i = 0; i <= distances.size() - 1; i++) {
                final ToyShipRace race = races.get(i);
                final Long highScore = distances.get(i);
                result *= race.getWinningStrategiesWithHigherScoreThan(highScore).size();
            }

            System.out.println(result);
        }

        private List<ToyShipRace> extractRaces(final List<String> lines) {
            String[] numbers = lines.get(0).split(":")[1].split(" ");
            return Arrays.stream(numbers).filter(StringUtils::isNotBlank)
                    .map(duration -> Long.parseLong(duration.trim()))
                    .map(ToyShipRace::new)
                    .toList();
        }

        private List<Long> extractDistances(final List<String> lines) {
            return Arrays.stream(lines.get(1).split(":")[1].split(" ")).filter(StringUtils::isNotBlank)
                    .map(duration -> Long.parseLong(duration.trim()))
                    .toList();
        }

        @DisplayName("Solve bad kerning toy ship race problem")
        @Test
        void solveBadKerningToyShipRaceProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/toyships/input"));

            final SimpleToyShipRace race = extractRaceWithBadKerning(lines);
            final Long highScore = extractDistanceWithBadKerning(lines);

            final long result = race.getWinningStrategyCountWithHigherScoreThan(highScore);

            System.out.println(result);
        }


        private SimpleToyShipRace extractRaceWithBadKerning(final List<String> lines) {
            String[] numbers = lines.get(0).split(":")[1].split(" ");
            final String number = Arrays.stream(numbers).filter(StringUtils::isNotBlank)
                    .map(String::trim).collect(Collectors.joining());

            return new SimpleToyShipRace(Long.parseLong(number));
        }

        private Long extractDistanceWithBadKerning(final List<String> lines) {
            final String number = Arrays.stream(lines.get(1).split(":")[1].split(" ")).filter(StringUtils::isNotBlank)
                    .map(String::trim).collect(Collectors.joining());

            return Long.parseLong(number);
        }
    }

}
