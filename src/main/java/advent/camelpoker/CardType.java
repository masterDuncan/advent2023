package advent.camelpoker;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public enum CardType {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIR(3),
    ONE_PAIR(2),
    HIGH_CARD(1);

    private final int power;

    CardType(final int power) {
        this.power = power;
    }

    public Integer getPower() {
        return power;
    }

    static CardType fromHand(final List<CardSuit> hand) {
        final Map<CardSuit, Integer> cardCounts = Maps.newHashMap();
        hand.forEach(cardSuit -> cardCounts.merge(cardSuit, 1, Integer::sum));
        final int highestCardSuitAmount = cardCounts.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        if (highestCardSuitAmount == 5) {
            return FIVE_OF_A_KIND;
        } else if (highestCardSuitAmount == 4) {
            return FOUR_OF_A_KIND;
        } else if (highestCardSuitAmount == 3) {
            if (getAmountOfPairs(cardCounts) == 1) {
                return FULL_HOUSE;
            }
            return THREE_OF_A_KIND;
        } else if (highestCardSuitAmount == 2) {
            if (getAmountOfPairs(cardCounts) == 2) {
                return TWO_PAIR;
            } else {
                return ONE_PAIR;
            }
        } else if (highestCardSuitAmount == 1) {
            return HIGH_CARD;
        }

        return FULL_HOUSE;
    }

    private static long getAmountOfPairs(Map<CardSuit, Integer> cardCounts) {
        return cardCounts.values().stream().filter(count -> count == 2).mapToInt(Integer::intValue).count();
    }
}
