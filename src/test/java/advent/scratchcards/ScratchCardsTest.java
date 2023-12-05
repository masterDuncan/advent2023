package advent.scratchcards;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Scratch Cards Test")
public class ScratchCardsTest {

    @DisplayName("Scratch Card Data")
    @Nested
    class ScratchCardData {

        private final int cardId = 1;

        @DisplayName("when there are no numbers - points are zero")
        @Test
        void whenThereAreNoNumbers_pointsAreZero() {

            final List<Integer> winningNumbers = Collections.emptyList();
            final List<Integer> numbersWeHave = Collections.emptyList();

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isZero();
        }

        @DisplayName("when there are no winning numbers - points are zero")
        @Test
        void whenThereAreNoWinningNumbers_pointsAreZero() {
            final List<Integer> winningNumbers = Collections.emptyList();
            final List<Integer> numbersWeHave = List.of(1);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isZero();
        }

        @DisplayName("when we have no numbers - points are zero")
        @Test
        void whenWeHaveNoNumbers_pointsAreZero() {
            final List<Integer> winningNumbers = List.of(1);
            final List<Integer> numbersWeHave = Collections.emptyList();

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isZero();
        }

        @DisplayName("when we have no numbers that win - points are zero")
        @Test
        void whenWeHaveNoNumbersThatWin_pointsAreZero() {
            final List<Integer> winningNumbers = List.of(1);
            final List<Integer> numbersWeHave = List.of(2);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isZero();
        }

        @DisplayName("when we have one number that win - points are 1")
        @Test
        void whenWeHaveOneNumberThatWin_pointsAreOne() {
            final List<Integer> winningNumbers = List.of(1);
            final List<Integer> numbersWeHave = List.of(1);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isEqualTo(1);
        }

        @DisplayName("when we have two numbers that win - points are 2")
        @Test
        void whenWeHaveTwoNumberThatWin_pointsAreTwo() {
            final List<Integer> winningNumbers = List.of(1, 2);
            final List<Integer> numbersWeHave = List.of(1, 2);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isEqualTo(2);
        }

        @DisplayName("when we have three numbers that win - points are 4")
        @Test
        void whenWeHaveThreeNumbersThatWin_pointsAreFour() {
            final List<Integer> winningNumbers = List.of(1, 2, 3);
            final List<Integer> numbersWeHave = List.of(1, 2, 3);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isEqualTo(4);
        }

        @DisplayName("when we have four numbers that win - points are 8")
        @Test
        void whenWeHaveFourNumbersThatWin_pointsAreFour() {
            final List<Integer> winningNumbers = List.of(1, 2, 3, 4);
            final List<Integer> numbersWeHave = List.of(1, 2, 3, 4);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final int points = scratchCard.calculatePoints();

            assertThat(points).isEqualTo(8);
        }

        @DisplayName("Solve scratch cards problem")
        @Test
        void solveScratchCardsProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/scratchcards/input"));

            final List<ScratchCard> scratchCards = Lists.newArrayList();
            for (final String line : lines) {
                scratchCards.add(readScratchCard(line));
            }

            final int result = scratchCards.stream().map(ScratchCard::calculatePoints).mapToInt(Integer::intValue).sum();
            System.out.println(result);
        }

    }


    @DisplayName("Scratch Card Replication")
    @Nested
    class ScratchCardReplication {

        @DisplayName("when card yields not points - no replication is needed")
        @Test
        void whenCardYieldsNoPoints_noReplicationIsNeeded() {
            final int cardId = 1;
            final List<Integer> winningNumbers = List.of(1);
            final List<Integer> numbersWeHave = List.of(2);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final List<Integer> replicatedIds = scratchCard.getReplicatedIds();

            assertThat(replicatedIds).isEmpty();
        }

        @DisplayName("when card yields one point - replicate following card id")
        @Test
        void whenCardYieldsOnePoint_replicateFollowingCardId() {
            final int cardId = 1;
            final List<Integer> winningNumbers = List.of(1);
            final List<Integer> numbersWeHave = List.of(1);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final List<Integer> replicatedIds = scratchCard.getReplicatedIds();

            assertThat(replicatedIds).hasSize(1);
            assertThat(replicatedIds).contains(2);
        }

        @DisplayName("when card yields two points - replicate following 2 card ids")
        @Test
        void whenCardYieldsTwoPoints_replicate2FollowingCardIds() {
            final int cardId = 1;
            final List<Integer> winningNumbers = List.of(1, 2);
            final List<Integer> numbersWeHave = List.of(1, 2);

            final ScratchCard scratchCard = new ScratchCard(cardId, winningNumbers, numbersWeHave);
            final List<Integer> replicatedIds = scratchCard.getReplicatedIds();

            assertThat(replicatedIds).hasSize(2);
            assertThat(replicatedIds).contains(2);
            assertThat(replicatedIds).contains(3);
        }

    }

    private ScratchCard readScratchCard(final String line) {
        final String[] parts = line.split(":");

        final String[] cardInfo = parts[0].split(" ");
        final int cardId = Integer.parseInt(cardInfo[cardInfo.length - 1]);

        final String[] numbers = parts[1].split("\\|");
        final String winNums = numbers[0];
        final String ourNums = numbers[1];

        final List<Integer> winningNumbers = Arrays.stream(winNums.split(" ")).filter(entry -> !entry.isBlank())
                .map(Integer::parseInt).toList();
        final List<Integer> ourNumbers = Arrays.stream(ourNums.split(" ")).filter(entry -> !entry.isBlank())
                .map(Integer::parseInt).toList();

        return new ScratchCard(cardId, winningNumbers, ourNumbers);
    }

}
