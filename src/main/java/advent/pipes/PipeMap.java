package advent.pipes;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PipeMap {

    private final String pipeFlatMap;
    private final int lineSize;
    private final int startIndex;

    public PipeMap(final List<String> input) {
        pipeFlatMap = String.join("", input);
        lineSize = input.isEmpty() ? 0 : input.get(0).length();
        startIndex = pipeFlatMap.indexOf(Pipe.START.getRepresentation());
    }

    public List<Integer> getTileIndexesInsideLoop() {
        final List<Tile> tiles = getTilesFromLoopIndexes();
        final Map<Integer, Tile> indexToTile = tiles.stream()
                .collect(Collectors.toMap(Tile::getIndexOnMap, Function.identity()));

        final List<Integer> result = Lists.newArrayList();
        for (int currentIndex = 0; currentIndex <= pipeFlatMap.length() - 1; currentIndex++) {
            if (indexToTile.containsKey(currentIndex)) {
                continue;
            }

            final boolean rayNorthBound = isRayBoundFor(Connection.NORTH, currentIndex, indexToTile);
            final boolean rayEastBound = isRayBoundFor(Connection.EAST, currentIndex, indexToTile);
            final boolean raySouthBound = isRayBoundFor(Connection.SOUTH, currentIndex, indexToTile);
            final boolean rayWestBound = isRayBoundFor(Connection.WEST, currentIndex, indexToTile);

            if (rayNorthBound && rayEastBound && raySouthBound && rayWestBound) {
                result.add(currentIndex);
            }
        }

        return result;
    }

    boolean isPointInside(final int currentIndex) {
        final List<Tile> tiles = getTilesFromLoopIndexes();
        final Map<Integer, Tile> indexToTile = tiles.stream()
                .collect(Collectors.toMap(Tile::getIndexOnMap, Function.identity()));

        final boolean rayNorthBound = isRayBoundFor(Connection.NORTH, currentIndex, indexToTile);
        final boolean rayEastBound = isRayBoundFor(Connection.EAST, currentIndex, indexToTile);
        final boolean raySouthBound = isRayBoundFor(Connection.SOUTH, currentIndex, indexToTile);
        final boolean rayWestBound = isRayBoundFor(Connection.WEST, currentIndex, indexToTile);

        return rayNorthBound && rayEastBound && raySouthBound && rayWestBound;
    }

    private boolean isRayBoundFor(final Connection connection, final int currentIndex, final Map<Integer, Tile> tileMap) {
        if (connection == Connection.NORTH) {
            int rayIndex = currentIndex - lineSize;
            while (insideFlatMap(rayIndex)) {
                if (tileMap.containsKey(rayIndex) && tileMap.get(rayIndex).getOrientationFor(connection).equals(Orientation.CONVEX)) {
                    return true;
                }
                rayIndex -= lineSize;
            }

            return false;
        }

        if (connection == Connection.EAST) {
            int rayIndex = currentIndex + 1;
            while (insideLine(currentIndex, rayIndex)) {
                if (tileMap.containsKey(rayIndex)) {
                    if (tileMap.containsKey(rayIndex) && tileMap.get(rayIndex).getOrientationFor(connection).equals(Orientation.CONVEX)) {
                        return true;
                    }
                }

                rayIndex += 1;
            }

            return false;
        }

        if (connection == Connection.SOUTH) {
            int rayIndex = currentIndex + lineSize;
            while (insideFlatMap(rayIndex)) {
                if (tileMap.containsKey(rayIndex) && tileMap.get(rayIndex).getOrientationFor(connection).equals(Orientation.CONVEX)) {
                    return true;
                }
                rayIndex += lineSize;
            }

            return false;
        }

        if (connection == Connection.WEST) {
            int rayIndex = currentIndex - 1;
            while (insideLine(currentIndex, rayIndex)) {
                if (tileMap.containsKey(rayIndex) && tileMap.get(rayIndex).getOrientationFor(connection).equals(Orientation.CONVEX)) {
                    return true;
                }
                rayIndex -= 1;
            }

            return false;
        }

        return false;
    }

    boolean insideLine(final int currentIndex, final int nextIndex) {
        if (insideFlatMap(nextIndex)) {
            final int currentLine = determineLineForIndex(currentIndex);
            final int nextLine = determineLineForIndex(nextIndex);
            return currentLine == nextLine;
        }

        return false;
    }

    public List<Tile> getTilesFromLoopIndexes() {
        final List<Tile> tiles = Lists.newArrayList();
        final List<Integer> loopStepIndexes = getLoopStepIndexes();

        Connection previousConnection = Connection.WEST;
        for (int currentIndex = 0; currentIndex <= loopStepIndexes.size() - 1; currentIndex++) {
            final int indexOnMap = loopStepIndexes.get(currentIndex);
            final Pipe pipe = Pipe.fromRepresentation(pipeFlatMap.charAt(indexOnMap));

            if (pipe == Pipe.START) {
                tiles.add(new Tile(startIndex, Pipe.START, previousConnection));
            }

            if (pipe == Pipe.SE_CORNER) {
                if (previousConnection == Connection.NORTH) {
                    previousConnection = Connection.EAST;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.EAST));
                }
                if (previousConnection == Connection.WEST) {
                    previousConnection = Connection.SOUTH;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.SOUTH));
                }
            }

            if (pipe == Pipe.HORIZONTAL) {
                if (previousConnection == Connection.EAST) {
                    tiles.add(new Tile(indexOnMap, pipe, Connection.EAST));
                }
                if (previousConnection == Connection.WEST) {
                    tiles.add(new Tile(indexOnMap, pipe, Connection.WEST));
                }
            }

            if (pipe == Pipe.SW_CORNER) {
                if (previousConnection == Connection.EAST) {
                    previousConnection = Connection.SOUTH;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.SOUTH));
                }
                if (previousConnection == Connection.NORTH) {
                    previousConnection = Connection.WEST;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.WEST));
                }
            }

            if (pipe == Pipe.VERTICAL) {
                if (previousConnection == Connection.SOUTH) {
                    tiles.add(new Tile(indexOnMap, pipe, Connection.SOUTH));
                }
                if (previousConnection == Connection.NORTH) {
                    tiles.add(new Tile(indexOnMap, pipe, Connection.NORTH));
                }
            }

            if (pipe == Pipe.NW_CORNER) {
                if (previousConnection == Connection.SOUTH) {
                    previousConnection = Connection.WEST;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.WEST));
                }

                if (previousConnection == Connection.EAST) {
                    previousConnection = Connection.NORTH;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.NORTH));
                }
            }

            if (pipe == Pipe.NE_CORNER) {
                if (previousConnection == Connection.WEST) {
                    previousConnection = Connection.NORTH;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.NORTH));
                }
                if (previousConnection == Connection.SOUTH) {
                    previousConnection = Connection.EAST;
                    tiles.add(new Tile(indexOnMap, pipe, Connection.EAST));
                }
            }

        }

        return tiles;
    }

    public String getPipeMapRepresentation() {
        final StringBuilder stringBuilder = new StringBuilder();
        final List<Integer> loopStepIndexes = getLoopStepIndexes();
        final List<Integer> inside = getTileIndexesInsideLoop();
        for (int currentIndex = 0; currentIndex <= pipeFlatMap.length() - 1; currentIndex++) {
            if (currentIndex % lineSize == 0) {
                stringBuilder.append("\n");
            }

            if (loopStepIndexes.contains(currentIndex)) {
                stringBuilder.append(pipeFlatMap.charAt(currentIndex));
            } else if (inside.contains(currentIndex)) {
                stringBuilder.append("!");
            } else {
                stringBuilder.append(".");
            }
        }

        return stringBuilder.toString();
    }

    public List<Integer> getLoopStepIndexes() {
        final List<Integer> result = Lists.newArrayList(startIndex);

        int previousIndex = -1;
        int currentIndex = startIndex;
        int nextIndex = -1;


        do {

            nextIndex = getNextIndex(currentIndex, previousIndex);
            previousIndex = currentIndex;
            currentIndex = nextIndex;
            if (nextIndex != startIndex) {
                result.add(currentIndex);
            }

        } while (nextIndex != startIndex);

        return result;
    }

    int determineLineForIndex(final int index) {
        int line = 1;
        while (!indexBelongsToLine(index, line)) {
            line++;
        }

        return line;
    }

    private boolean indexBelongsToLine(final int index, final int line) {
        final int init = lineSize * (line - 1);
        final int end = (lineSize * line) - 1;
        return index >= init && index <= end;
    }

    public int calculateLoopSteps() {
        int previousIndex = -1;
        int currentIndex = startIndex;
        int nextIndex = -1;
        int steps = 0;

        do {

            nextIndex = getNextIndex(currentIndex, previousIndex);
            previousIndex = currentIndex;
            currentIndex = nextIndex;
            steps++;

        } while (nextIndex != startIndex);

        return steps;
    }

    private int getNextIndex(final int currentIndex, final int previousIndex) {
        final Pipe currentPipe = Pipe.fromRepresentation(Character.valueOf(pipeFlatMap.charAt(currentIndex)).toString());

        final int nextIndexWest = getNextIndexFor(Connection.WEST, currentPipe, currentIndex, previousIndex);
        if (nextIndexWest != -1) {
            return nextIndexWest;
        }

        final int nextIndexNorth = getNextIndexFor(Connection.NORTH, currentPipe, currentIndex, previousIndex);
        if (nextIndexNorth != -1) {
            return nextIndexNorth;
        }

        final int nextIndexEast = getNextIndexFor(Connection.EAST, currentPipe, currentIndex, previousIndex);
        if (nextIndexEast != -1) {
            return nextIndexEast;
        }

        final int nextIndexSouth = getNextIndexFor(Connection.SOUTH, currentPipe, currentIndex, previousIndex);
        if (nextIndexSouth != -1) {
            return nextIndexSouth;
        }

        System.out.println("Cannot continue!");
        return 0;
    }

    private int getNextIndexFor(final Connection connection, final Pipe currentPipe, final int currentIndex, final int previousIndex) {
        final int nextIndex = getConnectionIndexFor(currentIndex, connection);
        if (nextIndex == previousIndex) {
            return -1;
        }

        if (insideMap(nextIndex)) {
            final Pipe nextPipe = Pipe.fromRepresentation(Character.valueOf(pipeFlatMap.charAt(nextIndex)).toString());
            if (currentPipe.isConnectedFrom(connection, nextPipe)) {
                return nextIndex;
            }
        }

        return -1;
    }

    public int getConnectionIndexFor(final int index, final Connection connection) {
        if (connection == Connection.NORTH) {
            if (index - lineSize >= 0) {
                return index - lineSize;
            }
        }

        if (connection == Connection.EAST) {
            if (index + 1 <= pipeFlatMap.length() - 1) {
                return index + 1;
            }
        }

        if (connection == Connection.SOUTH) {
            if (index + lineSize <= pipeFlatMap.length() - 1) {
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

    public boolean insideMap(final int index) {
        return index != -1;
    }

    private boolean insideFlatMap(final int index) {
        return index >= 0 && index <= pipeFlatMap.length() - 1;
    }

}
