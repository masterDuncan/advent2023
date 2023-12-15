package advent.ring;

import java.util.Set;

public class Segment {

    private final int index;
    private final Set<Integer> connectionIndexes;
    private final Stance stance;

    public Segment(final int index, final int startIndex, final Set<Integer> connectionIndexes, final int lineSize, final Direction direction) {
        this.index = index;
        this.connectionIndexes = connectionIndexes;
        this.stance = determineStance(startIndex, connectionIndexes, lineSize, direction);
    }

    private Stance determineStance(final int startIndex, final Set<Integer> connectionIndexes, final int lineSize, final Direction direction) {
        final int indexLeft = index - 1;
        final int indexRight = index + 1;
        final int indexUp = index - lineSize;
        final int indexDown = index + lineSize;

        if (connectionIndexes.isEmpty() || connectionIndexes.contains(-1)) {
            return Stance.EMPTY_STANCE;
        }

        // |
        if (connectionIndexes.contains(indexDown) && connectionIndexes.contains(indexUp)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.CONCAVE, Orientation.CONVEX, Orientation.NONE, Orientation.NONE);
                } else if (startIndex == indexUp) {
                    return new Stance(Orientation.CONVEX, Orientation.CONCAVE, Orientation.NONE, Orientation.NONE);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.CONVEX, Orientation.CONCAVE, Orientation.NONE, Orientation.NONE);
                } else if (startIndex == indexUp) {
                    return new Stance(Orientation.CONCAVE, Orientation.CONVEX, Orientation.NONE, Orientation.NONE);
                }
            }
        }

        // -
        if (connectionIndexes.contains(indexLeft) && connectionIndexes.contains(indexRight)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.NONE, Orientation.CONCAVE, Orientation.CONVEX);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.NONE, Orientation.NONE, Orientation.CONVEX, Orientation.CONCAVE);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.NONE, Orientation.CONVEX, Orientation.CONCAVE);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.NONE, Orientation.NONE, Orientation.CONCAVE, Orientation.CONVEX);
                }
            }
        }

        // F
        if (connectionIndexes.contains(indexDown) && connectionIndexes.contains(indexRight)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.CONCAVE, Orientation.NONE, Orientation.CONCAVE, Orientation.NONE);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.CONVEX, Orientation.NONE, Orientation.CONVEX, Orientation.NONE);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.CONVEX, Orientation.NONE, Orientation.CONVEX, Orientation.NONE);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.CONCAVE, Orientation.NONE, Orientation.CONCAVE, Orientation.NONE);
                }
            }
        }

        // 7
        if (connectionIndexes.contains(indexDown) && connectionIndexes.contains(indexLeft)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.NONE, Orientation.CONVEX, Orientation.CONVEX, Orientation.NONE);
                } else if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.CONCAVE, Orientation.CONCAVE, Orientation.NONE);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexDown) {
                    return new Stance(Orientation.NONE, Orientation.CONCAVE, Orientation.CONCAVE, Orientation.NONE);
                } else if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.CONVEX, Orientation.CONVEX, Orientation.NONE);
                }
            }
        }

        // J
        if (connectionIndexes.contains(indexUp) && connectionIndexes.contains(indexLeft)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexUp) {
                    return new Stance(Orientation.NONE, Orientation.CONCAVE, Orientation.NONE, Orientation.CONCAVE);
                } else if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.CONVEX, Orientation.NONE, Orientation.CONVEX);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexUp) {
                    return new Stance(Orientation.NONE, Orientation.CONVEX, Orientation.NONE, Orientation.CONVEX);
                } else if (startIndex == indexLeft) {
                    return new Stance(Orientation.NONE, Orientation.CONCAVE, Orientation.NONE, Orientation.CONCAVE);
                }
            }
        }

        // L
        if (connectionIndexes.contains(indexUp) && connectionIndexes.contains(indexRight)) {
            if (direction == Direction.CLOCKWISE) {
                if (startIndex == indexUp) {
                    return new Stance(Orientation.CONVEX, Orientation.NONE, Orientation.NONE, Orientation.CONVEX);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.CONCAVE, Orientation.NONE, Orientation.NONE, Orientation.CONCAVE);
                }
            } else if (direction == Direction.COUNTER_CLOCKWISE) {
                if (startIndex == indexUp) {
                    return new Stance(Orientation.CONCAVE, Orientation.NONE, Orientation.NONE, Orientation.CONCAVE);
                } else if (startIndex == indexRight) {
                    return new Stance(Orientation.CONVEX, Orientation.NONE, Orientation.NONE, Orientation.CONVEX);
                }
            }
        }

        return Stance.EMPTY_STANCE;
    }

    public int getIndex() {
        return index;
    }

    public Stance getStance() {
        return stance;
    }

    public Set<Integer> getConnectionIndexes() {
        return connectionIndexes;
    }

    @Override
    public String toString() {
        return "Segment{" + "index=" + index + ", connectionIndexes=" + connectionIndexes + ", stance=" + stance + '}';
    }
}
