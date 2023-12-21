package advent.seeds;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Seeds test")
public class SeedsTest {

    @DisplayName("Seed Translation")
    @Nested
    class SeedTranslation {

        @DisplayName("when not in range - return value")
        @Test
        void whenNotInRange_returnValue() {
            final SeedInfoRange range1 = new SeedInfoRange(50, 98, 2);
            final SeedInfoRange range2 = new SeedInfoRange(52, 50, 48);
            final SeedInfoMap seedInfoMap = new SeedInfoMap(List.of(range1, range2));

            final long value = 40;
            final long mapValue = seedInfoMap.mapValue(value);

            assertThat(mapValue).isEqualTo(40);
        }

        @DisplayName("when in range - map into range")
        @Test
        void whenInRange_mapIntoRange() {
            final SeedInfoRange range1 = new SeedInfoRange(50, 98, 2);
            final SeedInfoRange range2 = new SeedInfoRange(52, 50, 48);
            final SeedInfoMap seedInfoMap = new SeedInfoMap(List.of(range1, range2));

            final long value = 99;
            final long mapValue = seedInfoMap.mapValue(value);

            assertThat(mapValue).isEqualTo(51);
        }

        @DisplayName("Read input and try to solve seed problem")
        @Test
        void readInputAndTryToSolveSeedProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/seeds/input"));
            final List<Long> seeds = extractSeeds(lines.get(0));
            solve(lines, seeds);
        }

        private void solve(final List<String> lines, final List<Long> seeds) {

            SeedInfoMap currentInfoMap = new SeedInfoMap();
            final Map<String, SeedInfoMap> maps = Maps.newLinkedHashMap();

            for (final String line : lines.subList(2, lines.size())) {

                if (isMapDescription(line)) {
                    final String currentMapName = line.replace(":", "");
                    currentInfoMap = new SeedInfoMap();
                    maps.put(currentMapName, currentInfoMap);
                    continue;
                }

                if (isMapCoordinates(line)) {
                    final String[] parts = line.split(" ");
                    final SeedInfoRange seedInfoRange = new SeedInfoRange(Long.parseLong(parts[0]),
                            Long.parseLong(parts[1]), Long.parseLong(parts[2]));
                    currentInfoMap.addSeedInfoRange(seedInfoRange);
                }

            }

            final long minimumLocation = seeds.stream().map(seed -> calculateLocationForSeed(seed, maps))
                    .mapToLong(Long::longValue).min().orElse(0);

            System.out.println("Local minimum location: " + minimumLocation);
        }

        @Disabled("It works, but it takes some time")
        @DisplayName("Read input and try to solve multiple seed problem")
        @Test
        void readInputAndTryToSolveMultipleSeedProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/seeds/input"));
            final String[] parts = lines.get(0).split(":");
            final List<Long> ranges = Arrays.stream(parts[1].split(" ")).filter(entry -> !entry.isBlank())
                    .map(String::trim).map(Long::parseLong).toList();

            final List<Pair<Long, Long>> seedRanges = getSeedRanges(ranges);
            final Map<String, SeedInfoMap> infoMaps = getInfoMaps(lines);

            long min = Long.MAX_VALUE;
            for (final Pair<Long, Long> seedRange :seedRanges) {
                final Long init = seedRange.getKey();
                final Long limit = seedRange.getValue();
                System.out.println("Processing range from " + init + " with limit " + limit);

                long seed = init;
                while (seed <= init + limit) {
                    final long location = calculateLocationForSeed(seed, infoMaps);
                    if (location < min) {
                        System.out.println("Found local minimum: " + location);
                        min = location;
                    }
                    seed++;
                }
            }

        }

        private List<Pair<Long, Long>> getSeedRanges(List<Long> ranges) {
            final List<Pair<Long, Long>> seedRanges = Lists.newArrayList();

            int index = 0;
            long init = 0;
            for (final Long coordinate : ranges) {
                if (index % 2 == 0) {
                    init = coordinate;
                } else {
                    seedRanges.add(Pair.of(init, coordinate));
                }

                index++;
            }

            return seedRanges;
        }

        private Map<String, SeedInfoMap> getInfoMaps(final List<String> lines) {
            SeedInfoMap currentInfoMap = new SeedInfoMap();
            final Map<String, SeedInfoMap> maps = Maps.newLinkedHashMap();

            for (final String line : lines.subList(2, lines.size())) {

                if (isMapDescription(line)) {
                    final String currentMapName = line.replace(":", "");
                    currentInfoMap = new SeedInfoMap();
                    maps.put(currentMapName, currentInfoMap);
                    continue;
                }

                if (isMapCoordinates(line)) {
                    final String[] parts = line.split(" ");
                    final SeedInfoRange seedInfoRange = new SeedInfoRange(Long.parseLong(parts[0]),
                            Long.parseLong(parts[1]), Long.parseLong(parts[2]));
                    currentInfoMap.addSeedInfoRange(seedInfoRange);
                }

            }

            return maps;
        }

    }

    private List<Long> extractSeeds(final String line) {
        final String[] parts = line.split(":");
        return Arrays.stream(parts[1].split(" ")).filter(entry -> !entry.isBlank())
                .map(String::trim).map(Long::parseLong).toList();
    }

    private boolean isMapDescription(final String line) {
        return StringUtils.isAlpha(line.replace("-", "").replace(":", "").replace(" ", ""));
    }

    private boolean isMapCoordinates(final String line) {
        return StringUtils.isNumeric(line.replace(" ", ""));
    }

    private long calculateLocationForSeed(final long seed, final Map<String, SeedInfoMap> maps) {
        long currentMapping = seed;
        for (final SeedInfoMap seedInfoMap : maps.values()) {
            currentMapping = seedInfoMap.mapValue(currentMapping);
        }

        return currentMapping;
    }
}
