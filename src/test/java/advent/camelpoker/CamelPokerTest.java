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

@DisplayName("Camel Poker Test")
public class CamelPokerTest {

    @DisplayName("Ascertain card hand power")
    @Nested
    class AscertainCardHandPower {

        @DisplayName("test for five of a kind")
        @Test
        void testForFiveOfAKind() {
            final String hand = "AAAAA";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.FIVE_OF_A_KIND);
        }

        @DisplayName("test for four of a kind")
        @Test
        void testForFourOfAKind() {
            final String hand = "TAAAA";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.FOUR_OF_A_KIND);
        }

        @DisplayName("test for full house")
        @Test
        void testForFullHouse() {
            final String hand = "AAKKA";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.FULL_HOUSE);
        }

        @DisplayName("test for three of a kind")
        @Test
        void testForThreeOfAKind() {
            final String hand = "TJAAA";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.THREE_OF_A_KIND);
        }

        @DisplayName("test for two pair")
        @Test
        void testForTwoPair() {
            final String hand = "AAKK4";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.TWO_PAIR);
        }

        @DisplayName("test for one pair")
        @Test
        void testForOnePair() {
            final String hand = "A5KK4";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.ONE_PAIR);
        }

        @DisplayName("test for high card")
        @Test
        void testForHighCard() {
            final String hand = "A5KJ4";
            final CardType cardType = CardType.fromHand(Utils.extractHand(hand));

            assertThat(cardType).isEqualTo(CardType.HIGH_CARD);
        }

        @DisplayName("compare equal hands")
        @Test
        void compareEqualHands() {
            final String hand1 = "A5KJ4";
            final String hand2 = "A5KJ4";

            final CamelPokerHand camelPokerHand1 = new CamelPokerHand(hand1);
            final CamelPokerHand camelPokerHand2 = new CamelPokerHand(hand2);

            assertThat(camelPokerHand1.compareTo(camelPokerHand2)).isZero();
        }

        @DisplayName("compare hand we win by type")
        @Test
        void compareHandsWeWinByType() {
            final String hand1 = "AAACC";
            final String hand2 = "JJJ12";

            final CamelPokerHand camelPokerHand1 = new CamelPokerHand(hand1);
            final CamelPokerHand camelPokerHand2 = new CamelPokerHand(hand2);

            assertThat(camelPokerHand1.compareTo(camelPokerHand2)).isOne();
        }

        @DisplayName("compare hand we lose by type")
        @Test
        void compareHandsWeLoseByType() {
            final String hand1 = "AAACT";
            final String hand2 = "JJJ55";

            final CamelPokerHand camelPokerHand1 = new CamelPokerHand(hand1);
            final CamelPokerHand camelPokerHand2 = new CamelPokerHand(hand2);

            assertThat(camelPokerHand1.compareTo(camelPokerHand2)).isEqualTo(-1);
        }

        @DisplayName("compare hand we win by suit")
        @Test
        void compareHandsWeWinBySuit() {
            final String hand1 = "TTT32";
            final String hand2 = "TTT23";

            final CamelPokerHand camelPokerHand1 = new CamelPokerHand(hand1);
            final CamelPokerHand camelPokerHand2 = new CamelPokerHand(hand2);

            assertThat(camelPokerHand1.compareTo(camelPokerHand2)).isOne();
        }

        @DisplayName("compare hand we lose by suit")
        @Test
        void compareHandsWeLoseBySuit() {
            final String hand1 = "TTT32";
            final String hand2 = "TTT43";

            final CamelPokerHand camelPokerHand1 = new CamelPokerHand(hand1);
            final CamelPokerHand camelPokerHand2 = new CamelPokerHand(hand2);

            assertThat(camelPokerHand1.compareTo(camelPokerHand2)).isEqualTo(-1);
        }

        @DisplayName("Solve camel poker problem")
        @Test
        void solveCamelPokerProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/camelpoker/input"));
            final List<CamelPokerBid> camelPokerBids = Lists.newArrayList();
            for (final String line : lines) {
                final String[] parts = line.split(" ");
                camelPokerBids.add(new CamelPokerBid(new CamelPokerHand(parts[0]), Integer.parseInt(parts[1])));
            }

            Collections.sort(camelPokerBids);

            int result = 0;
            int index = 1;
            for (final CamelPokerBid camelPokerBid : camelPokerBids) {
                final int bid = camelPokerBid.getBid();
                result += bid * index;
                index++;
            }

            System.out.println(result);
        }

    }
}
