package advent.ring;

import advent.pipes.Connection;
import advent.pipes.Pipe;
import com.google.common.collect.Lists;

import java.util.List;

public class RingUtils {

    private RingUtils() {
    }

    static List<Integer> getRingStepIndexes(final String flatMap, final int lineSize, final int startIndex) {
        final List<Integer> result = Lists.newArrayList(startIndex);

        int previousIndex = -1;
        int currentIndex = startIndex;
        int nextIndex;


        do {

            nextIndex = getNextIndex(flatMap, lineSize, currentIndex, previousIndex);
            previousIndex = currentIndex;
            currentIndex = nextIndex;
            if (nextIndex != startIndex) {
                result.add(currentIndex);
            }

        } while (nextIndex != startIndex);

        return result;
    }

    static int getNextIndex(final String flatMap, final int lineSize, final int currentIndex, final int previousIndex) {
        final Pipe currentPipe = Pipe.fromRepresentation(Character.valueOf(flatMap.charAt(currentIndex)).toString());

        final int nextIndexNorth = getNextIndexFor(flatMap, lineSize, Connection.NORTH, currentPipe, currentIndex, previousIndex);
        if (nextIndexNorth != -1) {
            return nextIndexNorth;
        }

        final int nextIndexSouth = getNextIndexFor(flatMap, lineSize, Connection.SOUTH, currentPipe, currentIndex, previousIndex);
        if (nextIndexSouth != -1) {
            return nextIndexSouth;
        }

        final int nextIndexWest = getNextIndexFor(flatMap, lineSize, Connection.WEST, currentPipe, currentIndex, previousIndex);
        if (nextIndexWest != -1) {
            return nextIndexWest;
        }


        final int nextIndexEast = getNextIndexFor(flatMap, lineSize, Connection.EAST, currentPipe, currentIndex, previousIndex);
        if (nextIndexEast != -1) {
            return nextIndexEast;
        }

        System.out.println("Cannot continue!");
        return 0;
    }

    static int getNextIndexFor(final String flatMap, final int lineSize, final Connection connection, final Pipe currentPipe,
                               final int currentIndex, final int previousIndex) {

        final int nextIndex = getConnectionIndexFor(flatMap, lineSize, currentIndex, connection);
        if (nextIndex == previousIndex) {
            return -1;
        }

        if (nextIndex != -1) {
            final Pipe nextPipe = Pipe.fromRepresentation(Character.valueOf(flatMap.charAt(nextIndex)).toString());
            if (currentPipe.isConnectedFrom(connection, nextPipe)) {
                return nextIndex;
            }
        }

        return -1;
    }

    static int getConnectionIndexFor(final String flatMap, final int lineSize,
                                     final int index, final Connection connection) {

        if (connection == Connection.NORTH) {
            if (index - lineSize >= 0) {
                return index - lineSize;
            }
        }

        if (connection == Connection.EAST) {
            if (index + 1 <= flatMap.length() - 1) {
                return index + 1;
            }
        }

        if (connection == Connection.SOUTH) {
            if (index + lineSize <= flatMap.length() - 1) {
                return index + lineSize;
            }
        }

        if (connection == Connection.WEST) {
            if (index - 1 >= 0) {
                return index - 1;
            }
        }

        return -1;
    }
}
