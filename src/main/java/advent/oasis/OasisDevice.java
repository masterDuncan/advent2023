package advent.oasis;

public class OasisDevice {

    private OasisDevice() {
    }

    public static int predictPreviousValue(final int[] sensorHistory) {
        final int initialFirstValue = sensorHistory[0];
        final int previousDifference = getPreviousDifference(sensorHistory);
        System.out.println(initialFirstValue - previousDifference);
        return initialFirstValue - previousDifference;
    }

    private static int getPreviousDifference(final int[] previousLine) {
        final int[] nextDifferenceLine = getNextDifferenceLine(previousLine);

        if (isAllZeros(nextDifferenceLine)) {
            return 0;
        }

        final int currentValue = nextDifferenceLine[0];
        final int previousValue = getPreviousDifference(nextDifferenceLine);

        return currentValue - previousValue;
    }

    public static int predictNextValue(final int[] sensorHistory) {
        final int initialLastValue = sensorHistory[sensorHistory.length - 1];
        return initialLastValue + getNextDifference(sensorHistory);
    }

    private static int getNextDifference(final int[] previousLine) {
        final int[] nextDifferenceLine = getNextDifferenceLine(previousLine);

        if (isAllZeros(nextDifferenceLine)) {
            return 0;
        }

        final int currentValue = nextDifferenceLine[nextDifferenceLine.length - 1];
        final int nextValue = getNextDifference(nextDifferenceLine);

        return currentValue + nextValue;
    }

    private static int[] getNextDifferenceLine(final int[] previousLine) {
        final int[] nextDifferenceLine = new int[previousLine.length - 1];

        final int maxOperations = previousLine.length - 1;
        for (int operation = 1; operation <= maxOperations; operation++) {
            final int firstReading = previousLine[operation - 1];
            final int secondReading = previousLine[operation];
            final int difference = secondReading - firstReading;

            nextDifferenceLine[operation - 1] = difference;
        }

        return nextDifferenceLine;
    }

    private static boolean isAllZeros(final int[] line) {
        for (int i = 0; i <= line.length - 1; i++) {
            if (line[i] != 0) {
                return false;
            }
        }

        return true;
    }
}
