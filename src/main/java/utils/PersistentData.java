package utils;

import java.util.*;

public class PersistentData {

    public static final int MAX_USER_VIOLATIONS = 5;

    public static final List<String> blacklistedWords = new ArrayList<>(Arrays.asList("fuck", "suck", "bad", "bitch", "pranked","yeet"));
    public static Map<String, Integer> userViolations = new HashMap<>();

}
