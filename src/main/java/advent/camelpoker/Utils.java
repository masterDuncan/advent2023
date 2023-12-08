package advent.camelpoker;

import com.google.common.collect.Lists;

import java.util.List;

public class Utils {

    private Utils() {
    }

    public static List<CardSuit> extractHand(final String components) {
        final List<CardSuit> hand = Lists.newArrayList();
        for (final String suit : components.chars().mapToObj(c -> (char) c).map(Object::toString).toList()) {
            hand.add(CardSuit.forSuit(suit));
        }

        return hand;
    }

    public static List<CardSuitJoker> extractJokerHand(final String components) {
        final List<CardSuitJoker> hand = Lists.newArrayList();
        for (final String suit : components.chars().mapToObj(c -> (char) c).map(Object::toString).toList()) {
            hand.add(CardSuitJoker.forSuit(suit));
        }

        return hand;
    }
}
