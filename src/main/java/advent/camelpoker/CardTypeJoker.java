package advent.camelpoker;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public enum CardTypeJoker {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1);

    private final int power;

    CardTypeJoker(final int power) {
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    static CardTypeJoker fromHand(final List<CardSuitJoker> hand) {
        final Map<CardSuitJoker, Integer> cardCounts = Maps.newHashMap();
        hand.forEach(cardSuit -> cardCounts.merge(cardSuit, 1, Integer::sum));
        final int highestCardSuitAmount = cardCounts.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        final long jokers = getAmountOfJokers(cardCounts);
        final long pairs = getAmountOfPairs(cardCounts);

        if (highestCardSuitAmount == 5) {
            return FIVE_OF_A_KIND;
        }

        if (highestCardSuitAmount == 4) {
            if (jokers == 1 || jokers == 4) {
                return FIVE_OF_A_KIND;
            }
            return FOUR_OF_A_KIND;
        }

        if (highestCardSuitAmount == 3) {
            if (jokers == 3 && pairs == 1) { // JJJAA
                return FIVE_OF_A_KIND;
            }

            if (jokers == 3 && pairs == 0) { // JJJ1A
                return FOUR_OF_A_KIND;
            }

            if (jokers == 2) { // JJTTT
                return FIVE_OF_A_KIND;
            }

            if (jokers == 1) { // JKKK1
                return FOUR_OF_A_KIND;
            }

            if (pairs == 1) {
                return FULL_HOUSE;
            }
            return THREE_OF_A_KIND;
        }

        if (highestCardSuitAmount == 2) {
            if (jokers == 2 && pairs == 2) { // JJKKA
                return FOUR_OF_A_KIND;
            }

            if (jokers == 2 && pairs == 1) { // JJK1A
                return THREE_OF_A_KIND;
            }

            if (jokers == 1 && pairs == 2) { // TT11J
                return FULL_HOUSE;
            }

            if (jokers == 1 && pairs == 1) { // JAA12
                return THREE_OF_A_KIND;
            }

            if (pairs == 2) {
                return TWO_PAIR;
            } else {
                return ONE_PAIR;
            }
        }

        if (highestCardSuitAmount == 1) {
            if (jokers == 1) {
                return ONE_PAIR;
            }
            return HIGH_CARD;
        }

        return null;
    }

    private static long getAmountOfJokers(Map<CardSuitJoker, Integer> cardCounts) {
        return cardCounts.getOrDefault(CardSuitJoker.J, 0);
    }

    private static long getAmountOfPairs(Map<CardSuitJoker, Integer> cardCounts) {
        return cardCounts.values().stream().filter(count -> count == 2).mapToInt(Integer::intValue).count();
    }
}
