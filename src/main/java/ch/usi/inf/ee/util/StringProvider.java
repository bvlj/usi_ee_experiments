package ch.usi.inf.ee.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public final class StringProvider {
    private static final int MIN_CHAR = 48; // '0'
    private static final int MAX_CHAR = 122; // 'z'

    private StringProvider() {
    }

    public static Map<Integer, String[]> getStringsMap(int strSize, int ... arraySizes) {
        final Map<Integer, String[]> map = new HashMap<>();
        for (final int arraySize : arraySizes) {
            map.put(arraySize, getStringArray(strSize, arraySize));
        }
        return map;
    }

    private static String getRandomString(int length) {
        final Random random = new Random();
        return random.ints(MIN_CHAR, MAX_CHAR)
                .limit(length)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    private static String[] getStringArray(int strLength, int arrLength) {
        final String[] array = new String[arrLength];
        for (int i = 0; i < arrLength; i++) {
            array[i] = getRandomString(strLength);
        }
        return array;
    }
}
