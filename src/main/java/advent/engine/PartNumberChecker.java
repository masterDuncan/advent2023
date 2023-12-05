package advent.engine;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PartNumberChecker {
    private final String schematic;
    private final int lineSize;

    public PartNumberChecker(final List<String> lines) {
        this.schematic = String.join("", lines);
        this.lineSize = lines.isEmpty() ? 0 : lines.get(0).length();
    }

    public boolean isValidPartNumber(final int partNumber, final int indexOnSchematic) {
        if (schematic.isEmpty()) {
            return false;
        }

        final boolean symbolOnTheLeft = getSymbolOnTheLeft(indexOnSchematic).isPresent();
        final boolean symbolOnTheRight = getSymbolOnTheRight(partNumber, indexOnSchematic).isPresent();
        final boolean symbolOnTop = !getSymbolsOnTop(partNumber, indexOnSchematic).isEmpty();
        final boolean symbolOnBottom = !getSymbolsOnBottom(partNumber, indexOnSchematic).isEmpty();

        return symbolOnTheLeft || symbolOnTheRight || symbolOnTop || symbolOnBottom;
    }

    public List<Character> getAdjacentSymbolsForPartNumber(final int partNumber, final int indexOnSchematic) {
        if (schematic.isEmpty()) {
            return Collections.emptyList();
        }

        final Optional<Character> symbolOnTheLeft = getSymbolOnTheLeft(indexOnSchematic);
        final Optional<Character> symbolOnTheRight = getSymbolOnTheRight(partNumber, indexOnSchematic);
        final List<Character> symbolsOnTop = getSymbolsOnTop(partNumber, indexOnSchematic);
        final List<Character> symbolsOnBottom = getSymbolsOnBottom(partNumber, indexOnSchematic);

        final List<Character> symbols = Lists.newArrayList();
        symbolOnTheLeft.map(symbols::add);
        symbolOnTheRight.map(symbols::add);
        symbols.addAll(symbolsOnTop);
        symbols.addAll(symbolsOnBottom);

        return symbols;
    }

    private Optional<Character> getSymbolOnTheLeft(final int indexOnSchematic) {
        if (indexOnSchematic - 1 < 0) {
            return Optional.empty(); // This is the first character of a line
        }

        final int indexBeforePartNumber = indexOnSchematic - 1;
        final char possibleSymbol = getCharFromIndexOnTheLeft(indexBeforePartNumber);

        if (Utils.isSymbol(possibleSymbol)) {
            return Optional.of(possibleSymbol);
        } else {
            return Optional.empty();
        }
    }

    private char getCharFromIndexOnTheLeft(final int index) {
        final char[] array = new char[1];
        schematic.getChars(index, index + 1, array, 0);
        return array[0];
    }

    private Optional<Character> getSymbolOnTheRight(final int partNumber, final int indexOnSchematic) {
        final int numberOfDigits = partNumber == 0 ? 1 : (int) Math.log10(partNumber);
        if (indexOnSchematic + numberOfDigits >= schematic.length() - 1) {
            return Optional.empty(); // This is the last character of a line
        }

        final int indexAfterPartNumber = indexOnSchematic + numberOfDigits;
        final char possibleSymbol = getCharFromIndexOnTheRight(indexAfterPartNumber);

        if (Utils.isSymbol(possibleSymbol)) {
            return Optional.of(possibleSymbol);
        } else {
            return Optional.empty();
        }
    }

    private char getCharFromIndexOnTheRight(final int index) {
        final char[] array = new char[1];
        schematic.getChars(index + 1, index + 2, array, 0);
        return array[0];
    }

    private List<Character> getSymbolsOnTop(final int partNumber, final int indexOnSchematic) {
        if (indexOnSchematic - lineSize < 0) {
            return Collections.emptyList(); // We are on the first line
        }

        final int numberOfDigits = partNumber == 0 ? 1 : (int) Math.log10(partNumber);
        final int topMinIndex = indexOnSchematic - lineSize - 1;
        final int topMaxIndex = indexOnSchematic - lineSize + numberOfDigits + 2;
        final String possibleSymbolsOnTop = schematic.substring(topMinIndex, topMaxIndex);

        return possibleSymbolsOnTop.chars().mapToObj(c -> (char) c).filter(Utils::isSymbol).toList();
    }

    private List<Character> getSymbolsOnBottom(int partNumber, int indexOnSchematic) {
        if (indexOnSchematic + lineSize > schematic.length() - 1) {
            return Collections.emptyList(); // We are on the last line
        }

        final int numberOfDigits = partNumber == 0 ? 1 : (int) Math.log10(partNumber);
        final int bottomMinIndex = indexOnSchematic + lineSize - 1;
        final int bottomMaxIndex = indexOnSchematic + lineSize + numberOfDigits + 2;
        final String possibleSymbolsOnBottom = schematic.substring(bottomMinIndex, bottomMaxIndex);

        return possibleSymbolsOnBottom.chars().mapToObj(c -> (char) c).filter(Utils::isSymbol).toList();
    }

}
