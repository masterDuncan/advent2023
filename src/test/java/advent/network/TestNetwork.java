package advent.network;

import advent.utils.FileUtils;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Network")
public class TestNetwork {

    @DisplayName("Follow Network Route")
    @Nested
    class FollowNetworkRoute {

        @DisplayName("when transit left - go left")
        @Test
        void whenTransitLeft_goLeft() {
            final String line = "AAA = (BBB, CCC)";
            final NetworkMap networkMap = new NetworkMap(List.of(line));

            final Position newPosition = networkMap.transit(new Position("AAA"), Direction.L);

            assertThat(newPosition).isEqualTo(new Position("BBB"));
        }

        @DisplayName("when transit right - go right")
        @Test
        void whenTransitRight_goRight() {
            final String line = "AAA = (BBB, CCC)";
            final NetworkMap networkMap = new NetworkMap(List.of(line));

            final Position newPosition = networkMap.transit(new Position("AAA"), Direction.R);

            assertThat(newPosition).isEqualTo(new Position("CCC"));
        }

        @DisplayName("Solve network problem")
        @Test
        void solveNetworkProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/network/input"));

            final NetworkMap networkMap = new NetworkMap(lines.stream().skip(2).toList());
            final String directions = lines.get(0);
            final Position initialPosition = new Position("AAA");
            final Position finalPosition = new Position("ZZZ");

            final int steps = calculateStepsForRoute(initialPosition, finalPosition, networkMap, directions);
            System.out.println(steps);
        }

        private int calculateStepsForRoute(final Position initialPosition, final Position finalPosition, final NetworkMap networkMap,
                                           final String directions) {

            Position currentPosition = initialPosition;
            int index = 0;
            int steps = 0;
            while (true) {
                if (index > directions.length() - 1) {
                    index = 0;
                }

                final Direction nextDirection = Direction.fromChar(directions.charAt(index));
                currentPosition = networkMap.transit(currentPosition, nextDirection);
                steps++;
                if (currentPosition.equals(finalPosition)) {
                    break;
                }

                index++;
            }

            return steps;
        }

        @DisplayName("Solve parallel network problem - brute force")
        @Test
        void solveParallelNetworkProblem_bruteForce() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/network/input"));

            final NetworkMap networkMap = new NetworkMap(lines.stream().skip(2).toList());
            final String directions = lines.get(0);
            final List<Position> initialPositions = getInitialPositions(networkMap);

            final long steps = calculateParallelStepsForRoute(initialPositions, networkMap, directions);
            System.out.println(steps);
        }

        private long calculateParallelStepsForRoute(final List<Position> initialPositions, final NetworkMap networkMap,
                                                    final String directions) {

            Position[] currentPositions = initialPositions.toArray(new Position[0]);
            // System.out.println("Initial positions: " + currentPositions);
            int index = 0;
            long steps = 0;
            long cycle = 1;
            do {

                if (index > directions.length() - 1) {
                    index = 0;
                    cycle++;
                }

                final Direction nextDirection = Direction.fromChar(directions.charAt(index));
                for (int i = 0; i <= currentPositions.length - 1; i++) {
                    currentPositions[i] = networkMap.transit(currentPositions[i], nextDirection);
                }

                steps++;

                if (steps % 100_000_000 == 0) {
                    System.out.println(steps);
                }
                //  System.out.println("Cycle: " + cycle + ", Step: " + steps + " - Next direction: " + nextDirection + " transits to " + currentPositions);

                index++;

            } while (!isParallelEnd(currentPositions));

            return steps;
        }

        private boolean isParallelEnd(final Position[] positions) {
            for (int i = 0; i <= positions.length - 1; i++) {
                if (!positions[i].isParallelEnd()) {
                    return false;
                }
            }

            return true;
        }

        @DisplayName("Solve parallel network problem - least common multiple")
        @Test
        void solveParallelNetworkProblem_periodicChains() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/network/input"));
            final NetworkMap networkMap = new NetworkMap(lines.stream().skip(2).toList());
            final String directions = lines.get(0);
            final List<Position> initialPositions = getInitialPositions(networkMap);

            final long[] cycles = new long[initialPositions.size()];
            int i = 0;
            for (final Position initialPosition : initialPositions) {
                cycles[i] = (calculateCyclesForRoute(initialPosition, networkMap, directions));
                i++;
            }

            final long lcm = lcm(cycles);
            System.out.println(lcm);

            final long result = lcm * directions.length();
            System.out.println(result);
        }

        private List<Position> getInitialPositions(final NetworkMap networkMap) {
            return networkMap.getPositions().stream().filter(Position::isParallelStart).toList();
        }

        private long calculateCyclesForRoute(final Position initialPosition, final NetworkMap networkMap, final String directions) {

            Position currentPosition = initialPosition;
            int index = 0;
            long cycles = 1;
            do {
                if (index > directions.length() - 1) {
                    index = 0;
                    cycles++;
                }

                final Direction nextDirection = Direction.fromChar(directions.charAt(index));
                currentPosition = networkMap.transit(currentPosition, nextDirection);

                index++;
            } while (!currentPosition.isParallelEnd());

            return cycles;
        }

        private long lcm(long[] input) {
            long result = input[0];
            for (int i = 1; i < input.length; i++) {
                result = lcm(result, input[i]);
            }

            return result;
        }

        private long lcm(long a, long b) {
            return a * (b / gcd(a, b));
        }

        private long gcd(long a, long b) {
            while (b > 0) {
                long temp = b;
                b = a % b; // % is remainder
                a = temp;
            }

            return a;
        }
    }

}
