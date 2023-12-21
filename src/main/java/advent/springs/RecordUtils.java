package advent.springs;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class RecordUtils {

    private RecordUtils() {
    }

    public static List<String> getPermutations(final String record) {
        final List<String> result = Lists.newArrayList();

        if (!record.contains("?")) {
            return List.of(record);
        }

        final List<Integer> malfunctionIndexes = getMalfunctionIndexes(record);
        final List<String> permutations = getPermutationsFor(malfunctionIndexes.size());

        for (final String permutation : permutations) {
            String operatedOn = record;
            for (int i = 0; i <= malfunctionIndexes.size() - 1; i++) {
                char charReplacing = permutation.charAt(i);
                int indexToReplace = malfunctionIndexes.get(i);
                operatedOn = replaceAt(indexToReplace, charReplacing, operatedOn);
            }
            result.add(operatedOn);
        }

        return result;
    }

    private static List<Integer> getMalfunctionIndexes(final String record) {
        final List<Integer> result = Lists.newArrayList();
        for (int i = 0; i <= record.length() - 1; i++) {
            if (record.charAt(i) == '?') {
                result.add(i);
            }
        }

        return result;
    }

    static List<String> getPermutationsFor(final int numberOfElements) {
        final List<String> result = Lists.newArrayList();

        if (numberOfElements <= 0) {
            return Collections.emptyList();
        }

        for (int i = 0; i <= Math.pow(2, numberOfElements) - 1; i++) {
            final String permutation = zeroPad(Integer.toBinaryString(i), numberOfElements);
            final String translated = permutation.replace('0', '.').replace('1', '#');
            result.add(translated);
        }

        return result;
    }

    private static String zeroPad(final String binaryString, final int max) {
        if (max - binaryString.length() != 0) {
            return String.valueOf(getZeros(max - binaryString.length())) + binaryString;
        }

        return binaryString;
    }

    private static char[] getZeros(int i) {
        final char[] zeros = new char[i];
        for (int j = 0; j <= i - 1; j++) {
            zeros[j] = '0';
        }

        return zeros;
    }

    private static String replaceAt(final int index, final char replacing, final String toOperateOn) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= toOperateOn.length() - 1; i++) {
            if (i == index) {
                stringBuilder.append(replacing);
            } else {
                stringBuilder.append(toOperateOn.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    static List<Integer> extractDefectPattern(final String record) {
        final List<Integer> result = Lists.newArrayList();

        int adjacentCount = 0;
        boolean counting = false;
        for (int i = 0; i <= record.length() - 1; i++) {
            if (record.charAt(i) == '#') {
                adjacentCount++;
                counting = true;
            } else {
                if (counting) {
                    counting = false;
                    result.add(adjacentCount);
                    adjacentCount = 0;
                }
            }
        }

        if (counting) {
            result.add(adjacentCount);
        }

        return result;
    }
}
