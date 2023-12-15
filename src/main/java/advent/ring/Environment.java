package advent.ring;

import advent.pipes.Connection;
import advent.pipes.Pipe;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Environment {

    private final String flatMap;
    private final int lineSize;
    private final int ringStartIndex;

    public Environment(final List<String> input) {
        flatMap = String.join("", input);
        lineSize = input.isEmpty() ? 0 : input.get(0).length();
        ringStartIndex = flatMap.indexOf(Pipe.START.getRepresentation());
    }

    public List<Integer> getIndexesInsideRing() {
        final List<Integer> result = Lists.newArrayList();

        final List<Segment> ringSegments = getSegmentsForRing(Direction.CLOCKWISE);
        final Map<Integer, Segment> ringSegmentMap =
                ringSegments.stream().collect(Collectors.toMap(Segment::getIndex, Function.identity()));

        for (int index = 0; index <= flatMap.length() - 1; index++) {
            if (ringSegmentMap.containsKey(index)) {
                continue;
            }

            final Orientation northOrientation = indexRaysOrientationFrom(Connection.NORTH, index, ringSegmentMap);
            if (northOrientation == Orientation.CONVEX) {
                result.add(index);
                continue;
            }

            final Orientation southOrientation = indexRaysOrientationFrom(Connection.SOUTH, index, ringSegmentMap);
            if (southOrientation == Orientation.CONVEX) {
                result.add(index);
                continue;
            }

            final Orientation eastOrientation = indexRaysOrientationFrom(Connection.EAST, index, ringSegmentMap);
            if (eastOrientation == Orientation.CONVEX) {
                result.add(index);
                continue;
            }

            final Orientation westOrientation = indexRaysOrientationFrom(Connection.WEST, index, ringSegmentMap);
            if (westOrientation == Orientation.CONVEX) {
                result.add(index);
            }

        }

        return result;
    }

    public Orientation indexRaysOrientationFrom(final Connection connection, final Integer index, final Map<Integer, Segment> ringSegmentMap) {
        return EnvironmentUtils.indexRaysOrientationFrom(connection, index, flatMap, lineSize, ringSegmentMap);
    }

    public List<Segment> getSegmentsForRing(final Direction direction) {
        final List<Segment> result = Lists.newArrayList();
        final List<Integer> ringIndexes = getRingIndexes();

        if (direction == Direction.CLOCKWISE) {

            for (int i = 0; i <= ringIndexes.size() - 1; i++) {
                int currentIndex = ringIndexes.get(i);
                int previousIndex;
                if (i == 0) {
                    previousIndex = ringIndexes.get(ringIndexes.size() - 1);
                } else {
                    previousIndex = ringIndexes.get(i - 1);
                }
                final Segment segment = getSegmentForIndex(currentIndex, previousIndex, direction);
                result.add(segment);
            }

        } else if (direction == Direction.COUNTER_CLOCKWISE) {

            for (int i = ringIndexes.size() - 1; i >= 0; i--) {
                int currentIndex = ringIndexes.get(i);
                int previousIndex;
                if (i == 0) {
                    previousIndex = ringIndexes.get(ringIndexes.size() - 1);
                } else {
                    previousIndex = ringIndexes.get(i - 1);
                }
                final Segment segment = getSegmentForIndex(currentIndex, previousIndex, direction);
                result.add(segment);
            }

        }

        return result;
    }

    public Segment getSegmentForIndex(final int index, int startIndex, final Direction direction) {
        if (EnvironmentUtils.outsideFlatMap(index, flatMap)) {
            return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
        }

        final char content = flatMap.charAt(index);

        if (content == 'S') {
            return handleNorthWestSegment(index, startIndex, direction);
        }

        if (content == '.') {
            return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
        }

        if (content == '|') {
            return handleVerticalSegment(index, startIndex, direction);
        }

        if (content == '-') {
            return handleHorizontalSegment(index, startIndex, direction);
        }

        if (content == 'F') {
            return handleSouthEastSegment(index, startIndex, direction);
        }

        if (content == '7') {
            return handleSouthWestSegment(index, startIndex, direction);
        }

        if (content == 'J') {
            return handleNorthWestSegment(index, startIndex, direction);
        }

        if (content == 'L') {
            return handleNortEastSegment(index, startIndex, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleVerticalSegment(final int index, final int startIndex, final Direction direction) {
        final int indexUp = EnvironmentUtils.getIndexUp(index, flatMap, lineSize);
        final int indexDown = EnvironmentUtils.getIndexDown(index, flatMap, lineSize);

        if (startIndex == indexUp || startIndex == indexDown) {
            return new Segment(index, startIndex, Sets.newHashSet(indexUp, indexDown), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleHorizontalSegment(int index, final int startIndex, Direction direction) {
        final int indexLeft = EnvironmentUtils.getIndexLeft(index, flatMap, lineSize);
        final int indexRight = EnvironmentUtils.getIndexRight(index, flatMap, lineSize);

        if (startIndex == indexLeft || startIndex == indexRight) {
            return new Segment(index, startIndex, Sets.newHashSet(indexLeft, indexRight), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleSouthEastSegment(int index, final int startIndex, Direction direction) {
        final int indexRight = EnvironmentUtils.getIndexRight(index, flatMap, lineSize);
        final int indexDown = EnvironmentUtils.getIndexDown(index, flatMap, lineSize);

        if (startIndex == indexRight || startIndex == indexDown) {
            return new Segment(index, startIndex, Sets.newHashSet(indexRight, indexDown), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleSouthWestSegment(int index, final int startIndex, Direction direction) {
        final int indexLeft = EnvironmentUtils.getIndexLeft(index, flatMap, lineSize);
        final int indexDown = EnvironmentUtils.getIndexDown(index, flatMap, lineSize);

        if (startIndex == indexLeft || startIndex == indexDown) {
            return new Segment(index, startIndex, Sets.newHashSet(indexLeft, indexDown), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleNorthWestSegment(int index, final int startIndex, Direction direction) {
        final int indexUp = EnvironmentUtils.getIndexUp(index, flatMap, lineSize);
        final int indexLeft = EnvironmentUtils.getIndexLeft(index, flatMap, lineSize);

        if (startIndex == indexUp || startIndex == indexLeft) {
            return new Segment(index, startIndex, Sets.newHashSet(indexUp, indexLeft), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    private Segment handleNortEastSegment(int index, final int startIndex, Direction direction) {
        final int indexUp = EnvironmentUtils.getIndexUp(index, flatMap, lineSize);
        final int indexRight = EnvironmentUtils.getIndexRight(index, flatMap, lineSize);

        if (startIndex == indexUp || startIndex == indexRight) {
            return new Segment(index, startIndex, Sets.newHashSet(indexUp, indexRight), lineSize, direction);
        }

        return EnvironmentUtils.getEmptySegment(index, lineSize, direction);
    }

    public List<Integer> getRingIndexes() {
        return RingUtils.getRingStepIndexes(flatMap, lineSize, ringStartIndex);
    }

    public String getEnvironmentRepresentation() {
        final StringBuilder stringBuilder = new StringBuilder();
        final List<Integer> ringIndexes = getRingIndexes();
        final List<Integer> indexesInsideRing = getIndexesInsideRing();
        for (int currentIndex = 0; currentIndex <= flatMap.length() - 1; currentIndex++) {
            if (currentIndex % lineSize == 0) {
                stringBuilder.append("\n");
            }

            if (ringIndexes.contains(currentIndex)) {
                stringBuilder.append("*");
            } else if (indexesInsideRing.contains(currentIndex)) {
                stringBuilder.append("!");
            } else {
                stringBuilder.append(flatMap.charAt(currentIndex));
            }
        }

        return stringBuilder.toString();
    }
}
