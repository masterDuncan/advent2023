package advent.camelpoker;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Camel Poker Joker Test")
public class CamelPokerJokerTest {

    @DisplayName("Ascertain card hand power")
    @Nested
    class AscertainCardHandPower {

        @DisplayName("test for five identical cards - return five of a kind")
        @Test
        void testForFiveIdenticalCards_returnFiveOfAKind() {
            final String hand = "AAAAA";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for four identical cards and one joker - return five of a kind")
        @Test
        void testForFourIdenticalCardsAndOneJoker_returnFiveOfAKind() {
            final String hand = "AAAAJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for three identical cards and two jokers - return five of a kind")
        @Test
        void testForThreeIdenticalCardsAndTwoJokers_returnFiveOfAKind() {
            final String hand = "AAJAJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for two identical cards and three jokers - return five of a kind")
        @Test
        void testForTwoIdenticalCardsAndThreeJokers_returnFiveOfAKind() {
            final String hand = "AAJJJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for one card and four jokers - return five of a kind")
        @Test
        void testForOneCardAndFourJokers_returnFiveOfAKind() {
            final String hand = "JAJJJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for five jokers - return five of a kind")
        @Test
        void testForFiveOfAKind_fiveJokers() {
            final String hand = "JJJJJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FIVE_OF_A_KIND);
        }

        @DisplayName("test for four identical cards - return four of a kind")
        @Test
        void testForFourOfAKind() {
            final String hand = "AAAA6";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FOUR_OF_A_KIND);
        }

        @DisplayName("test for three identical cards - return three of a kind")
        @Test
        void testForFourOfAKind_oneJokerIsNotTheUniqueCard() {
            final String hand = "AA6A5";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.THREE_OF_A_KIND);
        }

        @DisplayName("test for three identical cards and one joker - return four of a kind")
        @Test
        void testForThreeIdenticalCardsAndOneJoker_returnFourOfAKind() {
            final String hand = "AA6AJ";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FOUR_OF_A_KIND);
        }

        @DisplayName("test for three identical cards and a pair - return full house")
        @Test
        void testForThreeIdenticalCardsAndAPair_returnFullHouse() {
            final String hand = "AA6A6";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FULL_HOUSE);
        }

        @DisplayName("test for two identical cards - return one pair")
        @Test
        void testForTwoIdenticalCards_returnOnePair() {
            final String hand = "KT224";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.ONE_PAIR);
        }

        @DisplayName("test for two identical cards and a joker - return three of a kind")
        @Test
        void testForTwoIdenticalCardsAndAJoker_returnThreeOfAKind() {
            final String hand = "KJ224";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.THREE_OF_A_KIND);
        }

        @DisplayName("test for two pairs of identical cards - return two pairs")
        @Test
        void testForTwoPairsIdenticalCards_returnTwoPairs() {
            final String hand = "K4224";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.TWO_PAIR);
        }

        @DisplayName("test for two pairs of identical cards and a joker - return full house")
        @Test
        void testForTwoPairsIdenticalCardsAndAJoker_returnFullHouse() {
            final String hand = "J4224";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FULL_HOUSE);
        }

        @DisplayName("test two identical cards and two jokers - return four of a kind")
        @Test
        void testTwoIdenticalCardsAndTwoJoker_returnFourOfAKind() {
            final String hand = "JJ228";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FOUR_OF_A_KIND);
        }

        @DisplayName("test all different and two jokers - return three of a kind")
        @Test
        void testAllDifferentAndTwoJokers_returnThreeOfAKind() {
            final String hand = "JJ267";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.THREE_OF_A_KIND);
        }

        @DisplayName("test all different and one joker - return one pair")
        @Test
        void testAllDifferentAndAJoker_returnOnePair() {
            final String hand = "J5267";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.ONE_PAIR);
        }

        @DisplayName("test all different - return high card")
        @Test
        void testAllDifferent_returnHighCard() {
            final String hand = "K5267";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.HIGH_CARD);
        }

        @DisplayName("test anomaly 1")
        @Test
        void testAnomaly_1() {
            final String hand = "J3J4J";
            final CardTypeJoker cardType = CardTypeJoker.fromHand(Utils.extractJokerHand(hand));

            assertThat(cardType).isEqualTo(CardTypeJoker.FOUR_OF_A_KIND);
        }

        @DisplayName("Solve camel poker with joker problem")
        @Test
        void solveCamelPokerWithJokerProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/camelpoker/input"));
            final List<CamelPokerBidJoker> camelPokerBids = Lists.newArrayList();
            for (final String line : lines) {
                final String[] parts = line.split(" ");

                camelPokerBids.add(new CamelPokerBidJoker(new CamelPokerHandJoker(parts[0]), Integer.parseInt(parts[1])));
            }

            Collections.sort(camelPokerBids);

            int rank = 1;
            for (final CamelPokerBidJoker bid : camelPokerBids) {
                System.out.println(rank + ": " + bid.getHand() + " is " + CardTypeJoker.fromHand(bid.getHand().getHand()));
                rank++;
            }

            int result = 0;
            int index = 1;
            for (final CamelPokerBidJoker camelPokerBid : camelPokerBids) {
                final int bid = camelPokerBid.getBid();
                result += bid * index;
                index++;
            }

            System.out.println(result);
        }
    }

}
