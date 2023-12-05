package advent.engine;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {
    }

    public static List<Pair<Integer, Integer>> extractPossiblePartNumbers(final List<String> lines) {
        final List<Pair<Integer, Integer>> partsAndIndexes = Lists.newArrayList();
        final String schematic = String.join("", lines);
        final List<Character> characters = schematic.chars().mapToObj(c -> (char) c).toList();

        int index = 0;
        while (index <= characters.size() - 1) {
            final char character = characters.get(index);

            if (Character.isDigit(character)) {
                final String partNumber = getPartNumberFromIndex(index, schematic);
                partsAndIndexes.add(Pair.of(Integer.valueOf(partNumber), index));
                index += partNumber.length();

                continue;
            }

            index++;
        }

        return partsAndIndexes;
    }

    private static String getPartNumberFromIndex(final int index, final String schematic) {
        String partNumber = "";

        int innerIndex = index;
        char character;
        do {
            if (innerIndex > schematic.length() - 1) {
                break;
            }

            character = schematic.charAt(innerIndex);
            if (Character.isDigit(character)) {
                partNumber = partNumber.concat(Character.valueOf(character).toString());
            }
            innerIndex++;

        } while (Character.isDigit(character));

        return partNumber;
    }

    static boolean isSymbol(final char character) {
        if (character == '.') {
            return false;
        }

        return !Character.isDigit(character);
    }

    static List<Integer> getNumbersFromSegment(final String schematic, final String segment, final int minIndex, final int maxIndex) {
        final List<Integer> result = Lists.newArrayList();

        if (segment.isEmpty() || allDots(segment)) {
            return Collections.emptyList();
        }

        if (StringUtils.isNumeric(segment)) {
            return List.of(Integer.parseInt(segment));
        }

        if (segment.length() == 2) {
            if (segment.charAt(0) == '.' && Character.isDigit(segment.charAt(1))) {
                result.add(searchPartNumberFromIndex(schematic, minIndex + 1));
            } else if (segment.charAt(1) == '.' && Character.isDigit(segment.charAt(0))) {
                result.add(searchReversePartNumberFromIndex(schematic, maxIndex - 1));
            }
        }

        if (segment.length() == 3) {
            if (segment.charAt(0) == '.' && Character.isDigit(segment.charAt(1))) {

                result.add(searchPartNumberFromIndex(schematic, minIndex + 1));

            } else if (segment.charAt(0) == '.' && segment.charAt(1) == '.' && Character.isDigit(segment.charAt(2))) {

                result.add(searchPartNumberFromIndex(schematic, minIndex + 2));

            } else if (Character.isDigit(segment.charAt(1)) && segment.charAt(2) == '.') {

                result.add(searchReversePartNumberFromIndex(schematic, maxIndex - 1));

            } else if (Character.isDigit(segment.charAt(0)) && segment.charAt(1) == '.' && segment.charAt(2) == '.') {

                result.add(searchReversePartNumberFromIndex(schematic, maxIndex - 2));

            } else if (Character.isDigit(segment.charAt(0)) && segment.charAt(1) == '.' && Character.isDigit(segment.charAt(2))) {

                result.add(searchReversePartNumberFromIndex(schematic, maxIndex - 2));
                result.add(searchPartNumberFromIndex(schematic, minIndex + 2));

            }
        }

        return result;
    }

    private static boolean allDots(final String segment) {
        for (final char character : segment.chars().mapToObj(c -> (char) c).toList()) {
            if (character != '.') {
                return false;
            }
        }

        return true;
    }

    private static int searchPartNumberFromIndex(final String schematic, final int init) {
        final List<Character> numberInChars = Lists.newArrayList();
        for (int i = init; i <= schematic.length() - 1; i++) {
            final char character = schematic.charAt(i);
            if (Character.isDigit(character)) {
                numberInChars.add(character);
            } else {
                break;
            }
        }

        return Integer.parseInt(numberInChars.stream().map(Object::toString).collect(Collectors.joining()));
    }

    private static Integer searchReversePartNumberFromIndex(final String schematic, final int init) {
        final List<Character> numberInChars = Lists.newArrayList();
        for (int i = init; i >= 0; i--) {
            final char character = schematic.charAt(i);
            if (Character.isDigit(character)) {
                numberInChars.add(character);
            } else {
                break;
            }
        }

        Collections.reverse(numberInChars);
        return Integer.parseInt(numberInChars.stream().map(Object::toString).collect(Collectors.joining()));
    }
}
