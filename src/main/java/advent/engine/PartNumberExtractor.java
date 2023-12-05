package advent.engine;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PartNumberExtractor {
    private final String schematic;
    private final int lineSize;

    public PartNumberExtractor(final List<String> lines) {
        this.schematic = String.join("", lines);
        this.lineSize = lines.isEmpty() ? 0 : lines.get(0).length();
    }

    public Map<Symbol, List<Integer>> extractPartNumbersForSymbols() {
        final Map<Symbol, List<Integer>> result = Maps.newHashMap();

        final List<Integer> symbolPositions = getSymbolPositions();
        for (final int position : symbolPositions) {

            final List<Integer> partNumbers = Lists.newArrayList();
            getLeftPartNumber(position).ifPresent(partNumbers::add);
            getRightPartNumber(position).ifPresent(partNumbers::add);
            partNumbers.addAll(getTopPartNumber(position));
            partNumbers.addAll(getBottomPartNumber(position));

            final char symbol = schematic.charAt(position);
            System.out.println(symbol + " (" + partNumbers.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")");

            result.put(new Symbol(symbol, position), partNumbers);
        }

        return result;
    }

    private List<Integer> getSymbolPositions() {
        final List<Integer> symbolPositions = Lists.newArrayList();
        int index = 0;
        for (final char character : schematic.chars().mapToObj(c -> (char) c).toList()) {
            if (Utils.isSymbol(character)) {
                symbolPositions.add(index);
            }

            index++;
        }

        return symbolPositions;
    }

    private Optional<Integer> getLeftPartNumber(final int symbolPosition) {
        String partNumber = "";
        char character;
        int index = symbolPosition;
        do {

            if (index - 1 < 0) {
                break;
            }

            character = getLeftChar(index);
            if (Character.isDigit(character)) {
                partNumber = String.valueOf(character).concat(partNumber);
            }

            index--;

        } while (Character.isDigit(character));

        if (!StringUtils.isNumeric(partNumber)) {
            return Optional.empty();
        }

        return Optional.of(Integer.parseInt(partNumber));
    }

    private Optional<Integer> getRightPartNumber(final int symbolPosition) {
        String partNumber = "";
        char character;
        int index = symbolPosition;
        do {

            if (index + 2 > schematic.length() - 1) {
                break;
            }

            character = getRightChar(index);
            if (Character.isDigit(character)) {
                partNumber = partNumber.concat(String.valueOf(character));
            }

            index++;

        } while (Character.isDigit(character));

        if (!StringUtils.isNumeric(partNumber)) {
            return Optional.empty();
        }

        return Optional.of(Integer.parseInt(partNumber));
    }

    private char getLeftChar(final int index) {
        final char[] array = new char[1];
        schematic.getChars(index - 1, index, array, 0);
        return array[0];
    }

    private char getRightChar(final int index) {
        final char[] array = new char[1];
        schematic.getChars(index + 1, index + 2, array, 0);
        return array[0];
    }

    private List<Integer> getTopPartNumber(final int symbolPosition) {
        if (symbolPosition - lineSize < 0) {
            return Collections.emptyList(); // We are on the first line
        }

        final int topMinIndex = symbolPosition - lineSize - 1 + getMinLineAdjustment(symbolPosition);
        final int topMaxIndex = symbolPosition - lineSize + 2 + getMaxLineAdjustment(symbolPosition);
        final String possiblePartNumbersOnTop = schematic.substring(topMinIndex, topMaxIndex);

        return Utils.getNumbersFromSegment(schematic, possiblePartNumbersOnTop, topMinIndex, topMaxIndex - 1);
    }

    private List<Integer> getBottomPartNumber(final int symbolPosition) {
        if (symbolPosition + lineSize > schematic.length() - 1) {
            return Collections.emptyList(); // We are on the last line
        }

        final int bottomMinIndex = symbolPosition + lineSize - 1 + getMinLineAdjustment(symbolPosition);
        final int bottomMaxIndex = symbolPosition + lineSize + 2 + getMaxLineAdjustment(symbolPosition);
        final String possiblePartNumbersOnBottom = schematic.substring(bottomMinIndex, bottomMaxIndex);

        return Utils.getNumbersFromSegment(schematic, possiblePartNumbersOnBottom, bottomMinIndex, bottomMaxIndex - 1);
    }

    private int getMinLineAdjustment(final int symbolPosition) {
        if (symbolPosition % lineSize == 0) {
            // We are on the beginning of the line
            return 1;
        }

        return 0;
    }

    private int getMaxLineAdjustment(final int symbolPosition) {
        if (symbolPosition % lineSize == lineSize - 1) {
            // We are on the end of the line
            return -1;
        }

        return 0;
    }

}
