package advent.camelpoker;

public enum CardSuit {
    A("A", 14),
    K("K", 13),
    Q("Q", 12),
    J("J", 11),
    T("T", 10),
    NINE("9", 9),
    EIGHT("8", 8),
    SEVEN("7", 7),
    SIX("6", 6),
    FIVE("5", 5),
    FOUR("4", 4),
    THREE("3", 3),
    TWO("2", 2);

    private final String representation;
    private final int points;

    CardSuit(final String representation, final int points) {
        this.representation = representation;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public static CardSuit forSuit(String suit) {
        for (final CardSuit cardSuit : CardSuit.values()) {
            if (cardSuit.representation.equalsIgnoreCase(suit)) {
                return cardSuit;
            }
        }

        return null;
    }
}
