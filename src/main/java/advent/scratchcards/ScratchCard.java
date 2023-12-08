package advent.scratchcards;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ScratchCard {

    private final int cardId;
    private final List<Integer> winningNumbers;
    private final List<Integer> numbersWeHave;

    public ScratchCard(final int cardId, final List<Integer> winningNumbers, final List<Integer> numbersWeHave) {
        this.cardId = cardId;
        this.winningNumbers = winningNumbers;
        this.numbersWeHave = numbersWeHave;
    }

    public int getCardId() {
        return cardId;
    }

    public int calculateTotalCardsGenerated(final Map<Integer, ScratchCard> allCardsGroupedById) {
        final List<Integer> replicatedIds = getReplicatedIds();
        if (replicatedIds.isEmpty()) {
            return 1;
        }

        int total = 1;
        for (final int replicatedId : replicatedIds) {
            final ScratchCard replicatedCard = allCardsGroupedById.get(replicatedId);
            total += replicatedCard.calculateTotalCardsGenerated(allCardsGroupedById);
        }

        return total;
    }

    public List<Integer> getReplicatedIds() {
        final int points = getCommonNumbers().size();
        return IntStream.rangeClosed(cardId + 1, cardId + points).boxed().toList();
    }

    public int calculatePoints() {
        final List<Integer> commonNumbers = getCommonNumbers();
        if (commonNumbers.isEmpty()) {
            return 0;
        }

        if (commonNumbers.size() == 1) {
            return 1;
        }

        return (int) Math.pow(2, commonNumbers.size() - 1);
    }

    private List<Integer> getCommonNumbers() {
        return Sets.intersection(new HashSet<>(winningNumbers), new HashSet<>(numbersWeHave)).stream().toList();
    }

    @Override
    public String toString() {
        return "ScratchCard{" +
                "cardId=" + cardId +
                ", winningNumbers=" + winningNumbers +
                ", numbersWeHave=" + numbersWeHave +
                '}';
    }
}
