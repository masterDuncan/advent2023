package advent.trebuchet;

import java.util.List;

public class CalibrationDecoder {
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
        return line.chars().mapToObj(c -> (char) c).filter(Character::isDigit)
                .map(character -> Integer.parseInt(character.toString())).toList();
    }
}
