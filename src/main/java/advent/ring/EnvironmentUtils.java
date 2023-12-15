package advent.ring;

import advent.pipes.Connection;

import java.util.Collections;
import java.util.Map;

class EnvironmentUtils {

    private EnvironmentUtils() {
    }

    static Segment getEmptySegment(final int index, final int lineSize, final Direction direction) {
        return new Segment(index, -1, Collections.emptySet(), lineSize, direction);
    }

    static Orientation indexRaysOrientationFrom(final Connection connection, final int index,
                                                final String flatMap, final int lineSize,
                                                final Map<Integer, Segment> ringSegmentMap) {

        if (connection == Connection.NORTH) {

            boolean isRingIndex = false;
            int indexUp = index;
            do {

                indexUp = getIndexUp(indexUp, flatMap, lineSize);
                if (outsideFlatMap(indexUp, flatMap)) {
                    break;
                }

                isRingIndex = ringSegmentMap.containsKey(indexUp);

            } while (!isRingIndex);

            if (isRingIndex) {
                return ringSegmentMap.get(indexUp).getStance().getDownOrientation();
            }

        }

        if (connection == Connection.SOUTH) {

            boolean isRingIndex = false;
            int indexDown = index;

            do {

                indexDown = getIndexDown(indexDown, flatMap, lineSize);
                if (outsideFlatMap(indexDown, flatMap)) {
                    break;
                }

                isRingIndex = ringSegmentMap.containsKey(indexDown);

            } while (!isRingIndex);

            if (isRingIndex) {
                final Segment segment = ringSegmentMap.get(indexDown);
                return segment.getStance().getUpOrientation();
            }

        }

        if (connection == Connection.EAST) {

            boolean isRingIndex = false;
            int indexRight = index;
            do {

                indexRight = getIndexRight(indexRight, flatMap, lineSize);
                if (outsideFlatMap(indexRight, flatMap)) {
                    break;
                }

                isRingIndex = ringSegmentMap.containsKey(indexRight);

            } while (!isRingIndex);

            if (isRingIndex) {
                return ringSegmentMap.get(indexRight).getStance().getLeftOrientation();
            }

        }

        if (connection == Connection.WEST) {

            boolean isRingIndex = false;
            int indexLeft = index;

            do {

                indexLeft = getIndexLeft(indexLeft, flatMap, lineSize);
                if (outsideFlatMap(indexLeft, flatMap)) {
                    break;
                }

                isRingIndex = ringSegmentMap.containsKey(indexLeft);

            } while (!isRingIndex);

            if (isRingIndex) {
                return ringSegmentMap.get(indexLeft).getStance().getRightOrientation();
            }
        }

        return Orientation.NONE;
    }

    static int getIndexUp(final int index, final String flatMap, final int lineSize) {
        final int indexUp = index - lineSize;
        if (outsideFlatMap(indexUp, flatMap)) {
            return -1;
        }

        return indexUp;
    }

    static int getIndexDown(final int index, final String flatMap, final int lineSize) {
        final int indexDown = index + lineSize;
        if (outsideFlatMap(indexDown, flatMap)) {
            return -1;
        }

        return indexDown;
    }

    public static int getIndexLeft(final int index, final String flatMap, final int lineSize) {
        final int indexLeft = index - 1;
        if (outsideFlatMap(indexLeft, flatMap)) {
            return -1;
        }

        final int originalLine = determineLineForIndex(index, lineSize);
        if (indexOutsideLine(indexLeft, originalLine, lineSize)) {
            return -1;
        }

        return indexLeft;
    }

    public static int getIndexRight(final int index, final String flatMap, final int lineSize) {
        final int indexRight = index + 1;
        if (outsideFlatMap(indexRight, flatMap)) {
            return -1;
        }

        final int originalLine = determineLineForIndex(index, lineSize);
        if (indexOutsideLine(indexRight, originalLine, lineSize)) {
            return -1;
        }

        return indexRight;
    }

    private static int determineLineForIndex(final int index, final int lineSize) {
        int line = 1;
        while (indexOutsideLine(index, line, lineSize)) {
            line++;
        }

        return line;
    }

    private static boolean indexOutsideLine(final int index, final int line, final int lineSize) {
        final int init = lineSize * (line - 1);
        final int end = (lineSize * line) - 1;
        return index < init || index > end;
    }

    static boolean outsideFlatMap(final int index, final String flatMap) {
        return index < 0 || index > flatMap.length() - 1;
    }
}
