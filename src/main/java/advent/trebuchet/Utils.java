package advent.trebuchet;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Set;

public class Utils {

    public static final Set<String> STRING_DIGITS = Sets.newHashSet();
    public static final Map<String, Pair<Integer, Integer>> STRING_DIGIT_VALUES = Maps.newLinkedHashMap();
    public static final Map<Integer, Integer> STRING_DIGIT_LENGTH = Maps.newHashMap();
    public static int LONGEST_STRING_DIGIT_SIZE = 9;

    static {
        STRING_DIGITS.add("one");
        STRING_DIGITS.add("two");
        STRING_DIGITS.add("three");
        STRING_DIGITS.add("four");
        STRING_DIGITS.add("five");
        STRING_DIGITS.add("six");
        STRING_DIGITS.add("seven");
        STRING_DIGITS.add("eight");
        STRING_DIGITS.add("nine");
        STRING_DIGITS.add("oneight");
        STRING_DIGITS.add("twone");
        STRING_DIGITS.add("eightwo");
        STRING_DIGITS.add("threeight");
        STRING_DIGITS.add("eighthree");
        STRING_DIGITS.add("fiveight");
        STRING_DIGITS.add("sevenine");
        STRING_DIGITS.add("nineight");

        STRING_DIGIT_VALUES.put("oneight", Pair.of(1, 8));
        STRING_DIGIT_VALUES.put("twone", Pair.of(2, 1));
        STRING_DIGIT_VALUES.put("eightwo", Pair.of(8, 2));
        STRING_DIGIT_VALUES.put("threeight", Pair.of(3, 8));
        STRING_DIGIT_VALUES.put("eighthree", Pair.of(8, 3));
        STRING_DIGIT_VALUES.put("fiveight", Pair.of(5, 8));
        STRING_DIGIT_VALUES.put("sevenine", Pair.of(7, 9));
        STRING_DIGIT_VALUES.put("nineight", Pair.of(9, 8));
        STRING_DIGIT_VALUES.put("one", Pair.of(1, 1));
        STRING_DIGIT_VALUES.put("two", Pair.of(2, 2));
        STRING_DIGIT_VALUES.put("three", Pair.of(3, 3));
        STRING_DIGIT_VALUES.put("four", Pair.of(4, 4));
        STRING_DIGIT_VALUES.put("five", Pair.of(5, 5));
        STRING_DIGIT_VALUES.put("six", Pair.of(6, 6));
        STRING_DIGIT_VALUES.put("seven", Pair.of(7, 7));
        STRING_DIGIT_VALUES.put("eight", Pair.of(8, 8));
        STRING_DIGIT_VALUES.put("nine", Pair.of(9, 9));

        STRING_DIGIT_LENGTH.put(1, 3); // one (3)
        STRING_DIGIT_LENGTH.put(2, 3); // two (3)
        STRING_DIGIT_LENGTH.put(3, 5); // three (5)
        STRING_DIGIT_LENGTH.put(4, 4); // four (4)
        STRING_DIGIT_LENGTH.put(5, 4); // five (4)
        STRING_DIGIT_LENGTH.put(6, 3); // six (3)
        STRING_DIGIT_LENGTH.put(7, 5); // seven (5)
        STRING_DIGIT_LENGTH.put(8, 5); // eight (5)
        STRING_DIGIT_LENGTH.put(9, 4); // nine (4)
    }

    private Utils() {
    }

}
