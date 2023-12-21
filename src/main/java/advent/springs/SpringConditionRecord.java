package advent.springs;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class SpringConditionRecord {

    private final String record;
    private final List<Integer> defectPattern;

    public SpringConditionRecord(final String input) {
        final String[] parts = input.split(" ");
        this.record = parts[0];

        final String[] defectPatterns = parts[1].split(",");
        this.defectPattern = Arrays.stream(defectPatterns).map(Integer::parseInt).toList();
    }

    public List<String> calculateValidArrangements() {
        final List<String> result = Lists.newArrayList();

        final List<String> permutations = RecordUtils.getPermutations(record);
        for (final String permutation : permutations) {
            final List<Integer> permutationDefectPattern = RecordUtils.extractDefectPattern(permutation);
            if (permutationDefectPattern.equals(defectPattern)) {
                result.add(permutation);
            }
        }

        return result;
    }
}
