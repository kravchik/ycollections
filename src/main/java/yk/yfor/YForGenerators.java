package yk.yfor;

import java.util.Collection;

/**
 * 12.12.2024
 */
public class YForGenerators {
    public static YForIntRange yfor(int to) {
        return new YForIntRange(0, to);
    }
    public static YForIntRange yfor(int from, int to) {
        return new YForIntRange(from, to);
    }
    public static YForIntRange yfor(int from, int to, int inc) {
        return new YForIntRange(from, to, inc);
    }

    public static <T> YForCollection<T> yfor(Collection<T> c) {
        return new YForCollection<>(c);
    }
}
