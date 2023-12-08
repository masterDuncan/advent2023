package advent.camelpoker;

import java.util.Objects;

public class CamelPokerBidJoker implements Comparable<CamelPokerBidJoker> {

    private final CamelPokerHandJoker hand;
    private final int bid;

    public CamelPokerBidJoker(final CamelPokerHandJoker hand, final int bid) {
        this.hand = hand;
        this.bid = bid;
    }

    public CamelPokerHandJoker getHand() {
        return hand;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(CamelPokerBidJoker other) {
        return this.hand.compareTo(other.hand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CamelPokerBidJoker that = (CamelPokerBidJoker) o;
        return Objects.equals(hand, that.hand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hand);
    }

    @Override
    public String toString() {
        return "CamelPokerBet{" +
                "hand=" + hand +
                ", bid=" + bid +
                '}';
    }
}
