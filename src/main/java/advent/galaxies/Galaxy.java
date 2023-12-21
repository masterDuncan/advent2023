package advent.galaxies;

import java.util.List;

public class Galaxy {

    private final int galaxyId;
    private final Coordinates coordinates;
    private final long hExpansion;
    private final long vExpansion;

    public Galaxy(final int galaxyId, final Coordinates coordinates, final List<String> input) {
        this.galaxyId = galaxyId;
        this.coordinates = coordinates;
        this.hExpansion = GalaxyUtils.getHExpandableUnits(coordinates, input);
        this.vExpansion = GalaxyUtils.getVExpandableUnits(coordinates, input);
    }

    public int getGalaxyId() {
        return galaxyId;
    }

    public Coordinates getExpandedCoordinatesAdding(final int toAdd) {
        return GalaxyUtils.expand(coordinates, toAdd, hExpansion, vExpansion);
    }

    @Override
    public String toString() {
        return "Galaxy{" +
                "galaxyId=" + galaxyId +
                ", coordinates=" + coordinates +
                ", hExpansion=" + hExpansion +
                ", vExpansion=" + vExpansion +
                '}';
    }
}
