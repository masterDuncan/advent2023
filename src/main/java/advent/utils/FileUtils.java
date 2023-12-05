package advent.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FileUtils {

    private FileUtils() {
    }

    public static List<String> readLinesFromFile(final Path filePath) {
        try (final Stream<String> lines = Files.lines(filePath)) {
            return lines.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
