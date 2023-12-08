package advent.seeds;

public class SeedInfoRange {

    private final long destinationRangeStart;
    private final long sourceRangeStart;
    private final long rangeLength;

    public SeedInfoRange(final long destinationRangeStart, final long sourceRangeStart, final long rangeLength) {
        this.destinationRangeStart = destinationRangeStart;
        this.sourceRangeStart = sourceRangeStart;
        this.rangeLength = rangeLength;
    }

    public long mapIntoRange(final long value) {
        final long where = whereInRange(value);
        return destinationRangeStart + where;
    }

    public boolean isInRange(final long value) {
        return sourceRangeStart <= value && value <= sourceRangeStart + rangeLength;
    }

    public long whereInRange(final long value) {
        return value - sourceRangeStart;
    }

    @Override
    public String toString() {
        return "SeedInfoRange{" +
                "destinationRangeStart=" + destinationRangeStart +
                ", sourceRangeStart=" + sourceRangeStart +
                ", rangeLength=" + rangeLength +
                '}';
    }
}
