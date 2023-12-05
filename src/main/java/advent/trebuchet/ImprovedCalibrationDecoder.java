package advent.trebuchet;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ImprovedCalibrationDecoder {

    public int decode(final String line) {
        final List<Integer> digits = extractDigits(line);
        if (digits.isEmpty()) {
            return 0;
        }

        if (digits.size() == 1) {
            final int digit = digits.get(0);
            return digit * 10 + digit;
        }

        final int firstDigit = digits.get(0);
        final int lastDigit = digits.get(digits.size() - 1);

        return firstDigit * 10 + lastDigit;
    }

    private List<Integer> extractDigits(final String line) {
        final List<Integer> digits = Lists.newArrayList();
        int index = 0;
        while (index <= line.length() - 1) {

            final DigitData digitData = extractDigitFromPosition(line, index);
            if (digitData.getValue() != 0) {
                digits.add(digitData.getValue());
            }

            index += digitData.getIncrement();
        }

        return digits;
    }

    DigitData extractDigitFromPosition(final String line, final int positionInLine) {
        if (positionInLine < 0 || positionInLine >= line.length()) {
            return new DigitData(0, 1);
        }

        final char characterInPosition = line.charAt(positionInLine);
        if (Character.isDigit(characterInPosition)) {
            return new DigitData(Integer.parseInt(Character.valueOf(characterInPosition).toString()), 1);
        }

        final Pair<Integer, Integer> stringDigit = extractPossibleStringDigit(line, positionInLine);
        final int stringDigitValue = stringDigit.getKey();
        final int increment = stringDigit.getValue();

        return new DigitData(stringDigitValue, increment);
    }

    private Pair<Integer, Integer> extractPossibleStringDigit(final String line, final int positionInLine) {
        final String possibleStringDigit = line.substring(positionInLine, capToLineEnd(line.length(), positionInLine
                + Utils.LONGEST_STRING_DIGIT_SIZE));
        final String stringDigit = Utils.STRING_DIGIT_VALUES.keySet().stream()
                .filter(key -> lineContainsKeyFromStart(possibleStringDigit, key))
                .findFirst().orElse("");
        if (stringDigit.isEmpty()) {
            return Pair.of(0, 1);
        }

        final boolean first = isFirst(line, positionInLine);
        final int value = first ? Utils.STRING_DIGIT_VALUES.get(stringDigit).getKey() :
                Utils.STRING_DIGIT_VALUES.get(stringDigit).getValue();

        return Pair.of(value, stringDigit.length());
    }

    private boolean lineContainsKeyFromStart(final String line, final String key) {
        final int indexEnd = capToLineEnd(line.length(), key.length());
        final String substring = line.substring(0, indexEnd);

        return substring.contains(key);
    }

    private int capToLineEnd(final int lineSize, final int endPosition) {
        return Math.min(endPosition, lineSize);
    }

    private boolean isFirst(final String line, final int positionInLine) {
        final String start = line.substring(0, positionInLine);
        if (containsDigits(start)) { // There are digits before
            return false;
        }

        return Utils.STRING_DIGITS.stream().noneMatch(start::contains); // There are string digits before
    }

    private boolean containsDigits(final String line) {
        return Utils.STRING_DIGIT_LENGTH.keySet().stream().map(Object::toString).anyMatch(line::contains);
    }
}
