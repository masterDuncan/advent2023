package advent.pipes;

public class Tile {

    private final int indexOnMap;
    private final Pipe pipe;
    private final Connection directionTo;

    public Tile(final int indexOnMap, final Pipe pipe, final Connection directionTo) {
        this.indexOnMap = indexOnMap;
        this.pipe = pipe;
        this.directionTo = directionTo;
    }

    public int getIndexOnMap() {
        return indexOnMap;
    }

    public Pipe getPipe() {
        return pipe;
    }

    public Connection getDirectionTo() {
        return directionTo;
    }

    public Orientation getOrientationFor(final Connection connection) {
        if (pipe == Pipe.START) {
            if (connection == Connection.EAST || (connection == Connection.SOUTH)) {
                return Orientation.CONCAVE;
            }
        }

        if (pipe == Pipe.HORIZONTAL) {
            if (directionTo == Connection.EAST) {
                if (connection == Connection.NORTH) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONCAVE;
                }
            }

            if (directionTo == Connection.WEST) {
                if (connection == Connection.NORTH) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONVEX;
                }
            }
        }

        if (pipe == Pipe.VERTICAL) {
            if (directionTo == Connection.NORTH) {
                if (connection == Connection.EAST) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.WEST) {
                    return Orientation.CONVEX;
                }
            }

            if (directionTo == Connection.SOUTH) {
                if (connection == Connection.EAST) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.WEST) {
                    return Orientation.CONCAVE;
                }
            }
        }

        if (pipe == Pipe.SW_CORNER) {
            if (directionTo == Connection.SOUTH) {
                if (connection == Connection.NORTH) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.EAST) {
                    return Orientation.CONCAVE;
                }
            }

            if (directionTo == Connection.WEST) {
                if (connection == Connection.NORTH) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.EAST) {
                    return Orientation.CONVEX;
                }
            }
        }

        if (pipe == Pipe.NW_CORNER) {
            if (directionTo == Connection.WEST) {
                if (connection == Connection.EAST) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONCAVE;
                }
            }

            if (directionTo == Connection.NORTH) {
                if (connection == Connection.EAST) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONVEX;
                }
            }
        }

        if (pipe == Pipe.NE_CORNER) {
            if (directionTo == Connection.NORTH) {
                if (connection == Connection.WEST) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONCAVE;
                }
            }

            if (directionTo == Connection.EAST) {
                if (connection == Connection.WEST) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.SOUTH) {
                    return Orientation.CONVEX;
                }
            }
        }

        if (pipe == Pipe.SE_CORNER) {
            if (directionTo == Connection.EAST) {
                if (connection == Connection.WEST) {
                    return Orientation.CONCAVE;
                }
                if (connection == Connection.NORTH) {
                    return Orientation.CONCAVE;
                }
            }

            if (directionTo == Connection.SOUTH) {
                if (connection == Connection.WEST) {
                    return Orientation.CONVEX;
                }
                if (connection == Connection.NORTH) {
                    return Orientation.CONVEX;
                }
            }
        }

        return Orientation.NO_ORIENTATION;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "indexOnMap=" + indexOnMap +
                ", pipe=" + pipe +
                ", directionTo=" + directionTo +
                '}';
    }
}
