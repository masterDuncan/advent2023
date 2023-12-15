package advent.ring;

import advent.pipes.Connection;
import advent.utils.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Ring test")
public class RingTest {

    @DisplayName("Compose segment")
    @Nested
    class ComposeSegment {

        @DisplayName("when composing segment for empty environment - return connectionless segment")
        @Test
        void whenComposingSegmentFromEmptyEnvironment_returnConnectionlessSegment() {
            final String line = "";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).isEmpty();
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for outside index - return connectionless segment")
        @Test
        void whenComposingSegmentForOutsideIndex_returnConnectionlessSegment() {
            final String line = ".";
            final List<String> input = List.of(line);

            final int index = 5;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).isEmpty();
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for empty index - return connectionless segment")
        @Test
        void whenComposingSegmentFromEmptyIndex_returnConnectionlessSegment() {
            final String line = ".";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).isEmpty();
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for bounded vertical index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedVerticalIndex_returnConnectionlessSegment() {
            final String line = "|";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for upper bounded vertical index - return connectionless segment")
        @Test
        void whenComposingSegmentForUpperBoundedVerticalIndex_returnConnectionlessSegment() {
            final String line1 = "|";
            final String line2 = ".";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for lower bounded vertical index - return connectionless segment")
        @Test
        void whenComposingSegmentForLowerBoundedVerticalIndex_returnConnectionlessSegment() {
            final String line1 = ".";
            final String line2 = "|";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0, -1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for vertical index up-down - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForVerticalIndex_upDown_returnConnectedSegment() {
            final String line1 = "...";
            final String line2 = ".|.";
            final String line3 = "...";
            final List<String> input = List.of(line1, line2, line3);

            final int index = 4;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(7);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing clockwise segment for vertical index down-up - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForVerticalIndex_downUp_returnConnectedSegment() {
            final String line1 = "...";
            final String line2 = ".|.";
            final String line3 = "...";
            final List<String> input = List.of(line1, line2, line3);

            final int index = 4;
            final int firstIndex = 7;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(7);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for vertical index up-down - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForVerticalIndex_upDown_returnConnectedSegment() {
            final String line1 = "...";
            final String line2 = ".|.";
            final String line3 = "...";
            final List<String> input = List.of(line1, line2, line3);

            final int index = 4;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(7);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for vertical index down-up - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForVerticalIndex_downUp_returnConnectedSegment() {
            final String line1 = "...";
            final String line2 = ".|.";
            final String line3 = "...";
            final List<String> input = List.of(line1, line2, line3);

            final int index = 4;
            final int firstIndex = 7;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(7);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing segment for bounded horizontal index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedHorizontalIndex_returnConnectionlessSegment() {
            final String line = "-";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for left bounded horizontal index - return connectionless segment")
        @Test
        void whenComposingSegmentForLeftBoundedHorizontalIndex_returnConnectionlessSegment() {
            final String line = "-.";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for right bounded horizontal index - return connectionless segment")
        @Test
        void whenComposingSegmentForRightBoundedHorizontalIndex_returnConnectionlessSegment() {
            final String line = ".-";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for horizontal index left-right - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForHorizontalIndex_leftRight_returnConnectedSegment() {
            final String line = ".-.";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }

        @DisplayName("when composing clockwise segment for horizontal index right-left - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForHorizontalIndex_rightLeft_returnConnectedSegment() {
            final String line = ".-.";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for horizontal index left-right - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForHorizontalIndex_leftRight_returnConnectedSegment() {
            final String line = ".-.";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for horizontal index right-left - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForHorizontalIndex_rightLeft_returnConnectedSegment() {
            final String line = ".-.";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }

        @DisplayName("when composing segment for bounded south-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedSouthEastIndex_returnConnectionlessSegment() {
            final String line = "F";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for south bounded south-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForSouthBoundedSouthEastIndex_returnConnectionlessSegment() {
            final String line = "F.";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for east bounded south-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForEastBoundedSouthEastIndex_returnConnectionlessSegment() {
            final String line1 = "F";
            final String line2 = ".";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).isEmpty();
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for south-east index - downToRight - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForSouthEastIndex_downToRight_returnConnectedSegment() {
            final String line1 = "F.";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing clockwise segment for south-east index - rightToDown - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForSouthEastIndex_rightToDown_returnConnectedSegment() {
            final String line1 = "F.";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for south-east index - downToRight - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForSouthEastIndex_downToRight_returnConnectedSegment() {
            final String line1 = "F.";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for south-east index - rightToDown - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForSouthEastIndex_rightToDown_returnConnectedSegment() {
            final String line1 = "F.";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing segment for bounded south-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedSouthWestIndex_returnConnectionlessSegment() {
            final String line = "7";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for south bounded south-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForSouthBoundedSouthWestIndex_returnConnectionlessSegment() {
            final String line = ".7";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).isEmpty();
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for west bounded south-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForWestBoundedSouthWestIndex_returnConnectionlessSegment() {
            final String line1 = "7";
            final String line2 = ".";
            final List<String> input = List.of(line1, line2);

            final int index = 0;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for south-west index - leftToDown - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForSouthWestIndex_leftToDown_returnConnectedSegment() {
            final String line1 = ".7";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing clockwise segment for south-west index - downToLeft - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForSouthWestIndex_downToLeft_returnConnectedSegment() {
            final String line1 = ".7";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 3;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for south-west index - leftToDown - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForSouthWestIndex_leftToDown_returnConnectedSegment() {
            final String line1 = ".7";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing counter-clockwise segment for south-west index - downToLeft - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForSouthWestIndex_downToLeft_returnConnectedSegment() {
            final String line1 = ".7";
            final String line2 = "..";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 3;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.NONE);
        }

        @DisplayName("when composing segment for bounded north-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedNorthWestIndex_returnConnectionlessSegment() {
            final String line = "J";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for north bounded north-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForNorthBoundedNorthWestIndex_returnConnectionlessSegment() {
            final String line = ".J";
            final List<String> input = List.of(line);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for west bounded north-west index - return connectionless segment")
        @Test
        void whenComposingSegmentForWestBoundedNorthWestIndex_returnConnectionlessSegment() {
            final String line1 = ".";
            final String line2 = "J";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for north-west index - leftToUp - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForNorthWestIndex_leftToDown_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = ".J";
            final List<String> input = List.of(line1, line2);

            final int index = 3;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }

        @DisplayName("when composing clockwise segment for north-west index - upToLeft - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForNorthWestIndex_upToLeft_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = ".J";
            final List<String> input = List.of(line1, line2);

            final int index = 3;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for north-west index - leftToUp - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForNorthWestIndex_leftToDown_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = ".J";
            final List<String> input = List.of(line1, line2);

            final int index = 3;
            final int firstIndex = 2;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for north-west index - upToLeft - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForNorthWestIndex_upToLeft_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = ".J";
            final List<String> input = List.of(line1, line2);

            final int index = 3;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(2);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }

        @DisplayName("when composing segment for bounded north-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForBoundedNorthEastIndex_returnConnectionlessSegment() {
            final String line = "L";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = -1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for north bounded north-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForNorthBoundedNorthEastIndex_returnConnectionlessSegment() {
            final String line = "L.";
            final List<String> input = List.of(line);

            final int index = 0;
            final int firstIndex = 1;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(1);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing segment for west bounded north-east index - return connectionless segment")
        @Test
        void whenComposingSegmentForWestBoundedNorthEastIndex_returnConnectionlessSegment() {
            final String line1 = ".";
            final String line2 = "L";
            final List<String> input = List.of(line1, line2);

            final int index = 1;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(-1);
            assertThat(segment.getStance()).isEqualTo(Stance.EMPTY_STANCE);
        }

        @DisplayName("when composing clockwise segment for north-east index - upToRight - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForNorthEastIndex_upToRight_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = "L.";
            final List<String> input = List.of(line1, line2);

            final int index = 2;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }

        @DisplayName("when composing clockwise segment for north-east index - rightToUp - return connected segment")
        @Test
        void whenComposingClockwiseSegmentForNorthEastIndex_rightToUp_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = "L.";
            final List<String> input = List.of(line1, line2);

            final int index = 2;
            final int firstIndex = 3;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for north-east index - upToRight - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForNorthEastIndex_upToRight_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = "L.";
            final List<String> input = List.of(line1, line2);

            final int index = 2;
            final int firstIndex = 0;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONCAVE);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONCAVE);
        }

        @DisplayName("when composing counter-clockwise segment for north-east index - rightToUp - return connected segment")
        @Test
        void whenComposingCounterClockwiseSegmentForNorthEastIndex_rightToUp_returnConnectedSegment() {
            final String line1 = "..";
            final String line2 = "L.";
            final List<String> input = List.of(line1, line2);

            final int index = 2;
            final int firstIndex = 3;

            final Environment environment = new Environment(input);
            final Segment segment = environment.getSegmentForIndex(index, firstIndex, Direction.COUNTER_CLOCKWISE);

            assertThat(segment.getConnectionIndexes()).hasSize(2);
            assertThat(segment.getConnectionIndexes()).contains(0);
            assertThat(segment.getConnectionIndexes()).contains(3);
            assertThat(segment.getStance().getLeftOrientation()).isEqualTo(Orientation.CONVEX);
            assertThat(segment.getStance().getRightOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getUpOrientation()).isEqualTo(Orientation.NONE);
            assertThat(segment.getStance().getDownOrientation()).isEqualTo(Orientation.CONVEX);
        }
    }

    @DisplayName("Extract ring")
    @Nested
    class ExtractRing {

        @DisplayName("test extract simple ring")
        @Test
        @Disabled("Needs internal stuff changed")
        void testExtractSimpleRing() {
            final String line1 = ".....";
            final String line2 = ".F-7.";
            final String line3 = ".|.|.";
            final String line4 = ".L-S.";
            final String line5 = ".....";
            final List<String> input = List.of(line1, line2, line3, line4, line5);

            final Environment environment = new Environment(input);

            final List<Integer> expectedRingIndexes = List.of(18, 17, 16, 11, 6, 7, 8, 13);
            final List<Integer> ringIndexes = environment.getRingIndexes();

            assertThat(ringIndexes).isEqualTo(expectedRingIndexes);
        }

        @Disabled("Needs internal stuff changed")
        @DisplayName("test extract indexes inside devious ring")
        @Test
        void testExtractIndexesInsideDeviousRing() {
            final String line1 = "..........";
            final String line2 = ".F------7.";
            final String line3 = ".|F----7|.";
            final String line4 = ".||....||.";
            final String line5 = ".||....||.";
            final String line6 = ".|L-7F-J|.";
            final String line7 = ".|..||..|.";
            final String line8 = ".L--JL--S.";
            final String line9 = "..........";
            final List<String> input = List.of(line1, line2, line3, line4, line5, line6, line7, line8, line9);

            final Environment environment = new Environment(input);
            final List<Segment> ringSegments = environment.getSegmentsForRing(Direction.CLOCKWISE);

            final Map<Integer, Segment> indexToSegment =
                    ringSegments.stream().collect(Collectors.toMap(Segment::getIndex, Function.identity()));

            // up to outside map
            assertThat(environment.indexRaysOrientationFrom(Connection.NORTH, 0, indexToSegment)).isEqualTo(Orientation.NONE);

            // up to outside map from bottom
            assertThat(environment.indexRaysOrientationFrom(Connection.NORTH, 89, indexToSegment)).isEqualTo(Orientation.NONE);

            // up to concave (start)
            assertThat(environment.indexRaysOrientationFrom(Connection.NORTH, 88, indexToSegment)).isEqualTo(Orientation.CONCAVE);

            // up to concave
            assertThat(environment.indexRaysOrientationFrom(Connection.NORTH, 86, indexToSegment)).isEqualTo(Orientation.CONCAVE);

            // up to convex
            assertThat(environment.indexRaysOrientationFrom(Connection.NORTH, 67, indexToSegment)).isEqualTo(Orientation.CONVEX);

            System.out.println(environment.getEnvironmentRepresentation());

            final List<Integer> indexesInsideRing = environment.getIndexesInsideRing();
            System.out.println(indexesInsideRing);
        }

        @DisplayName("Solve ring inside indexes problem")
        @Test
        void solveRingInsideIndexesProblem() {
            final List<String> lines = FileUtils.readLinesFromFile(Path.of("./src/test/resources/advent/ring/input"));
            final Environment environment = new Environment(lines);

            System.out.println(environment.getEnvironmentRepresentation());

            final List<Integer> indexesInsideRing = environment.getIndexesInsideRing();

            final long result = indexesInsideRing.size();
            System.out.println(result);
        }

    }

}
