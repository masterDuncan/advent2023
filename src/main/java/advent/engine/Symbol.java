package advent.engine;

import java.util.Objects;

public class Symbol {

    private final char symbol;
    private final int index;

    public Symbol(char symbol, int index) {
        this.symbol = symbol;
        this.index = index;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol1 = (Symbol) o;
        return symbol == symbol1.symbol && index == symbol1.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, index);
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbol=" + symbol +
                ", index=" + index +
                '}';
    }
}
