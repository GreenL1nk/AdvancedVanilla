package lib.utils;

import java.util.TreeMap;

public class RomanNumeralConverter {
    private final static TreeMap<Integer, String> romanMap = new TreeMap<>();

    static {
        romanMap.put(1000, "M");
        romanMap.put(900, "CM");
        romanMap.put(500, "D");
        romanMap.put(400, "CD");
        romanMap.put(100, "C");
        romanMap.put(90, "XC");
        romanMap.put(50, "L");
        romanMap.put(40, "XL");
        romanMap.put(10, "X");
        romanMap.put(9, "IX");
        romanMap.put(5, "V");
        romanMap.put(4, "IV");
        romanMap.put(1, "I");
    }

    public static String toRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be positive.");
        }
        int closestKey = romanMap.floorKey(number);
        if (number == closestKey) {
            return romanMap.get(number);
        }
        return romanMap.get(closestKey) + toRoman(number - closestKey);
    }
}
