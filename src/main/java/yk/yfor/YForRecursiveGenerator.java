package yk.yfor;

import java.util.function.Function;

/**
 * 13.12.2024
 */
public class YForRecursiveGenerator<T> implements YFor<T> {
    private boolean started = false;
    private T cur;
    private final Function<T, T> next;

    public YForRecursiveGenerator(T initial, Function<T, T> next) {
        this.cur = initial;
        this.next = next;
    }

    @Override
    public void next(YForResult result) {
        if (started) cur = next.apply(cur);
        else started = true;
        result.setPresent(cur);
    }
}
