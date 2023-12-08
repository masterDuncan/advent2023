package advent.camelpoker;

import java.util.List;
import java.util.Objects;

public class CamelPokerHand implements Comparable<CamelPokerHand> {

    private final List<CardSuit> hand;

    public CamelPokerHand(final String components) {
        this.hand = Utils.extractHand(components);
    }

    @Override
    public int compareTo(CamelPokerHand other) {
        final int comparison = CardType.fromHand(hand).getPower().compareTo(CardType.fromHand(other.hand).getPower());
        if (comparison != 0) {
            return comparison;
        }

        for (int i = 0; i <= hand.size() - 1; i++) {
            final CardSuit ourSuit = hand.get(i);
            final CardSuit theirSuit = other.hand.get(i);
            if (ourSuit.getPoints() > theirSuit.getPoints()) {
                return 1;
            } else if (ourSuit.getPoints() < theirSuit.getPoints()) {
                return -1;
            }
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CamelPokerHand that = (CamelPokerHand) o;
        return Objects.equals(hand, that.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand);
    }

    @Override
    public String toString() {
        return "CamelPokerHand{" +
                "hand=" + hand +
                '}';
    }
}
