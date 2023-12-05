package advent.scratchcards;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
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

    public List<Integer> getReplicatedIds() {
        final int points = calculatePoints();
        return IntStream.rangeClosed(cardId + 1, points + 1).boxed().toList();
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
}
