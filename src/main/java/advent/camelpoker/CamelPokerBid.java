package advent.camelpoker;

import java.util.Objects;

public class CamelPokerBid implements Comparable<CamelPokerBid> {

    private final CamelPokerHand hand;
    private final int bid;

    public CamelPokerBid(final CamelPokerHand hand, final int bid) {
        this.hand = hand;
        this.bid = bid;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public int compareTo(CamelPokerBid other) {
        return this.hand.compareTo(other.hand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CamelPokerBid that = (CamelPokerBid) o;
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
