package advent.seeds;

import com.google.common.collect.Lists;

import java.util.List;

public class SeedInfoMap {

    private final List<SeedInfoRange> mapRanges;

    public SeedInfoMap(final List<SeedInfoRange> mapRanges) {
        this.mapRanges = mapRanges;
    }

    public SeedInfoMap() {
        this.mapRanges = Lists.newArrayList();
    }

    public void addSeedInfoRange(final SeedInfoRange seedInfoRange) {
        mapRanges.add(seedInfoRange);
    }

    public long mapValue(final long value) {
        for (final SeedInfoRange range : mapRanges) {
            final boolean inRange = range.isInRange(value);
            if (inRange) {
                return range.mapIntoRange(value);
            }
        }

        return value;
    }

    @Override
    public String toString() {
        return "SeedInfoMap{" +
                "mapRanges=" + mapRanges +
                '}';
    }
}
