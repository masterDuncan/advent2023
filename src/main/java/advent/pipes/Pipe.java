package advent.pipes;

public enum Pipe {
    NO_PIPE("."),
    START("S"),
    VERTICAL("|"),
    HORIZONTAL("-"),
    SE_CORNER("F"),
    NE_CORNER("L"),
    SW_CORNER("7"),
    NW_CORNER("J");

    private final String representation;

    Pipe(final String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }

    public static Pipe fromRepresentation(final char character) {
        return fromRepresentation(Character.valueOf(character).toString());
    }

    public static Pipe fromRepresentation(final String representation) {
        for (final Pipe pipe : Pipe.values()) {
            if (pipe.representation.equals(representation)) {
                return pipe;
            }
        }

        return NO_PIPE;
    }

    public boolean isConnectedFrom(final Connection connection, final Pipe otherPipe) {
        if (otherPipe == NO_PIPE) {
            return false;
        }

        if (otherPipe == START) {
            if (connection == Connection.NORTH) {
                return this == VERTICAL || this == NE_CORNER || this == NW_CORNER;
            }
            if (connection == Connection.EAST) {
                return this == HORIZONTAL || this == NE_CORNER || this == SE_CORNER;
            }
            if (connection == Connection.SOUTH) {
                return this == VERTICAL || this == SW_CORNER || this == SE_CORNER;
            }
            if (connection == Connection.WEST) {
                return this == HORIZONTAL || this == SW_CORNER || this == NW_CORNER;
            }
            return true;
        }

        if (connection == Connection.NORTH) {
            final boolean weCanConnect = this == START || this == VERTICAL || this == NE_CORNER || this == NW_CORNER;
            final boolean theyCanConnect = otherPipe == VERTICAL || otherPipe == SE_CORNER || otherPipe == SW_CORNER;

            return weCanConnect && theyCanConnect;
        }

        if (connection == Connection.EAST) {
            final boolean weCanConnect = this == START || this == HORIZONTAL || this == SE_CORNER || this == NE_CORNER;
            final boolean theyCanConnect = otherPipe == HORIZONTAL || otherPipe == SW_CORNER || otherPipe == NW_CORNER;

            return weCanConnect && theyCanConnect;
        }

        if (connection == Connection.SOUTH) {
            final boolean weCanConnect = this == START || this == VERTICAL || this == SE_CORNER || this == SW_CORNER;
            final boolean theyCanConnect = otherPipe == VERTICAL || otherPipe == NE_CORNER || otherPipe == NW_CORNER;

            return weCanConnect && theyCanConnect;
        }

        if (connection == Connection.WEST) {
            final boolean weCanConnect = this == START ||  this == HORIZONTAL || this == SW_CORNER || this == NW_CORNER;
            final boolean theyCanConnect = otherPipe == HORIZONTAL || otherPipe == SE_CORNER || otherPipe == NE_CORNER;

            return weCanConnect && theyCanConnect;
        }

        return false;
    }
}
