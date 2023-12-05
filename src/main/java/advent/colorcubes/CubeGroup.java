package advent.colorcubes;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class CubeGroup {

    private final Map<Cube, Integer> contents = Maps.newHashMap();

    public void setCubesOfColor(final Cube cube, final int amount) {
        contents.put(cube, amount);
    }

    public int howManyCubesOfColor(final Cube cube) {
        return contents.getOrDefault(cube, 0);
    }

    public boolean canContainAllTheseGroups(final List<CubeGroup> cubeGroupsToContain) {
        for (final Cube cube : Cube.values()) {
            for (final CubeGroup cubeGroup : cubeGroupsToContain) {
                final int cubesToContain = cubeGroup.howManyCubesOfColor(cube);
                if (cubesToContain > howManyCubesOfColor(cube)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "CubeGroup{" +
                "contents=" + contents +
                '}';
    }
}
