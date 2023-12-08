package advent.camelpoker;

import java.util.List;
import java.util.Objects;

public class CamelPokerHandJoker implements Comparable<CamelPokerHandJoker> {

    private final List<CardSuitJoker> hand;

    public CamelPokerHandJoker(final String components) {
        this.hand = Utils.extractJokerHand(components);
    }

    public List<CardSuitJoker> getHand() {
        return hand;
    }

    @Override
    public int compareTo(CamelPokerHandJoker other) {
        final int comparison = CardTypeJoker.fromHand(hand).getPower().compareTo(CardTypeJoker.fromHand(other.hand).getPower());
        if (comparison != 0) {
            return comparison;
        }

        for (int i = 0; i <= hand.size() - 1; i++) {
            final CardSuitJoker ourSuit = hand.get(i);
            final CardSuitJoker theirSuit = other.hand.get(i);
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
        CamelPokerHandJoker that = (CamelPokerHandJoker) o;
        return Objects.equals(hand, that.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand);
    }

    @Override
    public String toString() {
        return "CamelPokerHandJoker{" +
                "hand=" + hand +
                '}';
    }
}
